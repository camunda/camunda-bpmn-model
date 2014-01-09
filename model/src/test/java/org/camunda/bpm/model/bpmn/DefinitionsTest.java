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

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.XML_SCHEMA_NS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.XPATH_NS;
import static org.fest.assertions.Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.camunda.bpm.model.bpmn.impl.BpmnModelInstanceImpl;
import org.camunda.bpm.model.bpmn.util.BpmnModelResource;
import org.camunda.bpm.model.core.ModelReferenceException;
import org.camunda.bpm.model.core.ModelValidationException;
import org.camunda.bpm.model.core.impl.util.IoUtil;
import org.camunda.bpm.model.core.impl.util.QName;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Daniel Meyer
 *
 */
public class DefinitionsTest extends BpmnModelTest {

  @Test
  @BpmnModelResource
  public void shouldImportEmptyDefinitions() {

    Definitions definitions = bpmnModelInstance.getDefinitions();
    assertThat(definitions).isNotNull();

    // provided in file
    assertThat(definitions.getTargetNamespace()).isEqualTo("http://camunda.org/test");

    // defaults provided in Schema
    assertThat(definitions.getExpressionLanguage()).isEqualTo(XPATH_NS);
    assertThat(definitions.getTypeLanguage()).isEqualTo(XML_SCHEMA_NS);

    // not provided in file
    assertThat(definitions.getExporter()).isNull();
    assertThat(definitions.getExporterVersion()).isNull();
    assertThat(definitions.getId()).isNull();
    assertThat(definitions.getName()).isNull();

    // has no imports
    assertThat(definitions.getImports()).isEmpty();
  }

  @Test
  public void shouldNotImportWrongOrderedSequence() {
    try {
      Bpmn.readModelFromStream(getClass().getResourceAsStream("DefinitionsTest.shouldNotImportWrongOrderedSequence.bpmn"));
      Assert.fail("Model is invalid and should not pass the validation");
    }
    catch (Exception e) {
      assertThat(e).isInstanceOf(ModelValidationException.class);
    }
  }

  @Test
  public void shouldAddChildElementsInCorrectOrder() {
    // create an empty model
    BpmnModelInstance bpmnModelInstance = Bpmn.createEmptyModel();

    // add definitions
    Definitions definitions = bpmnModelInstance.newInstance(Definitions.class);
    definitions.setTargetNamespace("Examples");
    bpmnModelInstance.setDefinitions(definitions);

    // create a Process element and add it to the definitions
    Process process = bpmnModelInstance.newInstance(Process.class);
    process.setId("some-process-id");
    definitions.getRootElements().add(process);

    // create an Import element and add it to the definitions
    Import importElement = bpmnModelInstance.newInstance(Import.class);
    importElement.setNamespace("Imports");
    importElement.setLocation("here");
    importElement.setImportType("example");
    definitions.getImports().add(importElement);

    // create another Process element and add it to the definitions
    process = bpmnModelInstance.newInstance(Process.class);
    process.setId("another-process-id");
    definitions.getRootElements().add(process);

    // create another Import element and add it to the definitions
    importElement = bpmnModelInstance.newInstance(Import.class);
    importElement.setNamespace("Imports");
    importElement.setLocation("there");
    importElement.setImportType("example");
    definitions.getImports().add(importElement);

    // validate model
    try {
      Bpmn.validateModel((BpmnModelInstanceImpl) bpmnModelInstance);
    }
    catch (ModelValidationException e) {
      Assert.fail();
    }
  }

  @Test
  @BpmnModelResource
  public void shouldNotAffectComments() throws IOException {
    Definitions definitions = bpmnModelInstance.getDefinitions();
    assertThat(definitions).isNotNull();

    // create another Process element and add it to the definitions
    Process process = bpmnModelInstance.newInstance(Process.class);
    process.setId("another-process-id");
    definitions.getRootElements().add(process);

    // create another Import element and add it to the definitions
    Import importElement = bpmnModelInstance.newInstance(Import.class);
    importElement.setNamespace("Imports");
    importElement.setLocation("there");
    importElement.setImportType("example");
    definitions.getImports().add(importElement);

    // validate model
    try {
      Bpmn.validateModel((BpmnModelInstanceImpl) bpmnModelInstance);
    }
    catch (ModelValidationException e) {
      Assert.fail();
    }

    // convert the model to the XML string representation
    OutputStream outputStream = new ByteArrayOutputStream();
    Bpmn.writeModelToStream(outputStream, (BpmnModelInstanceImpl) bpmnModelInstance);
    InputStream inputStream = IoUtil.convertOutputStreamToInputStream(outputStream);
    String modelString = IoUtil.getStringFromInputStream(inputStream);
    IoUtil.closeSilently(outputStream);
    IoUtil.closeSilently(inputStream);

    // read test process from file as string
    inputStream = getClass().getResourceAsStream("DefinitionsTest.shouldNotAffectCommentsResult.bpmn");
    String fileString = IoUtil.getStringFromInputStream(inputStream);
    IoUtil.closeSilently(inputStream);

    // compare strings
    assertThat(modelString).isEqualTo(fileString);
  }

  @Test
  public void shouldAddMessageAndMessageEventDefinition() {
    // create empty model
    BpmnModelInstance bpmnModelInstance = Bpmn.createEmptyModel();

    // add definitions to model
    Definitions definitions = bpmnModelInstance.newInstance(Definitions.class);
    definitions.setTargetNamespace("Examples");
    bpmnModelInstance.setDefinitions(definitions);

    // create and add message
    Message message = bpmnModelInstance.newInstance(Message.class);
    message.setId("start-message-id");
    definitions.getRootElements().add(message);

    // create and add message event definition
    MessageEventDefinition messageEventDefintion = bpmnModelInstance.newInstance(MessageEventDefinition.class);
    messageEventDefintion.setId("message-event-def-id");
    messageEventDefintion.setMessage(message);
    definitions.getRootElements().add(messageEventDefintion);

    // test if message was set correctly
    Message setMessage = messageEventDefintion.getMessage();
    assertThat(setMessage).isEqualTo(message);
    String messageRefString = message.getId();
    assertThat(messageRefString).isEqualTo(messageEventDefintion.getMessageRef());

    // add process
    Process process = bpmnModelInstance.newInstance(Process.class);
    process.setId("messageEventDefinition");
    definitions.getRootElements().add(process);

    // add start event
    StartEvent startEvent = bpmnModelInstance.newInstance(StartEvent.class);
    startEvent.setId("theStart");
    process.getFlowElements().add(startEvent);

    // create and add message event definition to start event
    MessageEventDefinition startEventMessageEventDefinition = bpmnModelInstance.newInstance(MessageEventDefinition.class);
    startEventMessageEventDefinition.setMessage(message);
    startEvent.getEventDefinitions().add(startEventMessageEventDefinition);

    // create another message but do not add it
    Message anotherMessage = bpmnModelInstance.newInstance(Message.class);
    anotherMessage.setId("another-message-id");

    // create a message event definition and try to add last create message
    MessageEventDefinition anotherMessageEventDefinition = bpmnModelInstance.newInstance(MessageEventDefinition.class);
    try {
      anotherMessageEventDefinition.setMessage(anotherMessage);
      Assert.fail("Message should not be added to message event definition, cause it is not part of the model");
    }
    catch(Exception e) {
      assertThat(e).isInstanceOf(ModelReferenceException.class);
    }

    // first add message to model than to event definition
    definitions.getRootElements().add(anotherMessage);
    anotherMessageEventDefinition.setMessage(anotherMessage);
    startEvent.getEventDefinitions().add(anotherMessageEventDefinition);

    // message event definition and add message by id to it
    anotherMessageEventDefinition = bpmnModelInstance.newInstance(MessageEventDefinition.class);
    startEvent.getEventDefinitions().add(anotherMessageEventDefinition);
    anotherMessageEventDefinition.setMessageRef(new QName(message.getId()));
    assertThat(anotherMessageEventDefinition.getMessage()).isEqualTo(message);

    // validate model
    try {
      Bpmn.validateModel((BpmnModelInstanceImpl) bpmnModelInstance);
    }
    catch (ModelValidationException e) {
      Assert.fail();
    }
  }

  @Test
  public void shouldAddParentChildElementInCorrectOrder() {
    // create empty model
    BpmnModelInstance bpmnModelInstance = Bpmn.createEmptyModel();

    // add definitions to model
    Definitions definitions = bpmnModelInstance.newInstance(Definitions.class);
    definitions.setTargetNamespace("Examples");
    bpmnModelInstance.setDefinitions(definitions);

    // add process
    Process process = bpmnModelInstance.newInstance(Process.class);
    process.setId("messageEventDefinition");
    definitions.getRootElements().add(process);

    // add start event
    StartEvent startEvent = bpmnModelInstance.newInstance(StartEvent.class);
    startEvent.setId("theStart");
    process.getFlowElements().add(startEvent);

    // create and add message
    Message message = bpmnModelInstance.newInstance(Message.class);
    message.setId("start-message-id");
    definitions.getRootElements().add(message);

    // add message event definition to start event
    MessageEventDefinition startEventMessageEventDefinition = bpmnModelInstance.newInstance(MessageEventDefinition.class);
    startEventMessageEventDefinition.setMessage(message);
    startEvent.getEventDefinitions().add(startEventMessageEventDefinition);

    // add property after message event defintion
    Property property = bpmnModelInstance.newInstance(Property.class);
    startEvent.getProperties().add(property);

    // finally add an extensions element
    ExtensionElements extensionElements = bpmnModelInstance.newInstance(ExtensionElements.class);
    process.setExtensionElements(extensionElements);

    // validate model
    try {
      Bpmn.validateModel((BpmnModelInstanceImpl) bpmnModelInstance);
    }
    catch (ModelValidationException e) {
      Assert.fail();
    }
  }

}
