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

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.impl.ModelBuildOperation;
import org.camunda.bpm.model.core.impl.ModelImpl;
import org.camunda.bpm.model.core.impl.type.attribute.BooleanAttributeBuilder;
import org.camunda.bpm.model.core.impl.type.attribute.EnumAttributeBuilder;
import org.camunda.bpm.model.core.impl.type.attribute.StringAttributeBuilderImpl;
import org.camunda.bpm.model.core.impl.type.child.SequenceBuilderImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.AttributeBuilder;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.SequenceBuilder;
import org.camunda.bpm.model.core.type.StringAttributeBuilder;

/**
 * @author Daniel Meyer
 *
 */
public class ModelElementTypeBuilderImpl implements ModelElementTypeBuilder, ModelBuildOperation {

  protected ModelElementTypeImpl modelType;
  protected ModelImpl model;
  protected Class<? extends ModelElementInstance> instanceType;

  protected List<ModelBuildOperation> modelBuildOperations = new ArrayList<ModelBuildOperation>();
  protected Class<? extends ModelElementInstance> extendedType;

  public ModelElementTypeBuilderImpl(Class<? extends ModelElementInstance> instanceType, String name, ModelImpl model) {
    this.instanceType = instanceType;
    this.model = model;
    modelType = new ModelElementTypeImpl(model, name, instanceType);
  }

  public ModelElementTypeBuilder extendsType(Class<? extends ModelElementInstance> extendedType) {
    this.extendedType = extendedType;
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
    BooleanAttributeBuilder builder = new BooleanAttributeBuilder(attributeName, modelType);
    modelBuildOperations.add(builder);
    return builder;
  }

  public StringAttributeBuilder stringAttribute(String attributeName) {
    StringAttributeBuilderImpl builder = new StringAttributeBuilderImpl(attributeName, modelType);
    modelBuildOperations.add(builder);
    return builder;
  }

  public <V extends Enum<V>> AttributeBuilder<V> enumAttribute(String attributeName, Class<V> enumType) {
    EnumAttributeBuilder<V> builder = new EnumAttributeBuilder<V>(attributeName, modelType, enumType);
    modelBuildOperations.add(builder);
    return builder;
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
    SequenceBuilderImpl builder = new SequenceBuilderImpl(modelType);
    modelBuildOperations.add(builder);
    return builder;
  }

  public void performModelBuild(Model model) {

    // build type hierarchy
    if(extendedType != null) {
      ModelElementTypeImpl extendedModelElementType = (ModelElementTypeImpl) model.getType(extendedType);
      if(extendedModelElementType == null) {
        throw new ModelException("Type "+modelType+" is defined to extend "+extendedModelElementType +" but no such type is defined.");

      } else {
        modelType.setBaseType(extendedModelElementType);
        extendedModelElementType.registerExtendingType(modelType);
      }
    }

    for (ModelBuildOperation operation : modelBuildOperations) {
      operation.performModelBuild(model);
    }
  }
}
