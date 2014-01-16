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

package org.camunda.bpm.model.bpmn.impl;

import org.camunda.bpm.xml.model.ModelBuilder;
import org.camunda.bpm.xml.model.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.xml.model.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.xml.model.instance.QNameElement;
import org.camunda.bpm.xml.model.type.ModelElementTypeBuilder;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN20_NS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_EVENT_DEFINITION_REF;

/**
 * @author Sebastian Menski
 */
public class EventDefinitionRefImpl extends ModelElementInstanceImpl implements QNameElement {

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(EventDefinitionRefImpl.class, BPMN_ELEMENT_EVENT_DEFINITION_REF)
      .namespaceUri(BPMN20_NS)
      .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<EventDefinitionRefImpl>() {
        public EventDefinitionRefImpl newInstance(ModelTypeInstanceContext instanceContext) {
          return new EventDefinitionRefImpl(instanceContext);
        }
      });

    typeBuilder.build();
  }

  public EventDefinitionRefImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }
}
