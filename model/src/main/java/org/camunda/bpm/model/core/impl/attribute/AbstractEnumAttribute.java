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
 *
 * @author Daniel Meyer
 *
 */
public abstract class AbstractEnumAttribute<T extends Enum<T>> extends Attribute<T> {

  protected Class<T> type;

  public AbstractEnumAttribute(Class<T> type, AbstractModelElement element) {
    super(element);
    this.type = type;
  }

  protected T convertXmlValueToModelValue(String rawValue) {
    return Enum.valueOf(type, rawValue);
  }

  protected String convertModelValueToXmlValue(T modelValue) {
    return modelValue.name();
  }


}
