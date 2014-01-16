/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.xml.model.impl.type.reference;

import org.camunda.bpm.xml.model.Model;
import org.camunda.bpm.xml.model.ModelException;
import org.camunda.bpm.xml.model.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.xml.model.impl.type.attribute.AttributeImpl;
import org.camunda.bpm.xml.model.impl.type.child.ChildElementCollectionImpl;
import org.camunda.bpm.xml.model.instance.ModelElementInstance;
import org.camunda.bpm.xml.model.type.reference.ElementReferenceCollection;
import org.camunda.bpm.xml.model.type.reference.ElementReferenceCollectionBuilder;

/**
 * @author Sebastian Menski
 */
public class ElementReferenceCollectionBuilderImpl<T extends ModelElementInstance, V extends ModelElementInstance> implements ElementReferenceCollectionBuilder<T, V> {

  private final Class<V> childElementType;
  private final Class<T> referenceTargetClass;
  private final ChildElementCollectionImpl<V> collection;
  private final ModelElementTypeImpl containingType;

  ElementReferenceCollectionImpl<T, V> elementReferenceCollectionImpl;

  public ElementReferenceCollectionBuilderImpl(Class<V> childElementType, Class<T> referenceTargetClass, ChildElementCollectionImpl<V> collection, ModelElementTypeImpl containingType) {
    this.childElementType = childElementType;
    this.referenceTargetClass = referenceTargetClass;
    this.collection = collection;
    this.containingType = containingType;
    this.elementReferenceCollectionImpl = new ElementReferenceCollectionImpl<T, V>(collection);
  }

  @Override
  public ElementReferenceCollection<T, V> build() {
    return elementReferenceCollectionImpl;
  }

  @SuppressWarnings("unchecked")
  public void performModelBuild(Model model) {
    ModelElementTypeImpl referenceTargetType = (ModelElementTypeImpl) model.getType(referenceTargetClass);
    ModelElementTypeImpl referenceSourceType = (ModelElementTypeImpl) model.getType(childElementType);
    elementReferenceCollectionImpl.setReferenceTargetElementType(referenceTargetType);
    elementReferenceCollectionImpl.setReferenceSourceElementType(referenceSourceType);

    // the referenced attribute may be declared on a base type of the referenced type.
    AttributeImpl<String> idAttribute = (AttributeImpl<String>) referenceTargetType.getAttribute("id");
    if (idAttribute != null) {
      idAttribute.registerIncoming(elementReferenceCollectionImpl);
      elementReferenceCollectionImpl.setReferenceTargetAttribute(idAttribute);
    } else {
      throw new ModelException("Unable to find id attribute of " + referenceTargetClass);
    }
  }
}
