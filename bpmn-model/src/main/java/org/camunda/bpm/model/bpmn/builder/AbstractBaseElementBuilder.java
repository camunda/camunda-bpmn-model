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

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.xml.impl.util.ModelUtil;

/**
 * @author Sebastian Menski
 */
public abstract class AbstractBaseElementBuilder<B extends AbstractBaseElementBuilder<B, E>, E extends BaseElement> extends AbstractBpmnModelElementBuilder<B, E> {

  protected AbstractBaseElementBuilder(BpmnModelInstance modelInstance, E element, Class<?> selfType) {
    super(modelInstance, element, selfType);
  }

  protected <T extends BaseElement> T createInstance(Class<T> typeClass, String identifier) {
    T instance = modelInstance.newInstance(typeClass);
    if (identifier == null) {
      identifier = ModelUtil.getUniqueIdentifier(instance.getElementType());
    }
    instance.setId(identifier);
    return instance;
  }

  protected <T extends BpmnModelElementInstance> T createInstance(Class<T> typeClass) {
    return modelInstance.newInstance(typeClass);
  }

  protected <T extends BaseElement> T createChild(Class<T> typeClass, String identifier) {
    T instance = createInstance(typeClass, identifier);
    element.addChildElement(instance);
    return instance;
  }

  protected <T extends BpmnModelElementInstance> T createChild(Class<T> typeClass) {
    T instance = createInstance(typeClass);
    element.addChildElement(instance);
    return instance;
  }


  protected <T extends BaseElement> T createSibling(Class<T> typeClass, String identifier) {
    T instance = createInstance(typeClass, identifier);
    element.getParentElement().addChildElement(instance);
    return instance;
  }

  /**
   * Sets the identifier of the element.
   *
   * @param identifier  the identifier to set
   * @return the builder object
   */
  public B id(String identifier) {
    element.setId(identifier);
    return myself;
  }

  /**
   * Add an extension element to the element.
   *
   * @param extensionElement  the extension element to add
   * @return the builder object
   */
  public B addExtensionElement(BpmnModelElementInstance extensionElement) {
    ExtensionElements extensionElements = element.getExtensionElements();
    if (extensionElements == null) {
      extensionElements = modelInstance.newInstance(ExtensionElements.class);
      element.setExtensionElements(extensionElements);
    }
    extensionElements.getElements().add(extensionElement);
    return myself;
  }
}
