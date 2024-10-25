#include <stdio.h>
#include <stdlib.h>
#include <string.h>


typedef struct la_estructura
{
  int num_orden;
  char cadena[10];
} la_estructura_t;

void copia_contenido_estructura(la_estructura_t *dest, la_estructura_t *origen);


int main()
{

  la_estructura_t array_estructuras[2];
  
  /* Inicializa la estructura origen */
  
  array_estructuras[0].num_orden = 1;
  strcpy(array_estructuras[0].cadena, "primera"); 
  
  /* Inicializa la estructura destino */
  array_estructuras[1].num_orden = 2;
  strcpy(array_estructuras[1].cadena, "fubar"); 
  
  printf("\n");
  printf ("Numero de orden inicial en destino = %d\n", array_estructuras[1].num_orden);
  printf("Cadena inicial en destino = %s\n\n", array_estructuras[1].cadena);
  
  
  /* Hace la copia de la estructura origen en la estructura destino, pasando por 
  referencia las direcciones de ambas estructuras */
  
  copia_contenido_estructura (&array_estructuras[1], &array_estructuras[0]);
  
  printf("\n");
  printf ("Número de orden final en destino = %d\n", array_estructuras[1].num_orden);
  printf("Cadena final en destino = %s\n\n", array_estructuras[1].cadena);
  
  
}


void copia_contenido_estructura(la_estructura_t *dest, la_estructura_t *origen)
{
  dest->num_orden = origen->num_orden;
  strcpy(dest->cadena, origen->cadena);
}
