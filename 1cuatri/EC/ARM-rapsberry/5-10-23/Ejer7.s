.include "inter.inc"

.text
	mov r0, #0b11010011
	msr cpsr_c, r0
	mov sp, #0x08000000 	@ Init stack in SVC mod
	
	ldr r6, =GPBASE
	// botones
/* guia bits  xx999888777666555444333222111000 */
	// seteo el map de los botones
	mov r2, #0b00000000000000000000000000000100
	mov r3, #0b00000000000000000000000000001000
	
	// pongo altavoz de output y botones de input
/* guia bits   xx999888777666555444333222111000 */
  	ldr r4, =0b00000000000000000001000000000000
	str r4, [r6, #GPFSEL0]		
/* guia bits      10987654321098765432109876543210 */
	// seteo mapeo altavoz
  	mov r4, #0b00000000000000000000000000010000	// GPIO4
	
	ldr r0, =STBASE
	
	
	

bucle:
	ldr r5, [r6, #GPLEV0]
	tst r5, r2
	beq suenado
	tst r5, r3
	beq suenasol
	b bucle

/* guia bits 	   10987654321098765432109876543210 */
 suenado:
	ldr r1, =1908		// tiempo de espera DO
	bl espera
	str r4, [r6, #GPSET0]				// enciende gpio4
	bl espera
	str r4, [r6, #GPCLR0]				// apaga gpio4
	b bucle

suenasol:
	ldr r1, =1278		// tiempo espera SOL
	bl espera
	str r4, [r6, #GPSET0]				// enciende gpio4
	bl espera
	str r4, [r6, #GPCLR0]				// apaga gpio4
	b bucle

espera:	
	push  {r4, r5}	         	// Save r4 and r5 in the stack
	ldr   r4, [r0, #STCLO]		 // Load CLO timer
	add   r4, r1		 			// Add waiting time -> this is our ending time
ret1:	
	ldr   r5, [r0, #STCLO]	 	@ Enter waiting loop: load current CLO timer
	cmp   r5, r4		 			@ Compare current time with ending time
	blo   ret1		 				@ If lower, go back to read timer again
	pop   {r4, r5}		 		@ Restore r4 and r5
	bx    lr		 					@ Return from routine