package com.biblioteca.aes;

import java.security.*;
import java.util.Calendar;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.*;

public class Aes {

    private static final String ALGORITHM = "AES";
    private static final byte[] keyValue = new byte[]{'T', 'h', 'i', 's',
        'I', 's', 'A', 'S', 'e', 'c', 'r', 'e', 't', 'K', 'e', 'y'};

    public static String encriptar(String valueToEnc) throws Exception {

        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encValue = c.doFinal(valueToEnc.getBytes());
        String encryptedValue = new BASE64Encoder().encode(encValue);
        return encryptedValue;
    }

    public static String desencriptar(String encryptedValue) throws Exception {

        Key key = generateKey();
        Cipher c = Cipher.getInstance(ALGORITHM);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedValue);
        byte[] decValue = c.doFinal(decordedValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private static Key generateKey() throws Exception {
        Key key = new SecretKeySpec(keyValue, ALGORITHM);
        return key;
    }

    public static void main(String[] args) throws Exception {
        String password = "jimaguere@gmail.com";

        String passwordEnc = Aes.encriptar(password.trim());
        String passwordDec = Aes.desencriptar(passwordEnc.trim());
        
        System.out.println("Original : " + password);
        System.out.println("Encriptado : " + passwordEnc);
        System.out.println("Desencriptado : " + passwordDec);
        Calendar calendar = Calendar.getInstance();
        Date fecha=new Date();
        calendar.setTime(fecha); // Configuramos la fecha que se recibe
        calendar.add(Calendar.DAY_OF_YEAR, 2);  // numero de días a añadir, o restar en caso de días<0
        System.out.println(calendar.getTime());
    }
}
