package com.impactqa.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.utils.Utils;

/**
 * Example showing use of the CryptoCipher API using a byte array
 */
public class CryptoUtils2 {

    SecretKeySpec key;
    IvParameterSpec iv;
    Properties properties;
    String transform;
    CryptoCipher encipher;
    ByteBuffer outBuffer;
    static final int bufferSize = 1024;
    int updateBytes;
    int finalBytes;

private CryptoUtils2()  {
    try {
        key = new SecretKeySpec(getUTF8Bytes("learningkids-a-z"), "AES");
        iv = new IvParameterSpec(getUTF8Bytes("learningkids-a-z"));

        properties = new Properties();
        properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.OPENSSL.getClassName());
        //Creates a CryptoCipher instance with the transformation and properties.
        transform = "AES/CBC/PKCS5Padding";
        int bufferSize = 1024;
    }catch (Exception e){
        throw new RuntimeException(e);
    }
}
    public  String encryptTheValue(String sampleInput) throws Exception {
//        System.out.println("input:  " + sampleInput);

        CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
        ByteBuffer inBuffer = ByteBuffer.allocateDirect(bufferSize);
        outBuffer = ByteBuffer.allocateDirect(bufferSize);
        inBuffer.put(getUTF8Bytes(sampleInput));
        inBuffer.flip(); // ready for the cipher to read it
        // Show the data is there
//        System.out.println("inBuffer=" + byteBufferToString(inBuffer));
        // Initializes the cipher with ENCRYPT_MODE,key and iv.
        encipher.init(Cipher.ENCRYPT_MODE, key, iv);
        // Continues a multiple-part encryption/decryption operation for byte buffer.
        updateBytes = encipher.update(inBuffer, outBuffer);
//        System.out.println(updateBytes);
        // We should call do final at the end of encryption/decryption.
        finalBytes = encipher.doFinal(inBuffer, outBuffer);
//        System.out.println(finalBytes);

          outBuffer.flip(); // ready for use as decrypt
          final byte [] encoded = new byte[updateBytes + finalBytes];
          outBuffer.duplicate().get(encoded);
        String returnOut = Base64.getEncoder().encodeToString(encoded);
//          System.out.println("Base64: "+returnOut);

        return returnOut;

    }

    public  String decryptTheValue(String encryptedTextInput) throws Exception {

        byte[] b64Decoded = Base64.getDecoder().decode(encryptedTextInput);

        ByteBuffer inBuffer = ByteBuffer.allocateDirect(bufferSize);
        inBuffer.put(b64Decoded);
        inBuffer.flip(); // ready for the cipher to read it

        CryptoCipher decipher = Utils.getCipherInstance(transform, properties);
        decipher.init(Cipher.DECRYPT_MODE, key, iv);
        ByteBuffer decoded = ByteBuffer.allocateDirect(bufferSize);
        decipher.update(inBuffer, decoded);
        decipher.doFinal(inBuffer, decoded);
        decoded.flip();
        String retDecryptPass = byteBufferToString(decoded);
//        System.out.println("decoded="+retDecryptPass);
        return retDecryptPass;
    }
        public static void main(final String[] args) throws Exception {
        String enc = new CryptoUtils2().encryptTheValue("123");
        System.out.println("enc: "+enc);

        String decr = new CryptoUtils2().decryptTheValue("P7NNDMyCa3LQ94rJeaubLg==");
        System.out.println("decr: "+decr);
    }

    /**
     * Converts String to UTF8 bytes
     *
     * @param input the input string
     * @return UTF8 bytes
     */
    private static byte[] getUTF8Bytes(final String input) {
        return input.getBytes(StandardCharsets.UTF_8);
    }

    private static String byteBufferToString(final ByteBuffer buffer) {
                 final ByteBuffer copy = buffer.duplicate();
                 final byte[] bytes = new byte[copy.remaining()];
                 copy.get(bytes);
                 return new String(bytes, StandardCharsets.UTF_8);
    }

    private static ByteBuffer byteArrayToByteBuffer(final byte[] byteArray) throws UnsupportedEncodingException {
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        return buffer;
    }

}