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
	
	// PONGO OUTPUTS
	ldr r0, =GPBASE
	// gpio22 y gpio27 (verdes)
	ldr r1, =0b00000000001000000000000001000000
	str r1, [r0, #GPFSEL2]
	// gpio9 (rojo) y gpio4 (altavoz)
	/*        xx999888777666555444333222111000 */
	ldr r1, =0b00001000000000000001000000000000
	str r1, [r0, #GPFSEL0]
	// gpio10 (rojo), gpio11 y gpio17 (amarillo)
	ldr r1, =0b00000000001000000000000000001001
	str r1, [r0, #GPFSEL1]

	// Cargo CLO, anado 200ms y guardo en C3
	ldr r0, =STBASE
	ldr r1, [r0, #STCLO]
	add r1, #0x030000	// aprox 196ms
	str r1, [r0, #STC1]
	str r1, [r0, #STC3]
	// Habilitar la interrupcion en C1 y C3
	ldr r0, =INTBASE
	mov r1, #0b1010		// cambiando esto puedo probar independienteme leds y altavoz
	str r1, [r0, #INTENIRQ1]
	
	// ???
	mov r0, #0b01010011 @ SVC mode, IRQ enabled
	msr cpsr_c, r0

	// Cargo contador para LEDs
	mov r4, #1
buc: 
	b buc

irq_handler:	// aqui debo decidir si la interrupcion que entra es C1 o C3. 
		// Si es C1 -> leds, si es C3 -> altavoz
	push {r0, r1,r2,r3}
	
	// Cargo lo que hay en STCS. Si es 0010 -> C1 -> LEDs, si es 1000 -> C3 -> altavoz
	
	/*ldr r0, =STBASE
	ldr r1, [r0, #STCS]
	cmp r1, #0b0010		// interrupcion es C1?
	
	ldr r0, =GPBASE		// antes de saltar, meto a r0 -> GPBASE para que no haya problemas
	beq leds		// si es C1, salto a lo que hace la interrupcion C1
	bne altavoz		// si no es C1, es que es C3
	*/

// C1
leds:	
	// Endender o apagar led
	
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
	str r3, [r0, #GPSET0]	// enciendo el que deberia estar encendido
	eor r1, r1, r3		// escojo todos los que NO deberian estar encendidos
	str r1, [r0, #GPCLR0]	// y los apago

	// modificar contador
	add r4, #1
	cmp r4, #7
	subeq r4, #6

	// Resetear interrupcion C1
	ldr r0, =STBASE
	mov r1, #0b0010
	str r1, [r0, #STCS]
	
	// Timer C1 -> 200ms
	ldr r1, [r0, #STCLO]
	add r1, #0x030000
	
	str r1, [r0, #STC1]

	b fin

// C3
altavoz:
	// cargo el altavoz (para poder apagarlo y encenderlo)
	ldr r2,  =0b00000000000000000000000000010000
	
	ldr r1, =onoff
	ldr r3, [r1]			// cargo contenido onoff
	eors r3, #1			// cambio onoff y actualizo flags
	streq r2, [r0, #GPCLR0]		// si esta encendido, apago el altavoz
	strne r2, [r0, #GPSET0]		// si esta apagado, enciendo el altavoz
	str r3, [r1]			// guardo el valor de onoff
	
	// Resetear interrupcion C3
	ldr r0, =STBASE
	mov r1, #0b1000
	str r1, [r0, #STCS]
	
	
	// Timer C3 -> freq nota 440hz
	ldr r1, =1336
	ldr r3, [r0, #STCLO]
	add r3, r1
	
	str r3, [r0, #STC3]

	// aqui no pongo b fin porque pasa a fin sin ejecutar nada

fin:
	pop {r0, r1,r2,r3}
	subs pc, lr, #4
	
onoff: .word 0	// esta variable sirve para poder controlar el apagado y encendido del altavoz
	