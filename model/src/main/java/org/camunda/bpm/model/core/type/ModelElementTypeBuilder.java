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
package org.camunda.bpm.model.core.type;

import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.impl.type.child.SequenceBuilder;
import org.camunda.bpm.model.core.instance.ModelElementInstance;

/**
 * @author Daniel Meyer
 *
 * @param <T>
 */
public interface ModelElementTypeBuilder {

  ModelElementTypeBuilder namespaceUri(String namespaceUri);

  ModelElementTypeBuilder extendsType(Class<? extends ModelElementInstance> extendedType);

  <T extends ModelElementInstance> ModelElementTypeBuilder instanceProvider(ModelTypeIntanceProvider<T> instanceProvider);

  ModelElementTypeBuilder abstractType();

  AttributeBuilder<Boolean> booleanAttribute(String attributeName);

  AttributeBuilder<String> stringAttribute(String attributeName);

  <V extends Enum<V>> AttributeBuilder<V> enumAttribute(String attributeName, Class<V> enumType);

  SequenceBuilder sequence();

  ModelElementType build();

  <V extends ModelElementInstance> ReferenceBuilder<V> qNameReference(Class<V> referencedElementType, String referencedAttributeName);

  public static interface ModelTypeIntanceProvider<T extends ModelElementInstance> {
    T newInstance(ModelTypeInstanceContext instanceContext);
  }

}