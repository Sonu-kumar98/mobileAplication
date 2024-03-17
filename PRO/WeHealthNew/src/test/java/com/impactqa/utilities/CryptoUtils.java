package com.impactqa.utilities;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.util.Base64;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;


public class CryptoUtils {

    private static final CryptoUtils cryptoUtils = new CryptoUtils();
    private static SecretKey key;

   private CryptoUtils() {
       try {
           String encryptionKeyStr = FrameworkConfig.getStringEnvProperty("encryptionKey");
           DESKeySpec keySpec = new DESKeySpec(encryptionKeyStr.getBytes("UTF8"));
           SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
           key = keyFactory.generateSecret(keySpec);
       }catch (Exception e)
       {
           throw new RuntimeException("Error occured while creating the singleton instance of CryptoUtils. ", e);
       }
   }

   public static String encryptTheValue(String plainText) throws Exception {
       Base64.Encoder base64encoder = Base64.getEncoder();
       byte[] cleartext = plainText.getBytes("UTF8");
       Cipher cipher = Cipher.getInstance("DES"); // cipher is not thread safe
       cipher.init(Cipher.ENCRYPT_MODE, key);
       String encrypedPwd = base64encoder.encodeToString(cipher.doFinal(cleartext));
       return encrypedPwd;
   }

    public static String decryptTheValue(String encryptedTextInput) throws Exception {
        Base64.Decoder base64decoder = Base64.getDecoder();
        byte[] encryptedBytes = base64decoder.decode(encryptedTextInput);
        Cipher cipher1 = Cipher.getInstance("DES");
        cipher1.init(Cipher.DECRYPT_MODE, key);
        byte[] plainTextPwdBytes = (cipher1.doFinal(encryptedBytes));
        return new String(plainTextPwdBytes);
    }

    public static void main(String[] args) throws Exception {

//        String plainText = "abc";
//        System.out.println(encryptTheValue(plainText));
//        System.out.println(cryptoUtils.decryptTheValue(encryptTheValue(plainText)));

//        System.out.println(cryptoUtils.decryptTheValue("KG8utvniwO8="));


        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);

        //Generate the pair of keys
        KeyPair pair = keyPairGen.generateKeyPair();

        //Getting the public key from the key pair
        PublicKey publicKey = pair.getPublic();




    }
}
