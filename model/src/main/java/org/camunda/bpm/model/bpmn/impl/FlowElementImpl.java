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
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_IMPORT;

import org.camunda.bpm.model.bpmn.BaseElement;
import org.camunda.bpm.model.bpmn.FlowElement;
import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;

/**
 * @author Daniel Meyer
 *
 */
public abstract class FlowElementImpl extends BaseElementImp implements FlowElement {

  public static ModelElementType MODEL_TYPE;

  static Attribute<String> nameAttr;

  public static void registerType(Model model) {

    ModelElementTypeBuilder builder = model.defineType(FlowElement.class, BPMN_ELEMENT_IMPORT)
      .namespaceUri(BPMN20_NS)
      .abstractType()
      .extendsType(model.getType(BaseElement.class));

    nameAttr = builder.stringAttribute(BPMN_ATTRIBUTE_NAME)
      .build();

    MODEL_TYPE = builder.build();
  }

  public FlowElementImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public String getName() {
    return nameAttr.getValue(this);
  }

  public void setName(String name) {
    nameAttr.setValue(this, name);
  }

}
