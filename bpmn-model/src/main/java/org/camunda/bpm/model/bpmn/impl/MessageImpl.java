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

import org.camunda.bpm.model.bpmn.Message;
import org.camunda.bpm.model.bpmn.RootElement;
import org.camunda.bpm.xml.model.ModelBuilder;
import org.camunda.bpm.xml.model.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.xml.model.type.attribute.Attribute;
import org.camunda.bpm.xml.model.type.ModelElementType;
import org.camunda.bpm.xml.model.type.ModelElementTypeBuilder;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 * @author Sebastian Menski
 *
 */
public class MessageImpl extends RootElementImpl implements Message {

  public static ModelElementType MODEL_TYPE;

  static Attribute<String> nameAttr;

  public static void registerType(ModelBuilder modelBuilder) {

    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(Message.class, BPMN_ELEMENT_MESSAGE)
      .namespaceUri(BPMN20_NS)
      .extendsType(RootElement.class)
      .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Message>() {
        public Message newInstance(ModelTypeInstanceContext instanceContext) {
          return new MessageImpl(instanceContext);
        }
      });

    nameAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_NAME)
      .build();

    MODEL_TYPE = typeBuilder.build();
  }

  public MessageImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public String getName() {
    return nameAttr.getValue(this);
  }

  public void setName(String name) {
    nameAttr.setValue(this, name);
  }

}
