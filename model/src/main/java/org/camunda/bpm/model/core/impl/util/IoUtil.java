/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.model.core.impl.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * @author Daniel Meyer
 * @author Sebastian Menski
 *
 */
public class IoUtil {

  public static void closeSilently(Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (Exception e) {
      // ignored
    }
  }

  /**
   * Converst an {@link InputStream} to a {@link String}
   *
   * @param inputStream the {@link InputStream} to convert
   * @return the resulting {@link String}
   * @throws IOException
   */
  public static String getStringFromInputStream(InputStream inputStream) throws IOException {
    BufferedReader bufferdReader = null;
    StringBuilder stringBuilder = new StringBuilder();
    try {
      bufferdReader = new BufferedReader(new InputStreamReader(inputStream));
      String line;
      while ((line = bufferdReader.readLine()) != null) {
        stringBuilder.append(line.trim());
      }
    }
    finally {
      closeSilently(bufferdReader);
    }

    return stringBuilder.toString();
  }

  /**
   * Converts a {@link OutputStream} to an {@link InputStream} by coping the data directly.
   * WARNING: Do not use for large data (>100MB). Only for testing purpose.
   *
   * @param outputStream the {@link OutputStream} to convert
   * @return the resulting {@link InputStream}
   */
  public static InputStream convertOutputStreamToInputStream(OutputStream outputStream) {
    byte[] data = ((ByteArrayOutputStream) outputStream).toByteArray();
    InputStream inputStream = new ByteArrayInputStream(data);
    return inputStream;
  }
}
