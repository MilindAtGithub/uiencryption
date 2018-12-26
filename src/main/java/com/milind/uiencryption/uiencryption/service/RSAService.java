package com.milind.uiencryption.uiencryption.service;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class RSAService {

    @Value("${app.private.key.path:private_key.der}")
    private String privateKeyPath;
    @Value("${app.public.key.path:public_key.der}")
    private String publicKeyPath;

    private PrivateKey privateKey;

    private  PublicKey publicKey;

    @PostConstruct
    public void init() throws Exception{
        com.sun.org.apache.xml.internal.security.Init.init();
        privateKey= getPrivateKey(privateKeyPath);
        publicKey=getPublicKey(publicKeyPath);
    }

    /**
     * Public method to decrypt the text
     * @param encryptedText
     * @return
     * @throws Exception
     */
    public String decryptText(String encryptedText) throws Exception{

        return decrypt(encryptedText,privateKey);
    }
    /**
     * This will read the file and generate the private key
     * @param filename
     * @return
     * @throws Exception
     */
    public PrivateKey getPrivateKey(String filename)
            throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    /**
     * This will read the file and generate the public key
     * @param filename
     * @return
     * @throws Exception
     */
    public PublicKey getPublicKey(String filename)
            throws Exception {

        byte[] keyBytes = Files.readAllBytes(Paths.get(filename));
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    /**
     * This will encrypt the text
     * @param plainText
     * @param publicKey
     * @return
     * @throws Exception
     */
    private String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encode(cipher.doFinal(plainText.getBytes()));
    }

    /**
     * This will decrypt the encrypted text
     * @param encryptedText
     * @param privateKey
     * @return
     * @throws Exception
     */
    private String decrypt(String encryptedText,  PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decode(encryptedText.getBytes())));
    }
}
