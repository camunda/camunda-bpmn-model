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

package org.camunda.bpm.model.bpmn.builder;

import org.camunda.bpm.model.bpmn.BpmnModelException;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.impl.instance.ConditionExpression;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.util.Collection;

/**
 * @author Sebastian Menski
 */
public abstract class AbstractFlowNodeBuilder<B extends AbstractFlowNodeBuilder<B, E>, E extends FlowNode> extends AbstractFlowElementBuilder<B, E> {

  private SequenceFlowBuilder currentSequenceFlowBuilder;

  protected AbstractFlowNodeBuilder(BpmnModelInstance modelInstance, E element, Class<?> selfType) {
    super(modelInstance, element, selfType);
  }

  private SequenceFlowBuilder getCurrentSequenceFlowBuilder() {
    if (currentSequenceFlowBuilder == null) {
      SequenceFlow sequenceFlow = createSibling(SequenceFlow.class);
      currentSequenceFlowBuilder = sequenceFlow.builder();
    }
    return currentSequenceFlowBuilder;
  }

  public B condition(String name, String condition) {
    if (name != null) {
      getCurrentSequenceFlowBuilder().name(name);
    }
    ConditionExpression conditionExpression = modelInstance.newInstance(ConditionExpression.class);
    conditionExpression.setTextContent(condition);
    getCurrentSequenceFlowBuilder().condition(conditionExpression);
    return myself;
  }

  public B sequenceFlowCondition(String condition) {
    return condition(null, condition);
  }

  private void connectTarget(FlowNode target) {
    getCurrentSequenceFlowBuilder()
      .from(element)
      .to(target);

    currentSequenceFlowBuilder = null;
  }

  private <T extends FlowNode> T createTarget(Class<T> typeClass) {
    T target = createSibling(typeClass);
    connectTarget(target);
    return target;
  }

  public UserTaskBuilder userTask() {
    return createTarget(UserTask.class).builder();
  }

  public ServiceTaskBuilder serviceTask() {
    return createTarget(ServiceTask.class).builder();
  }

  public ScriptTaskBuilder scriptTask() {
    return createTarget(ScriptTask.class).builder();
  }

  public EndEventBuilder endEvent() {
    return createTarget(EndEvent.class).builder();
  }

  public ParallelGatewayBuilder parallelGateway() {
    return createTarget(ParallelGateway.class).builder();
  }

  public ExclusiveGatewayBuilder exclusiveGateway() {
    return createTarget(ExclusiveGateway.class).builder();
  }

  public Gateway findLastGateway() {
    FlowNode lastGateway = element;
    while (true) {
      Collection<FlowNode> previousNodes = lastGateway.getPreviousNodes();
      if (previousNodes.size() > 1) {
        throw new BpmnModelException("Unable to determine unique previous node of " + lastGateway.getId());
      }
      else if (previousNodes.isEmpty()) {
        return null;
      }
      else {
        lastGateway = previousNodes.iterator().next();
        if (lastGateway instanceof Gateway) {
          return (Gateway) lastGateway;
        }
      }
    }
  }

  public AbstractGatewayBuilder parallel() {
    Gateway lastGateway = findLastGateway();
    if (lastGateway == null) {
      throw new BpmnModelException("No previous gateway found for element " + element.getId());
    }
    else {
      return lastGateway.builder();
    }
  }

  public AbstractGatewayBuilder parallel(String identifier) {
    ModelElementInstance instance = modelInstance.getModelElementById(identifier);
    if (instance instanceof Gateway) {
      return ((Gateway) instance).builder();
    }
    else {
      throw new BpmnModelException("Gateway not found for id " + identifier);
    }
  }

  public AbstractFlowNodeBuilder connectTo(String identifier) {
    ModelElementInstance target = modelInstance.getModelElementById(identifier);
    if (target == null) {
      throw new BpmnModelException("Unable to connect " + element.getId() + " to element " + identifier + " cause it not exists.");
    }
    else if (!(target instanceof FlowNode)) {
      throw new BpmnModelException("Unable to connect " + element.getId() + " to element " + identifier + " cause its not a flow node.");
    }
    else {
      FlowNode targetNode = (FlowNode) target;
      connectTarget(targetNode);
      return targetNode.builder();
    }
  }

}
