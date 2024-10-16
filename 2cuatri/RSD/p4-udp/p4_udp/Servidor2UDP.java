package p4_udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Servidor2UDP {
    // FUNCIÓN PARA FILTRAR EL MENSAJE Y ASÍ GENERAR UNA RESPUESTA
    public static String palabrasLongitudMayorDigito(String msj){
        int n = Character.getNumericValue(msj.charAt(0));
        StringBuilder sb = new StringBuilder();
        msj = msj.substring(1); // para eliminar el número del string
        String[] palabras_msj = msj.split(" ");

        for(String palabra : palabras_msj){
            if(palabra.length() > n){
                sb.append(palabra);
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        int puerto = Integer.parseInt(args[0]);
        DatagramSocket s = null;

        // CREACIÓN DE SERVER
        try {
            s = new DatagramSocket(puerto);
            System.out.println("STATUS ― server conectado al puerto " + puerto + "\n");
        } catch (SocketException e) {
            System.err.println("ERROR: no se pudo crear el socket");
            System.err.println("MESSAGE: " + e.getMessage());
            throw new RuntimeException(e);
        }

        while(true){
            byte[] buffer = new byte[1000];

            DatagramPacket dp = new DatagramPacket(
                    buffer,// Zona de memoria
                    buffer.length// Tamaño de esa zona
            );

            System.out.println("###################################");
            // RECEPCIÓN DEL MENSAJE
            try {
                System.out.println("STATUS ― esperando petición del cliente");
                s.receive(dp);// dp se ha rellenado con los datos pertinentes
            } catch (IOException e) {
                System.err.println("ERROR: no se pudo recibir la respuesta");
                throw new RuntimeException(e);
            }

            // DECODIFICACIÓN DEL MENSAJE
            String mensaje = new String(
                    dp.getData(),
                    dp.getOffset(),
                    dp.getLength(),
                    StandardCharsets.UTF_8
            );
            System.out.println("STATUS ― mensaje de " + dp.getSocketAddress() + " recibido: " + mensaje);

            // FILTRADO
            String respuesta = palabrasLongitudMayorDigito(mensaje);

            DatagramPacket resp = new DatagramPacket(
                    respuesta.getBytes(StandardCharsets.UTF_8),
                    respuesta.length(),
                    dp.getAddress(),
                    dp.getPort()
            );

            // ENVÍO DE LA RESPUESTA
            s.send(resp);
            System.out.println("STATUS ― respuesta enviada a " + dp.getSocketAddress() + ": " + respuesta);
            System.out.println("###################################\n");


        }


        //s.close();
    }
}
