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
package org.camunda.bpm.model.core.impl.util;

/**
 * @author Daniel Meyer
 *
 */
public class QName {

  protected final String namespaceUri;
  protected final String localName;

  public QName(String localName) {
    this(localName, null);
  }

  public QName(String localName, String namespaceUri) {
    this.localName = localName;
    this.namespaceUri = namespaceUri;
  }

  public String getNamespaceUri() {
    return namespaceUri;
  }

  public String getLocalName() {
    return localName;
  }

  @Override
  public String toString() {
    return ((namespaceUri == null) ? "" : (namespaceUri + ":")) + localName;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((localName == null) ? 0 : localName.hashCode());
    result = prime * result + ((namespaceUri == null) ? 0 : namespaceUri.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    QName other = (QName) obj;
    if (localName == null) {
      if (other.localName != null)
        return false;
    } else if (!localName.equals(other.localName))
      return false;
    if (namespaceUri == null) {
      if (other.namespaceUri != null)
        return false;
    } else if (!namespaceUri.equals(other.namespaceUri))
      return false;
    return true;
  }


}
