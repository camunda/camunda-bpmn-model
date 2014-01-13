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

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Sebastian Menski
 *
 */
public class ReferenceTest extends BpmnModelTest {

  protected BpmnModelInstance bpmnModelInstance;
  protected Message message;
  protected MessageEventDefinition messageEventDefinition;

  @Before
  public void createModel() {
    bpmnModelInstance = Bpmn.createEmptyModel();
    Definitions definitions = bpmnModelInstance.newInstance(Definitions.class);
    bpmnModelInstance.setDefinitions(definitions);

    message = bpmnModelInstance.newInstance(Message.class);
    message.setId("message-id");
    definitions.getRootElements().add(message);

    Process process = bpmnModelInstance.newInstance(Process.class);
    process.setId("process-id");
    definitions.getRootElements().add(process);

    StartEvent startEvent = bpmnModelInstance.newInstance(StartEvent.class);
    process.getFlowElements().add(startEvent);

    messageEventDefinition = bpmnModelInstance.newInstance(MessageEventDefinition.class);
    messageEventDefinition.setId("msg-def-id");
    messageEventDefinition.setMessage(message);
    startEvent.getEventDefinitions().add(messageEventDefinition);
  }

  @Test
  public void testShouldUpdateReferenceOnIdChange() {
    assertThat(messageEventDefinition.getMessageRef()).isEqualTo(message.getId());
    message.setId("changed-message-id");
    assertThat(message.getId()).isEqualTo("changed-message-id");
    assertThat(messageEventDefinition.getMessageRef()).isEqualTo(message.getId());

    message.setAttributeValue("id", "another-message-id", true);
    assertThat(message.getId()).isEqualTo("another-message-id");
    assertThat(messageEventDefinition.getMessageRef()).isEqualTo(message.getId());
  }

  @Test
  public void testShouldRemoveReferenceIfReferencingElementIsRemoved() {
    assertThat(messageEventDefinition.getMessageRef()).isEqualTo(message.getId());

    Definitions definitions = bpmnModelInstance.getDefinitions();
    definitions.getRootElements().remove(message);

    assertThat(messageEventDefinition.getId()).isEqualTo("msg-def-id");
    assertThat(messageEventDefinition.getMessageRef()).isNull();
  }

  @Test
  public void testShouldRemoveReferenceIfReferencingAttributeIsRemoved() {
    assertThat(messageEventDefinition.getMessageRef()).isEqualTo(message.getId());

    message.removeAttribute("id");

    assertThat(messageEventDefinition.getId()).isEqualTo("msg-def-id");
    assertThat(messageEventDefinition.getMessageRef()).isNull();
  }

  @Test
  public void testShouldUpdateReferenceIfReferencingElementIsReplaced() {
    assertThat(messageEventDefinition.getMessageRef()).isEqualTo(message.getId());
    Message newMessage = bpmnModelInstance.newInstance(Message.class);
    newMessage.setId("new-message-id");

    message.replaceElement(newMessage);

    assertThat(messageEventDefinition.getMessageRef()).isEqualTo(newMessage.getId());
  }

}
