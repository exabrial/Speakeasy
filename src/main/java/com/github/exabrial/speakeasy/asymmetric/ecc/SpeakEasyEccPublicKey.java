package com.github.exabrial.speakeasy.asymmetric.ecc;

import static com.github.exabrial.speakeasy.internal.ECCConstants.GEN_ALG;

import java.security.PublicKey;

import com.github.exabrial.speakeasy.asymmetric.SpeakEasyPublicKey;

public class SpeakEasyEccPublicKey implements SpeakEasyPublicKey {
	public final PublicKey publicKey;

	public SpeakEasyEccPublicKey(final PublicKey publicKey) {
		if (!publicKey.getAlgorithm().equals(GEN_ALG)) {
			// TODO
			throw new RuntimeException("unsupportted alg");
		} else {
			this.publicKey = publicKey;
		}
	}

	@Override
	public byte[] getKeyBytes() {
		return publicKey.getEncoded();
	}

	@Override
	public PublicKey toKey() {
		return publicKey;
	}
}