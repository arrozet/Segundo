.set GPBASE, 0x3F200000
.set GPFSEL0, 0x00
.set GPFSEL2, 0x08

.set GPSET0, 0x1c
.set GPCLR0, 0x28

.set STBASE,  0x3F003000
.set STCLO,  0x04

.text
	mov r0, #0b11010011
	msr cpsr_c, r0
	mov sp, #0x08000000 	
	
	ldr r4, =GPBASE

	mov r2, #0b00000000000000000001000000000000	
	str r2, [r4, #GPFSEL0]			

	
	ldr r0, =STBASE
	ldr r1, =1706		
	mov r2, #0b00000000000000000000000000010000
	
	
	bucle:
		str r2, [r4, #GPSET0]
		bl espera
		str r2, [r4, #GPCLR0]
		bl espera
		b bucle
		
		
espera:	
	push {r4,r5}
 	ldr   r4, [r0, #STCLO]		
	add   r4, r1		 			
ret1:	
	ldr   r5, [r0, #STCLO]	 	
	cmp   r5, r4		 			
	blo   ret1		 				
	pop   {r4, r5}		 		
	bx    lr		 					
