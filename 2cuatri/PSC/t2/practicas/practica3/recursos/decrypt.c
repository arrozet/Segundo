#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* Parte 1: algoritmo de descifrado
 * 	v: puntero a un bloque de 64 bits.
 * 	k: puntero a la clave para descifrar.
 * 	Sabiendo que "unsigned int" equivale a 4 bytes (32 bits)
 * 	Podemos usar la notaci�n de array con v y k
 * 	v[0] v[1] --- k[0] ... k[3]
 */
void decrypt(unsigned int *v, unsigned int *k)
{

	//Definir variables e inicializar los valores de delta y sum
	const unsigned int delta = 0x9e3779b9;
	unsigned int sum = 0xC6EF3720;	
	//Repetir 32  veces (usar un bucle) la siguiente secuencia de operaciones de bajo nivel
	for(int i=0; i<32; i++){
		//Restar a v[1] el resultado de la operacion :
				// (v[0] desplazado a la izquierda 4 bits +k[2]) XOR (v[0] + sum)  XOR (v[0] desplazado a la derecha 5 bits)+k[3]
		unsigned int operacion = ((v[0]<<4) + k[2]) ^ (v[0] + sum) ^ ((v[0] >> 5) +k[3]);
		v[1]-=operacion;
		//Restar a v[0] el resultado de la operacion:
				// (v[1] desplazado a la izquierda 4 bits + k[0]) XOR (v[1]+ sum)  XOR (v[1] desplazado a la derecha 5 bits)+k[1]
		operacion = ((v[1]<<4) + k[0]) ^ (v[1] + sum) ^ ((v[1] >> 5) +k[1]);
		v[0]-=operacion;
		// Restar a sum el valor de delta
		sum-=delta;
	}
			
			
			
}

/* Parte 2: Metodo main. Tenemos diferentes opciones para obtener el nombre del fichero cifrado y el descifrado
 * 1. Usar los argumentos de entrada (argv)
 * 2. Pedir que el usuario introduzca los nombres por teclado
 * 3. Definir arrays de caracteres con los nombres
 */
int main()
{
	/*Declaraci�n de las variables necesarias, por ejemplo:
	* variables para los descriptores de los ficheros ( FILE * fent, *fsal)
	* la constante k inicializada con los valores de la clave
	* buffer para almacenar los datos (puntero a unsigned int, m�s adelante se reserva memoria din�mica */
	FILE *fent, *fsal;
	unsigned int k[4] = {128,129,130,131};
	unsigned int v[2];	// 2 unsigned int -> 8 bytes -> 64 bits
	unsigned int *buffer;
	char nomFichEnt[30], nomFichSal[30];

	
	/*Abrir fichero encriptado fent en modo lectura binario
	 * nota: comprobar que se ha abierto correctamente*/
    printf("Introduzca el nombre del fichero de entrada (con la extension): ");
	scanf("%s", nomFichEnt);
	fent = fopen(nomFichEnt,"rb");
	if(fent==NULL){
		perror("Error abriendo fichero de entrada");
		fflush(stdout);
	}
	/*Abrir/crear fichero fsal en modo escritura binario
	 * nota: comprobar que se ha abierto correctamente*/
	printf("Introduzca el nombre del fichero de salida (con la extension): ");
	scanf("%s", nomFichSal);
	fsal=fopen(nomFichSal, "wb");
	if(fsal==NULL){
		perror("Error abriendo fichero de entrada");
		fflush(stdout);
	}

   /*Al comienzo del fichero cifrado esta almacenado el tama�o en bytes que tendr� el fichero descifrado.
    * Leer este valor (imgSize)*/
   unsigned int imgSize, imgSizeWithPossiblePadding;
   fread((void*) &imgSize, sizeof(unsigned int), 1, fent);

	/*Reservar memoria din�mica para el buffer que almacenara el contenido del fichero cifrado
	 * nota1: si el tama�o del fichero descifrado (imgSize) no es m�ltiplo de 8 bytes,
	 * el fichero cifrado tiene adem�s un bloque de 8 bytes incompleto, por lo que puede que no coincida con imgSize
	 * nota2: al reservar memoria din�mica comprobar que se realiz� de forma correcta */
	imgSizeWithPossiblePadding += imgSize;
	if(imgSize%8!=0){
		imgSizeWithPossiblePadding += (8-imgSize%8);	// completo artificialmente hasta llegar a 8 bytes
	}
	buffer = (unsigned int*) malloc(imgSizeWithPossiblePadding*4);
	unsigned int* buffIter = buffer;
	if(buffer==NULL){
		perror("No se pudo asignar memoria\n");
		fflush(stdout);
	}

	/*Leer la informaci�n del fichero cifrado, almacenado el contenido en el buffer*/
	unsigned int leidos = (unsigned int) fread((void*) buffer, sizeof(char), imgSizeWithPossiblePadding, fent);
	if(leidos!=imgSizeWithPossiblePadding){
		fclose(fent);
		fclose(fsal);
		free(buffer);
		exit(-1);
	}
	unsigned int cantidadBloques = imgSizeWithPossiblePadding / 8;	// yo lo hago con el que tiene padding y comprueba si es el ultimo bloque

// la solucion saca la cantidad de bloques con el que no tiene padding y despues mira el ultimo aparte (solo lo mira en caso de que si haya padding, en otro caso no tiene sentido mirarlo)

	/*Para cada bloque de 64 bits (8 bytes o dos unsigned int) del buffer, ejecutar el algoritmo de desencriptado*/
	
	for(unsigned int i = 0; i<cantidadBloques; i++){
		if(imgSize % 8 != 0 && i == cantidadBloques-1){	// si sí tiene padding y es el ultimo bloque
			memcpy((void*) v, (void*) buffIter,8);
			decrypt(v,k);
			fwrite((void*) v, sizeof(char), imgSize%8, fsal);	// solo escribo los que me hacen falta del ultimo bloque
		}
		else{
			memcpy((void*) v, (void*) buffIter,8);
			decrypt(v,k);
			buffIter+=2; 	// pq buffIter es de unsigned int, que son 4 bytes cada uno -> 2*4=8
			//Guardar el contenido del buffer en el fichero fsal
		// nota: en fsal solo se almacenan tantos bytes como diga imgSize
			fwrite((void*) v,sizeof(unsigned int), 2, fsal);
		}
		
	}	


	/*Cerrar los ficheros*/
	free(buffer);
	fclose(fent);
	fclose(fsal);
}


