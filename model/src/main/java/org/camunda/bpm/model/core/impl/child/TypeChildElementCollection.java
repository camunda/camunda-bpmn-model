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
package org.camunda.bpm.model.core.impl.child;

import org.camunda.bpm.model.core.impl.AbstractModelElement;
import org.camunda.bpm.model.core.impl.ModelElement;
import org.camunda.bpm.model.core.impl.util.DomUtil.ElementByTypeListFilter;
import org.camunda.bpm.model.core.impl.util.DomUtil.ElementNodeListFilter;

/**
 * <p>Collection containing all elements with a given type.</p>
 *
 * @author Daniel Meyer
 *
 */
public class TypeChildElementCollection<T extends ModelElement> extends ChildElementCollection<T> {

  /** the filter to use */
  private final ElementNodeListFilter filter;

  public TypeChildElementCollection(Class<T> type,  AbstractModelElement element) {
    this(type, element, true);
  }

  public TypeChildElementCollection(Class<T> type,  AbstractModelElement element, boolean isMutable) {
    super(element, isMutable);
    this.filter = new ElementByTypeListFilter(type, element.getModel());
  }

  /**
   * @return the filter
   */
  protected ElementNodeListFilter getFilter() {
    return filter;
  }
}
