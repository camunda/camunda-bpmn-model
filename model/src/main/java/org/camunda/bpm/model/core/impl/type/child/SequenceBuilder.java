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

import org.camunda.bpm.model.core.instance.ModelElementInstance;

/**
 * @author Daniel Meyer
 *
 */
public interface SequenceBuilder {

  <T extends ModelElementInstance> ChildElementBuilder<T> element(String elementName);

  <T extends ModelElementInstance> ChildElementCollectionBuilder<T> elementCollection(Class<T> childElementType, String localName);

  <T extends ModelElementInstance> ChildElementCollectionBuilder<T> elementCollection(Class<T> childElementType, String localName, String namespaceUri);

  <T extends ModelElementInstance> ChildElementCollectionBuilder<T> elementCollection(Class<T> childElementType);

}
