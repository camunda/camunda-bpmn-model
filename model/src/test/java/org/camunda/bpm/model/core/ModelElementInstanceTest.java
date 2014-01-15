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
package org.camunda.bpm.model.core;

import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.testmodel.Animals;
import org.camunda.bpm.model.core.testmodel.Bird;
import org.camunda.bpm.model.core.testmodel.TestModelParser;
import org.junit.Test;

import static org.fest.assertions.Assertions.*;

/**
 * @author Daniel Meyer
 *
 */
public class ModelElementInstanceTest {

  @Test
  public void shouldReturnTextContent() {
    ModelInstance modelInstance = new TestModelParser()
      .parseModelFromStream(getClass().getResourceAsStream("ModelElementInstanceTest.textContent.xml"));

    ModelElementInstance tweety = modelInstance.getModelElementById("tweety");
    assertThat(tweety.getTextContent()).isEqualTo("");

    ModelElementInstance donald = modelInstance.getModelElementById("donald");
    assertThat(donald.getTextContent()).isEqualTo("some text content");

    ModelElementInstance daisy = modelInstance.getModelElementById("daisy");
    assertThat(daisy.getTextContent()).isEqualTo("some text content with outer line breaks");

    ModelElementInstance hedwig = modelInstance.getModelElementById("hedwig");
    assertThat(hedwig.getTextContent()).isEqualTo("some text content with inner\n        line breaks");
  }

  @Test
  public void shouldReturnRawTextContent() {
    ModelInstance modelInstance = new TestModelParser()
      .parseModelFromStream(getClass().getResourceAsStream("ModelElementInstanceTest.textContent.xml"));

    ModelElementInstance tweety = modelInstance.getModelElementById("tweety");
    assertThat(tweety.getRawTextContent()).isEqualTo("");

    ModelElementInstance donald = modelInstance.getModelElementById("donald");
    assertThat(donald.getRawTextContent()).isEqualTo("some text content");

    ModelElementInstance daisy = modelInstance.getModelElementById("daisy");
    assertThat(daisy.getRawTextContent()).isEqualTo("\n        some text content with outer line breaks\n    ");

    ModelElementInstance hedwig = modelInstance.getModelElementById("hedwig");
    assertThat(hedwig.getRawTextContent()).isEqualTo("\n        some text content with inner\n        line breaks\n    ");
  }

  private static Bird createBird(ModelInstance modelInstance, String id) {
    Bird bird = modelInstance.newInstance(Bird.class);
    bird.setId(id);
    ((Animals) modelInstance.getDocumentElement()).getAnimals().add(bird);
    return bird;
  }

  @Test
  public void shouldUpdateReference() {
    ModelInstance modelInstance = new TestModelParser().getEmptyModel();
    Animals animals = modelInstance.newInstance(Animals.class);
    modelInstance.setDocumentElement(animals);

    // create some birds
    Bird tweety = createBird(modelInstance, "tweety");
    Bird fiffy = createBird(modelInstance, "fiffy");
    Bird daffy = createBird(modelInstance, "daffy");
    Bird donald = createBird(modelInstance, "donald");
    Bird daisy = createBird(modelInstance, "daisy");
    Bird henri = createBird(modelInstance, "henri");
    Bird rowdy = createBird(modelInstance, "rowdy");
    Bird speedy = createBird(modelInstance, "speedy");
    Bird timmy = createBird(modelInstance, "timmy");

    // set references
    assertThat(tweety.getMother()).isNull();
    tweety.setMother(fiffy);
    assertThat(tweety.getMother()).isNotNull();
    assertThat(tweety.getMother()).isEqualTo(fiffy);

    assertThat(tweety.getFather()).isNull();
    tweety.setFather(daffy);
    assertThat(tweety.getFather()).isNotNull();
    assertThat(tweety.getFather()).isEqualTo(daffy);

    assertThat(tweety.getFriendRefs()).isEmpty();
    tweety.getFriendRefs().add(donald);
    tweety.getFriendRefs().add(daisy);
    tweety.getFriendRefs().add(henri);
    assertThat(tweety.getFriendRefs()).isNotEmpty();
    assertThat(tweety.getFriendRefs()).hasSize(3);
    assertThat(tweety.getFriendRefs()).contains(donald, daisy, henri);
    assertThat(tweety.getFriendRefs()).excludes(fiffy, daffy, rowdy, speedy, timmy);

    assertThat(tweety.getPartnerRefs()).isEmpty();
    tweety.getPartnerRefs().add(rowdy);
    tweety.getPartnerRefs().add(speedy);
    assertThat(tweety.getPartnerRefs()).isNotEmpty();
    assertThat(tweety.getPartnerRefs()).hasSize(2);
    assertThat(tweety.getPartnerRefs()).contains(rowdy, speedy);
    assertThat(tweety.getPartnerRefs()).excludes(fiffy, daffy, donald, daisy, henri, timmy);

    // change ids
    fiffy.setId("mother");
    assertThat(tweety.getMother()).isEqualTo(fiffy);

    daffy.setId("father");
    assertThat(tweety.getFather()).isEqualTo(daffy);

    donald.setId("friend1");
    daisy.setId("friend2");
    henri.setId("friend3");
    assertThat(tweety.getFriendRefs()).hasSize(3);
    assertThat(tweety.getFriendRefs()).contains(donald, daisy, henri);
    assertThat(tweety.getFriendRefs()).excludes(fiffy, daffy, rowdy, speedy, timmy);

    rowdy.setId("partner1");
    speedy.setId("partner2");
    assertThat(tweety.getPartnerRefs()).hasSize(2);
    assertThat(tweety.getPartnerRefs()).contains(rowdy, speedy);
    assertThat(tweety.getPartnerRefs()).excludes(fiffy, daffy, donald, daisy, henri, timmy);

    // replace birds
    fiffy.replaceElement(timmy);
    assertThat(tweety.getMother()).isEqualTo(timmy);
    timmy.replaceElement(fiffy);
    assertThat(tweety.getMother()).isEqualTo(fiffy);

    daffy.replaceElement(timmy);
    assertThat(tweety.getFather()).isEqualTo(timmy);
    timmy.replaceElement(daffy);
    assertThat(tweety.getFather()).isEqualTo(daffy);

    daisy.replaceElement(timmy);
    assertThat(tweety.getFriendRefs()).hasSize(3);
    assertThat(tweety.getFriendRefs()).contains(donald, timmy, henri);
    assertThat(tweety.getFriendRefs()).excludes(fiffy, daffy, rowdy, speedy, daisy);
    timmy.replaceElement(daisy);
    assertThat(tweety.getFriendRefs()).hasSize(3);
    assertThat(tweety.getFriendRefs()).contains(donald, daisy, henri);
    assertThat(tweety.getFriendRefs()).excludes(fiffy, daffy, rowdy, speedy, timmy);

    speedy.replaceElement(timmy);
    assertThat(tweety.getPartnerRefs()).hasSize(2);
    assertThat(tweety.getPartnerRefs()).contains(rowdy, timmy);
    assertThat(tweety.getPartnerRefs()).excludes(fiffy, daffy, donald, daisy, henri, speedy);
    timmy.replaceElement(speedy);
    assertThat(tweety.getPartnerRefs()).hasSize(2);
    assertThat(tweety.getPartnerRefs()).contains(rowdy, speedy);
    assertThat(tweety.getPartnerRefs()).excludes(fiffy, daffy, donald, daisy, henri, timmy);

    // remove id
    fiffy.removeAttribute("id");
    assertThat(tweety.getMother()).isNull();

    daffy.removeAttribute("id");
    assertThat(tweety.getFather()).isNull();

    donald.removeAttribute("id");
    daisy.removeAttribute("id");
    henri.removeAttribute("id");
    assertThat(tweety.getFriendRefs()).isEmpty();

    rowdy.removeAttribute("id");
    speedy.removeAttribute("id");
    assertThat(tweety.getPartnerRefs()).isEmpty();

  }

}
