/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.model.bpmn.instance;

import java.util.Arrays;
import java.util.Collection;

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;

/**
 * @author Sebastian Menski
 */
public class ServiceTaskTest extends BpmnModelElementInstanceTest {

  public TypeAssumption getTypeAssumption() {
    return new TypeAssumption(Task.class, false);
  }

  public Collection<ChildElementAssumption> getChildElementAssumptions() {
    return null;
  }

  public Collection<AttributeAssumption> getAttributesAssumptions() {
    return Arrays.asList(
      new AttributeAssumption("implementation", false, false, "##WebService"),
      new AttributeAssumption("operationRef"),
      new AttributeAssumption("class", BpmnModelConstants.CAMUNDA_NS),
      new AttributeAssumption("delegateExpression", BpmnModelConstants.CAMUNDA_NS),
      new AttributeAssumption("expression", BpmnModelConstants.CAMUNDA_NS)
    );
  }
}
