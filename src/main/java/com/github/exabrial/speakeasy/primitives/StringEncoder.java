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

package com.github.exabrial.speakeasy.primitives;

import java.nio.charset.StandardCharsets;

/**
 * Transforms a byte[] to a textual representation, and vice versa. All strings
 * are assumed to be UTF-8. Implementations do not have to be deterministic, but
 * must be fully reversible.
 */
public interface StringEncoder {
	/**
	 * Encodes a byte[] to a string representation.
	 * 
	 * @param message
	 *          the byte[] to transform
	 * @return a String representation of said bytes
	 */
	String encodeBytesAsString(byte[] message);

	/**
	 * Decodes a string into bytes.
	 * 
	 * @param message
	 *          The input string to decode
	 * @return a byte representation of said string
	 */
	byte[] decodeStringToBytes(String message);

	default byte[] getStringAsBytes(final String message) {
		return message.getBytes(StandardCharsets.UTF_8);
	}

	default String stringFromBytes(final byte[] message) {
		return new String(message, StandardCharsets.UTF_8);
	}
}
