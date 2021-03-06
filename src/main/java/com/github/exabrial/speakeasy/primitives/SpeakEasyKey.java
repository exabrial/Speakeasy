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

import java.security.Key;

/**
 * A key that can be used by various SpeakEasy primitives.
 */
public interface SpeakEasyKey {
	/**
	 * Key the raw bytes for this key.
	 *
	 * @return key raw bytes.
	 */
	byte[] getKeyBytes();

	/**
	 * Transforms the key material into a key usable by the JCE.
	 *
	 * @return JCE representation of this Key
	 */
	Key toJCEKey();
}
