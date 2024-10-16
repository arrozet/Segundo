.set GPBASE, 0x3F200000	// direccion de la memoria gpio
.set GPFSEL2, 0x08	// direccion de GPIO Function Select Registers (a añadir a GPBASE) donde se encuentra GPIO22 (primer led verde)

.set GPSET0, 0x1c	// direccion seteo de pines 0 a 31 (GPISET1, que va de 32 a 53, no lo usaremos) - (a añadir a GPBASE)
.set GPCLR0, 0x28
.set STBASE,  0x3F003000
.set STCLO,  0x04

.text
	mov r0, #0b11010011
	msr cpsr_c, r0
	mov sp, #0x08000000 	@ Init stack in SVC mod
	
	ldr r4, =GPBASE
/* guia bits  xx999888777666555444333222111000 */
	mov r2, #0b00000000000000000000000001000000	// 22-20=2, pero
	str r2, [r4, #GPFSEL2]			// en la dir de memoria GPBASE + 0x08
/* guia bits         10987654321098765432109876543210 */
	
	ldr r1, =100000		// tiempo de espera
	ldr r0, =STBASE
	
	bucle:
		
		mov r2, #0b00000000010000000000000000000000		// gpio 22, primer led verde
		str r2, [r4, #GPSET0]
		bl espera
		str r2, [r4, #GPCLR0]
		bl espera
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
