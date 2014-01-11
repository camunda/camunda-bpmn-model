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

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.impl.type.reference.QNameReferenceBuilderImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ReferenceBuilder;
import org.camunda.bpm.model.core.type.StringAttributeBuilder;


/**
 *
 * @author Daniel Meyer
 *
 */
public class StringAttributeBuilderImpl extends AttributeBuilderImpl<String> implements StringAttributeBuilder {

  protected QNameReferenceBuilderImpl<?> referenceBuilder;

  public StringAttributeBuilderImpl(String attributeName, ModelElementTypeImpl modelType) {
    super(attributeName, modelType, new StringAttribute(modelType));
  }

  public <V extends ModelElementInstance> ReferenceBuilder<V> qNameReference(Class<V> referencedElementType) {
    if (referenceBuilder != null) {
      throw new ModelException("Attribute have more than one reference");
    }
    QNameReferenceBuilderImpl<V> referenceBuilder = new QNameReferenceBuilderImpl<V>(attribute, referencedElementType);
    this.referenceBuilder = referenceBuilder;
    return referenceBuilder;
  }

  @Override
  public void performModelBuild(Model model) {
    super.performModelBuild(model);
    if (referenceBuilder != null) {
      referenceBuilder.performModelBuild(model);
    }
  }

}
