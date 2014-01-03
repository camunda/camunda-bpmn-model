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

import org.camunda.bpm.model.bpmn.Import;
import org.camunda.bpm.model.core.impl.ModelElementCreateContext;
import org.camunda.bpm.model.core.impl.attribute.Attribute;
import org.camunda.bpm.model.core.impl.attribute.StringAttribute;

/**
 *
 * @author Daniel Meyer
 *
 */
public class ImportImpl extends AbstractBpmnModelElement implements Import {

  public final static String ELEMENT_NAME = BPMN_ELEMENT_IMPORT;

  protected Attribute<String> namespaceAttr = new StringAttribute(BPMN_ATTRIBUTE_NAMESPACE, this);
  protected Attribute<String> locationAttr = new StringAttribute(BPMN_ATTRIBUTE_LOCATION, this);
  protected Attribute<String> importTypeAttr = new StringAttribute(BPMN_ATTRIBUTE_IMPORT_TYPE, this);

  public ImportImpl(ModelElementCreateContext context) {
    super(context);
  }

  public void setNamespace(String namespace) {
    namespaceAttr.setValue(namespace);
  }

  public String getNamespace() {
    return namespaceAttr.getValue();
  }

  public void setLocation(String location) {
    locationAttr.setValue(location);
  }

  public String getLocation() {
    return locationAttr.getValue();
  }

  public void setImportType(String importType) {
    importTypeAttr.setValue(importType);
  }

  public String getImportType() {
    return importTypeAttr.getValue();
  }

}
