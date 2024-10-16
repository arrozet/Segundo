	.include "inter.inc"
.text
	mov r0, #0
	ADDEXC 0x18, irq_handler
	ADDEXC 0x1c, fiq_handler
	
	
	mov r0, #0b11010001
	msr cpsr_c, r0
	mov sp, #0x4000
	
	mov r0, #0b11010010 
	msr cpsr_c, r0
	mov sp, #0x8000
	
	mov r0, #0b11010011
	msr cpsr_c, r0
	mov sp, #0x8000000
	
	
	ldr r0, =GPBASE
	
	ldr r1, =0b00000000001000000000000001000000
	str r1, [r0, #GPFSEL2]
	
	ldr r1, =0b00001000000000000001000000000000
	str r1, [r0, #GPFSEL0]
	
	ldr r1, =0b00000000001000000000000000001001
	str r1, [r0, #GPFSEL1]

	
	ldr r0, =STBASE
	ldr r1, [r0, #STCLO]
	add r1, #0x079000	
	str r1, [r0, #STC1]
	str r1, [r0, #STC3]
	
	ldr r0, =INTBASE
	mov r1, #0b0010		
	str r1, [r0, #INTENIRQ1]
	mov r1, #0b10000011		
	str r1, [r0, #INTFIQCON]
	
	
	mov r0, #0b00010011 @ SVC mode, IRQ and FIQ enabled
	msr cpsr_c, r0

	
	mov r4, #1
	
	mov r7, #1
buc: 
	b buc
	

irq_handler:	 
		
	push {r0, r1,r2,r3}
	
	ldr r0, =GPBASE		
	b leds	
	

fiq_handler:
	push {r0, r1,r2,r3}

	cmp r7, #1
	ldreq r5,   =re
	ldreq r5, [r5]

	cmp r7, #2
	ldreq r5,   =re
	ldreq r5, [r5]
	
	cmp r7, #3
	ldreq r5,   =mi
	ldreq r5, [r5]
	
	cmp r7, #4
	ldreq r5,   =re
	ldreq r5, [r5]
	
	cmp r7, #5
	ldreq r5,   =sol
	ldreq r5, [r5]
	
	cmp r7, #6
	ldreq r5,   =fa_hash
	ldreq r5, [r5]
	
	cmp r7, #7
	ldreq r5,   =re
	ldreq r5, [r5]
	
	cmp r7, #8
	ldreq r5,   =re
	ldreq r5, [r5]
	
	cmp r7, #9
	ldreq r5,   =mi
	ldreq r5, [r5]
	
	cmp r7, #10
	ldreq r5,   =re
	ldreq r5, [r5]
	
	cmp r7, #11
	ldreq r5,   =la
	ldreq r5, [r5]
	
	cmp r7, #12
	ldreq r5,   =sol
	ldreq r5, [r5]
	
	cmp r7, #13
	ldreq r5,   =re
	ldreq r5, [r5]
	
	cmp r7, #14
	ldreq r5,   =re
	ldreq r5, [r5]
	
	cmp r7, #15
	ldreq r5,   =re_coma
	ldreq r5, [r5]
	
	cmp r7, #16
	ldreq r5,   =si
	ldreq r5, [r5]
	
	cmp r7, #17
	ldreq r5,   =sol
	ldreq r5, [r5]
	
	cmp r7, #18
	ldreq r5,   =fa_hash
	ldreq r5, [r5]
	
	cmp r7, #19
	ldreq r5,   =mi
	ldreq r5, [r5]
	
	cmp r7, #20
	ldreq r5,   =do_coma
	ldreq r5, [r5]
	
	cmp r7, #21
	ldreq r5,   =do_coma
	ldreq r5, [r5]
	
	cmp r7, #22
	ldreq r5,   =si
	ldreq r5, [r5]
	
	cmp r7, #23
	ldreq r5,   =sol
	ldreq r5, [r5]
	
	cmp r7, #24
	ldreq r5,   =la
	ldreq r5, [r5]
	
	cmp r7, #25
	ldreq r5,   =sol
	ldreq r5, [r5]
	
	
	
	ldr r0, =GPBASE
	b altavoz
	


leds:	
	
	
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
	
	
	add r7, #1
	cmp r7, #26
	subeq r7, #25

	
	ldr r0, =STBASE
	mov r1, #0b0010
	str r1, [r0, #STCS]
	
	
	ldr r1, [r0, #STCLO]
	add r1, #0x079000	
	
	str r1, [r0, #STC1]

	b fin


altavoz:
	
	ldr r2,  =0b00000000000000000000000000010000
	
	
	ldr r1, =onoff
	ldr r3, [r1]			
	eors r3, #1			
	streq r2, [r0, #GPCLR0]		
	strne r2, [r0, #GPSET0]		
	str r3, [r1]			
	
	
	ldr r0, =STBASE
	mov r1, #0b1000
	str r1, [r0, #STCS]
	
	
	
	
	ldr r3, [r0, #STCLO]
	add r3, r5
	
	str r3, [r0, #STC3]

	

fin:
	pop {r0, r1,r2,r3}
	subs pc, lr, #4
	
onoff: .word 0	


la:			.word 1136
si:			.word 1012
mi: 			.word 1515
sol: 			.word 1275
re: 			.word 1706
fa_hash:	.word 1351
re_coma:	.word 851
do_coma: 	.word 956
	