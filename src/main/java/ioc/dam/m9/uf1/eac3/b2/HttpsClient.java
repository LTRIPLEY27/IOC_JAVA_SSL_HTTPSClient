/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ioc.dam.m9.uf1.eac3.b2;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.io.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class HttpsClient{
	
   public static void main(String[] args)
   {
        new HttpsClient().testeig();
   }
	
   private void testeig(){

      String https_url = "https://www.google.es/";
      URL url;
      try {

	     url = new URL(https_url);
	     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			
	     //metode per treure la informació del certificat
	     print_https_cert(con);
			
	     //metode per treure tota la informació
	     print_contingut(con);
			
      } catch (MalformedURLException e) {
	     e.printStackTrace();
      } catch (IOException e) {
	     e.printStackTrace();
      }

   }
	
   private void print_https_cert(HttpsURLConnection con){
     
    if(con!=null){
        //IMPLEMENTA
        try {
            System.out.println("Código de respuesta :  " + con.getResponseCode());
            System.out.println("Cipher Suite : " + con.getCipherSuite() + "\n\n");

            Certificate [] certificados = con.getServerCertificates(); // OBTIENE EL ARRAY

            for( Certificate certificado : certificados ) {
                System.out.println("Certificado Tipo : " + certificado.getType());
                System.out.println("Certificado Código Hash: " + certificado.hashCode());
                System.out.println("Certificado Public key Algorithmo : " + certificado.getPublicKey().getAlgorithm());
                System.out.println("Certificado Formato de la Public Key : " + certificado.getPublicKey().getFormat() + "\n\n");
            }
        } catch  (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
	
   }
	
   private void print_contingut(HttpsURLConnection con){
	if(con!=null){
			
	try {

        //IMPLEMENTA
        System.out.println("********* Contenido de la URL  ********* \n");
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String entrada;

        while ((entrada = br.readLine()) != null) {
            System.out.println(entrada);
        }
        br.close();
				
	} catch (IOException e) {
	   e.printStackTrace();
	}
			
       }
		
   }
	
}
