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

package org.camunda.bpm.model.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.UserTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.Rendering;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.ChildElementCollection;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

import java.util.Collection;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;
import static org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

/**
 * The BPMN userTask element
 *
 * @author Sebastian Menski
 */
public class UserTaskImpl extends TaskImpl implements UserTask {

  private static Attribute<String> implementationAttribute;
  private static Attribute<String> formKeyAttribute;
  private static Attribute<String> assigneeAttribute;
  private static Attribute<String> candidateUsersAttribute;
  private static Attribute<String> candidateGroupsAttribute;
  private static ChildElementCollection<Rendering> renderingCollection;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(UserTask.class, BPMN_ELEMENT_USER_TASK)
      .namespaceUri(BPMN20_NS)
      .extendsType(Task.class)
      .instanceProvider(new ModelTypeInstanceProvider<UserTask>() {
        public UserTask newInstance(ModelTypeInstanceContext instanceContext) {
          return new UserTaskImpl(instanceContext);
        }
      });

    implementationAttribute = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_IMPLEMENTATION)
      .defaultValue("##unspecified")
      .build();

    formKeyAttribute = typeBuilder.stringAttribute(ACTIVITI_ATTRIBUTE_FORM_KEY)
      .namespace(CAMUNDA_NS)
      .build();

    assigneeAttribute = typeBuilder.stringAttribute(ACTIVITI_ATTRIBUTE_ASSIGNEE)
      .namespace(CAMUNDA_NS)
      .build();

    candidateUsersAttribute = typeBuilder.stringAttribute(ACTIVITI_ATTRIBUTE_CANDIDATE_USERS)
      .namespace(CAMUNDA_NS)
      .build();

    candidateGroupsAttribute = typeBuilder.stringAttribute(ACTIVITI_ATTRIBUTE_CANDIDATE_GROUPS)
      .namespace(CAMUNDA_NS)
      .build();

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    renderingCollection = sequenceBuilder.elementCollection(Rendering.class)
      .build();

    typeBuilder.build();
  }

  public UserTaskImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  @Override
  @SuppressWarnings("unchecked")
  public UserTaskBuilder builder() {
    return new UserTaskBuilder((BpmnModelInstance) modelInstance, this);
  }

  public String getImplementation() {
    return implementationAttribute.getValue(this);
  }

  public void setImplementation(String implementation) {
    implementationAttribute.setValue(this, implementation);
  }

  public Collection<Rendering> getRenderings() {
    return renderingCollection.get(this);
  }

  public String getFormKey() {
    return formKeyAttribute.getValue(this);
  }

  public void setFormKey(String formKey) {
    formKeyAttribute.setValue(this, formKey);
  }

  public String getAssignee() {
    return assigneeAttribute.getValue(this);
  }

  public void setAssignee(String assignee) {
    assigneeAttribute.setValue(this, assignee);
  }

  public String getCandidateUsers() {
    return candidateUsersAttribute.getValue(this);
  }

  public void setCandidateUsers(String candidateUsers) {
    candidateUsersAttribute.setValue(this, candidateUsers);
  }

  public String getCandidateGroups() {
    return candidateGroupsAttribute.getValue(this);
  }

  public void setCandidateGroups(String candidateGroups) {
    candidateGroupsAttribute.setValue(this, candidateGroups);
  }

}
