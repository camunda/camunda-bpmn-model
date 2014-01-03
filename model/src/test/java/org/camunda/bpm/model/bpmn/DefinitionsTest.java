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
package org.camunda.bpm.model.bpmn;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.XML_SCHEMA_NS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.XPATH_NS;
import static org.fest.assertions.Assertions.assertThat;

import org.camunda.bpm.model.bpmn.util.BpmnModelResource;
import org.camunda.bpm.model.core.impl.ModelParseException;
import org.junit.Test;

/**
 * @author Daniel Meyer
 *
 */
public class DefinitionsTest extends BpmnModelTest {

  @Test
  @BpmnModelResource
  public void shouldImportEmptyDefinitions() {

    Definitions definitions = bpmnModel.getDefinitions();
    assertThat(definitions).isNotNull();

    // provided in file
    assertThat(definitions.getTargetNamespace()).isEqualTo("http://camunda.org/test");

    // defaults provided in Schema
    assertThat(definitions.getExpressionLanguage()).isEqualTo(XPATH_NS);
    assertThat(definitions.getTypeLanguage()).isEqualTo(XML_SCHEMA_NS);

    // not provided in file
    assertThat(definitions.getExporter()).isNull();
    assertThat(definitions.getExporterVersion()).isNull();
    assertThat(definitions.getId()).isNull();
    assertThat(definitions.getName()).isNull();

    // has no imports
    assertThat(definitions.getImports()).isEmpty();

  }

  @Test
  public void shouldNotImportWrongOrderedSequence() {
    try {
      BpmnModel model = Bpmn.readModelFromStream(getClass().getResourceAsStream("DefinitionsTest.shouldNotImportWrongOrderedSequence.bpmn"));
    }
    catch(ModelParseException e) {
      e.printStackTrace();
    }
  }

}
