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
	// Cargo CLO, añado 3 segundos y guardo en C3
	ldr r0, =STBASE
	ldr r1, [r0, #STCLO]
	add r1, #0x300000 @ 4.19 seconds // 0x400000 en hexadecimal es igual a 4194304 en decimal
	str r1, [r0, #STC3]
	// Habilitar la interrupcion en C3
	ldr r0, =INTBASE
	mov r1, #0b1000
	str r1, [r0, #INTENIRQ1]
	// ???
	mov r0, #0b01010011 @ SVC mode, IRQ enabled
	msr cpsr_c, r0
buc: 
	b buc
irq_handler:
	push {r0, r1}
	// encender led verde - gpio22
	ldr r0, =GPBASE
	mov r1, #0b00000000010000000000000000000000
	str r1, [r0, #GPSET0]
	pop {r0, r1}
	subs pc, lr, #4