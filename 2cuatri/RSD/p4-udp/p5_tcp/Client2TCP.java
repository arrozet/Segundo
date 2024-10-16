package p5_tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client2TCP {

    public static void main(String[] args) throws IOException {
        String ip = args[0];
        int puerto = Integer.parseInt(args[1]);
        Socket socket = new Socket(ip, puerto);

        // Obtener flujos
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

        // Leer de teclado
        System.out.println("Introduce el envío a " + socket.toString() + ": ");

        // CREO EL SCANNER PARA INPUT
        Scanner sc = new Scanner(System.in);

        String msj = sc.nextLine();
        System.out.println("Recibido: " + msj);

        boolean force_finish = false;

        // mientras que lo leído sea correcto
        while(Character.isDigit(msj.charAt(0)) && !force_finish){
            try{
                // enviar mensaje
                out.println(msj);

                // recibir respuesta
                String respuesta = in.readLine();
                System.out.println("RESPUESTA: " + respuesta);
                System.out.println("Introduce el envío a " + socket.toString() + ": ");
                msj = sc.nextLine();
            }catch (Exception e){
                System.err.println("SERVIDOR CERRADO... CONEXIÓN CERRADA");
                force_finish = true;
            }

        }
        
        // Enviar FINISH
        if(!force_finish){
            out.println("FINISH");
            // recibir OK
            String respuesta_final = in.readLine();
            System.out.println("RESPUESTA: " + respuesta_final);
        }

        // Finalizar y liberar recursos
        out.close();
        in.close();
        socket.close();
    }
}
