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
package org.camunda.bpm.model.core.impl.instance;

import java.util.List;

import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.impl.ModelInstanceImpl;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.impl.util.DomUtil;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * <p>Base class for implementing Model Elements. </p>
 *
 * @author Daniel Meyer
 *
 */
public abstract class ModelElementInstanceImpl implements ModelElementInstance {

  /** the wrapped DOM {@link Element} */
  protected final Element domElement;

  protected final ModelInstanceImpl modelInstance;

  protected final ModelElementTypeImpl elementType;

  public ModelElementInstanceImpl(ModelTypeInstanceContext instanceContext) {
    this.domElement = instanceContext.getDomElement();
    this.modelInstance = instanceContext.getModel();
    this.elementType = instanceContext.getModelType();
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
  public ModelElementInstance getUniqueChildElementByNameNs(String elementName, String namespaceUri) {

    NodeList childNodes = domElement.getChildNodes();

    List<Element> childElements = DomUtil.filterNodeListByName(childNodes, elementName, namespaceUri);

    if(!childElements.isEmpty()) {
      return ModelUtil.getModelElement(childElements.get(0), modelInstance);

    } else {
      return null;

    }
  }

  /**
   * Adds or replaces a child element by name. replaces an existing Child Element with the same name or adds a new child if no such element exists.
   *
   * @param newChild the child to add
   */
  public void setUniqueChildElementByNameNs(ModelElementInstance newChild) {
    ModelUtil.ensureInstanceOf(newChild, ModelElementInstanceImpl.class);
    ModelElementInstanceImpl newChildElement = (ModelElementInstanceImpl) newChild;

    Element childElement = newChildElement.getDomElement();
    ModelElementInstance existingChild = getUniqueChildElementByNameNs(childElement.getNodeName(), childElement.getNamespaceURI());
    if(existingChild == null) {
      addChildElement(newChild);

    } else {
      replaceChildElement((ModelElementInstanceImpl) existingChild, newChildElement);

    }

  }

  /**
   * Replace an existing child element with a new child element. Changes the underlying DOM element tree.
   *
   * @param existingChild the child element to replace
   * @param newChild the new child element
   */
  protected void replaceChildElement(ModelElementInstanceImpl existingChild, ModelElementInstanceImpl newChild) {

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
  public void addChildElement(ModelElementInstance newChild) {
    ModelUtil.ensureInstanceOf(newChild, ModelElementInstanceImpl.class);
    ModelElementInstanceImpl newChildElement = (ModelElementInstanceImpl) newChild;
    Element newChildDomElement = newChildElement.getDomElement();

    // add new element to the DOM
    NodeList childDomElements = domElement.getChildNodes();
    Node beforeNewChildDomElement = null;
    Node childDomElement = null;
    List<Class<?>> childElementTypes = elementType.getChildElementTypes();
    int newChildTypeIndex = ModelUtil.getIndexOfElementType(newChild, childElementTypes);
    if (newChildTypeIndex == -1) {
      throw new ModelException("New child for " + elementType.getTypeName() + " is not a valid child element type: " + newChild.getElementType().getTypeName() +"; valid types are: " + childElementTypes);
    }
    for (int i = 0; i < childDomElements.getLength(); i++) {
      childDomElement = childDomElements.item(i);
      if (childDomElement.getNodeType() != Node.ELEMENT_NODE) {
        // skip not element nodes like comments
        continue;
      }
      // get model element wrapper for current DOM child element
      ModelElementInstance currentChild = ModelUtil.getModelElement((Element) childDomElement, modelInstance);
      // compare child element type with new child element type
      int childTypeIndex = ModelUtil.getIndexOfElementType(currentChild, childElementTypes);
      if (childTypeIndex == -1) {
        throw new ModelException("Child element " + currentChild.getElementType().getTypeName() + " is not a valid child element for " + elementType.getTypeName() +"; valid types are: " + childElementTypes);
      }
      if (childTypeIndex > newChildTypeIndex) {
        break;
      }
      else {
        beforeNewChildDomElement = childDomElement;
      }
    }

    // add new element to the DOM in the correct position
    if (beforeNewChildDomElement == null) {
      if (domElement.getFirstChild() == null) {
        domElement.appendChild(newChildDomElement);
      }
      else {
        domElement.insertBefore(newChildDomElement, domElement.getFirstChild());
      }
    }
    else if (beforeNewChildDomElement.getNextSibling() == null) {
      domElement.appendChild(newChildDomElement);
    }
    else {
      domElement.insertBefore(newChildDomElement, beforeNewChildDomElement.getNextSibling());
    }
  }

  public ModelInstanceImpl getModelInstance() {
    return modelInstance;
  }

  /**
   * @param child the child element to remove
   * @return true if the child element could be removed.
   */
  public boolean removeChildElement(ModelElementInstanceImpl child) {
    // TODO: remove references
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
  public void setAttributeValue(String attributeName, String xmlValue, boolean isIdAttribute) {
    DomUtil.setAttributeValue(attributeName, xmlValue, domElement);
    if(isIdAttribute) {
      DomUtil.setIdAttribute(domElement, attributeName);
    }
  }

  public void setAttributeValueNs(String attributeName, String namespaceUri, String xmlValue, boolean isIdAttribute) {
    DomUtil.setAttributeValueNs(attributeName, namespaceUri, xmlValue, domElement);
    if(isIdAttribute) {
      DomUtil.setIdAttributeNs(domElement, attributeName, namespaceUri);
    }
  }

  public void removeAttribute(String attributeName) {
    DomUtil.removeAttribute(domElement, attributeName);
  }

  public void removeAttributeNs(String attributeName, String namespaceUri) {
    DomUtil.removeAttributeNs(domElement, attributeName, namespaceUri);
  }

  public ModelElementType getElementType() {
    return elementType;
  }

  @Override
  public boolean equals(Object obj) {
    if(obj == null) {
      return false;
    } else if(obj == this) {
      return true;
    } else if(!(obj instanceof ModelElementInstanceImpl)) {
      return false;
    } else {
      ModelElementInstanceImpl other = (ModelElementInstanceImpl) obj;
      return other.domElement == domElement;
    }
  }

}
