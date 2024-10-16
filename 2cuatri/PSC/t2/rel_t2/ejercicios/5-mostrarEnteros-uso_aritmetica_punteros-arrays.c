#include <stdio.h>
#include <stdlib.h>

void leerEnteros(int* ptr, int cantidad){
    printf("Introduce %d nºs enteros: ", cantidad);
    for(int i=0; i<cantidad; i++){
        scanf("%d", ptr);
        ptr++;
    }
}

void mostrarEnteros(const int* ptr, const int tam){
    for(int i=0; i<tam; i++){
        printf("%d ", *(ptr+i));
    }
}

int main(){
    int tam;
    printf("Size of array: ");
    scanf("%d", &tam);
    int arr[tam];
    mostrarEnteros(&arr[0], tam);

    leerEnteros(&arr[0], tam);

    mostrarEnteros(&arr[0], tam);

    return 0;
}