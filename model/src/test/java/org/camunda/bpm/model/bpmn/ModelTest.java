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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.junit.Test;

public class ModelTest {

  @Test
  public void testCreateEmptyModel() {
    BpmnModelInstance bpmnModelInstance = Bpmn.createEmptyModel();

    Definitions definitions = bpmnModelInstance.getDefinitions();
    assertThat(definitions).isNull();

    definitions = bpmnModelInstance.newInstance(Definitions.class);
    bpmnModelInstance.setDefinitions(definitions);

    definitions = bpmnModelInstance.getDefinitions();
    assertThat(definitions).isNotNull();
  }

  @Test
  public void testBaseTypeCalculation() {
    BpmnModelInstance bpmnModelInstance = Bpmn.createEmptyModel();
    Model model = bpmnModelInstance.getModel();
    Collection<ModelElementType> allBaseTypes = ModelUtil.calculateAllBaseTypes(model, StartEvent.class);
    assertThat(allBaseTypes).hasSize(5);

    allBaseTypes = ModelUtil.calculateAllBaseTypes(model, MessageEventDefinition.class);
    assertThat(allBaseTypes).hasSize(3);

    allBaseTypes = ModelUtil.calculateAllBaseTypes(model, BaseElement.class);
    assertThat(allBaseTypes).hasSize(0);
  }

  @Test
  public void testExtendingTypeCalculation() {
    BpmnModelInstance bpmnModelInstance = Bpmn.createEmptyModel();
    List<Class<? extends ModelElementInstance>> baseInstanceTypes = new ArrayList<Class<? extends ModelElementInstance>>();
    baseInstanceTypes.add(Event.class);
    baseInstanceTypes.add(CatchEvent.class);
    baseInstanceTypes.add(ExtensionElements.class);
    baseInstanceTypes.add(EventDefinition.class);
    Collection<ModelElementType> allExtendingTypes = ModelUtil.calculateAllExtendingTypes(bpmnModelInstance.getModel(), baseInstanceTypes);
    assertThat(allExtendingTypes).hasSize(3);
  }

}
