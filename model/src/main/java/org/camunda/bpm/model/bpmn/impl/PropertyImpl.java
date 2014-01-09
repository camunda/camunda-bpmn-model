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
package org.camunda.bpm.model.bpmn.impl;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

import org.camunda.bpm.model.bpmn.BaseElement;
import org.camunda.bpm.model.bpmn.Property;
import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder.ModelTypeIntanceProvider;

/**
 * @author Sebastian Menski
 *
 */
public class PropertyImpl extends BaseElementImp implements Property {

  public static ModelElementType MODEL_TYPE;

  public static void registerType(Model model) {
    ModelElementTypeBuilder typeBuilder = model.defineType(Property.class, BPMN_ELEMENT_PROPERTY)
      .namespaceUri(BPMN20_NS)
      .extendsType(model.getType(BaseElement.class))
      .instanceProvider(new ModelTypeIntanceProvider<Property>() {
        public Property newInstance(ModelTypeInstanceContext instanceContext) {
          return new PropertyImpl(instanceContext);
        }
      });

    MODEL_TYPE = typeBuilder.build();
  }

  public PropertyImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

}
