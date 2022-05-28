/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m9.uf1.eac3.b1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore;
import java.util.Scanner;
import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class FitxerClientSegur {

    private static String CLAU_CLIENT = "C:\\Program Files\\Java\\jdk-17.0.1\\bin\\client_ks"; //heu de ficar la vostra direcció
    private static String CLAU_CLIENT_PASSWORD = "client";
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static Socket s;

    public static void main(String[] args) throws Exception {

        //Estableix el magatzem de claus a utilitzar per validar el certificat del servidor
        System.setProperty("javax.net.ssl.trustStore", CLAU_CLIENT);
        System.setProperty("javax.net.debug", "ssl,handshake");

        Scanner ask = new Scanner(System.in);
        System.out.println("RUTA DE LA KEY" + CLAU_CLIENT);
        FitxerClientSegur client = new FitxerClientSegur();

        s = client.clientAmbCert(); // ADICIÓN AL socket client el clientconcertificao

        writer = new PrintWriter(s.getOutputStream()); //
        reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
            //Envio missatge al servidor
        writer.println("Hola Servidor (Meritxell), Soc el Client (Núria) i tenim una connexió segura");
        writer.flush();

        //IMPLEMENTA

            while(true) {
                String answer = "";
                System.out.println("escribe el mensaje de prueba al server o SALIR");
                answer = ask.nextLine();

                if (answer.equalsIgnoreCase("SALIR")) {
                    writer.flush();
                    s.close();
                    break;
                }

                writer.println(answer);
                writer.flush();

            }
            //Llegeixo missatge que rebo del servido

    }

    private Socket clientSenseCert() throws Exception {
        SocketFactory sf = SSLSocketFactory.getDefault();
        Socket s = sf.createSocket("localhost", 8443);
        return s;
    }

    Socket clientAmbCert() throws Exception {
        SSLContext context = SSLContext.getInstance("TLS");
        KeyStore ks = KeyStore.getInstance("jceks");

        ks.load(new FileInputStream(CLAU_CLIENT), null);
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks, CLAU_CLIENT_PASSWORD.toCharArray());
        context.init(kf.getKeyManagers(), null, null);

        SocketFactory factory = context.getSocketFactory();
        Socket s = factory.createSocket("localhost", 8443);
        return s;
    }

}
