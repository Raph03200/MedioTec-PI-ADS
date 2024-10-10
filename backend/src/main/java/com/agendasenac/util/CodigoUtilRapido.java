package com.agendasenac.util;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.apache.tomcat.jni.Buffer;

import ch.qos.logback.core.net.SyslogOutputStream;


public class CodigoUtilRapido {
	
	public String FazerHash(String userSenha) {
		
		try {
	        // Criação de uma instância do algoritmo de hash (SHA-256 neste exemplo)
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        
	        // Atualização do algoritmo de hash com a senha
	        md.update(userSenha.getBytes());
	        
	        // Geração do hash
	        byte[] hashedPassword = md.digest();
	        
	        // Convertendo o hash para uma representação em String (Base64 neste exemplo)
	        return Base64.getEncoder().encodeToString(hashedPassword);
	    } catch (NoSuchAlgorithmException e) {
	        // Tratamento de exceção para caso o algoritmo de hash não seja suportado
	        e.printStackTrace();
	        return null;
	    }
	}
	
	
public String FazerHashId(Long userID) {
	
	String CodigoHasdo = null;
		
		try {
	        
	        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
	        buffer.putLong(userID);
	        byte[] longAsBytes = buffer.array();
	        
	     // Criação de uma instância do algoritmo de hash (SHA-256 neste exemplo)
	        MessageDigest md = MessageDigest.getInstance("SHA-256");
	        
	        byte[] hashBytes = md.digest(longAsBytes);
	        
	        StringBuilder hexString = new StringBuilder();
	        for (byte b: hashBytes) {
	        	String hex = Integer.toHexString(0xff & b);
	        	if(hex.length()==1) hexString.append('0');
	        	hexString.append(hex);
	        	CodigoHasdo = hexString.toString();
	        }
	        
	        
	            
	       
	    } catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    	return "Erro ao Fazer o Hash";
	    }
		
		return CodigoHasdo;
		
		
	}
	
	
}
