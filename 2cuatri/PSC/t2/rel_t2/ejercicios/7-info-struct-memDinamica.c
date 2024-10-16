#include <stdio.h>
#include <stdlib.h>

typedef struct Info Info;
struct Info{
    char nombre[50];
    char apellidos[50];
    int numNotas;
    double* ptrNotas;
};

void leerInfo(Info* estudiante){
    printf("Nombre del estudiante:\n");
    scanf("%s", estudiante->nombre);
    printf("Apellidos del estudiante:\n");
    scanf("%s", estudiante->apellidos);
    // si meto 2 palabras con espacio no funciona pq se queda la segunda palabra en el buffer de entrada, y despues se espera int no un caracter
    // scanf mete en %s segun espacios o intros
    printf("Número de notas del estudiante:\n");
    scanf("%d", &estudiante->numNotas);

    estudiante->ptrNotas=(double*) malloc(estudiante->numNotas*sizeof(double));

    printf("Notas del estudiante:\n");
    for(int i=0; i<estudiante->numNotas; i++){
        scanf("%lf", estudiante->ptrNotas + i);
    }
}

void mostrarInfo(Info* estudiante){
    printf("\nNombre del estudiante: %s \n", estudiante->nombre);
    printf("Apellidos del estudiante: %s \n", estudiante->apellidos);
    printf("Número de notas del estudiante: %d \n", estudiante->numNotas);
    printf("Notas del estudiante:\n");
    for(int i=0; i<estudiante->numNotas; i++){
        printf("%.2f\n", *(estudiante->ptrNotas + i));
    }
}

void liberarMemoria(Info* estudiante){
    free(estudiante->ptrNotas);
    free(estudiante);
}

int main(){
    Info* estudiante = (Info*) malloc(sizeof(Info));
    leerInfo(estudiante);
    mostrarInfo(estudiante);
    liberarMemoria(estudiante);
    return 0;
}