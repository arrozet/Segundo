	.include "inter.inc"
.text
	mov r0, #0
	ADDEXC 0x18, irq_handler
	// Inicio la pila en modo IRQ
	mov r0, #0b11010010 
	msr cpsr_c, r0
	mov sp, #0x8000
	// Inicio la pila en modo SVC
	mov r0, #0b11010011
	msr cpsr_c, r0
	mov sp, #0x8000000
	
	// GPIO22 como output
	ldr r0, =GPBASE
	ldr r1, =0b00000000000000000000000001000000
	str r1, [r0, #GPFSEL2]
	// Cargo CLO, a�ado 3 segundos y guardo en C3
	ldr r0, =STBASE
	ldr r1, [r0, #STCLO]
	add r1, #0x050000
	//lsr r1, #1	// para que sea medio segundo
	str r1, [r0, #STC1]
	// Habilitar la interrupcion en C1
	ldr r0, =INTBASE
	mov r1, #0b0010
	str r1, [r0, #INTENIRQ1]
	
	// ???
	mov r0, #0b01010011 @ SVC mode, IRQ enabled
	msr cpsr_c, r0
buc: 
	b buc
irq_handler:
	push {r0, r1,r2}
	
	// encender led verde - gpio22
	ldr r0, =GPBASE
	
	ldr r1, =onoff
	ldr r2, [r1]
	eors r2, #1
	str r2, [r1]
	// Endender o apagar led
	mov r1, #0b00000000010000000000000000000000
	str r1, [r0, #GPSET0]	// este sobra no?
	strne r1, [r0, #GPSET0]
	streq r1, [r0, #GPCLR0]
	
	// Vaciar timer
	ldr r0, =STBASE
	mov r1, #0b0010
	str r1, [r0, #STCS]
	
	// Timer -> 0,5s
	ldr r1, [r0, #STCLO]
	add r1, #0x050000
	
	str r1, [r0, #STC1]

	pop {r0, r1,r2}
	subs pc, lr, #4
	
onoff: .word 0
	