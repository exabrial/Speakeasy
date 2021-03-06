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

import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.AES;
import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.AES_GCM;
import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.AES_GCM_TAG_LENGTH;
import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.GCM_NONCE_LENGTH;
import static com.github.exabrial.speakeasy.internal.SpeakEasyConstants.SUN_JCE;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;

import com.github.exabrial.speakeasy.encoding.Base64StringEncoder;
import com.github.exabrial.speakeasy.primitives.Decrypter;
import com.github.exabrial.speakeasy.primitives.StringEncoder;
import com.github.exabrial.speakeasy.symmetric.SymmetricKey128;

/**
 * See corresponding class for information.
 *
 * @see AESGCMEncrypter
 */
@SuppressWarnings("PMD.TooManyStaticImports")
public class AESGCMDecrypter implements Decrypter {
	private final StringEncoder stringEncoder;
	private final SymmetricKey128 sharedKey;

	public AESGCMDecrypter(final SymmetricKey128 sharedKey) {
		this.stringEncoder = Base64StringEncoder.getSingleton();
		this.sharedKey = sharedKey;
	}

	public AESGCMDecrypter(final SymmetricKey128 sharedKey, final StringEncoder stringEncoder) {
		this.stringEncoder = stringEncoder;
		this.sharedKey = sharedKey;
	}

	@Override
	public String decrypt(final String message) {
		try {
			final byte[] messageBytes = stringEncoder.decodeStringToBytes(message);
			final byte[] iv = new byte[GCM_NONCE_LENGTH];
			System.arraycopy(messageBytes, 0, iv, 0, iv.length);
			final GCMParameterSpec gcmSpec = new GCMParameterSpec(AES_GCM_TAG_LENGTH, iv);
			final Cipher cipher = Cipher.getInstance(AES_GCM, SUN_JCE);
			cipher.init(Cipher.DECRYPT_MODE, sharedKey.toJCEKey(AES), gcmSpec, null);
			final byte[] cipherTextBytes = new byte[messageBytes.length - iv.length];
			System.arraycopy(messageBytes, iv.length, cipherTextBytes, 0, cipherTextBytes.length);
			final byte[] plainTextBytes = cipher.doFinal(cipherTextBytes);
			final String plainText = stringEncoder.stringFromBytes(plainTextBytes);
			return plainText;
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException
				| NoSuchAlgorithmException | NoSuchPaddingException | NoSuchProviderException e) {
			throw new RuntimeException(e);
		}
	}
}
