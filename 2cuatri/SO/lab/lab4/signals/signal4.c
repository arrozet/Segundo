#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <signal.h>

//
/*He incluido estos headers porque son necesarios para que no salga el warning 

warning: implicit declaration of function ‘wait’ [-Wimplicit-function-declaration]    
30 |     while (pidfork != wait(&status));

Parece que el wait que usa signal1.c es wait(2) y se importa correctamente así*/
#include <sys/types.h>
#include <sys/wait.h>
//

void manejador(int senal){
	printf("Hijo: Soy el hijo con pid %d. Mi padre con pid %d me quiere matar. Me lo ha dicho la del horóscopo\n", getpid(), getppid());
 	sleep(2);
 	printf("Voy a por ti, papá con pid %d.\n", getppid());
 	kill(getppid(),SIGINT);
 	
 	/*Ahora te saldrá para escribir en el shell, pero te estará
	saliendo el output del hijo que se sigue ejecutando. El shell ha detectado que
	el proceso que se lanzó en primer plano (el padre) ha finalizado, por lo que
	te deja escribir en el shell, a pesar de que esté saliendo el output del hijo.
	
	Las combinaciones de teclas no funcionan ya que el proceso hijo no tiene control sobre
	el shell.
	
	Para matarlo, debes primero saber su pid. Si no lo has visto en la salida al inicio del
	programa, puedes escribir en el shell (te deja escribir, aunque se entrecorte con la
	salida del programa) "ps", para listar procesos con sus pid. Escoge el pid del que tiene
	en la columna cmd "signal4.c" y ejecuta en el shell "kill <pid de signal4.c>".
	Habrás matado al proceso hijo y solucionado el problema.
	*/
}

void main(int argc, char *argv[]) {
  pid_t pidfork;
  int status;

  pidfork = fork(); // creamos proceso hijo
  if (pidfork == 0) { /* proceso hijo */
    int i=0;
    signal(SIGINT,manejador);
    printf("Hijo: pid %d: ejecutando...\n",getpid());
    while(1) {
    	sleep(1); 
    	printf("Hijo: %d seg\n",++i);
    }; // bucle infinito
  } 
  else {/* proceso padre */
    sleep(5);
    printf("\nPadre: pid %d: mandando señal SIGINT\n",getpid());
    kill(pidfork,SIGINT);
    while (pidfork != wait(&status));
    if (WIFEXITED(status)) { // el proceso ha terminado con un exit()
      printf("El proceso terminó con estado %d\n", WEXITSTATUS(status));
    } else if (WIFSIGNALED(status)) { // el proceso ha terminado por la recepción de una señal
      printf("El proceso terminó al recibir la señal %d\n", WTERMSIG(status));
    } else if (WIFSTOPPED(status)) { // el proceso se ha parado por la recepción de una señal
      printf("El proceso se ha parado al recibir la señal %d\n", WSTOPSIG(status));
    }
  }
  exit(0);
} 
