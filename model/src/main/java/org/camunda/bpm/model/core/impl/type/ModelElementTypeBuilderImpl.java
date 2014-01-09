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
package org.camunda.bpm.model.core.impl.type;

import org.camunda.bpm.model.core.impl.ModelImpl;
import org.camunda.bpm.model.core.impl.type.attribute.BooleanAttributeBuilder;
import org.camunda.bpm.model.core.impl.type.attribute.EnumAttributeBuilder;
import org.camunda.bpm.model.core.impl.type.attribute.StringAttributeBuilder;
import org.camunda.bpm.model.core.impl.type.child.SequenceBuilder;
import org.camunda.bpm.model.core.impl.type.child.SequenceBuilderImpl;
import org.camunda.bpm.model.core.impl.type.reference.QNameReferenceBuilderImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.AttributeBuilder;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.ReferenceBuilder;

/**
 * @author Daniel Meyer
 *
 */
public class ModelElementTypeBuilderImpl implements ModelElementTypeBuilder {

  protected ModelElementTypeImpl modelType;
  protected ModelImpl model;
  protected Class<? extends ModelElementInstance> instanceType;

  public ModelElementTypeBuilderImpl(Class<? extends ModelElementInstance> instanceType, String name, ModelImpl model) {
    this.instanceType = instanceType;
    this.model = model;
    modelType = new ModelElementTypeImpl(model, name);
  }

  public ModelElementTypeBuilder extendsType(ModelElementType extendedType) {
    ModelElementTypeImpl typeImpl = (ModelElementTypeImpl) extendedType;
    modelType.setPartentType(typeImpl);
    return this;
  }

  public <T extends ModelElementInstance> ModelElementTypeBuilder instanceProvider(ModelTypeIntanceProvider<T> instanceProvider) {
    modelType.setInstanceProvider(instanceProvider);
    return this;
  }

  public ModelElementTypeBuilder namespaceUri(String namespaceUri) {
    modelType.setTypeNamespace(namespaceUri);
    return this;
  }

  public AttributeBuilder<Boolean> booleanAttribute(String attributeName) {
    return new BooleanAttributeBuilder(attributeName, modelType);
  }

  public AttributeBuilder<String> stringAttribute(String attributeName) {
    return new StringAttributeBuilder(attributeName, modelType);
  }

  public <V extends Enum<V>> AttributeBuilder<V> enumAttribute(String attributeName, Class<V> enumType) {
    return new EnumAttributeBuilder<V>(attributeName, modelType, enumType);
  }

  public <V extends ModelElementInstance> ReferenceBuilder<V> qNameReference(Class<V> referencedElementType, String referencedAttributeName) {
    return new QNameReferenceBuilderImpl<V>(referencedAttributeName, referencedElementType, modelType);
  }

  public ModelElementType build() {
    model.registerType(modelType, instanceType);
    return modelType;
  }

  public ModelElementTypeBuilder abstractType() {
    modelType.setAbstract(true);
    return this;
  }

  public SequenceBuilder sequence() {
    return new SequenceBuilderImpl(modelType);
  }
}
