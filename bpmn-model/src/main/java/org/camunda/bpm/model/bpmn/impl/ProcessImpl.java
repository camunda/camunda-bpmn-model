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
package org.camunda.bpm.model.bpmn.impl;

import org.camunda.bpm.model.bpmn.CallableElement;
import org.camunda.bpm.model.bpmn.FlowElement;
import org.camunda.bpm.model.bpmn.Process;
import org.camunda.bpm.model.bpmn.ProcessType;
import org.camunda.bpm.xml.model.ModelBuilder;
import org.camunda.bpm.xml.model.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.xml.model.type.*;
import org.camunda.bpm.xml.model.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;
import org.camunda.bpm.xml.model.type.attribute.Attribute;
import org.camunda.bpm.xml.model.type.child.ChildElementCollection;
import org.camunda.bpm.xml.model.type.child.SequenceBuilder;

import java.util.Collection;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 *
 * @author Daniel Meyer
 *
 */
public class ProcessImpl extends CallableElementImpl implements Process {

  public static ModelElementType MODEL_TYPE;

  static Attribute<ProcessType> processTypeAttr;
  static Attribute<Boolean> isClosedAttr;
  static Attribute<Boolean> isExecutableAttr;

  static ChildElementCollection<FlowElement> flowElementsColl;

  public static void registerType(ModelBuilder modelBuilder) {

    ModelElementTypeBuilder builder = modelBuilder.defineType(Process.class, BPMN_ELEMENT_PROCESS)
      .namespaceUri(BPMN20_NS)
      .extendsType(CallableElement.class)
      .instanceProvider(new ModelTypeInstanceProvider<Process>() {
        public Process newInstance(ModelTypeInstanceContext instanceContext) {
          return new ProcessImpl(instanceContext);
        }
      });

    processTypeAttr = builder.enumAttribute(BPMN_ATTRIBUTE_PROCESS_TYPE, ProcessType.class)
      .defaultValue(ProcessType.None)
      .build();

    isClosedAttr = builder.booleanAttribute(BPMN_ATTRIBUTE_IS_CLOSED)
      .defaultValue(false)
      .build();

    isExecutableAttr = builder.booleanAttribute(BPMN_ATTRIBUTE_IS_EXECUTABLE)
      .defaultValue(false)
      .build();

    SequenceBuilder sequence = builder.sequence();

    flowElementsColl = sequence.elementCollection(FlowElement.class, BPMN_TYPE_FLOW_ELEMENT)
      .build();

    MODEL_TYPE = builder.build();
  }

  public ProcessImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public ProcessType getProcessType() {
    return processTypeAttr.getValue(this);
  }

  public void setProcessType(ProcessType processType) {
    processTypeAttr.setValue(this, processType);
  }

  public boolean isClosed() {
    return isClosedAttr.getValue(this);
  }

  public void setClosed(boolean closed) {
    isClosedAttr.setValue(this, closed);
  }

  public boolean isExecutable() {
    return isExecutableAttr.getValue(this);
  }

  public void setExecutable(boolean executable) {
    isExecutableAttr.setValue(this, executable);
  }

  public Collection<FlowElement> getFlowElements() {
    return flowElementsColl.get(this);
  }

}
