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


#define MAX_LINE 256 /* 256 chars per line, per command, should be enough. */

job* lista_jobs;

void manejador_child(int signal){	// para que no se queden zombies los hijos
	block_SIGCHLD();	// esto es como un mutex
	job* task;
	enum status status_res;
	int status, info;
	pid_t pid_wait;


	status_res = analyze_status(status, &info);
	
	while((pid_wait = waitpid(-1, &status, WNOHANG | WUNTRACED | WCONTINUED)) > 0){
		task = get_item_bypid(lista_jobs, pid_wait);
		status_res = analyze_status(status, &info);
		if(status_res == SUSPENDED){
			task->state = STOPPED;
			printf("Background pid: %d, command: %s, %s, info: %d\n\n", task->pgid, task->command, status_strings[status_res],info);
		}
		else if(status_res == CONTINUED){
			task->state = BACKGROUND;
			printf("Background pid: %d, command: %s, %s, info: %d\n\n", task->pgid, task->command, status_strings[status_res],info);
		}
			
			// EXITED -> acaba por su propio pie	SIGNALED -> le dicen de morir
		else if(status_res == EXITED || status_res == SIGNALED){
			printf("Background pid: %d, command: %s, %s, info: %d\n\n", task->pgid, task->command, status_strings[status_res],info);
			delete_job(lista_jobs, task);
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
		printf("ERROR: waitpid failed\n");
		exit(-1);
	}
	tcsetpgrp(STDIN_FILENO, pid_shell);	// le devuelvo el terminal al shell
	status_res = analyze_status(status, &info);
	printf("Foreground job — pid: %d, command: %s, %s, info: %d\n", pid_wait, args[0], status_strings[status_res], info);
	if(status_res==SUSPENDED){
		block_SIGCHLD();
		add_job(lista_jobs, new_job(fg_pid, args[0], STOPPED));	
		unblock_SIGCHLD();
	}
}

// -----------------------------------------------------------------------
//                            MAIN          
// -----------------------------------------------------------------------

int main(void)
{
	char inputBuffer[MAX_LINE]; /* buffer to hold the command entered */
	int background;             /* equals 1 if a command is followed by '&' */
	char *args[MAX_LINE/2];     /* command line (of 256) has max of 128 arguments */
	// probably useful variables:
	int pid_fork; 
	//pid_wait; /* pid for created and waited process */
	//int status;             /* status returned by wait */
	//enum status status_res; /* status processed by analyze_status() */
	//int info;				/* info processed by analyze_status() */

	int pid_shell = getpid();
	lista_jobs = new_list("Tareas");
	//char *file_in, *file_out;	// para parsear redirecciones (aún no sé para qué sirve)

	signal(SIGCHLD,manejador_child);
	ignore_terminal_signals();	// para que solo funcionen las que yo quiero que funcionen
	while (1)   /* Program terminates normally inside get_command() after ^D is typed*/
	{   		
		printf("COMMAND->");
		fflush(stdout);
		get_command(inputBuffer, MAX_LINE, args, &background);  /* get next command */

		
		

		// EMPTY
		if(args[0]==NULL) continue;   // if empty command

		// CD
		if(strcmp(args[0], "cd")==0){
			if(args[1]==NULL){
				// Cambiar a $HOME si no se especifica un directorio
				char* homeDirectory = getenv("HOME");
				if(homeDirectory==NULL){	// si $HOME no tiene nada
					fprintf(stderr, "ERROR: can't get home directory. $HOME contains %s", args[1]);
					exit(EXIT_FAILURE);
				}
				if(chdir(homeDirectory)==-1){	// si no me puedo cambiar a $HOME
					perror("ERROR: can't change directory to $HOME");
					exit(EXIT_FAILURE);
				}
			}
			else{
				if(chdir(args[1])==-1){
					fprintf(stderr, "ERROR: can't change directory to %s\n", args[1]);
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
					perror("ERROR: index was not a number\n");
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
				perror("ERROR: index invalid\n");
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
					perror("ERROR: index was not a number\n");
					unblock_SIGCHLD();	// esto es como un mutex, si salgo debo liberarlo para que funcione todo ok
					continue;
				}
				task = get_item_bypos(lista_jobs, pos);
			}
			else{// si no le meto un argumento
				task = get_item_bypos(lista_jobs, 1);
			}

			// PROCESO LA TAREA
			if(task!=NULL && task->state == STOPPED){// si hay tarea
				//pid_t fg_pid = task->pgid; // tarea que quiero enviar a bg
				killpg(task->pgid, SIGCONT);
				//printf("Background job running... pid: %d, command: %s\n", pid_fork, task->command);
				
			}
			else{	// control de error -> task es NULL si get_item_bypos no coge nada
				perror("ERROR: index invalid\n");
			}

			unblock_SIGCHLD();	
			continue;
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
			if(background){	// BACKGROUND
				block_SIGCHLD();
				add_job(lista_jobs, new_job(pid_fork, args[0], BACKGROUND));
				printf("Background job running... pid: %d, command: %s\n", pid_fork, args[0]);
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
			if(!background){	// para que no fallen los bg
				tcsetpgrp(STDIN_FILENO, pid_fork);	// asigno el terminal solo si el hijo es foreground
			}
			
			restore_terminal_signals();

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
					perror("Open");
					exit(EXIT_FAILURE);
				}
				if(dup2(f_out, STDOUT_FILENO) == -1){
					perror("dup2");
					exit(EXIT_FAILURE);
				}
				
				close(f_out);
			}

			execvp(args[0], args);	// ejecuto el comando - otra opción es execve(args[0],args,environ)
			// si execvp falla, se mostrara el error
			fprintf(stderr, "ERROR: command not found: %s\n", args[0]);	// si no se ha ejecutado correctamente, indico el error
			exit(-1);
		}
		else{	// si fork retorna -1, es que ha habido un error
			perror("ERROR: couldn't fork\n");
		}
	} // end while
}
