package com.paymentsprocessor.highspeedpayments.service;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.security.*;
import java.util.Base64;

@Service
public class CryptoService {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public String sign(String data) throws Exception {
        Signature rsa = Signature.getInstance("SHA256withRSA");
        rsa.initSign(privateKey);
        rsa.update(data.getBytes());
        byte[] signature = rsa.sign();
        return Base64.getEncoder().encodeToString(signature);
    }
}