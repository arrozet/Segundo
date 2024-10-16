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
	
	ldr r0, =GPBASE
	// gpio22 y gpio27 (verdes)
	ldr r1, =0b00000000001000000000000001000000
	str r1, [r0, #GPFSEL2]
	mov r1, #0b00001000010000000000000000000000
	str r1, [r0, #GPSET0]		// encender los verdes
	// gpio9 (rojo)
	ldr r1, =0b00001000000000000000000000000000
	str r1, [r0, #GPFSEL0]
	// gpio10 (rojo)
	ldr r1, =0b00000000000000000000000000000001
	str r1, [r0, #GPFSEL1]

	// los inputs no hay que ponerlos porque son 000	

	// ponemos falling edge interruptions por GPIO2 y GPIO3
	mov r1, #0b00000000000000000000000000001100
	str r1, [r0, #GPFEN0]
	// permitir interrupciones de cualquier GPIO
	ldr r0, =INTBASE
	mov r1, #0b00000000000100000000000000000000	// por qué hay un 1 en mitad?
	str r1, [r0, #INTENIRQ2]
	
	// Poner modo SVC con IRQ activado
	mov r0, #0b01010011 @ SVC mode, IRQ enabled
	msr cpsr_c, r0

buc: 
	b buc

irq_handler:
	push {r0,r1,r2}
	ldr r0, =GPBASE

	// se ha presionado GPIO2?
	ldr r2, [r0, #GPEDS0]
	ands r2, #0b00000000000000000000000000000100
	// si la and no da 0, gpio2 se presiono y se enciende gpio9 y apaga gpio10
	movne r1, #0b00000000000000000000001000000000
	strne r1, [r0, #GPSET0]
	movne r1, #0b00000000000000000000010000000000
	strne r1, [r0, #GPCLR0]
	
	
	// se presiono GPIO3?
	ldr r2, [r0, #GPEDS0]	// hace falta porque el ands modifica r2
	ands r2, #0b00000000000000000000000000001000
	// si la and no da 0, gpio3 se presiono y se enciende gpio10 y apaga gpio9
	movne r1, #0b00000000000000000000010000000000
	strne r1, [r0, #GPSET0]
	movne r1, #0b00000000000000000000001000000000
	strne r1, [r0, #GPCLR0]

	// reseteamos el evento
	movne r1, #0b00000000000000000000000000001100
	strne r1, [r0, #GPEDS0]

	pop {r0,r1,r2}
	subs pc, lr, #4