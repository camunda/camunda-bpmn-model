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
import org.camunda.bpm.model.bpmn.impl.instance.RootElementImpl;
import org.camunda.bpm.model.bpmn.instance.RootElement;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 * @author Daniel Meyer
 *
 */
public abstract class CallableElementImpl extends RootElementImpl implements CallableElement {

  private static ModelElementType MODEL_TYPE;

  private static Attribute<String> nameAttr;

  public static void registerType(ModelBuilder bpmnModelBuilder) {

    ModelElementTypeBuilder typeBuilder = bpmnModelBuilder.defineType(CallableElement.class, BPMN_ELEMENT_CALLABLE_ELEMENT)
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
