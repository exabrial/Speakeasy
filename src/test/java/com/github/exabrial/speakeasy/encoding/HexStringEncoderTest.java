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

package com.github.exabrial.speakeasy.encoding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class HexStringEncoderTest {
	private final HexStringEncoder encoder = HexStringEncoder.getSingleton();
	private final byte[] testVectorBytes = new byte[] { 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7 };
	private final String testVectorString = "01020304050607";

	@Test
	public void testEncodeBytesAsString() {
		final String encodeBytesAsString = encoder.encodeBytesAsString(testVectorBytes);
		assertEquals(testVectorString, encodeBytesAsString);
	}

	@Test
	public void testDecodeStringToBytes() {
		final byte[] decodeStringToBytes = encoder.decodeStringToBytes(testVectorString);
		assertTrue(Arrays.equals(decodeStringToBytes, testVectorBytes));
	}

	@Test
	public void testEncodeNull() {
		assertNull(encoder.encodeBytesAsString(null));
	}

	@Test
	public void testDecodeNull() {
		assertNull(encoder.decodeStringToBytes(null));
	}

	@Test
	public void testEncode0Length() {
		assertEquals("", encoder.encodeBytesAsString(new byte[0]));
	}

	@Test
	public void testDecode0Length() {
		assertTrue(Arrays.equals(new byte[0], encoder.decodeStringToBytes("")));
	}
}
