package com.github.exabrial.speakeasy.symmetric.aesgcm;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.junit.Test;

import com.github.exabrial.speakeasy.symmetric.SymmetricKey;

public class AESGCMEncrypterTest {

	@Test
	public void testEncrypt() throws Exception {
		byte[] encodedKey = new byte[16];
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey key = keyGen.generateKey();
		SymmetricKey sharedKey = new SymmetricKey(key);
		AESGCMEncrypter encrypter = new AESGCMEncrypter(sharedKey);
		String cipherText = encrypter.encrypt("plainText");
		System.out.println(cipherText);
		AESGCMDecrypter decrypter = new AESGCMDecrypter(sharedKey);
		System.out.println(decrypter.decrypt(cipherText));
	}

}
