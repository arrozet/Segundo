.set GPBASE, 0x3F200000	// direccion de la memoria gpio
.set GPFSEL2, 0x08	// direccion de GPIO Function Select Registers (a añadir a GPBASE) donde se encuentra GPIO22 (primer led verde)
.set GPSET0, 0x1c	// direccion seteo de pines 0 a 31 (GPISET1, que va de 32 a 53, no lo usaremos) - (a añadir a GPBASE)
.set GPCLR0, 0x28

.text
	ldr r0, =GPBASE
/* guia bits  xx999888777666555444333222111000 */
	mov r1, #0b00000000000000000000000001000000	// 22-20=2, pero
	str r1, [r0, #GPFSEL2]			// en la dir de memoria GPBASE + 0x08
/* guia bits         10987654321098765432109876543210 */
	
	bucle:
		mov r1, #0b00000000010000000000000000000000		// gpio 22, primer led verde
		str r1, [r0, #GPSET0]
		str r1, [r0, #GPCLR0]
		b bucle
		
		// no funciona porque no hay tiempo de espera
	
infi: b infi
