#include <stdio.h>

void intercambiar(int* p1, int* p2){
    int temp = *p1;
    *p1 = *p2;
    *p2 = temp;
}

int main(){
    // en vez de pre-declarar las variables, usar scanf
    int x = 10;
    int y = 13;

    printf("Los valores antes de intercambiar son: %d, %d \n", x, y);
    intercambiar(&x, &y);
    printf("Los valores después de intercambiar son: %d, %d\n", x, y);

    return 0;
}