/*
ALUMNO: Oliva Zamora, Rubén
GRADO: Ingeniería del Software A
*/

/**
UNIX Shell Project

Sistemas Operativos
Grados I. Informatica, Computadores & Software
Dept. Arquitectura de Computadores - UMA

Some code adapted from "Fundamentos de Sistemas Operativos", Silberschatz et al.

To compile and run the program:
   $ gcc Shell_project.c job_control.c -o Shell
   $ ./Shell          
	(then type ^D to exit program)

**/

#include "job_control.h"   // remember to compile with module job_control.c 
#include <string.h>
#include "parse_redir.h"	// para parsear redirecciones
#include <fcntl.h>			// para open 2
#include <pthread.h>

#define RESET "\x1b[0m"
#define RED "\x1b[31m"
#define GREEN "\x1b[32m"
#define YELLOW "\x1b[33m"
#define BLUE "\x1b[34m"
#define MAGENTA "\x1b[35m"
#define CYAN "\x1b[36m"
#define WHITE "\x1b[37m"
#define DEFAULT "\x1b[39m"



#define MAX_LINE 256 /* 256 chars per line, per command, should be enough. */
//MASK - AMPLIACION 4
#define NUMBER_OF_SIGNALS 32		// es 32 ya que hay 31 señales, y si me las ponen todas debo saber parar -> la ultima sera 0

job* lista_jobs;

// ALARM-THREAD - AMPLIACIÓN 2
typedef struct {
    pid_t pid;
    int time;
} timeoutArgs;

//DELAY-THREAD - AMPLIACIÓN 3
typedef struct {
    int time;
    char** cmd;
} delayArgs;


void manejador_child(int signal){	// para que no se queden zombies los hijos
	block_SIGCHLD();	// esto es como un mutex
	job* task;
	enum status status_res; /* status processed by analyze_status() */
	int status;	/* status returned by wait */
	int info;	/* info processed by analyze_status() */
	pid_t pid_wait;/* pid for created and waited process */
	pid_t pid_respawnable;

	status_res = analyze_status(status, &info);
	
	while((pid_wait = waitpid(-1, &status, WNOHANG | WUNTRACED | WCONTINUED)) > 0){
		//debug(pid_wait, %u);
		task = get_item_bypid(lista_jobs, pid_wait);
		status_res = analyze_status(status, &info);
		if(status_res == SUSPENDED){
			if(task->state == RESPAWNABLE){//RESPAWNABLE - AMPLIACIÓN 1
				printf(CYAN"Respawnable pid: %d, command: %s, %s, info: %d\n\n"RESET, task->pgid, task->command, status_strings[status_res],info);
			}
			else{
				printf(CYAN"Background pid: %d, command: %s, %s, info: %d\n\n"RESET, task->pgid, task->command, status_strings[status_res],info);
			}
			task->state = STOPPED;
		}
		else if(status_res == CONTINUED){
			task->state = BACKGROUND;
			printf(CYAN"Background pid: %d, command: %s, %s, info: %d\n\n"RESET, task->pgid, task->command, status_strings[status_res],info);
		}
			
			// EXITED -> acaba por su propio pie	SIGNALED -> le dicen de morir
		else if(status_res == EXITED || status_res == SIGNALED){
			//RESPAWNABLE - AMPLIACIÓN 1

			// aquí debo asegurar que si es respawnable, respawnee cuando acabe
			if(task->state == RESPAWNABLE){
				pid_respawnable = fork();	// creo un nuevo proceso respawnable
				if(pid_respawnable<0){
					perror(RED"ERROR: couldn't fork respawnable process\n"RESET);
				}
				else if(pid_respawnable==0){	// hijo: igual que en el hijo del while pero sabiendo que va a ser respawnable
					new_process_group(getpid());
					restore_terminal_signals();
					execvp(task->command, task->args);

					// si execvp falla, se mostrara el error
					fprintf(stderr, RED"ERROR: command not found: %s\n"RESET, task->command);	// si no se ha ejecutado correctamente, indico el error
					exit(-1);
				}
				else{	// padre
					printf(CYAN"Respawnable job respawned... pid: %d, command: %s\n"RESET, pid_respawnable, task->command);
					new_process_group(pid_respawnable);
					task->pgid = pid_respawnable;
				}
			}
			else{
				printf(CYAN"Background pid: %d, command: %s, %s, info: %d\n\n"RESET, task->pgid, task->command, status_strings[status_res],info);
				delete_job(lista_jobs, task);
			}
		}
	}
	unblock_SIGCHLD();
}

void foreground_control(pid_t fg_pid, pid_t pid_shell, char *args[]){
	enum status status_res;
	int status, info;
	pid_t pid_wait;

	tcsetpgrp(STDIN_FILENO, fg_pid);	// le asigno el terminal
	pid_wait = waitpid(fg_pid, &status, WUNTRACED);	// WUNTRACED para notificar si un hijo se para o suspende
	if(pid_wait == -1){		// manejo de errores
		printf(RED"ERROR: waitpid failed\n"RESET);
		exit(-1);
	}
	tcsetpgrp(STDIN_FILENO, pid_shell);	// le devuelvo el terminal al shell
	status_res = analyze_status(status, &info);
	printf(CYAN"Foreground job — pid: %d, command: %s, %s, info: %d\n"RESET, pid_wait, args[0], status_strings[status_res], info);
	if(status_res==SUSPENDED){
		block_SIGCHLD();
		add_job(lista_jobs, new_job(fg_pid, args[0], STOPPED));	
		unblock_SIGCHLD();
	}
}

//ALARM-THREAD - AMPLIACION 2
void* timeoutHandler(void* arg){	// arg es un array de int, pero los thread esperan void*
	timeoutArgs* parsed_arg = (timeoutArgs*) arg;	// asi que lo parseo ya que el handler solo coge void
	pid_t pid = parsed_arg->pid;
	int time = parsed_arg->time;

	sleep(time);	// espero
	killpg(pid, SIGKILL);	// mato al proceso

	free(arg);

	return NULL;	// no necesita devolver nada
}

//DELAY-THREAD - AMPLIACION 3
void* delayHandler(void* arg) {
	
    delayArgs* parsed_arg = (delayArgs*) arg;
    int time = parsed_arg->time;
    char** cmd = parsed_arg->cmd;

    sleep(time);

	pid_t pid_delay = fork();
    if (pid_delay == 0) {  // Crear un proceso hijo
        execvp(cmd[0], cmd);  // Ejecutar el comando en el proceso hijo
        perror(RED"execvp"RESET);  // Si execvp falla, se imprime un error
        exit(EXIT_FAILURE);
	}
	else if(pid_delay>0){	// el padre lo añade a la lista de jobs
		add_job(lista_jobs, new_job(pid_delay, cmd[0], BACKGROUND));
	}
	
    for (int i = 0; cmd[i] != NULL; i++) {
        free(cmd[i]);  // Liberar memoria de cada argumento
    }
    free(cmd);  // Liberar memoria de la lista de argumentos
    free(parsed_arg);  // Liberar memoria del struct
	
	
    return NULL;
}

// -----------------------------------------------------------------------
//                            MAIN          
// -----------------------------------------------------------------------

int main(void)
{
	char inputBuffer[MAX_LINE]; /* buffer to hold the command entered */
	int background;             /* equals 1 if a command is followed by '&' */
	int respawnable;			/* 1 si el comando tiene '+' */
	char *args[MAX_LINE/2];     /* command line (of 256) has max of 128 arguments */
	int pid_fork; 

	//ALARM-THREAD - AMPLIACION 2
	int time = 0;
	int timeout = 0;
	pthread_t timeout_thr_id;

	//DELAY-THREAD - AMPLIACION 3
	pthread_t delay_thr_id;
	int delay_time = 0;

	//MASK - AMPLIACION 4
	int mask[NUMBER_OF_SIGNALS];	// hay 31 señales, pero pongo 32 para poder tener 1 caracter para parar (0)
	int maskIsOn=0;
			

	int pid_shell = getpid();
	lista_jobs = new_list("Tareas");

	signal(SIGCHLD,manejador_child);
	ignore_terminal_signals();	// para que solo funcionen las que yo quiero que funcionen
	while (1)   /* Program terminates normally inside get_command() after ^D is typed*/
	{   		
		printf("COMMAND->");
		fflush(stdout);
		get_command(inputBuffer, MAX_LINE, args, &background, &respawnable);  /* get next command */

		// EMPTY
		if(args[0]==NULL) continue;   // if empty command
		
		// CD
		if(strcmp(args[0], "cd")==0){
			if(args[1]==NULL){
				// Cambiar a $HOME si no se especifica un directorio
				char* homeDirectory = getenv("HOME");
				if(homeDirectory==NULL){	// si $HOME no tiene nada
					fprintf(stderr, RED"ERROR: can't get home directory. $HOME contains %s"RESET, args[1]);
					exit(EXIT_FAILURE);
				}
				if(chdir(homeDirectory)==-1){	// si no me puedo cambiar a $HOME
					perror(RED"ERROR: can't change directory to $HOME"RESET);
					exit(EXIT_FAILURE);
				}
			}
			else{
				if(chdir(args[1])==-1){
					fprintf(stderr, RED"ERROR: can't change directory to %s\n"RESET, args[1]);
				}
			}
			
			continue;
		}

		// JOBS
		else if(strcmp(args[0],"jobs")==0){
			block_SIGCHLD();
			print_job_list(lista_jobs);
			unblock_SIGCHLD();
			continue;
		}

		// FG		
		else if(strcmp(args[0],"fg")==0){
			block_SIGCHLD();
			job* task;
			
			// BUSCO LA TAREA
			if(args[1]!=NULL){
				int pos = atoi(args[1]);
				if(pos==0){	// control de error -> atoi devuelve 0 si no se puede parsear
					perror(RED"ERROR: index was not a number\n"RESET);
					unblock_SIGCHLD();	// esto es como un mutex, si salgo debo liberarlo para que funcione todo ok
					continue;
				}
				task = get_item_bypos(lista_jobs, pos);
			}
			else{// si no le meto un argumento
				task = get_item_bypos(lista_jobs, 1);
			}

			// PROCESO LA TAREA
			if(task!=NULL){// si hay tarea
				pid_t fg_pid = task->pgid; // tarea que quiero enviar a fg
				if(task->state == STOPPED){	// si estaba parada, ahora esta continuandose
					killpg(fg_pid, SIGCONT);
				}

				strcpy(args[0],task->command);	// para que se printee el nombre del proceso a poner en fg
				delete_job(lista_jobs, task);	// lo saco de cola de background
				foreground_control(fg_pid, pid_shell, args);	// a fg
			}
			else{	// control de error -> task es NULL si get_item_bypos no coge nada
				perror(RED"ERROR: index invalid\n"RESET);
			}

			unblock_SIGCHLD();
			continue;
		}
		
		//BG
		else if(strcmp(args[0],"bg")==0){
			block_SIGCHLD();
			job* task;

			// BUSCO LA TAREA
			if(args[1]!=NULL){
				int pos = atoi(args[1]);
				if(pos==0){	// control de error -> atoi devuelve 0 si no se puede parsear
					printf(RED"ERROR: index was not a number\n"RESET);
					unblock_SIGCHLD();	// esto es como un mutex, si salgo debo liberarlo para que funcione todo ok
					continue;
				}
				task = get_item_bypos(lista_jobs, pos);
			}
			else{// si no le meto un argumento
				task = get_item_bypos(lista_jobs, 1);
			}

			// PROCESO LA TAREA
			if(task!=NULL && (task->state == STOPPED || task->state == RESPAWNABLE) ){// si hay tarea
				task->state = BACKGROUND;	// si envío SIGCONT y ya estaba RESPAWNABLE, no se cambia a BACKGROUND. Así evito este problema
				killpg(task->pgid, SIGCONT);
				
			}
			else{	// control de error -> task es NULL si get_item_bypos no coge nada
				printf(RED"ERROR: index invalid\n"RESET);
			}

			unblock_SIGCHLD();	
			continue;
		}

		//ALARM-THREAD - AMPLIACION 2
		else if(strcmp(args[0],"alarm-thread")==0){	
			if(args[1]==NULL || args[2]==NULL){
				printf(RED"ERROR: not enough arguments"RESET);
				continue;
			}

			time = atoi(args[1]);
			if(time==0){
				printf(RED"ERROR: time was not a number or was 0\n"RESET);
				continue;
			}
			else if(time<0){
				printf(RED"ERROR: time cannot be negative\n"RESET);
				continue;
			}

			timeout = 1;	// booleano de timeout a 1
			
			// pongo el comando a ejecutar en su sitio
			int i=0;
			while(args[i+2]!=NULL){
				args[i] = strdup(args[i+2]);
				args[i+2]=NULL;	// borro lo que habia
				i++;
			}
			args[i]=NULL;	// para ponerlo en background
		}

		//DELAY-THREAD - AMPLIACION 3
		else if(strcmp(args[0],"delay-thread")==0){	
			if(args[1]==NULL || args[2]==NULL){
				printf(RED"ERROR: not enough arguments"RESET);
				continue;
			}

			delay_time = atoi(args[1]);
			if(delay_time==0 && strcmp(args[1],"0")!=0){
				printf(RED"ERROR: time was not a number\n"RESET);
				continue;
			}
			else if(delay_time<0){
				printf(RED"ERROR: time cannot be negative\n"RESET);
				continue;
			}

			// Preparar los argumentos para el comando
			int i = 2;
			int cmd_len = 0;
			while (args[i] != NULL) {
				cmd_len++;
				i++;
			}

			// Copiar los argumentos del comando
			char** cmd = malloc((cmd_len + 1) * sizeof(char*));
			for (i = 0; i < cmd_len; i++) {
				cmd[i] = strdup(args[i + 2]);
			}
			cmd[cmd_len] = NULL;  // NULL terminador para execvp

			// Preparar los argumentos para el hilo
			delayArgs* delay_args = malloc(sizeof(delayArgs));
			delay_args->time = delay_time;
			delay_args->cmd = cmd;

			// Crear y lanzar el hilo
			if (pthread_create(&delay_thr_id, NULL, delayHandler, delay_args) != 0) {
				printf(RED"ERROR: thread could not be created\n"RESET);
				free(cmd);
				free(delay_args);
				continue;
			}

			if (pthread_detach(delay_thr_id) != 0) {
				printf(RED"ERROR: thread could not be detached\n"RESET);
				free(cmd);
				free(delay_args);
				continue;
			}

			continue;
		}
		//MASK - AMPLIACIÓN 4
		else if(strcmp(args[0],"mask")==0){
			if(args[1] == NULL || args[2]==NULL || args[3]==NULL){// control de error -> no hay suficientes argumentos
				printf(RED"ERROR: not enough arguments in mask command\n"RESET);
				continue;
			}
			
			// Reseteo las mask:
			for(int i = 0; i<NUMBER_OF_SIGNALS; i++){
				mask[i]=0;
			}

			int c_position = 1;
			int mascara=0;	// las pongo al principio
			int valid_signal = 1;
			// escojo las señales que necesito
			while(strcmp(args[c_position],"-c")!=0 && args[c_position]!=NULL && valid_signal){
				int n = atoi(args[c_position]);
				if(n==0 || n>31){// control de error -> la señal no es valida (no es un numero o es un numero pero no corresponde a una señal)
					valid_signal = 0;
				}
				else{
					mask[mascara]=n;
					mascara++;
					c_position++;
				}
			}
			if(!valid_signal){
				printf(RED"ERROR: syntax of mask command is invalid\n"RESET);	// puede pasar pq no pase un numero o pq falte -c
				continue;
			}
			
			maskIsOn = 1;
			// Si he llegado aquí, c_position tiene el indice donde esta -c
			// pongo el comando a ejecutar en su sitio
			int i=0;
			c_position++; // para pasar al comando
			while(args[i+c_position]!=NULL){
				args[i] = strdup(args[i+c_position]);
				args[i+c_position]=NULL;	// borro lo que habia
				i++;
			}
			args[i]=NULL;
			// Me dicen que sea foreground. Para forzarlo, pongo background y respawnable a 0
			background = 0;
			respawnable = 0;
		}
		/* the steps are:
			 (1) fork a child process using fork()
			 (2) the child process will invoke execvp()
			 (3) if background == 0, the parent will wait, otherwise continue 
			 (4) Shell shows a status message for processed command 
			 (5) loop returns to get_commnad() function
		*/
	
		pid_fork = fork();	// hago el fork al proceso
		if(pid_fork>0){	// padre
			new_process_group(pid_fork);

			//ALARM-THREAD - AMPLIACION 2
			if(timeout){
				timeoutArgs* thread_args = malloc(sizeof(timeoutArgs));
				thread_args->pid = pid_fork;		// proceso que espero
				thread_args->time = time;		// tiempo que espero
								// thread, atributos, funcion, argumentos
				if(pthread_create(&timeout_thr_id, NULL, timeoutHandler, thread_args) == -1){
					printf(RED"ERROR: thread could not be created"RESET);
					free(thread_args);
					exit(-1);
				}
				
				if(pthread_detach(timeout_thr_id) != 0){	// espero a que termine
					printf(RED"ERROR: thread could not be detached"RESET);
					free(thread_args);
					exit(-1);
				}
				
				timeout=0;	// reseteo timeout
			}

			if(background){	// BACKGROUND
				block_SIGCHLD();
				add_job(lista_jobs, new_job(pid_fork, args[0], BACKGROUND));
				printf(CYAN"Background job running... pid: %d, command: %s\n"RESET, pid_fork, args[0]);
				unblock_SIGCHLD();
			}
			else if(respawnable){//RESPAWNABLE - AMPLIACIÓN 1
				block_SIGCHLD();
				add_respawnable_job(lista_jobs, new_job(pid_fork, args[0], RESPAWNABLE), args);
				printf(CYAN"Respawnable job running... pid: %d, command: %s\n"RESET, pid_fork, args[0]);
				unblock_SIGCHLD();
			}
			else{			// FOREGROUND
				// a quien espero, que pasa con el que espero y las opciones (en este caso 0)
				foreground_control(pid_fork, pid_shell, args);
			}
		}
		else if(pid_fork==0){	// hijo
			pid_fork = getpid();
			
			new_process_group(pid_fork);
			//RESPAWNABLE - AMPLIACIÓN 1
			if(!background && !respawnable){	// para que no fallen los bg
				tcsetpgrp(STDIN_FILENO, pid_fork);	// asigno el terminal solo si el hijo es foreground
			}
			
			restore_terminal_signals();

			//MASK - AMPLICACION 4
			if(maskIsOn){
				for(int i = 0; mask[i]!=0; i++){
					mask_signal(mask[i],SIG_BLOCK);
				}
				maskIsOn=0;
			}

			// REDIRECCIONES
			char *file_in, *file_out;
			parse_redirections(args, &file_in, &file_out);// para parsear redirecciones
			if(file_in){ 	// si no es null
				int f_in = open(file_in, O_RDONLY);
				if(f_in == -1){
					perror("Open");
					exit(EXIT_FAILURE);
				}
				if(dup2(f_in, STDIN_FILENO) == -1){
					perror("dup2");
					exit(EXIT_FAILURE);
				}
				
				close(f_in);
			}
			
			if(file_out){	
				// si no existe el fileout, se puede crear
																// usr puede leer y escribir, group y others pueden leer
				int f_out = open(file_out, O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);
				if(f_out == -1){
					perror(RED"Open"RESET);
					exit(EXIT_FAILURE);
				}
				if(dup2(f_out, STDOUT_FILENO) == -1){
					perror(RED"dup2"RESET);
					exit(EXIT_FAILURE);
				}
				
				close(f_out);
			}
			
			execvp(args[0], args);	// ejecuto el comando - otra opción es execve(args[0],args,environ)
			// si execvp falla, se mostrara el error
			fprintf(stderr, RED"ERROR: command not found: %s\n"RESET, args[0]);	// si no se ha ejecutado correctamente, indico el error
			exit(-1);
		}
		else{	// si fork retorna -1, es que ha habido un error
			perror(RED"ERROR: couldn't fork\n"RESET);
		}
	} // end while
}
