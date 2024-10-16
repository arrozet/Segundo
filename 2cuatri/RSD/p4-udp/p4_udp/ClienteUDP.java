package p4_udp;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClienteUDP {
    // por los args del main se pasa <ip,port> como args[0] y args[1] respectivamente

    public static void main(String[] args) {
        String ip_server = args[0];
        String puerto_server = args[1];

        // CREO EL SOCKET
        DatagramSocket s = null;
        try{
            s = new DatagramSocket();
        }catch (SocketException e){
            System.err.println("MESSAGE: " + e.getMessage());
            throw new RuntimeException(e);
        }

        //System.out.println("Mi puerto es " + s.getLocalPort());

        // INDICO QUE ESTÁ CONECTADO
        System.out.println("Conectado a " + ip_server + ":" + puerto_server + "\n");

        // CREO EL SCANNER PARA INPUT
        Scanner sc = new Scanner(System.in);

        // ESTO ES COMO UN DO-WHILE
        System.out.println("###################################");
        System.out.println("Introduce tu nuevo mensaje. Debe empezar por un nº (si no empieza por un nº, termina): ");
        String t = sc.nextLine();
        while(t.charAt(0)>='0' && t.charAt(0)<='9'){

            // CREO EL PAQUETE
            DatagramPacket dp = null;
            try {
                dp = new DatagramPacket(
                        t.getBytes(StandardCharsets.UTF_8),// Información a enviar
                        t.getBytes(StandardCharsets.UTF_8).length,// Longitud de esa información
                        InetAddress.getByName(ip_server),// IP del servidor
                        Integer.parseInt(puerto_server)// Puerto
                );
            } catch (UnknownHostException e) {
                System.err.println("ERROR: no se pudo crear el paquete");
                throw new RuntimeException(e);
            }

            // ENVÍO EL PAQUETE
            try {
                s.send(dp);
                System.out.println("STATUS ― paquete enviado");
            } catch (IOException e) {
                System.err.println("ERROR: no se pudo enviar el paquete");
                throw new RuntimeException(e);
            }

            // RECIBO EL PAQUETE
            byte[] buffer = new byte[1000];

            DatagramPacket received_dp = new DatagramPacket(
                    buffer,// Zona de memoria
                    buffer.length// Tamaño de esa zona
            );

            try {
                System.out.println("STATUS ― esperando recepción de respuesta...");
                s.receive(received_dp);// dp se ha rellenado con los datos pertinentes

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String mensaje = new String(
                    received_dp.getData(),
                    received_dp.getOffset(),
                    received_dp.getLength(),
                    StandardCharsets.UTF_8
            );

            System.out.println("----------------------");
            System.out.println("RESPUESTA: " + mensaje);
            System.out.println("----------------------\n");

            // PIDO SIGUIENTE MENSAJE
            System.out.println("###################################");
            System.out.println("Introduce tu nuevo mensaje. Debe empezar por un nº (si no empieza por un nº, termina): ");
            t = sc.nextLine();
        }


        System.out.println("PROGRAMA FINALIZADO");
        s.close(); // libero memorias

    }
}
