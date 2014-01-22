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

import org.camunda.bpm.model.bpmn.CatchEvent;
import org.camunda.bpm.model.bpmn.Event;
import org.camunda.bpm.model.bpmn.EventDefinition;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.child.ChildElementCollection;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;
import org.camunda.bpm.model.xml.type.reference.ElementReferenceCollection;

import java.util.Collection;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 * @author Sebastian Menski
 *
 */
public abstract class CatchEventImpl extends EventImpl implements CatchEvent {

  public static ModelElementType MODEL_TYPE;

  static ChildElementCollection<EventDefinition> eventDefinitionsColl;

  static ElementReferenceCollection<EventDefinition, EventDefinitionRefImpl> eventDefinitionsRefColl;

  public static void registerType(ModelBuilder modelBuilder) {

    ModelElementTypeBuilder builder = modelBuilder.defineType(CatchEvent.class, BPMN_ELEMENT_CATCH_EVENT)
      .namespaceUri(BPMN20_NS)
      .abstractType()
      .extendsType(Event.class);

    SequenceBuilder sequence = builder.sequence();

    eventDefinitionsColl = sequence.elementCollection(EventDefinition.class)
      .build();

    eventDefinitionsRefColl = sequence.elementCollection(EventDefinitionRefImpl.class)
      .qNameElementReferenceCollection(EventDefinition.class)
      .build();

    MODEL_TYPE = builder.build();
  }
  public CatchEventImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public Collection<EventDefinition> getEventDefinitions() {
    return eventDefinitionsColl.get(this);
  }

  public Collection<EventDefinition> getEventDefinitionRefs() {
    return eventDefinitionsRefColl.getReferenceTargetElements(this);
  }
}
