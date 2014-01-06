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

import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.AttributeBuilder;


/**
 *
 * @author Daniel Meyer
 *
 */
public abstract class AttributeBuilderImpl<T> implements AttributeBuilder<T> {

  protected final AttributeImpl<T> attribute;
  protected final ModelElementTypeImpl modelType;

  public AttributeBuilderImpl(String attributeName, ModelElementTypeImpl modelType, AttributeImpl<T> attribute) {
    this.modelType = modelType;
    this.attribute = attribute;
    attribute.setAttributeName(attributeName);
  }


  public AttributeBuilder<T> defaultValue(T defaultValue) {
    attribute.setDefaultValue(defaultValue);
    return this;
  }

  public AttributeBuilder<T> required() {
    attribute.setRequired(true);
    return this;
  }

  public Attribute<T> build() {
    modelType.registerAttribute(attribute);
    return attribute;
  }

}
