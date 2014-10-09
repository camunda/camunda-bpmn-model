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

import org.camunda.bpm.model.bpmn.MultiInstanceFlowConditionType;
import org.camunda.bpm.model.bpmn.impl.instance.LoopDataInputRef;
import org.camunda.bpm.model.bpmn.impl.instance.LoopDataOutputRef;

/**
 * @author Filip Hrisafov
 */
public class MultiInstanceLoopCharacteristicsTest extends BpmnModelElementInstanceTest {

  public TypeAssumption getTypeAssumption() {
    return new TypeAssumption(LoopCharacteristics.class, false);
  }
  public Collection<ChildElementAssumption> getChildElementAssumptions() {
    return Arrays.asList(new ChildElementAssumption(LoopCardinality.class, 0, 1),
        new ChildElementAssumption(LoopDataInputRef.class, 0, 1), 
        new ChildElementAssumption(LoopDataOutputRef.class, 0, 1), 
        new ChildElementAssumption(OutputDataItem.class, 0, 1),
        new ChildElementAssumption(InputDataItem.class, 0, 1), 
        new ChildElementAssumption(ComplexBehaviorDefinition.class), 
        new ChildElementAssumption(CompletionCondition.class, 0, 1));
  }

  public Collection<AttributeAssumption> getAttributesAssumptions() {
    return Arrays.asList(new AttributeAssumption("isSequential", false, false, false),
        new AttributeAssumption("behavior", false, false, MultiInstanceFlowConditionType.All),
        new AttributeAssumption("oneBehaviorEventRef"), 
        new AttributeAssumption("noneBehaviorEventRef"));
  }
}
