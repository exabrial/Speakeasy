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

import java.util.Base64;

public class Base64StringEncoder implements StringEncoder {
  public static Base64StringEncoder getSingleton() {
    return Singleton.Instance.encoder;
  }

  private static enum Singleton {
    Instance;
    public final Base64StringEncoder encoder;

    Singleton() {
      this.encoder = new Base64StringEncoder();
    }
  }

  private Base64StringEncoder() {
  }

  @Override
  public String encodeBytesAsString(final byte[] message) {
    return Base64.getEncoder().encodeToString(message);
  }

  @Override
  public byte[] decodeStringToBytes(final String message) {
    return Base64.getDecoder().decode(message);
  }
}