package br.com.prodama.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public class CriptografaSenha {

    public static String criptografaSenha(String senha) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
        String senhaCriptografada = hash.toString(16);
        if (senhaCriptografada.length() % 2 != 0) {
            senhaCriptografada = "0" + senhaCriptografada;
        }
        return senhaCriptografada;
    }
    
    public static String criptografa(String senha){
    	 return new Base64().encodeToString(senha.getBytes());
    }
    
    public static String descriptografa(String senha){
    	return new String(new Base64().decode(senha));	
   }
}
