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

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;


/**
 * @author meyerd
 *
 */
public class TestCreateEmptyModel {

  @Test
  public void testCreateEmptyModel() {
    BpmnModel bpmnModel = Bpmn.createEmptyModel();
    bpmnModel.printModel();

    Definitions definitions = bpmnModel.getDefinitions();
    assertThat(definitions).isNull();

    BpmnElementFactory bpmnElementFactory = bpmnModel.getBpmnElementFactory();
    definitions = bpmnElementFactory.newDefinitionsElement();
    bpmnModel.setDefinitions(definitions);

    definitions = bpmnModel.getDefinitions();
    assertThat(definitions).isNotNull();

    bpmnModel.printModel();
  }

  @Test
  public void testAddElementsInWrongOrder() {
    BpmnModel bpmnModel = Bpmn.createEmptyModel();
    BpmnElementFactory bpmnElementFactory = bpmnModel.getBpmnElementFactory();
    Definitions definitions = bpmnElementFactory.newDefinitionsElement();
    bpmnModel.setDefinitions(definitions);
    definitions.getRootElements().add(bpmnElementFactory.newProcessElement());
    definitions.getImports().add(bpmnElementFactory.newImportElement());
    bpmnModel.printModel();
  }

}
