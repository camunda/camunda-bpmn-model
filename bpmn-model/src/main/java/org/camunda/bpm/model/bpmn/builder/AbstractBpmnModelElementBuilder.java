/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.camunda.bpm.model.bpmn.builder;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstance;
import org.camunda.bpm.model.xml.impl.util.ModelUtil;

/**
 * @author Sebastian Menski
 */
public abstract class AbstractBpmnModelElementBuilder<B extends AbstractBpmnModelElementBuilder<B, E>, E extends BpmnModelElementInstance> {

  protected final BpmnModelInstance modelInstance;
  protected final E element;
  protected final B myself;

  @SuppressWarnings("unchecked")
  protected AbstractBpmnModelElementBuilder(BpmnModelInstance modelInstance, E element, Class<?> selfType) {
    this.modelInstance = modelInstance;
    myself = (B) selfType.cast(this);
    this.element = element;
  }

  /**
   * Finishes the process building.
   *
   * @return the model instance with the build process
   */
  public BpmnModelInstance done() {
    return modelInstance;
  }

}
