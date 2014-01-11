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

import java.util.Collection;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.impl.ModelBuildOperation;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.impl.type.attribute.AttributeImpl;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.Reference;
import org.camunda.bpm.model.core.type.ReferenceBuilder;

/**
 * A builder for a model reference based on qname
 *
 * @author Sebastian Menski
 *
 */
public class QNameReferenceBuilderImpl<T extends ModelElementInstance> implements ReferenceBuilder<T>, ModelBuildOperation {

  protected final AttributeImpl<String> referencingAttribute;
  protected QNameReferenceImpl<T> qNameReferenceImpl;
  protected final Class<T> referencedElementType;

  public QNameReferenceBuilderImpl(AttributeImpl<String> referencingAttribute, Class<T> referencedElementType) {
    this.referencingAttribute = referencingAttribute;
    this.referencedElementType = referencedElementType;
    qNameReferenceImpl = new QNameReferenceImpl<T>(referencingAttribute);
  }

  public Reference<T> build() {
    referencingAttribute.registerOutgoingReference(qNameReferenceImpl);
    return qNameReferenceImpl;
  }

  public void performModelBuild(Model model) {
    // register declaring type as a referencing type of referenced type
    ModelElementTypeImpl referencedType = (ModelElementTypeImpl) model.getType(referencedElementType);

    // the actual referenced type
    qNameReferenceImpl.setReferencedElementType(referencedType);

    // the referenced attribute may be declared on a base type of the referenced type.
    AttributeImpl<String> idAttribute = getIdAttribute(referencedType, model);
    if(idAttribute != null) {
      idAttribute.registerIncoming(qNameReferenceImpl);
      qNameReferenceImpl.setReferencedAttribute(idAttribute);
    } else {
      throw new ModelException();
    }
  }

  /**
   * @param referencedType
   * @return
   */
  @SuppressWarnings("unchecked")
  protected AttributeImpl<String> getIdAttribute(ModelElementTypeImpl referencedType, Model model) {
    for (Attribute<?> attr : referencedType.getAttributes()) {
      if(attr.getAttributeName().equals("id")) {
        return (AttributeImpl<String>) attr;
      }
    }
    Collection<ModelElementType> baseTypes = ModelUtil.calculateAllBaseTypes(model, referencedType);
    for (ModelElementType baseType : baseTypes) {
      for (Attribute<?> attr : baseType.getAttributes()) {
        if (attr.getAttributeName().equals("id")) {
          return (AttributeImpl<String>) attr;
        }
      }
    }
    return null;
  }

}
