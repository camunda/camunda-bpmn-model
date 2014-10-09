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
package org.camunda.bpm.model.bpmn.instance;

import java.util.Collection;

import org.camunda.bpm.model.bpmn.MultiInstanceFlowConditionType;
import org.camunda.bpm.model.bpmn.instance.Expression;
import org.camunda.bpm.model.bpmn.instance.LoopCharacteristics;

/**
 * The BPMN 2.0 multiInstanceLoopCharacteristics element type
 * 
 * @author Filip Hrisafov
 * 
 */
public interface MultiInstanceLoopCharacteristics extends LoopCharacteristics {

  LoopCardinality getLoopCardinality();

  void setLoopCardinality(LoopCardinality loopCardinality);

  DataInput getLoopDataInputRef();

  void setLoopDataInputRef(DataInput loopDataInputRef);

  DataOutput getLoopDataOutputRef();

  void setLoopDataOutputRef(DataOutput loopDataOutputRef);

  InputDataItem getInputDataItem();

  void setInputDataItem(InputDataItem inputDataItem);

  OutputDataItem getOutputDataItem();

  void setOutputDataItem(OutputDataItem outputDataItem);

  Collection<ComplexBehaviorDefinition> getComplexBehaviorDefinitions();

  CompletionCondition getCompletionCondition();

  void setCompletionCondition(CompletionCondition completionCondition);

  boolean isSequentail();

  void setSequential(boolean sequential);

  MultiInstanceFlowConditionType getBehavior();

  void setBehavior(MultiInstanceFlowConditionType behavior);

  EventDefinition getOneBehaviorEventRef();

  void setOneBehaviorEventRef(EventDefinition oneBehaviorEventRef);

  EventDefinition getNoneBehaviorEventRef();

  void setNoneBehaviorEventRef(EventDefinition noneBehaviorEventRef);

}
