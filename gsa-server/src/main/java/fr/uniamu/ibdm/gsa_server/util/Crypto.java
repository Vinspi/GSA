package fr.uniamu.ibdm.gsa_server.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {

    static public byte[] hashPassword(byte[] salt, byte[] password){

        try {

            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt);
            byte[] hash = md.digest(password);
            return hash;

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

}
