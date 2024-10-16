.set GPBASE, 0x3F200000
.set GPFSEL0, 0x00
.set GPFSEL2, 0x08
.set GPSET0, 0x1c
.set GPLEV0, 0x34
.set GPCLR0, 0x28
.text
	ldr r0, =GPBASE
	// botones
/* guia bits 	   xx999888777666555444333222111000 */
	mov r1, #0b00000000000000000000000000000000
	str r1, [r0, #GPFSEL0]
/* mask for testing GPIO2 */
	mov r2, #0b00000000000000000000000000000100
/* mask for testing GPIO3 */
	mov r3, #0b00000000000000000000000000001000
	
	// leds
/* guia bits 	  xx999888777666555444333222111000 */
  	ldr r4, =0b00000000001000000000000001000000
	/*mov r4, #0b00000000000000000000000001000000	// GPIO22
	// error "mov can only load an 8 bit immediate value optionally shifted an even number of bits (the assembler will work out the required shift for you). The error you are getting is because 3000 requires more than 8 bits between the highest and lowest set bits."
	add r4, r4, #0b00000000001000000000000000000000	// GPIO27*/
	str r4, [r0, #GPFSEL2]		
/* guia bits      10987654321098765432109876543210 */
  	mov r4, #0b00001000010000000000000000000000	// GPIO22 y GPIO27
	str r4, [r0, #GPSET0]
	

bucle:
	ldr r5, [r0, #GPLEV0]
	tst r5, r2
	beq enciende22
	tst r5, r3
	beq enciende27
	b bucle

/* guia bits 	   10987654321098765432109876543210 */
enciende22:
  	mov r1, #0b00000000010000000000000000000000
	str r1, [r0, #GPSET0]				// enciende gpio22
	mov r1, #0b00001000000000000000000000000000	
	str r1, [r0, #GPCLR0]				// apaga gpio27
	b bucle

/* guia bits 	   10987654321098765432109876543210 */
enciende27:
  	mov r1, #0b00001000000000000000000000000000
	str r1, [r0, #GPSET0]				// enciende gpio27
	mov r1, #0b00000000010000000000000000000000	
	str r1, [r0, #GPCLR0]				// apaga gpio22
	b bucle
