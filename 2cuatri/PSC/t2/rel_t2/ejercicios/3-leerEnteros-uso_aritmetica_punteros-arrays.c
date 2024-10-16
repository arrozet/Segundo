#include <stdio.h>

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
    int arr[tam];
    int* ptr = &arr[0];
    printf("%p", ptr);

    leerEnteros(ptr, tam);

    for(int i=0; i<tam; i++){
        printf("%d ", *(ptr+i));
    }
    printf("%p", ptr);
    return 0;
}