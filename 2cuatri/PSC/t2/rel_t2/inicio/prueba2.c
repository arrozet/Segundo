#include <stdio.h>

int main(){
    int perfecta = 1, escalera = 1;
    int num, anterior, actual;

    do{
        printf("Introduzca un numero de al menos 5 digitos:\n");
        scanf("%d", &num);
    }while(num<10000);
    
    return 0;
}