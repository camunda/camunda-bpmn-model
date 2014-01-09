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
package org.camunda.bpm.model.core.impl.type.reference;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.ModelBuildOperation;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Reference;
import org.camunda.bpm.model.core.type.ReferenceBuilder;

/**
 * A builder for a model reference based on qname
 *
 * @author Sebastian Menski
 *
 */
public class QNameReferenceBuilderImpl<T extends ModelElementInstance> implements ReferenceBuilder<T>, ModelBuildOperation {

  protected final QNameReferenceImpl<T> qNameReferenceImpl;
  protected final ModelElementTypeImpl declaringType;
  protected Class<T> referencedElementType;

  public QNameReferenceBuilderImpl(String referencedAttributeName, Class<T> referencedElementType, ModelElementTypeImpl declaringType, Class<? extends ModelElementInstance> instanceType) {
    this.referencedElementType = referencedElementType;
    this.declaringType = declaringType;
    qNameReferenceImpl = new QNameReferenceImpl<T>(referencedAttributeName, referencedElementType, declaringType.getModel());

    // register declaring type as a referencing type of referenced type
    ModelElementTypeImpl referencedType = (ModelElementTypeImpl) declaringType.getModel().getType(referencedElementType);
    referencedType.registerReferencingType(instanceType);
  }

  public Reference<T> build() {
    declaringType.registerReference(qNameReferenceImpl);
    return qNameReferenceImpl;
  }

  public void performModelBuild(Model model) {
    // do nothing
  }

}
