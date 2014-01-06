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
import org.camunda.bpm.model.core.ModelValidationException;
import org.camunda.bpm.model.core.impl.util.IoUtil;
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
      Assert.fail("Model is invalid and should not be validated correctly");
    }
    catch (Exception e) {
      assertThat(e).isInstanceOf(ModelValidationException.class);
    }
  }

  @Test
  public void shouldExportCorrectOrderedSequence() throws IOException {
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

   // convert the model to the XML string representation
   OutputStream outputStream = new ByteArrayOutputStream();
   Bpmn.writeModelToStream(outputStream, (BpmnModelInstanceImpl) bpmnModelInstance);
   InputStream inputStream = IoUtil.convertOutputStreamToInputStream(outputStream);
   String modelString = IoUtil.getStringFromInputStream(inputStream);
   IoUtil.closeSilently(outputStream);
   IoUtil.closeSilently(inputStream);

   // read test process from file as string
   inputStream = getClass().getResourceAsStream("DefinitionsTest.shouldExportCorrectOrderedSequence.bpmn");
   String fileString = IoUtil.getStringFromInputStream(inputStream);
   IoUtil.closeSilently(inputStream);

   // compare strings
   assertThat(modelString).isEqualTo(fileString);
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

}
