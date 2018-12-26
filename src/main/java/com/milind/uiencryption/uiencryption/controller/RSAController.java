package com.milind.uiencryption.uiencryption.controller;

import com.milind.uiencryption.uiencryption.service.RSAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RSAController {

    @Autowired
    RSAService rsaService;

    @RequestMapping(value="/rsaasencryption", method = RequestMethod.POST)
    public String create(@RequestBody String value)  {

        String decryptedText = null;
        try{
            decryptedText = rsaService.decryptText(value);
            return  "Decryption Successful decrypted text:"+decryptedText;
        } catch (Exception e){
            e.printStackTrace();
            return  "Decryption failed due to reason:"+e.getMessage();
        }
    }
}
