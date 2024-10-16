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
	// gpio22 y gpio27 (verdes)
	ldr r1, =0b00000000001000000000000001000000
	str r1, [r0, #GPFSEL2]
	// gpio9 (rojo)
	ldr r1, =0b00001000000000000000000000000000
	str r1, [r0, #GPFSEL0]
	// gpio10 (rojo), gpio11 y gpio17 (amarillo)
	ldr r1, =0b00000000001000000000000000001001
	str r1, [r0, #GPFSEL1]
	// Cargo CLO, a�ado 3 segundos y guardo en C3
	ldr r0, =STBASE
	ldr r1, [r0, #STCLO]
	add r1, #0x100000
	//lsr r1, #1	// para que sea medio segundo
	str r1, [r0, #STC1]
	// Habilitar la interrupcion en C1
	ldr r0, =INTBASE
	mov r1, #0b0010
	str r1, [r0, #INTENIRQ1]
	
	// ???
	mov r0, #0b01010011 @ SVC mode, IRQ enabled
	msr cpsr_c, r0

	// Cargo contador
	ldr r4, #1
buc: 
	b buc
irq_handler:
	push {r0, r1,r2,r3}
	
	ldr r0, =GPBASE
	
	ldr r1, =onoff
	ldr r2, [r1]
	eors r2, #1
	str r2, [r1]
 
	// Endender o apagar led
	//ldr r1,   =0b00001000010000100000111000000000
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
	
	/*// pongo el comprobante de encendido o apagado aqui para que las flags funcionen
	ldr r1, =onoff
	ldr r2, [r1]
	eors r2, #1
	str r2, [r1]*/
	
	ldr r1,   =0b00001000010000100000111000000000
	str r3, [r0, #GPSET0]	// enciendo el que debería estar encendido
	eor r1, r1, r3		// escojo todos los que NO deberían estar encendidos
	str r1, [r0, #GPCLR0]	// y los apago

	// modificar contador
	add r4, #1
	cmp r4, #7
	subeq r4, #6

	// Vaciar timer
	ldr r0, =STBASE
	mov r1, #0b0010
	str r1, [r0, #STCS]
	
	// Timer -> 1s
	ldr r1, [r0, #STCLO]
	add r1, #0x100000
	
	str r1, [r0, #STC1]

	pop {r0, r1,r2,r3}
	subs pc, lr, #4
	
//onoff: .word 0
	