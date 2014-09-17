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

package org.camunda.bpm.model.bpmn.impl.instance;

import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.ChildElement;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;
import org.camunda.bpm.model.xml.type.reference.AttributeReference;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;
import static org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

/**
 * The BPMN dataObject element
 *
 * @author Dario Campagna
 */
public class DataObjectImpl extends FlowElementImpl implements DataObject {

  protected static AttributeReference<ItemDefinition> itemSubjectRefAttribute;
  protected static ChildElement<DataState> dataStateChild;
  protected static Attribute<Boolean> isCollectionAttribute;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(DataObject.class, BPMN_ELEMENT_DATA_OBJECT)
      .namespaceUri(BPMN20_NS)
      .extendsType(FlowElement.class)
      .instanceProvider(new ModelTypeInstanceProvider<DataObject>() {
        public DataObject newInstance(ModelTypeInstanceContext instanceContext) {
          return new DataObjectImpl(instanceContext);
        }
      });

    itemSubjectRefAttribute = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_ITEM_SUBJECT_REF)
      .qNameAttributeReference(ItemDefinition.class)
      .build();

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    dataStateChild = sequenceBuilder.element(DataState.class)
      .build();

    isCollectionAttribute = typeBuilder.booleanAttribute(BPMN_ATTRIBUTE_IS_COLLECTION)
      .defaultValue(false)
      .build();

    typeBuilder.build();
  }

  public DataObjectImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  public ItemDefinition getItemSubject() {
    return itemSubjectRefAttribute.getReferenceTargetElement(this);
  }

  public void setItemSubject(ItemDefinition itemSubject) {
    itemSubjectRefAttribute.setReferenceTargetElement(this, itemSubject);
  }

  public DataState getDataState() {
    return dataStateChild.getChild(this);
  }

  public void setDataState(DataState dataState) {
    dataStateChild.setChild(this, dataState);
  }

  public boolean isCollection() {
    return isCollectionAttribute.getValue(this);
  }

  public void setCollection(boolean isCollection) {
    isCollectionAttribute.setValue(this, isCollection);
  }

}
