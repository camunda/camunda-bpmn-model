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
import org.camunda.bpm.model.core.impl.util.DomUtil;
import org.camunda.bpm.model.core.impl.util.DomUtil.ElementByNameListFilter;
import org.camunda.bpm.model.core.impl.util.DomUtil.ElementNodeListFilter;

/**
 * <p>Collection containing all elements with a given QName.</p>
 *
 * @author Daniel Meyer
 *
 */
public class NamedChildElementCollection<T extends ModelElement> extends ChildElementCollection<T> {

  /** the filter to use */
  private final ElementNodeListFilter filter;

  /**
   * Crates a mutable collection.
   *
   * @param localName the local name of the elements in the same namespace as xmlElement
   * @param element the element to wrap
   */
  public NamedChildElementCollection(String localName, AbstractModelElement element) {
    this(localName, element, true);
  }

  /**
   * Crates a collection.
   *
   * @param localName the local name of the elements in the same namespace as xmlElement
   * @param element the element to wrap
   * @param isMutable indicates whether collection is mutable
   */
  public NamedChildElementCollection(String localName, AbstractModelElement element, boolean isMutable) {
    this(localName, DomUtil.getNamespaceUri(element.getDomElement()), element, isMutable);
  }

  /**
   * Crates a collection.
   *
   * @param localName the local name of the elements
   * @param namespaceUri the namespace Uri
   * @param element the element to wrap
   * @param isMutable indicates whether collection is mutable
   */
  public NamedChildElementCollection(String localName, String namespaceUri, AbstractModelElement element, boolean isMutable) {
    super(element, isMutable);
    this.filter = new ElementByNameListFilter(localName, namespaceUri);
  }

  /**
   * @return the filter
   */
  protected ElementNodeListFilter getFilter() {
    return filter;
  }

}
