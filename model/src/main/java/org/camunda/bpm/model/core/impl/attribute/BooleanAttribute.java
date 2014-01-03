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
package org.camunda.bpm.model.core.impl.attribute;

import org.camunda.bpm.model.core.impl.AbstractModelElement;

/**
 * <p>class for providing Boolean value attributes. Takes care of type conversion and
 * the interaction with the underlying Xml core model.</p>
 *
 * @author Daniel Meyer
 *
 */
public class BooleanAttribute extends AbstractBooleanValueAttribute {

  protected final String attributeName;

  public BooleanAttribute(String attributeName, AbstractModelElement element) {
    super(element);
    this.attributeName = attributeName;
  }

  public BooleanAttribute(String attributeName, AbstractModelElement element, String namespaceUri) {
    super(element, namespaceUri);
    this.attributeName = attributeName;
  }

  protected String getAttributeName() {
    return attributeName;
  }

}
