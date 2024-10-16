#include <stdio.h>
#include <stdlib.h>

void leerEnteros(int* ptr, int cantidad){
    printf("Introduce %d nºs enteros: ", cantidad);
    for(int i=0; i<cantidad; i++){
        scanf("%d", ptr);
        ptr++;
    }
    

}

int main(){
    int tam;
    printf("Size of array: ");
    scanf("%d", &tam);
    int* arr = (int *) malloc(tam*sizeof(int));

    leerEnteros(arr, tam);

    for(int i=0; i<tam; i++){
        printf("%d ", *(arr+i));
    }

    return 0;
}