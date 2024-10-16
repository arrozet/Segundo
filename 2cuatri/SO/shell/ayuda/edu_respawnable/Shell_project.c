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
#include "parse_redir.h"
#include <string.h>
#include <fcntl.h>
#define MAX_LINE 256 /* 256 chars per line, per command, should be enough. */

job *job_list;

void manejador_sigchld(int signal){
	block_SIGCHLD();
	//matar o morir
	int status, info_bg;
	int status_res;
	int found = 0;
	pid_t pid_anomalia;
	
	while((pid_anomalia = waitpid(-1, &status, WNOHANG | WUNTRACED | WCONTINUED)) > 0){ //Se ejecuta mientras haya un proceso que haya parado
		job *anomalia = get_item_bypid(job_list, pid_anomalia);
		if (anomalia != NULL){

			status_res = analyze_status(status, &info_bg);
			if (status_res == SIGNALED || status_res == EXITED){
				if (anomalia->state == RESPAWNABLE){
					pid_t pid_respawn = fork();
					if (pid_respawn < 0) perror("Error on fork");
					else if (pid_respawn == 0){
						new_process_group(getpid());
						restore_terminal_signals();

						if (execvp(anomalia->command, anomalia->args) == -1){
							perror("Error on execvp");
						}
					} else {
						printf("Respawnable job running again... pid: %d, command: %s\n", pid_respawn, anomalia->command);
						new_process_group(pid_respawn);
						anomalia->pgid = pid_respawn;
					}
				} else {
					printf("Background process ended pid: %d, command: %s, %s, info: %d\n", pid_anomalia, anomalia->command, status_strings[status_res], info_bg);
					if (delete_job(job_list, anomalia) == 0){
						fprintf(stderr, "Couldn't delete job.\n");
					}
				}
			
			}

			else if (status_res == SUSPENDED){
				if(anomalia->state == RESPAWNABLE){//RESPAWNABLE - AMPLIACIÓN 1
					printf("Respawnable pid: %d, command: %s, %s, info: %d\n\n", anomalia->pgid, anomalia->command, status_strings[status_res],info_bg);
				}
				else{
					printf("Background pid: %d, command: %s, %s, info: %d\n\n", anomalia->pgid, anomalia->command, status_strings[status_res],info_bg);
				}
				anomalia->state = STOPPED;
			}

			else if (status_res == CONTINUED){
				anomalia->state = BACKGROUND;
				printf("Background process pid: %d, command: %s, %s, info: %d\n", pid_anomalia, anomalia->command, status_strings[status_res], info_bg);
			}
		}
	
		
	}
	unblock_SIGCHLD();
	
}

void control_foreground(pid_t child, pid_t terminal, char* command){
	pid_t pidw;
	int status, info, status_res;
	tcsetpgrp(STDIN_FILENO, child);
	pidw = waitpid(child, &status, WUNTRACED);
	if (pidw == -1){
		perror("waitpid failed");
	}
	new_process_group(child); //NUEVO GRUPO (TAREA 2)
	tcsetpgrp(STDIN_FILENO, terminal); //CEDE TERMINAL
	status_res = analyze_status(status, &info);
				
	printf("Foreground pid: %d, command: %s, %s, info: %d\n", child, command, status_strings[status_res], info);
	if (status_res == SUSPENDED){
		block_SIGCHLD();
		job *new = new_job(child, command, STOPPED);
		add_job(job_list, new);
		unblock_SIGCHLD();
	}
}

// -----------------------------------------------------------------------
//                            MAIN          
// -----------------------------------------------------------------------

int main(void)
{
	char inputBuffer[MAX_LINE]; /* buffer to hold the command entered */
	int background, respawnable;             /* equals 1 if a command is followed by '&' */
	char *args[MAX_LINE/2];     /* command line (of 256) has max of 128 arguments */
	// probably useful variables:
	int pid_fork, pid_wait, pid_wait_fg; /* pid for created and waited process */
	int status;             /* status returned by wait */
	enum status status_res, status_res_fg; /* status processed by analyze_status() */
	int info;				/* info processed by analyze_status() */
	job_list = new_list("job_list");
	int fg_index, bg_index;


	ignore_terminal_signals();

	signal(SIGCHLD, manejador_sigchld);
	while (1)   /* Program terminates normally inside get_command() after ^D is typed*/
	{   		
		printf("COMMAND->");
		fflush(stdout);
		get_command(inputBuffer, MAX_LINE, args, &background, &respawnable);  /* get next command */
		
		if(args[0]==NULL) continue;   // if empty command, CONTINUE VUELVE AL INICIO DEL BUCLE

		if (strcmp("cd", args[0]) == 0){ //Implementacion de cd (Tarea 2)
			if (args[1] == NULL){
				char *home = getenv("HOME");
				if (home== NULL){
					fprintf(stderr, "Couldn't get home directory\n");
				} else if (chdir(home) == -1){
					fprintf(stderr, "Couldn't change directory to home\n");
				}
			} else if (chdir(args[1]) == -1){
				fprintf(stderr,"Couldn't change directory to %s\n", args[1]);
			}

			continue;
		} else if (strcmp("jobs", args[0]) == 0){
			block_SIGCHLD();
			print_job_list(job_list);
			unblock_SIGCHLD();
			continue;
		} else if (strcmp("fg", args[0]) == 0){
			block_SIGCHLD();
			if (args[1] != NULL){
				fg_index = atoi(args[1]);
				if (fg_index == 0){
					fprintf(stderr, "NO SE USO ATOI");
					unblock_SIGCHLD();
					continue;
				}
			} else {
				fg_index = 1;
			}
			job *toChange = get_item_bypos(job_list, fg_index);
			if (toChange != NULL){
				if (toChange->state == STOPPED){
					killpg(toChange->pgid, SIGCONT);
				}
				pid_t p = toChange->pgid;
				char str[30];
				strcpy(str, toChange->command);
				delete_job(job_list, toChange);
				control_foreground(p, getpid(), str);
			}

			unblock_SIGCHLD();
			continue;
		} else if (strcmp("bg", args[0]) ==  0){
			block_SIGCHLD();
			if (args[1] != NULL){
				bg_index = atoi(args[1]);
				if (fg_index == 0){
					fprintf(stderr, "NO SE USO ATOI");
					unblock_SIGCHLD();
					continue;
				}
			} else {
				bg_index = 1;
			}
			job *toChange = get_item_bypos(job_list, bg_index);
			if (toChange != NULL){
				killpg(toChange->pgid, SIGCONT);
				toChange->state = BACKGROUND;
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
		
		pid_fork = fork();

		if (pid_fork == 0){

			new_process_group(getpid()); //NUEVO GRUPO (TAREA 2)

			if (!background && !respawnable){ //foreground cede terminal
				tcsetpgrp(STDIN_FILENO, getpid());
			}

			restore_terminal_signals();

			char *file_in, *file_out;
			parse_redirections(args, &file_in, &file_out);

			if (file_in){
				int fd_in= open(file_in, O_RDONLY);
				if (fd_in == -1){
					perror("Couldn´t open file");
					exit(EXIT_FAILURE);
				}

				if(dup2(fd_in, STDIN_FILENO) == -1){
					perror("Error on dup2");
					exit(EXIT_FAILURE);
				}

				close(fd_in);
			}

			if (file_out){
				int fd_out = open(file_out, O_WRONLY | O_CREAT | O_TRUNC, S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH);//TRUNC BORRA EL CONTENIDO SI HUBIERA ALGO
				//LAS FLAGS DEL TERCER ARGUMENTO SON LOS PERMISOS
				if (fd_out == -1){
					perror("Couldn´t open file");
					exit(EXIT_FAILURE);
				}
				if(dup2(fd_out, STDOUT_FILENO) == -1){
					perror("Error on dup2");
					exit(EXIT_FAILURE);
				}
				close(fd_out);
 

			}


		
			if (execvp(args[0], args) == -1){
				fprintf(stderr,"ERROR: Command not found: %s\n", args[0]);
				exit(-1);
			}
			exit(0);
		} 
		else if (pid_fork > 0){
			new_process_group(pid_fork);
			if(background){
				block_SIGCHLD();
				add_job(job_list,new_job(pid_fork, args[0], BACKGROUND));
				printf("Background job running... pid: %d, command: %s\n", pid_fork, args[0]);
				unblock_SIGCHLD();
				
			} else if (respawnable){
				block_SIGCHLD();
				add_job_with_args(job_list,new_job(pid_fork, args[0], RESPAWNABLE), args);
				printf("Respawnable job running... pid: %d, command: %s\n", pid_fork, args[0]);
				unblock_SIGCHLD();
			}
			else {
				control_foreground(pid_fork, getpid(), args[0]);
			}
		}
	} // end while
}
