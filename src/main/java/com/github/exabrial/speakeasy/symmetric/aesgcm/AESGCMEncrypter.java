/**
 * Copyright [2018] [Jonathan S. Fisher]
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.github.exabrial.speakeasy.symmetric.aesgcm;

import static com.github.exabrial.speakeasy.encoding.Base64StringEncoder.getSingleton;
import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.AES_GCM;
import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.AES_GCM_TAG_LENGTH;
import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.GCM_NONCE_LENGTH;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;

import com.github.exabrial.speakeasy.encoding.StringEncoder;
import com.github.exabrial.speakeasy.entropy.NativeThreadLocalSecureRandomProvider;
import com.github.exabrial.speakeasy.primitives.Encrypter;
import com.github.exabrial.speakeasy.primitives.SecureRandomProvider;
import com.github.exabrial.speakeasy.symmetric.SymmetricKey;

public class AESGCMEncrypter implements Encrypter {
	private final StringEncoder stringEncoder;
	private final SymmetricKey sharedKey;
	private final SecureRandomProvider secureRandomProvider;

	public AESGCMEncrypter(final SymmetricKey sharedKey) {
		this.stringEncoder = getSingleton();
		this.sharedKey = sharedKey;
		this.secureRandomProvider = NativeThreadLocalSecureRandomProvider.getSingleton();
	}

	public AESGCMEncrypter(final SymmetricKey sharedKey, final StringEncoder stringEncoder,
			final SecureRandomProvider secureRandomProvider) {
		this.stringEncoder = stringEncoder;
		this.sharedKey = sharedKey;
		this.secureRandomProvider = secureRandomProvider;
	}

	@Override
	public String encrypt(final String plainText) {
		try {
			final byte[] iv = new byte[GCM_NONCE_LENGTH];
			final SecureRandom secureRandom = secureRandomProvider.borrowSecureRandom();
			secureRandom.nextBytes(iv);
			final GCMParameterSpec gcmSpec = new GCMParameterSpec(AES_GCM_TAG_LENGTH, iv);
			final Cipher cipher = Cipher.getInstance(AES_GCM);
			cipher.init(Cipher.ENCRYPT_MODE, sharedKey.toKey(), gcmSpec, secureRandom);
			final byte[] plainTextBytes = stringEncoder.getStringAsBytes(plainText);
			final byte[] cipherTextBytes = cipher.doFinal(plainTextBytes);
			final byte[] ivAndCipherText = new byte[iv.length + cipherTextBytes.length];
			System.arraycopy(iv, 0, ivAndCipherText, 0, iv.length);
			System.arraycopy(cipherTextBytes, 0, ivAndCipherText, iv.length, cipherTextBytes.length);
			final String encodedmessage = stringEncoder.encodeBytesAsString(ivAndCipherText);
			return encodedmessage;
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}
}
