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

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN20_NS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_ID;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_TEXT_FORMAT;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_TYPE_BASE_ELEMENT;

import org.camunda.bpm.model.bpmn.Documentation;
import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder.ModelTypeIntanceProvider;

/**
 *
 * @author Daniel Meyer
 *
 */
public class DocumentationImpl extends AbstractBpmnModelElement implements Documentation {

  static Attribute<String> idAttr;
  static Attribute<String> textFormatAttr;

  public static ModelElementType MODEL_TYPE;

  public static void registerType(Model model) {

    ModelElementTypeBuilder typeBuilder = model.defineType(Documentation.class, BPMN_TYPE_BASE_ELEMENT)
      .namespaceUri(BPMN20_NS)
      .instanceProvider(new ModelTypeIntanceProvider<Documentation>() {
        public Documentation newInstance(ModelTypeInstanceContext instanceContext) {
          return new DocumentationImpl(instanceContext);
        }
      });

    idAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_ID)
      .build();

    textFormatAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_TEXT_FORMAT)
      .build();

    MODEL_TYPE = typeBuilder.build();
  }

  public DocumentationImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public String getId() {
    return idAttr.getValue(this);
  }

  public void setId(String id) {
    idAttr.setValue(this, id);
  }

  public String getTextFormat() {
    return textFormatAttr.getValue(this);
  }

  public void setTextFormat(String textFormat) {
    textFormatAttr.setValue(this, textFormat);
  }

}
