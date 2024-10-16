.set GPBASE, 0x3F200000
.set GPFSEL0, 0x00
.set GPFSEL2, 0x08
.set GPSET0, 0x1c
.set GPLEV0, 0x34
.set GPCLR0, 0x28
.text
	ldr r0, =GPBASE
	

	mov r1, #0b00000000000000000000000000000000
	str r1, [r0, #GPFSEL0]

	mov r2, #0b00000000000000000000000000000100

	mov r3, #0b00000000000000000000000000001000
	
  	ldr r4, =0b00000000001000000000000001000000

	str r4, [r0, #GPFSEL2]		

  	mov r4, #0b00001000010000000000000000000000	
	str r4, [r0, #GPSET0]
	

bucle:
	ldr r5, [r0, #GPLEV0]
	tst r5, r2
	beq enciende22
	tst r5, r3
	beq enciende27
	b bucle


enciende22:
  	mov r1, #0b00000000010000000000000000000000
	str r1, [r0, #GPSET0]				
	mov r1, #0b00001000000000000000000000000000	
	str r1, [r0, #GPCLR0]				
	b bucle


enciende27:
  	mov r1, #0b00001000000000000000000000000000
	str r1, [r0, #GPSET0]				
	mov r1, #0b00000000010000000000000000000000	
	str r1, [r0, #GPCLR0]				
	b bucle