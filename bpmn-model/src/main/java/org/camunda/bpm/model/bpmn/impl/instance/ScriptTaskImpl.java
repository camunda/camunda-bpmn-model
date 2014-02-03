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

import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.builder.AbstractBaseElementBuilder;
import org.camunda.bpm.model.bpmn.builder.ScriptTaskBuilder;
import org.camunda.bpm.model.bpmn.instance.Script;
import org.camunda.bpm.model.bpmn.instance.ScriptTask;
import org.camunda.bpm.model.bpmn.instance.Task;
import org.camunda.bpm.model.xml.ModelBuilder;
import org.camunda.bpm.model.xml.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.xml.type.ModelElementTypeBuilder;
import org.camunda.bpm.model.xml.type.attribute.Attribute;
import org.camunda.bpm.model.xml.type.child.ChildElement;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;
import static org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

/**
 * The BPMN scriptTask element
 *
 * @author Sebastian Menski
 */
public class ScriptTaskImpl extends TaskImpl implements ScriptTask {

  private static Attribute<String> scriptFormatAttribute;
  private static ChildElement<Script> scriptChild;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(ScriptTask.class, BPMN_ELEMENT_SCRIPT_TASK)
      .namespaceUri(BPMN20_NS)
      .extendsType(Task.class)
      .instanceProvider(new ModelTypeInstanceProvider<ScriptTask>() {
        public ScriptTask newInstance(ModelTypeInstanceContext instanceContext) {
          return new ScriptTaskImpl(instanceContext);
        }
      });

    scriptFormatAttribute = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_SCRIPT_FORMAT)
      .build();

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    scriptChild = sequenceBuilder.element(Script.class)
      .build();

    typeBuilder.build();
  }

  public ScriptTaskImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends AbstractBaseElementBuilder> T builder() {
    return (T) new ScriptTaskBuilder((BpmnModelInstance) modelInstance, this);
  }

  public String getScriptFormat() {
    return scriptFormatAttribute.getValue(this);
  }

  public void setScriptFormat(String scriptFormat) {
    scriptFormatAttribute.setValue(this, scriptFormat);
  }

  public Script getScript() {
    return scriptChild.getChild(this);
  }

  public void setScript(Script script) {
    scriptChild.setChild(this, script);
  }
}
