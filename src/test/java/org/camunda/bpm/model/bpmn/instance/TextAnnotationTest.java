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
package org.camunda.bpm.model.bpmn.instance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import org.camunda.bpm.model.bpmn.instance.Association;
import org.camunda.bpm.model.bpmn.instance.BaseElement;
import org.camunda.bpm.model.bpmn.instance.TextAnnotation;
import org.camunda.bpm.model.bpmn.instance.BpmnModelElementInstanceTest.ChildElementAssumption;

/**
 * @author Filip Hrisafov
 */
public class TextAnnotationTest extends BpmnModelElementInstanceTest {

  private static BpmnModelInstance modelInstance;

  @BeforeClass
  public static void parseModel() {
    modelInstance = Bpmn.readModelFromStream(TextAnnotationTest.class
        .getResourceAsStream("TextAnnotationTest.bpmn"));
  }

  @Test
  public void testGetTextAnnotationsByType() {
    Collection<TextAnnotation> textAnnotations = modelInstance
        .getModelElementsByType(TextAnnotation.class);
    assertNotNull(textAnnotations);
    assertEquals(2, textAnnotations.size());
  }

  @Test
  public void testGetTextAnnotationById() {
    TextAnnotation textAnnotation = modelInstance.getModelElementById("textAnnotation2");
    assertNotNull(textAnnotation);
    assertEquals("text/plain", textAnnotation.getTextFormat());
    assertEquals("Attached text annotation", textAnnotation.getText().getTextContent());
  }

  @Test
  public void testTextAnnotationAsAssociationSource() {
    Association association = modelInstance.getModelElementById("Association_1");
    BaseElement source = association.getSource();
    if (source instanceof TextAnnotation) {
      TextAnnotation textAnnotation = (TextAnnotation) source;
      assertEquals("textAnnotation2", textAnnotation.getId());
    } else {
      fail();
    }
  }

  @Test
  public void testTextAnnotationAsAssociationTarget() {
    Association association = modelInstance.getModelElementById("Association_2");
    BaseElement target = association.getTarget();
    if (target instanceof TextAnnotation) {
      TextAnnotation textAnnotation = (TextAnnotation) target;
      assertEquals("textAnnotation1", textAnnotation.getId());
    } else {
      fail();
    }
  }

  public TypeAssumption getTypeAssumption() {
    return new TypeAssumption(Artifact.class, false);
  }

  public Collection<ChildElementAssumption> getChildElementAssumptions() {
    return Arrays.asList(new ChildElementAssumption(Text.class, 0, 1));
  }

  public Collection<AttributeAssumption> getAttributesAssumptions() {
    return Arrays.asList(new AttributeAssumption("textFormat", false, false, "text/plain"));
  }
}
