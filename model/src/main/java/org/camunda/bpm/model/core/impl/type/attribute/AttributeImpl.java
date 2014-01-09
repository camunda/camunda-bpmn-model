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
package org.camunda.bpm.model.core.impl.type.attribute;

import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Attribute;

/**
 * <p>Base class for implementing primitive value attributes</p>
 *
 * @author Daniel Meyer
 *
 */
public abstract class AttributeImpl<T> implements Attribute<T> {

  /** the local name of the attribute */
  protected String attributeName;

  /** the namespace for this attribute */
  protected String namespaceUri;

  /** the default value for this attribute: the default value is returned
   * by the {@link #getValue()} method in case the attribute is not set on the
   * domElement.
   */
  protected T defaultValue;

  protected boolean isRequired = false;

  protected boolean isIdAttribute = false;

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
  public T getValue(ModelElementInstance modelElement) {
    String value = null;
    if(namespaceUri == null) {
      value = modelElement.getAttributeValue(attributeName);
    } else {
      value = modelElement.getAttributeValueNs(attributeName, namespaceUri);
    }

    // default value
    if(value == null && defaultValue != null) {
      return defaultValue;
    } else {
      return convertXmlValueToModelValue(value);
    }
  }

  /**
   * sets the value of the attribute.
   *
   *  the value of the attribute.
   */
  public void setValue(ModelElementInstance modelElement, T value) {
    String xmlValue = convertModelValueToXmlValue(value);
    if(namespaceUri == null) {
      modelElement.setAttributeValue(attributeName, xmlValue, isIdAttribute);
    } else {
      modelElement.setAttributeValueNs(attributeName, namespaceUri, xmlValue, isIdAttribute);
    }
  }

  public void setDefaultValue(T defaultValue) {
    this.defaultValue = defaultValue;
  }

  /**
   * @param isRequired the isRequired to set
   */
  public void setRequired(boolean isRequired) {
    this.isRequired = isRequired;
  }

  /**
   * @param namespaceUri the namespaceUri to set
   */
  public void setNamespaceUri(String namespaceUri) {
    this.namespaceUri = namespaceUri;
  }

  /**
   * @return the namespaceUri
   */
  public String getNamespaceUri() {
    return namespaceUri;
  }

  /**
   * @return the attributeName
   */
  public String getAttributeName() {
    return attributeName;
  }

  /**
   * @param attributeName the attributeName to set
   */
  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }

  /**
   * indicate whether this attribute is an Id attribtue
   * @param b
   */
  public void setId(boolean b) {
    this.isIdAttribute = b;
  }

}
