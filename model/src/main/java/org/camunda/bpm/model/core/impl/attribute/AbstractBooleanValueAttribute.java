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
import org.camunda.bpm.model.core.impl.util.ModelUtil;

/**
 * <p>Base class for implementing boolean value attributes</p>
 *
 * @author Daniel Meyer
 *
 */
public abstract class AbstractBooleanValueAttribute extends Attribute<Boolean> {

  public AbstractBooleanValueAttribute(AbstractModelElement element) {
    super(element);
  }

  public AbstractBooleanValueAttribute(AbstractModelElement element, String namespaceUri) {
    super(element, namespaceUri);
  }

  protected Boolean convertXmlValueToModelValue(String rawValue) {
    return ModelUtil.valueAsBoolean(rawValue);
  }

  protected String convertModelValueToXmlValue(Boolean modelValue) {
    return ModelUtil.valueAsString(modelValue);
  }

}
