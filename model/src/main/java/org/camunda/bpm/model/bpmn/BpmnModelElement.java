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
package org.camunda.bpm.model.bpmn;

import org.camunda.bpm.model.core.impl.ModelElement;

/**
 * <p>Interface implemented by all elements in a BPMN Model. Provides access to model
 * resources such as the {@link BpmnModel} element itself and the {@link BpmnElementFactory}.</p>
 *
 * @author Daniel Meyer
 *
 */
public interface BpmnModelElement extends ModelElement {

  /**
   * Return the {@link BpmnModel} this element is part of. Note: the element may
   * not actually be connected to the model tree in the sense that it is a child
   * element of some element in the Model tree. It means that it was created by this
   * model's {@link BpmnElementFactory} and can be connected to the actual model tree
   * at any time.
   *
   * @return the {@link BpmnModel} this element is part of.
   */
  BpmnModel getBpmnModel();

  /**
   * The factory to be used for creating new elements in this model. Only elements
   * obtained through this factory can be added as child elements to the model.
   *
   * @return the {@link BpmnElementFactory} for creating new elements in this model.
   */
  BpmnElementFactory getBpmnElementFactory();

}
