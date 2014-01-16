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

import org.camunda.bpm.model.bpmn.EventDefinition;
import org.camunda.bpm.model.bpmn.Message;
import org.camunda.bpm.model.bpmn.MessageEventDefinition;
import org.camunda.bpm.model.core.ModelBuilder;
import org.camunda.bpm.model.core.ModelReferenceException;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.impl.type.reference.AttributeReferenceImpl;
import org.camunda.bpm.model.core.impl.util.QName;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.reference.AttributeReference;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
*
* @author Sebastian Menski
*
*/
public class MessageEventDefinitionImpl extends EventDefinitionImpl implements MessageEventDefinition {

  public static ModelElementType MODEL_TYPE;

  static AttributeReference<Message> messageRef;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(MessageEventDefinition.class, BPMN_ELEMENT_MESSAGE_EVENT_DEFINITION)
      .namespaceUri(BPMN20_NS)
      .extendsType(EventDefinition.class)
      .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<MessageEventDefinition>() {
        public MessageEventDefinition newInstance(ModelTypeInstanceContext instanceContext) {
          return new MessageEventDefinitionImpl(instanceContext);
        }
      });

    messageRef = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_MESSAGE_REF)
      .qNameAttributeReference(Message.class)
      .build();

    MODEL_TYPE = typeBuilder.build();
  }

  public MessageEventDefinitionImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  /**
   * Get the reference target message
   * @return the message or null if not set
   */
  public Message getMessage() {
    return messageRef.getReferencedElement(this);
  }

  /**
   * Get the QName of the reference target message
   * @return the QName of the message
   */
  public String getMessageRef() {
    return messageRef.getReferenceIdentifier(this);
  }

  /**
   * Set message as reference target by QName
   *
   * @param qname the QName of the reference target message
   */
  public void setMessageRef(QName qname) {
    String referenceIdentifier = qname.getLocalName();
    ModelElementInstance referenceTargetElement = modelInstance.getModelElementById(referenceIdentifier);
    if (referenceTargetElement != null) {
      try {
        setMessage((Message) referenceTargetElement);
      }
      catch (ClassCastException e) {
        throw new ModelReferenceException("Expected type " + Message.class + " for reference target but found " +
          referenceTargetElement.getClass());
      }
    }
    else {
      throw new ModelReferenceException("Unable to find model element for id " + referenceIdentifier +
        ". Please add model element before referencing to the model");
    }
  }

  /**
   * Set the reference target message
   * @param message the reference target message
   */
  public void setMessage(Message message) {
    ((AttributeReferenceImpl<Message>) messageRef).setReferencedElement(this, message);
  }

}
