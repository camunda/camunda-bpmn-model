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
package org.camunda.bpm.model.core.impl.type.child;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.impl.ModelBuildOperation;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ChildElementCollectionBuilder;
import org.camunda.bpm.model.core.type.ChildElementCollection;
import org.camunda.bpm.model.core.type.ModelElementType;

/**
 * @author Daniel Meyer
 *
 */
public class ChildElementCollectionBuilderImpl<T extends ModelElementInstance> implements ChildElementCollectionBuilder<T>, ModelBuildOperation {

  protected final ModelElementTypeImpl containingType;
  protected final ChildElementCollectionImpl<T> collection;
  protected final Class<T> childElementType;

  public ChildElementCollectionBuilderImpl(Class<T> childElementType, String localName, String namespaceUri, ModelElementType containingType) {
    this.childElementType = childElementType;
    this.containingType = (ModelElementTypeImpl) containingType;
    this.collection = createCollectionInstance(localName, namespaceUri);
  }

  public ChildElementCollectionBuilderImpl(Class<T> type, ModelElementType containingType) {
    this.childElementType = type;
    this.containingType = (ModelElementTypeImpl) containingType;
    this.collection = createCollectionInstance(type);
  }

  protected ChildElementCollectionImpl<T> createCollectionInstance(Class<T> type) {
    return new TypedChildElementCollectionImpl<T>(type);
  }

  protected ChildElementCollectionImpl<T> createCollectionInstance(String localName, String namespaceUri) {
    return new NamedChildElementCollection<T>(localName, namespaceUri);
  }

  public ChildElementCollectionBuilder<T> immutable() {
    collection.setMutable(false);
    return this;
  }

  public ChildElementCollectionBuilder<T> maxOccurs(int i) {
    collection.setMaxOccurs(i);
    return this;
  }

  public ChildElementCollectionBuilder<T> minOccurs(int i) {
    collection.setMinOccurs(i);
    return this;
  }

  public ChildElementCollection<T> build() {
    return collection;
  }

  public void performModelBuild(Model model) {
    ModelElementType elementType = model.getType(childElementType);
    if(elementType == null) {
      throw new ModelException(containingType+" declares undefined child element of type "+childElementType+".");
    }
    containingType.registerChildElementType(elementType);
  }

}
