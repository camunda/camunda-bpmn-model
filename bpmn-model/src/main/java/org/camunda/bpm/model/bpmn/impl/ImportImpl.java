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

import org.camunda.bpm.model.bpmn.Import;
import org.camunda.bpm.xml.model.ModelBuilder;
import org.camunda.bpm.xml.model.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.xml.model.type.Attribute;
import org.camunda.bpm.xml.model.type.ModelElementType;
import org.camunda.bpm.xml.model.type.ModelElementTypeBuilder;
import org.camunda.bpm.xml.model.type.ModelElementTypeBuilder.ModelTypeInstanceProvider;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 *
 * @author Daniel Meyer
 *
 */
public class ImportImpl extends AbstractBpmnModelElement implements Import {

  protected static ModelElementType MODEL_TYPE;

  static Attribute<String> namespaceAttr;
  static Attribute<String> locationAttr;
  static Attribute<String> importTypeAttr;

  public static void registerType(ModelBuilder bpmnModelBuilder) {

    ModelElementTypeBuilder builder = bpmnModelBuilder.defineType(Import.class, BPMN_ELEMENT_IMPORT)
      .namespaceUri(BPMN20_NS)
      .instanceProvider(new ModelTypeInstanceProvider<Import>() {
        public Import newInstance(ModelTypeInstanceContext instanceContext) {
          return new ImportImpl(instanceContext);
        }
      });

    namespaceAttr = builder.stringAttribute(BPMN_ATTRIBUTE_NAMESPACE)
      .build();

    locationAttr = builder.stringAttribute(BPMN_ATTRIBUTE_LOCATION)
      .build();

    importTypeAttr = builder.stringAttribute(BPMN_ATTRIBUTE_IMPORT_TYPE)
      .build();

    MODEL_TYPE = builder.build();
  }

  public ImportImpl(ModelTypeInstanceContext context) {
    super(context);
  }

  public void setNamespace(String namespace) {
    namespaceAttr.setValue(this, namespace);
  }

  public String getNamespace() {
    return namespaceAttr.getValue(this);
  }

  public void setLocation(String location) {
    locationAttr.setValue(this, location);
  }

  public String getLocation() {
    return locationAttr.getValue(this);
  }

  public void setImportType(String importType) {
    importTypeAttr.setValue(this, importType);
  }

  public String getImportType() {
    return importTypeAttr.getValue(this);
  }

}
