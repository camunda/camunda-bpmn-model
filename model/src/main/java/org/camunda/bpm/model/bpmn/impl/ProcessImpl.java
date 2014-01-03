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

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

import org.camunda.bpm.model.bpmn.Process;
import org.camunda.bpm.model.bpmn.ProcessType;
import org.camunda.bpm.model.core.impl.ModelElementCreateContext;
import org.camunda.bpm.model.core.impl.attribute.Attribute;
import org.camunda.bpm.model.core.impl.attribute.BooleanAttribute;
import org.camunda.bpm.model.core.impl.attribute.EnumAttribute;

/**
 *
 * @author Daniel Meyer
 *
 */
public class ProcessImpl extends CallableElementImpl implements Process {

  public static final String ELEMENT_NAME = BPMN_ELEMENT_PROCESS;

  protected Attribute<ProcessType> processTypeAttr = new EnumAttribute<ProcessType>(BPMN_ATTRIBUTE_PROCESS_TYPE, ProcessType.class, this);
  protected Attribute<Boolean> isClosedAttr = new BooleanAttribute(BPMN_ATTRIBUTE_IS_CLOSED, this);
  protected Attribute<Boolean> isExecutableAttr = new BooleanAttribute(BPMN_ATTRIBUTE_IS_EXECUTABLE, this);

  public ProcessImpl(ModelElementCreateContext context) {
    super(context);
  }

  public ProcessType getProcessType() {
    return processTypeAttr.getValue();
  }

  public void setProcessType(ProcessType processType) {
    processTypeAttr.setValue(processType);
  }

  public boolean isClosed() {
    return isClosedAttr.getValue();
  }

  public void setClosed(boolean closed) {
    isClosedAttr.setValue(closed);
  }

  public boolean isExecutable() {
    return isExecutableAttr.getValue();
  }

  public void setExecutable(boolean executable) {
    isExecutableAttr.setValue(executable);
  }

}
