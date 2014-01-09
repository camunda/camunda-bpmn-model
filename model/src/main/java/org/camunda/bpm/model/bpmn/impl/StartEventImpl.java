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

import org.camunda.bpm.model.bpmn.CatchEvent;
import org.camunda.bpm.model.bpmn.StartEvent;
import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder.ModelTypeIntanceProvider;

/**
 * @author Sebastian Menski
 *
 */
public class StartEventImpl extends CatchEventImpl implements StartEvent {

  public static ModelElementType MODEL_TYPE;

  public static void registerType(Model model) {

    ModelElementTypeBuilder builder = model.defineType(StartEvent.class, BPMN_ELEMENT_START_EVENT)
      .namespaceUri(BPMN20_NS)
      .extendsType(model.getType(CatchEvent.class))
      .instanceProvider(new ModelTypeIntanceProvider<StartEvent>() {
        public StartEvent newInstance(ModelTypeInstanceContext instanceContext) {
          return new StartEventImpl(instanceContext);
        }
      });

    MODEL_TYPE = builder.build();
  }

  public StartEventImpl(ModelTypeInstanceContext context) {
    super(context);
  }

}
