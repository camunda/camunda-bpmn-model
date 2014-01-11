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
package org.camunda.bpm.model.core;

/**
 * {@link IllegalStateException} thrown if the model refuses to perform a state altering
 * operation which would result in the model being put in an illegal state.
 *
 * @author Daniel Meyer
 *
 */
public class IllegalModelStateException extends IllegalStateException {

  private static final long serialVersionUID = 1L;

  public IllegalModelStateException() {
    super();
  }

  public IllegalModelStateException(String message, Throwable cause) {
    super(message, cause);
  }

  public IllegalModelStateException(String s) {
    super(s);
  }

  public IllegalModelStateException(Throwable cause) {
    super(cause);
  }

}
