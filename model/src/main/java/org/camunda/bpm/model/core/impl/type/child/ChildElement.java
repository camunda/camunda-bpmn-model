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
import org.camunda.bpm.model.core.instance.ModelElementInstance;

/**
 * <p>Provides access to one of the child element of an element</p>
 *
 * @author Daniel Meyer
 *
 */
public class ChildElement<T extends ModelElementInstance> {

  protected final String localName;

  protected String namespace;

  public ChildElement(String localName) {
    this.localName = localName;
  }

  public void setNamespace(String namespace) {
    this.namespace = namespace;
  }

  public String getNamespace() {
    return namespace;
  }

  @SuppressWarnings("unchecked")
  public T get(ModelElementInstanceImpl parentElement) {
    return (T) parentElement.getUniqueChildElementByNameNs(localName, namespace);
  }

  public void set(ModelElementInstanceImpl parentElement, T newChild) {
    parentElement.setUniqueChildElementByNameNs((ModelElementInstance) newChild);
  }

}
