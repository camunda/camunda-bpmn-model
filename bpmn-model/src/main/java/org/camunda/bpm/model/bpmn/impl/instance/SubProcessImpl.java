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
import org.camunda.bpm.model.xml.type.child.ChildElementCollection;
import org.camunda.bpm.model.xml.type.child.SequenceBuilder;

import java.util.Collection;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;
import static org.camunda.bpm.model.xml.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

/**
 * The BPMN subProcess element
 *
 * @author Sebastian Menski
 */
public class SubProcessImpl extends ActivityImpl implements SubProcess {

  private static Attribute<Boolean> triggeredByEventAttribute;
  private static ChildElementCollection<LaneSet> laneSetCollection;
  private static ChildElementCollection<FlowElement> flowElementCollection;
  private static ChildElementCollection<Artifact> artifactCollection;

  public static void registerType(ModelBuilder modelBuilder) {
    ModelElementTypeBuilder typeBuilder = modelBuilder.defineType(SubProcess.class, BPMN_ELEMENT_SUB_PROCESS)
      .namespaceUri(BPMN20_NS)
      .extendsType(Activity.class)
      .instanceProvider(new ModelTypeInstanceProvider<SubProcess>() {
        public SubProcess newInstance(ModelTypeInstanceContext instanceContext) {
          return new SubProcessImpl(instanceContext);
        }
      });

    triggeredByEventAttribute = typeBuilder.booleanAttribute(BPMN_ATTRIBUTE_TRIGGERED_BY_EVENT)
      .defaultValue(false)
      .build();

    SequenceBuilder sequenceBuilder = typeBuilder.sequence();

    laneSetCollection = sequenceBuilder.elementCollection(LaneSet.class)
      .build();

    flowElementCollection = sequenceBuilder.elementCollection(FlowElement.class)
      .build();

    artifactCollection = sequenceBuilder.elementCollection(Artifact.class)
      .build();

    typeBuilder.build();
  }

  public SubProcessImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public boolean triggeredByEvent() {
    return triggeredByEventAttribute.getValue(this);
  }

  public void setTriggeredByEvent(boolean triggeredByEvent) {
    triggeredByEventAttribute.setValue(this, triggeredByEvent);
  }

  public Collection<LaneSet> getLaneSets() {
    return laneSetCollection.get(this);
  }

  public Collection<FlowElement> getFlowElements() {
    return flowElementCollection.get(this);
  }

  public Collection<Artifact> getArtifacts() {
    return artifactCollection.get(this);
  }
}
