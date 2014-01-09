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

import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;

/**
 * @author Daniel Meyer
 *
 */
public class ChildElementBuilderImpl<T extends ModelElementInstance> implements ChildElementBuilder<T> {

  protected final ModelElementTypeImpl containingType;
  protected final ChildElement<T> childElement;
  protected Class<T> childElementType;

  public ChildElementBuilderImpl(Class<T> childElementType, String localName, ModelElementTypeImpl containingType) {
    this.childElementType = childElementType;
    this.containingType = containingType;
    childElement = new ChildElement<T>(localName);
    childElement.setNamespace(containingType.getTypeNamespace());
  }

  public ChildElementBuilder<T> namespaceUri(String namespaceUri) {
    childElement.setNamespace(namespaceUri);
    return this;
  }

  public ChildElement<T> build() {
    containingType.registerChildElementType(childElementType);
    return childElement;
  }

}
