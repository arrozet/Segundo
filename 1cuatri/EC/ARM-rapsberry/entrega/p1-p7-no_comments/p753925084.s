.include "inter.inc"

.text
	mov r0, #0b11010011
	msr cpsr_c, r0
	mov sp, #0x08000000 	
	
	ldr r6, =GPBASE
	
	
	mov r2, #0b00000000000000000000000000000100
	mov r3, #0b00000000000000000000000000001000
	
	

  	ldr r4, =0b00000000000000000001000000000000
	str r4, [r6, #GPFSEL0]		

	
  	mov r4, #0b00000000000000000000000000010000	
	
	ldr r0, =STBASE
	
	
	

bucle:
	ldr r5, [r6, #GPLEV0]
	tst r5, r2
	beq suenado
	tst r5, r3
	beq suenasol
	b bucle


 suenado:
	ldr r1, =1908		
	bl espera
	str r4, [r6, #GPSET0]				
	bl espera
	str r4, [r6, #GPCLR0]				
	b bucle

suenasol:
	ldr r1, =1278		
	bl espera
	str r4, [r6, #GPSET0]				
	bl espera
	str r4, [r6, #GPCLR0]				
	b bucle

espera:	
	push  {r4, r5}	         	
	ldr   r4, [r0, #STCLO]		 
	add   r4, r1		 			
ret1:	
	ldr   r5, [r0, #STCLO]	 	
	cmp   r5, r4		 			
	blo   ret1		 				
	pop   {r4, r5}		 		
	bx    lr		 					