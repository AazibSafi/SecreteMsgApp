package com.example.smsmessenger;

import android.util.Base64;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class StringCryptor {

	private static final String CIPHER_ALGORITHM = "AES";
	private static final String RANDOM_GENERATOR_ALGORITHM = "SHA1PRNG";
	private static final int RANDOM_KEY_SIZE = 128;
	public static final byte[] PASSWORD = new byte[]{ 0x20, 0x32, 0x34, 0x47, (byte) 0x84, 0x33, 0x58 };

	// Encrypts string and encode in Base64
	public static String encrypt( String data ) throws Exception {
		byte[] secretKey = generateKey( PASSWORD );
		byte[] clear = data.getBytes();

		SecretKeySpec secretKeySpec = new SecretKeySpec( secretKey, CIPHER_ALGORITHM );
		Cipher cipher = Cipher.getInstance( CIPHER_ALGORITHM );
		cipher.init( Cipher.ENCRYPT_MODE, secretKeySpec );

		byte[] encrypted = cipher.doFinal( clear );
		String encryptedString = Base64.encodeToString( encrypted, Base64.DEFAULT );

		return encryptedString;
	}

	// Decrypts string encoded in Base64
	public static String decrypt( String encryptedData ) throws Exception {
		byte[] secretKey = generateKey( PASSWORD );

		SecretKeySpec secretKeySpec = new SecretKeySpec( secretKey, CIPHER_ALGORITHM );
		Cipher cipher = Cipher.getInstance( CIPHER_ALGORITHM );
		cipher.init( Cipher.DECRYPT_MODE, secretKeySpec );


		byte[] encrypted = Base64.decode( encryptedData.getBytes(), Base64.DEFAULT );
		byte[] decrypted = cipher.doFinal( encrypted );

		return new String( decrypted );
	}

	public static byte[] generateKey( byte[] seed ) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance( CIPHER_ALGORITHM );
		SecureRandom secureRandom = SecureRandom.getInstance( RANDOM_GENERATOR_ALGORITHM );
		secureRandom.setSeed( seed );
		keyGenerator.init( RANDOM_KEY_SIZE, secureRandom );
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

}
