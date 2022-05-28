/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m9.uf1.eac3.b1;

/**
 *
 * @author isabel c
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;

import javax.net.ServerSocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

public class FitxerSegurServidor {

    private Socket socket;

    private static String SERVIDOR_CLAU = "C:\\Program Files\\Java\\jdk-17.0.1\\bin\\server_ks";
    private static String SERVIDOR_CLAU_PASSWORD = "server";
    public static void main(String[] args) throws Exception {
         
  System.out.println("Esperant connexions");

        System.setProperty("javax.net.ssl.trustStore", SERVIDOR_CLAU);
        SSLContext context = SSLContext.getInstance("TLS");

        KeyStore ks = KeyStore.getInstance("jceks");
        ks.load(new FileInputStream(SERVIDOR_CLAU), null);
        
        KeyManagerFactory kf = KeyManagerFactory.getInstance("SunX509");
        kf.init(ks, SERVIDOR_CLAU_PASSWORD.toCharArray());

        context.init(kf.getKeyManagers(), null, null);

        
        //implementa
        ServerSocketFactory factory = context.getServerSocketFactory();
        ServerSocket _socket = factory.createServerSocket(8443);

        ((SSLServerSocket) _socket).setNeedClientAuth(false);
        
        //Obtenim un client
        SSLSocket newSocket = (SSLSocket) _socket.accept();

            //Opté canal d'entrada
        InputStream in = newSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = br.readLine();
            while (line != null) {
                System.out.println(line.toUpperCase());
                line = br.readLine();
            }

            newSocket.close();
       
    }
 }


    