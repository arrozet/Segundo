package p5_tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
//import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

class ServerTCP {
    public static String extract_text(String texto){
        StringBuilder sb = new StringBuilder();
        // Extraer el dígito al inicio del texto
        int lengthThreshold = Character.getNumericValue(texto.charAt(0));

        // Extraer el resto del texto
        texto = texto.substring(1).trim();

        // Dividir el texto en palabras
        String[] words = texto.split("\\s+");

        // Construir la salida con las palabras que tienen una longitud mayor al dígito inicial
        for (String word : words) {
            if (word.length() > lengthThreshold) {
                sb.append(word).append(" ");
            }
        }

        return sb.toString().trim();
    }

    public static void main(String[] args) throws IOException {

        /*LO DE CLASE
        // creamos socket pasivo -> Se encargha de reibir a los clientes
        ServerSocket server = new ServerSocket(20000);

        //Conseguimos un cliente
        Socket client = server.accept();

        //Obtención  de los flujos para envío y recepción
        PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

        //Recibir
        String mensaje_recibido = in.readLine();
        System.out.println("Recibido del cliente: " + mensaje_recibido);
        //Enviar
        out.println("Respuesta a " + mensaje_recibido);

        //Liberar recursos
        out.close();
        in.close();
        client.close();
         */

        // Puerto por el que recibo peticiones
        int port = Integer.parseInt(args[0]);

        ServerSocket server = null; // Pasivo (recepción de peticiones)
        try {
            server = new ServerSocket(port, 1);
        } catch (IOException e) {
            System.out.println("Error: No se pudo crear un socket para escuchar en el puerto " + port);
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        System.out.println("Se ha creado un socket para recibir peticiones en el puerto " + port);
        while(true){ // Bucle de recepción de cliente
            System.out.println("Esperando a los clientes");
            Socket client = server.accept(); // Sacamos un cliente de la cola de clientes
            System.out.println("Cliente conectado: " + client.getInetAddress() + ":" + client.getPort());
            
            // FLUJOS PARA EL ENVÍO Y RECEPCIÓN
            BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream(), StandardCharsets.UTF_8)
            );
            PrintWriter out = new PrintWriter(client.getOutputStream(), true, StandardCharsets.UTF_8);

            boolean salir = false;
            while(!salir){
                String line = in.readLine();
                System.out.println("Recibido: " + line);
                // Modificar o tratar
                String respuesta = extract_text(line);
                if(line.equals("FINISH")){
                    respuesta = "OK";
                    salir = true;
                }
                out.println(respuesta);

            }

            in.close();
            out.close();
            client.close();
        }
    } // fin del metodo
}
