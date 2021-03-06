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

package com.github.exabrial.speakeasy.fingerprint;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.github.exabrial.speakeasy.fingerprint.Blake2b256Fingerprinter;
import com.github.exabrial.speakeasy.testing.StringBytesEncoder;

public class Blake2b256FingerprinterTest {
	private final String testVector = "hello";
	private final String encodedTestVectorFingerprint = ("324dcf027dd4a30a932c441f365a25e86b173defa4b8e58948253471b81b72cf")
			.toUpperCase();

	@Test
	public void testFingerprint() {
		final Blake2b256Fingerprinter fingerprinter = new Blake2b256Fingerprinter(new StringBytesEncoder());
		final String fingerprint = fingerprinter.fingerprint(testVector);
		assertEquals(encodedTestVectorFingerprint, fingerprint);
	}

	@Test
	public void testVerifyFingerprint() {
		final Blake2b256Fingerprinter fingerprinter = new Blake2b256Fingerprinter();
		final String fingerprint = fingerprinter.fingerprint(testVector);
		assertTrue(fingerprinter.verifyFingerprint(testVector, fingerprint));
	}
}
