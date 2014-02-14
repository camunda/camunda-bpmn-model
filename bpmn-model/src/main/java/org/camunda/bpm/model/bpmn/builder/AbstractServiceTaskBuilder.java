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
import org.camunda.bpm.model.bpmn.instance.ServiceTask;

/**
 * @author Sebastian Menski
 */
public abstract class AbstractServiceTaskBuilder<B extends AbstractServiceTaskBuilder<B>> extends AbstractTaskBuilder<B, ServiceTask> {

  protected AbstractServiceTaskBuilder(BpmnModelInstance modelInstance, ServiceTask element, Class<?> selfType) {
    super(modelInstance, element, selfType);
  }

  /**
   * Sets the implementation of the build service task.
   *
   * @param implementation  the implementation to set
   * @return the builder object
   */
  public B implementation(String implementation) {
    element.setImplementation(implementation);
    return myself;
  }

  /**
   * Sets the camunda class attribute.
   *
   * @param className  the class name to set
   * @return the builder object
   */
  public B className(String className) {
    element.setClassName(className);
    return myself;
  }

  /**
   * Sets the camunda expression attribute.
   *
   * @param expression  the expression to set
   * @return the builder object
   */
  public B expression(String expression) {
    element.setExpression(expression);
    return myself;
  }

  /**
   * Sets the camunda delegateExpression attribute.
   *
   * @param expression  the delegateExpression to set
   * @return the builder object
   */
  public B delegateExpression(String expression) {
    element.setDelegateExpression(expression);
    return myself;
  }


}
