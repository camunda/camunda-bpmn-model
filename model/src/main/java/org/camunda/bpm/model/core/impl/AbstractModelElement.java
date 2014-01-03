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
package org.camunda.bpm.model.core.impl;

import java.util.List;

import org.camunda.bpm.model.core.impl.util.DomUtil;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>Base class for implementing Model Elements. </p>
 *
 * @author Daniel Meyer
 *
 */
public abstract class AbstractModelElement implements ModelElement {

  /** the wrapped DOM {@link Element} */
  protected final Element domElement;

  protected final AbstractModel model;

  public AbstractModelElement(ModelElementCreateContext context) {
    this.domElement = context.getDomElement();
    this.model = context.getModel();
  }

  /**
   * @return the wrapped DOM {@link Element}
   */
  public Element getDomElement() {
    return domElement;
  }

  /**
   * Returns a child element with the given name or 'null' if no such element exists
   *
   * @param elementName the local name of the element
   * @param namespaceUri the namespace of the element
   * @return the child element or null.
   */
  public <T extends ModelElement> T getUniqueChildElementByNameNs(String elementName, String namespaceUri) {

    NodeList childNodes = domElement.getChildNodes();

    List<Element> childElements = DomUtil.filterNodeListByName(childNodes, elementName, namespaceUri);

    if(!childElements.isEmpty()) {
      return (T) ModelUtil.getModelElement(childElements.get(0), model);

    } else {
      return null;

    }
  }

  /**
   * Adds or replaces a child element by name. replaces an existing Child Element with the same name or adds a new child if no such element exists.
   *
   * @param newChild the child to add
   */
  public void setUniqueChildElementByNameNs(ModelElement newChild) {
    ModelUtil.ensureInstanceOf(newChild, AbstractModelElement.class);
    AbstractModelElement newChildElement = (AbstractModelElement) newChild;

    Element childElement = newChildElement.getDomElement();
    ModelElement existingChild = getUniqueChildElementByNameNs(childElement.getNodeName(), childElement.getNamespaceURI());
    if(existingChild == null) {
      addChildElement(newChild);

    } else {
      replaceChildElement((AbstractModelElement) existingChild, newChildElement);

    }

  }

  /**
   * Replace an existing child element with a new child element. Changes the underlying DOM element tree.
   *
   * @param existingChild the child element to replace
   * @param newChild the new child element
   */
  protected void replaceChildElement(AbstractModelElement existingChild, AbstractModelElement newChild) {

    Element existingChildDomElement = existingChild.getDomElement();
    Element newChildDomElement = newChild.getDomElement();

    // replace the existing child with the new child in the DOM
    domElement.replaceChild(newChildDomElement, existingChildDomElement);

    // TODO: update references from existing child to new child
  }

  /**
   * Appends a new child element to the children of this element. Updates the underlying DOM element tree.
   *
   * @param newChild the new child element
   */
  public void addChildElement(ModelElement newChild) {
    ModelUtil.ensureInstanceOf(newChild, AbstractModelElement.class);
    AbstractModelElement newChildElement = (AbstractModelElement) newChild;
    // add new element to the DOM
    Element newChildDomElement = newChildElement.getDomElement();
    domElement.appendChild(newChildDomElement);
  }

  public AbstractModel getModel() {
    return model;
  }

  /**
   * @param child the child elememt to remove
   * @return true if the child element could be removed.
   */
  public boolean removeChildElement(AbstractModelElement child) {
    return DomUtil.removeChild(domElement, child.getDomElement());
  }

  /**
   * @param attributeName
   * @return
   */
  public String getAttributeValue(String attributeName) {
    return DomUtil.getAttributeValue(attributeName, domElement);
  }

  /**
   * @param attributeName
   * @param namespaceUri
   * @return
   */
  public String getAttributeValueNs(String attributeName, String namespaceUri) {
    return DomUtil.getAttributeValueNs(attributeName, namespaceUri, domElement);
  }

  /**
   * @param attributeName
   * @param xmlValue
   */
  public void setAttributeValue(String attributeName, String xmlValue) {
    DomUtil.setAttributeValue(attributeName, xmlValue, domElement);
  }

  public void setAttributeValueNs(String attributeName, String namespaceUri, String xmlValue) {
    DomUtil.setAttributeValueNs(attributeName, namespaceUri, xmlValue, domElement);
  }

}
