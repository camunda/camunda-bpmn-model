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

import org.camunda.bpm.model.core.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.model.core.impl.util.DomUtil.ElementByTypeListFilter;
import org.camunda.bpm.model.core.impl.util.DomUtil.ElementNodeListFilter;
import org.camunda.bpm.model.core.instance.ModelElementInstance;

/**
 * <p>Collection containing all elements with a given type.</p>
 *
 * @author Daniel Meyer
 *
 */
public class TypedChildElementCollection<T extends ModelElementInstance> extends ChildElementCollection<T> {

  protected Class<T> type;

  public TypedChildElementCollection(Class<T> type) {
    this.type = type;
  }

  protected ElementNodeListFilter getFilter(ModelElementInstanceImpl modelElement) {
    return new ElementByTypeListFilter(type, modelElement.getModelInstance());
  }
}
