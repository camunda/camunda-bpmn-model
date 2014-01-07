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

import java.util.Collection;

import org.camunda.bpm.model.bpmn.BaseElement;
import org.camunda.bpm.model.bpmn.Documentation;
import org.camunda.bpm.model.bpmn.ExtensionElements;
import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.impl.type.child.ChildElement;
import org.camunda.bpm.model.core.impl.type.child.ChildElementCollection;
import org.camunda.bpm.model.core.impl.type.child.SequenceBuilder;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;

/**
 * @author Daniel Meyer
 *
 */
public abstract class BaseElementImp extends AbstractBpmnModelElement implements BaseElement {

  static Attribute<String> idAttr;
  static ChildElement<ExtensionElements> extensionElementsChild;
  static ChildElementCollection<Documentation> documentationChildren;

  public static ModelElementType TYPE;

  public static void registerType(Model model) {

    ModelElementTypeBuilder typeBuilder = model.defineType(BaseElement.class, BPMN_TYPE_BASE_ELEMENT)
      .namespaceUri(BPMN20_NS)
      .abstractType();

    idAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_ID)
      .build();

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    extensionElementsChild = sequenceBuilder.<ExtensionElements>element(BPMN_ELEMENT_EXTENSION_ELEMENTS)
      .namespaceUri(BPMN20_NS)
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
    return (ExtensionElements) extensionElementsChild.get(this);
  }

  public void setExtensionElements(ExtensionElements extensionElements) {
    extensionElementsChild.set(this, (ExtensionElements)extensionElements);
  }

}
