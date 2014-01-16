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

import org.camunda.bpm.model.bpmn.BaseElement;
import org.camunda.bpm.model.bpmn.Documentation;
import org.camunda.bpm.model.bpmn.ExtensionElements;
import org.camunda.bpm.xml.model.ModelBuilder;
import org.camunda.bpm.xml.model.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.xml.model.type.*;

import java.util.Collection;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 * @author Daniel Meyer
 *
 */
public abstract class BaseElementImp extends AbstractBpmnModelElement implements BaseElement {

  static Attribute<String> idAttr;
  static ChildElement<ExtensionElements> extensionElementsChild;
  static ChildElementCollection<Documentation> documentationChildren;

  public static ModelElementType TYPE;

  public static void registerType(ModelBuilder bpmnModelBuilder) {

    ModelElementTypeBuilder typeBuilder = bpmnModelBuilder.defineType(BaseElement.class, BPMN_TYPE_BASE_ELEMENT)
      .namespaceUri(BPMN20_NS)
      .abstractType();

    idAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_ID)
      .idAttribute()
      .build();

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    extensionElementsChild = sequenceBuilder.element(ExtensionElements.class, BPMN_ELEMENT_EXTENSION_ELEMENTS)
      .build();

    TYPE = typeBuilder.build();
  }

  public BaseElementImp(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  public String getId() {
    return idAttr.getValue(this);
  }

  public void setId(String id) {
    idAttr.setValue(this, id);
  }

  public Collection<Documentation> getDocumentation() {
    return documentationChildren.get(this);
  }

  public ExtensionElements getExtensionElements() {
    return extensionElementsChild.getChild(this);
  }

  public void setExtensionElements(ExtensionElements extensionElements) {
    extensionElementsChild.setChild(this, extensionElements);
  }

}
