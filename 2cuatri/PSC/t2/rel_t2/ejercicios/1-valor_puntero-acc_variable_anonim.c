#include <stdio.h>

void mostrarValorPuntero(const int *p){
    printf("%d", *p);
}

int main(){
    int x = 10;

    mostrarValorPuntero(&x);

    return 0;
}