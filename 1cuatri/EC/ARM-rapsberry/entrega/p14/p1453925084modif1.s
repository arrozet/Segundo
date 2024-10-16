	.include "inter.inc"
.text
	mov r0, #0
	ADDEXC 0x18, irq_handler
	ADDEXC 0x1c, fiq_handler
	
	// Inicio la pila en modo FIQ
	mov r0, #0b11010001
	msr cpsr_c, r0
	mov sp, #0x4000
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
	
	mov r1, #0b00000000000000000000000000001100
	str r1, [r0, #GPFEN0]
	
	// Cargo CLO, anado 200ms y guardo en C3
	ldr r0, =STBASE
	ldr r1, [r0, #STCLO]
	add r1, #0x079000	// aprox 495ms
	str r1, [r0, #STC1]
	str r1, [r0, #STC3]
	// Habilitar la interrupcion en C1 (IRQ) y C3 (FIQ)
	ldr r0, =INTBASE
	mov r1, #0b0010		// cambiando esto puedo probar independienteme leds y altavoz
	str r1, [r0, #INTENIRQ1]
	mov r1, #0b10000011		// cuidado, con C3 y C1 son valores distintos
	str r1, [r0, #INTFIQCON]
	
	
	
	// ???
	mov r0, #0b00010011 @ SVC mode, IRQ and FIQ enabled
	msr cpsr_c, r0

	// Cargo contador para LEDs
	mov r4, #1
	// cargo contador para altavoz
	mov r7, #1
buc:
	
	b buc
	
// C1
irq_handler:	// aqui debo decidir si la interrupcion que entra es C1 o C3. 
		// Si es C1 -> leds, si es C3 -> altavoz
	push {r0, r1,r2,r3}
	
	// Cargo lo que hay en STCS. Si es 0010 -> C1 -> LEDs, si es 1000 -> C3 -> altavoz
	ldr r0, =GPBASE
	// si se pulsó el 2
	ldr r2, [r0, #GPEDS0]
	ands r2, #0b00000000000000000000000000000100
	ldrne r3, =botonaltavoz
	ldrne r0, [r3]
	eorne r0, #1
	strne r0, [r3]
	ldrne r0, =GPBASE
	movne r1, #0b00000000000000000000000000000100
	strne r1, [r0, #GPEDS0]
	
	ldr r0, =GPBASE
	ldr r2, [r0, #GPEDS0]
	ands r2, #0b00000000000000000000000000001000
	ldrne r3, =botonleds
	ldrne r0, [r3]
	eorne r0, #1
	strne r0, [r3]
	ldrne r0, =GPBASE
	movne r1, #0b00000000000000000000000000001000
	strne r1, [r0, #GPEDS0]
	
	/*
	mov r1, #0b00000000000000000000000000001100
	str r1, [r0, #GPEDS0]
	*/
	/* Esto no hay que mirarlo pq si es irq entra en irqhandler, y si es fiq entra en fiq handler
	ldr r0, =STBASE
	ldr r1, [r0, #STCS]
	cmp r1, #0b0010		// interrupcion es C1?
	*/
	
	ldr r0, =GPBASE		// antes de saltar, meto a r0 -> GPBASE para que no haya problemas
	b leds		// si es C1, salto a lo que hace la interrupcion C1
	//bne altavoz		// si no es C1, es que es C3
	
// C3
fiq_handler:
	push {r0, r1,r2,r3}
	
	
	// en r5 tengo que meter la nota, pues es lo que se suma a C3
	
	// Elijo que nota tocar
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
	
	ldr r0, =botonleds
	ldr r0, [r0]
	cmp r0, #0
	beq ledsnormal
	bne ledsinvertido
	
	// modificar contador
	ledsnormal:
		add r4, #1
		cmp r4, #7
		subeq r4, #6
		b sigue
	
	ledsinvertido:
		sub r4, #1
		cmp r4, #0
		addeq r4, #6
	
	sigue:
	ldr r0, =botonaltavoz
	ldr r0, [r0]
	cmp r0, #0
	beq normal
	bne invertido
	
	// modificar contador sonido (para que no se solapen las notas)
	normal:	
		add r7, #1
		cmp r7, #26
		subeq r7, #25
		b continuar

	invertido:
		sub r7, #1
		cmp r7, #1
		addeq r7, #25

	// Resetear interrupcion C1
	continuar:
		ldr r0, =STBASE
		mov r1, #0b0010
		str r1, [r0, #STCS]
		
		// Timer C1 -> 500ms
		ldr r1, [r0, #STCLO]
		add r1, #0x079000	// aprox 495ms
		
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
	
	
	// Timer C3 -> freq nota
	//ldr r1, =1336
	ldr r3, [r0, #STCLO]
	add r3, r5
	
	str r3, [r0, #STC3]

	// aqui no pongo b fin porque pasa a fin sin ejecutar nada

fin:
	pop {r0, r1,r2,r3}
	subs pc, lr, #4
	
onoff: .word 0	// esta variable sirve para poder controlar el apagado y encendido del altavoz
botonaltavoz: .word 0
botonleds: .word 0

// declaro las notas
la:			.word 1136
si:			.word 1012
mi: 			.word 1515
sol: 			.word 1275
re: 			.word 1706
fa_hash:	.word 1351
re_coma:	.word 851
do_coma: 	.word 956
	