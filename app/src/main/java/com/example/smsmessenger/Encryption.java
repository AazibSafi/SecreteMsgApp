package com.example.smsmessenger;

import android.util.Base64;

public class Encryption {

    // Encrypts string and encode in Base64
    public static String encrypt( String data ) throws Exception {
        return Base64.encodeToString( data.getBytes(), Base64.DEFAULT );
    }

    // Decrypts string encoded in Base64
    public static String decrypt( String encryptedData ) throws Exception {
        byte[] decrypted = Base64.decode( encryptedData.getBytes(), Base64.DEFAULT );
        return new String( decrypted );
    }

}
