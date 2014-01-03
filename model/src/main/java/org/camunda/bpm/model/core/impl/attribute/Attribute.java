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
package org.camunda.bpm.model.core.impl.attribute;

import org.camunda.bpm.model.core.impl.AbstractModelElement;

/**
 * <p>Base class for implementing primitive value attributes</p>
 *
 * @author Daniel Meyer
 *
 */
public abstract class Attribute<T> {

  /** the modelElement for this attribute */
  protected final AbstractModelElement modelElement;

  /** the namespace for this attribute */
  protected String namespaceUri;

  public Attribute(AbstractModelElement element) {
    this(element, null);
  }

  public Attribute(AbstractModelElement modelElement, String namespaceUri) {
    this.modelElement = modelElement;
    this.namespaceUri = namespaceUri;
  }

  /**
   * to be implemented by subclasses
   *
   * @return the Attribute name as String.
   */
  protected abstract String getAttributeName();

  /**
   * to be implemented by subclasses: converts the raw (String) value of the
   * attribute to the type required by the model
   *
   * @return the converted value
   */
  protected abstract T convertXmlValueToModelValue(String rawValue);

  /**
   * to be implemented by subclasses: converts the raw (String) value of the
   * attribute to the type required by the model
   *
   * @return the converted value
   */
  protected abstract String convertModelValueToXmlValue(T modelValue);

  /**
   * returns the value of the attribute.
   *
   * @return the value of the attribute.
   */
  public T getValue() {
    String value = null;
    if(namespaceUri == null) {
      value = modelElement.getAttributeValue(getAttributeName());
    } else {
      value = modelElement.getAttributeValueNs(getAttributeName(), namespaceUri);
    }
    return convertXmlValueToModelValue(value);
  }

  /**
   * sets the value of the attribute.
   *
   *  the value of the attribute.
   */
  public void setValue(T value) {
    String xmlValue = convertModelValueToXmlValue(value);
    if(namespaceUri == null) {
      modelElement.setAttributeValue(getAttributeName(), xmlValue);
    } else {
      modelElement.setAttributeValueNs(getAttributeName(), namespaceUri, xmlValue);
    }
  }

}
