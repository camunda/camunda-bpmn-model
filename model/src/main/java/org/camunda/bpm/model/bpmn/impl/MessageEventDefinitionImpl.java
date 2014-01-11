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
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_MESSAGE_REF;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_MESSAGE_EVENT_DEFINITION;

import org.camunda.bpm.model.bpmn.EventDefinition;
import org.camunda.bpm.model.bpmn.Message;
import org.camunda.bpm.model.bpmn.MessageEventDefinition;
import org.camunda.bpm.model.core.ModelBuilder;
import org.camunda.bpm.model.core.ModelReferenceException;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.impl.type.reference.QNameReferenceImpl;
import org.camunda.bpm.model.core.impl.util.QName;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder.ModelTypeIntanceProvider;
import org.camunda.bpm.model.core.type.Reference;
import org.camunda.bpm.model.core.type.StringAttributeBuilder;

/**
*
* @author Sebastian Menski
*
*/
public class MessageEventDefinitionImpl extends EventDefinitionImpl implements MessageEventDefinition {

  public static ModelElementType MODEL_TYPE;

  static Attribute<String>  messageRefAttr;
  static Reference<Message> messageRef;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(MessageEventDefinition.class, BPMN_ELEMENT_MESSAGE_EVENT_DEFINITION)
      .namespaceUri(BPMN20_NS)
      .extendsType(EventDefinition.class)
      .instanceProvider(new ModelTypeIntanceProvider<MessageEventDefinition>() {
        public MessageEventDefinition newInstance(ModelTypeInstanceContext instanceContext) {
          return new MessageEventDefinitionImpl(instanceContext);
        }
      });

    StringAttributeBuilder messageRefAttrBuilder = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_MESSAGE_REF);
    messageRef = messageRefAttrBuilder.qNameReference(Message.class).build();
    messageRefAttr = messageRefAttrBuilder.build();

    MODEL_TYPE = typeBuilder.build();
  }

  public MessageEventDefinitionImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public String getMessageRef() {
    QNameReferenceImpl<Message> qnameRef = (QNameReferenceImpl<Message>) messageRef;
    Message message = getMessage();
    if (message != null) {
      return message.getAttributeValue(qnameRef.getIdAttributeName());
    }
    else {
      return null;
    }
  }

  public void setMessageRef(QName qname) {
    QNameReferenceImpl<Message> qnameRef = (QNameReferenceImpl<Message>) messageRef;
    if (qnameRef.getIdAttributeName().equals("id")) {
      ModelElementInstance modelElement = modelInstance.getModelElementById(qname.getLocalName());
      try {
        Message message = (Message) modelElement;
        setMessage(message);
      }
      catch (ClassCastException e) {
        throw new ModelReferenceException("Expected type " + Message.class + " but found " + modelElement.getClass());
      }
    }
    else {
      throw new ModelReferenceException("Unable to get model element by " + qnameRef.getIdAttributeName() + " attribute");
    }
  }

  public Message getMessage() {
    return messageRef.getReferencedElement(this);
  }

  public void setMessage(Message message) {
    messageRef.setReferencedElement(this, message);
  }

}
