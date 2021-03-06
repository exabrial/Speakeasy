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
package com.github.exabrial.speakeasy.comporator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class ConstantTimeMessageComporatorTest {
	private final SecureMessageComporator comporator = SecureMessageComporator.getSingleton();

	@Test
	public void testCompare() {
		assertTrue(comporator.compare("calculatedFingerprint".getBytes(), "calculatedFingerprint".getBytes()));
	}

	@Test
	public void testCompare_false() {
		final byte[] bytes = "calculatedFingerprint".getBytes();
		bytes[5] = 0;
		assertFalse(comporator.compare("calculatedFingerprint".getBytes(), bytes));
	}

	@Test
	public void testCompare_false2() {
		assertFalse(comporator.compare("NotcalculatedFingerprint".getBytes(), "calculatedFingerprint".getBytes()));
	}

	@Test
	public void testCompare_null() {
		final Executable executable = () -> {
			comporator.compare(null, "calculatedFingerprint".getBytes());
		};
		assertThrows(NullPointerException.class, executable);
	}

	@Test
	public void testCompare_null2() {
		final Executable executable = () -> {
			comporator.compare("NotcalculatedFingerprint".getBytes(), null);
		};
		assertThrows(NullPointerException.class, executable);
	}
}
