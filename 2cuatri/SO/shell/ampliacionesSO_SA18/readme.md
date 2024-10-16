In user directory,
    cd 
[WINDOWS->LINUX] To write (not overwrite) all the files in Windows shell to Linux shell:

    alias WindowsToLinux_shell='sudo cp -r  /mnt/d/\!UMA/\!\!UMA_CODE/2/2cuatri/SO/shell/ /home/roz/'
[LINUX->WINDOWS] To update Windows shell just to be backed up on Google Drive:

    alias overwrite_LinuxToWindows_shell='sudo rm -r /mnt/d/\!UMA/\!\!UMA_CODE/2/2cuatri/SO/shell/ && sudo cp -r  /home/roz/shell /mnt/d/\!UMA/\!\!UMA_CODE/2/2cuatri/SO/'
    
    alias write_LinuxToWindows_shell='sudo cp -r  /home/roz/shell /mnt/d/\!UMA/\!\!UMA_CODE/2/2cuatri/SO/'



To compile with all flags do:
1. First go to the directory
    cd home/roz/shell/Shell_project-ROZ/

2. Compile
    gcc -Wall -Wextra -O2 -D_GLIBCXX_DEBUG Shell_project.c job_control.c -o shell

3. Execute
    ./shell

Or write command
    executeShellProject

---
If you have problems with permissions in Ubuntu, execute
    sudo chown -R roz /home/roz