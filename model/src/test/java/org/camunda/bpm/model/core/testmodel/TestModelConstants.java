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
package org.camunda.bpm.model.core.testmodel;

/**
 * @author Daniel Meyer
 *
 */
public abstract class TestModelConstants {

  public final static String MODEL_NAME = "animals";
  public final static String MODEL_NAMESPACE = "http://camunda.org/animals";

  public final static String TYPE_NAME_ANIMAL = "animal";
  public final static String TYPE_NAME_FYING_ANIMAL = "flyingAnimal";

  public final static String ELEMENT_NAME_ANIMALS = "animals";
  public final static String ELEMENT_NAME_BIRD = "bird";

  public final static String ATTRIBUTE_NAME_ID = "id";

  private TestModelConstants() {

  }

}
