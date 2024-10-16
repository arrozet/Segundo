	.include "inter.inc"
.text
	mov r0, #0
	ADDEXC 0x18, irq_handler
	
	mov r0, #0b11010010 
	msr cpsr_c, r0
	mov sp, #0x8000
	
	mov r0, #0b11010011
	msr cpsr_c, r0
	mov sp, #0x8000000
	
	
	ldr r0, =GPBASE
	
	ldr r1, =0b00000000001000000000000001000000
	str r1, [r0, #GPFSEL2]
	
	ldr r1, =0b00001000000000000000000000000000
	str r1, [r0, #GPFSEL0]
	
	ldr r1, =0b00000000001000000000000000001001
	str r1, [r0, #GPFSEL1]
	
	ldr r0, =STBASE
	ldr r1, [r0, #STCLO]
	add r1, #0x100000
	
	str r1, [r0, #STC1]
	
	ldr r0, =INTBASE
	mov r1, #0b0010
	str r1, [r0, #INTENIRQ1]
	
	
	mov r0, #0b01010011 @ SVC mode, IRQ enabled
	msr cpsr_c, r0

	
	mov r4, #1
buc: 
	b buc
irq_handler:
	push {r0, r1,r2,r3}
	
	ldr r0, =GPBASE
	
	
	cmp r4, #1
	ldreq r3,   =0b00000000000000000000001000000000

	cmp r4, #2
	ldreq r3,   =0b00000000000000000000010000000000

	cmp r4, #3
	ldreq r3,   =0b00000000000000000000100000000000

	cmp r4, #4
	ldreq r3,   =0b00000000000000100000000000000000

	cmp r4, #5
	ldreq r3,   =0b00000000010000000000000000000000

	cmp r4, #6
	ldreq r3,   =0b00001000000000000000000000000000
	
	ldr r1,   =0b00001000010000100000111000000000
	str r3, [r0, #GPSET0]	
	eor r1, r1, r3		
	str r1, [r0, #GPCLR0]	

	
	add r4, #1
	cmp r4, #7
	subeq r4, #6

	
	ldr r0, =STBASE
	mov r1, #0b0010
	str r1, [r0, #STCS]
	
	
	ldr r1, [r0, #STCLO]
	add r1, #0x100000
	
	str r1, [r0, #STC1]

	pop {r0, r1,r2,r3}
	subs pc, lr, #4
	