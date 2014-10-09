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
package org.camunda.bpm.model.bpmn.impl.instance;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN20_NS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_COMPLEX_BEHAVIOR_DEFINITION;

import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.ComplexBehaviorDefinition;
import org.camunda.bpm.model.bpmn.instance.Condition;
import org.camunda.bpm.model.bpmn.instance.ImplicitThrowEvent;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;
import org.camunda.bpm.model.xml.type.child.ChildElement;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

/**
 * The BPMN 2.0 complexBehaviorDefinition element
 * 
 * @author Filip Hrisafov
 */
public class ComplexBehaviorDefinitionImpl extends BaseElementImpl implements
    ComplexBehaviorDefinition {

  // Use condition since tFormalExpression is not fully supported by
  // the model API
  // See https://groups.google.com/d/msg/camunda-bpm-users/n6j13SjbjqE/ORE6u3WT5NkJ
  protected static ChildElement<Condition> conditionChild;
  protected static ChildElement<ImplicitThrowEvent> eventChild;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder
        .defineType(ComplexBehaviorDefinition.class, BPMN_ELEMENT_COMPLEX_BEHAVIOR_DEFINITION)
        .namespaceUri(BPMN20_NS).extendsType(BaseElement.class)
        .instanceProvider(new ModelTypeInstanceProvider<ComplexBehaviorDefinition>() {
          public ComplexBehaviorDefinition newInstance(ModelTypeInstanceContext instanceContext) {
            return new ComplexBehaviorDefinitionImpl(instanceContext);
          }
        });

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    conditionChild = sequenceBuilder.element(Condition.class).required()
        .build();

    eventChild = sequenceBuilder.element(ImplicitThrowEvent.class)
    		.minOccurs(0).maxOccurs(1).build();

    typeBuilder.build();
  }

  public ComplexBehaviorDefinitionImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  @Override
  public Condition getCondition() {
    return conditionChild.getChild(this);
  }

  @Override
  public void setCondition(Condition condition) {
    conditionChild.setChild(this, condition);

  }

  @Override
  public ImplicitThrowEvent getEvent() {
    return eventChild.getChild(this);
  }

  @Override
  public void setEvent(ImplicitThrowEvent event) {
    eventChild.setChild(this, event);
  }

}
