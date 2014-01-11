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

import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.testmodel.TestModelParser;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

/**
 * @author Daniel Meyer
 *
 */
public class ModelElementInstanceTest {

  @Test
  public void shouldReturnTextContent() {
    ModelInstance modelInstance = new TestModelParser()
      .parseModelFromStream(getClass().getResourceAsStream("ModelElementInstanceTest.textContent.xml"));

    ModelElementInstance tweety = modelInstance.getModelElementById("tweety");
    assertThat(tweety.getTextContent()).isEqualTo("");

    ModelElementInstance donald = modelInstance.getModelElementById("donald");
    assertThat(donald.getTextContent()).isEqualTo("some text content");

    ModelElementInstance daisy = modelInstance.getModelElementById("daisy");
    assertThat(daisy.getTextContent()).isEqualTo("\n        some text content with outer line breaks\n    ");

    ModelElementInstance hedwig = modelInstance.getModelElementById("hedwig");
    assertThat(hedwig.getTextContent()).isEqualTo("\n        some text content with inner\n        line breaks\n    ");
  }

}
