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
import org.camunda.bpm.model.bpmn.instance.UserTask;

/**
 * @author Sebastian Menski
 */
public abstract class AbstractUserTaskBuilder<B extends AbstractUserTaskBuilder<B>> extends AbstractTaskBuilder<B, UserTask> {

  protected AbstractUserTaskBuilder(BpmnModelInstance modelInstance, UserTask element, Class<?> selfType) {
    super(modelInstance, element, selfType);
  }

  /**
   * Sets the implementation of the build user task.
   *
   * @param implementation  the implementation to set
   * @return the builder object
   */
  public B implementation(String implementation) {
    element.setImplementation(implementation);
    return myself;
  }

  /**
   * Sets the activiti attribute form key.
   *
   * @param formKey  the form key to set
   * @return the builder object
   */
  public B formKey(String formKey) {
    element.setFormKey(formKey);
    return myself;
  }

  /**
   * Sets the activiti attribute assignee.
   *
   * @param assignee  the assignee to set
   * @return the builder object
   */
  public B assignee(String assignee) {
    element.setAssignee(assignee);
    return myself;
  }

  /**
   * Sets the activiti attribute candidate users.
   *
   * @param candidateUsers  the candidate users to set
   * @return the builder object
   */
  public B candidateUsers(String candidateUsers) {
    element.setCandidateUsers(candidateUsers);
    return myself;
  }

  /**
   * Sets the activiti attribute candidate groups.
   *
   * @param candidateGroups  the candidate groups to set
   * @return the builder object
   */
  public B candidateGroups(String candidateGroups) {
    element.setCandidateGroups(candidateGroups);
    return myself;
  }

}
