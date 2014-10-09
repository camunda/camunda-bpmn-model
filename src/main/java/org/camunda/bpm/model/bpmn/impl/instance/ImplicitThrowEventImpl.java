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

import org.camunda.bpm.model.bpmn.impl.BpmnModelConstants;
import org.camunda.bpm.model.bpmn.instance.ImplicitThrowEvent;
import org.camunda.bpm.model.bpmn.instance.ThrowEvent;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

/**
 * The BPMN 2.0 implicitThrowEvent element
 * 
 * @author Filip Hrisafov
 */
public class ImplicitThrowEventImpl extends ThrowEventImpl implements ImplicitThrowEvent {

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder
        .defineType(ImplicitThrowEvent.class,
            BpmnModelConstants.BPMN_ELEMENT_IMPLICIT_THROW_EVENT)
        .namespaceUri(BpmnModelConstants.BPMN20_NS).extendsType(ThrowEvent.class)
        .instanceProvider(new ModelTypeInstanceProvider<ImplicitThrowEvent>() {
          public ImplicitThrowEvent newInstance(ModelTypeInstanceContext instanceContext) {
            return new ImplicitThrowEventImpl(instanceContext);
          }
        });


    typeBuilder.build();
  }

  public ImplicitThrowEventImpl(ModelTypeInstanceContext context) {
    super(context);
  }
}
