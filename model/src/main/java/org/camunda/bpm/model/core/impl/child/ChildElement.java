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

/**
 * <p>Wraps an {@link XmlElement} and provides access to one of its child elements</p>
 *
 * @author Daniel Meyer
 *
 */
public class ChildElement<T extends ModelElement> {

  protected final AbstractModelElement parentElement;
  protected final String localName;
  protected String namespaceUri;

  public ChildElement(String localName, String namespaceUri, AbstractModelElement parentElement) {
    this.localName = localName;
    this.namespaceUri = namespaceUri;
    this.parentElement = parentElement;
  }

  public ChildElement(String localName, AbstractModelElement parentElement) {
    this(localName, parentElement.getDomElement().getNamespaceURI(), parentElement);
  }

  /**
   * @return the element or null
   */
  public T get() {
    return parentElement.getUniqueChildElementByNameNs(localName, namespaceUri);
  }

  public void set(T newChild) {
    parentElement.setUniqueChildElementByNameNs((AbstractModelElement) newChild);
  }

}
