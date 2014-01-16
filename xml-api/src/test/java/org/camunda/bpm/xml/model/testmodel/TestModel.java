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
package org.camunda.bpm.xml.model.testmodel;

import org.camunda.bpm.xml.model.Model;
import org.camunda.bpm.xml.model.ModelBuilder;
import org.camunda.bpm.xml.model.testmodel.instance.*;

/**
 * @author Daniel Meyer
 *
 */
class TestModel {

  private static Model model;

  public static Model getTestModel() {
    if(model == null) {
      ModelBuilder modelBuilder = ModelBuilder.createInstance(TestModelConstants.MODEL_NAME);

      Animals.registerType(modelBuilder);
      Animal.registerType(modelBuilder);
      FlyingAnimal.registerType(modelBuilder);
      Bird.registerType(modelBuilder);
      FriendRef.registerType(modelBuilder);
      PartnerRef.registerType(modelBuilder);

      model = modelBuilder.build();
    }

    return model;
  }

}
