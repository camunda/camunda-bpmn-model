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

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_ID;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_DOCUMENTATION;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_EXTENSION_ELEMENTS;

import java.util.Collection;

import org.camunda.bpm.model.bpmn.BaseElement;
import org.camunda.bpm.model.bpmn.Documentation;
import org.camunda.bpm.model.bpmn.ExtensionElements;
import org.camunda.bpm.model.core.impl.ModelElementCreateContext;
import org.camunda.bpm.model.core.impl.attribute.Attribute;
import org.camunda.bpm.model.core.impl.attribute.StringAttribute;
import org.camunda.bpm.model.core.impl.child.ChildElement;
import org.camunda.bpm.model.core.impl.child.ChildElementCollection;
import org.camunda.bpm.model.core.impl.child.NamedChildElementCollection;

/**
 * @author Daniel Meyer
 *
 */
public abstract class BaseElementImp extends AbstractBpmnModelElement implements BaseElement {

  protected Attribute<String> idAttr = new StringAttribute(BPMN_ATTRIBUTE_ID, this);
  protected ChildElement<ExtensionElements> extensionElementsChild = new ChildElement<ExtensionElements>(BPMN_ELEMENT_EXTENSION_ELEMENTS, this);
  protected ChildElementCollection<Documentation> documentationChildren = new NamedChildElementCollection<Documentation>(BPMN_ELEMENT_DOCUMENTATION, this);

  public BaseElementImp(ModelElementCreateContext context) {
    super(context);
  }

  public String getId() {
    return idAttr.getValue();
  }

  public void setId(String id) {
    idAttr.setValue(id);
  }

  public Collection<Documentation> getDocumentation() {
    return documentationChildren;
  }

  public ExtensionElements getExtensionElements() {
    return (ExtensionElements) extensionElementsChild.get();
  }

  public void setExtensionElements(ExtensionElements extensionElements) {
    extensionElementsChild.set((ExtensionElements)extensionElements);
  }

}
