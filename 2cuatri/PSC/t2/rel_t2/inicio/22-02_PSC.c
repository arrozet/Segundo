#include <stdio.h>

int main(){
    int x = 10;
    int *ptr = &x;  // guardo dir memoria x (operador &)

    printf("Valor de x: %d\n", x); // cuando llamas a la variable como *ptr en vez de ptr, te da el contenido del puntero no la dir a la que apunta
    printf("Dirección de x: %p\n", &x);
    
    printf("Valor apuntado por ptr: %p\n", ptr);  // %p sirve para formatear dir
    printf("Contenido del valor apuntado por ptr: %d\n", *ptr);
    printf("Dirección de ptr: %p\n", &ptr);

    return 0;
}