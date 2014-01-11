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

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN20_NS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_NAME;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_TYPE_CALLABLE_ELEMENT;

import org.camunda.bpm.model.bpmn.CallableElement;
import org.camunda.bpm.model.bpmn.RootElement;
import org.camunda.bpm.model.core.ModelBuilder;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;

/**
 * @author Daniel Meyer
 *
 */
public abstract class CallableElementImpl extends RootElementImpl implements CallableElement {

  public static ModelElementType MODEL_TYPE;

  static Attribute<String> nameAttr;

  public static void registerType(ModelBuilder bpmnModelBuilder) {

    ModelElementTypeBuilder typeBuilder = bpmnModelBuilder.defineType(CallableElement.class, BPMN_TYPE_CALLABLE_ELEMENT)
      .namespaceUri(BPMN20_NS)
      .abstractType()
      .extendsType(RootElement.class);

    nameAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_NAME).build();

    MODEL_TYPE = typeBuilder.build();

  }

  public CallableElementImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  public String getName() {
    return nameAttr.getValue(this);
  }

  public void setName(String name) {
    nameAttr.setValue(this, name);
  }

}
