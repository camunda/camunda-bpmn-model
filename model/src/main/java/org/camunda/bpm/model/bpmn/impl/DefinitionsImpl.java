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

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_EXPORTER;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_EXPORTER_VERSION;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_EXPRESSION_LANGUAGE;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_ID;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_NAME;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_TARGET_NAMESPACE;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ATTRIBUTE_TYPE_LANGUAGE;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_DEFINITIONS;
import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.BPMN_ELEMENT_IMPORT;

import java.util.Collection;

import org.camunda.bpm.model.bpmn.Definitions;
import org.camunda.bpm.model.bpmn.Import;
import org.camunda.bpm.model.bpmn.RootElement;
import org.camunda.bpm.model.core.impl.ModelElementCreateContext;
import org.camunda.bpm.model.core.impl.attribute.Attribute;
import org.camunda.bpm.model.core.impl.attribute.StringAttribute;
import org.camunda.bpm.model.core.impl.child.NamedChildElementCollection;
import org.camunda.bpm.model.core.impl.child.TypeChildElementCollection;

/**
 * The BPMN Definitions Element
 *
 * @author Daniel Meyer
 *
 */
public class DefinitionsImpl extends AbstractBpmnModelElement implements Definitions {

  public final static String ELEMENT_NAME = BPMN_ELEMENT_DEFINITIONS;

  protected Attribute<String> idAttr = new StringAttribute(BPMN_ATTRIBUTE_ID, this);
  protected Attribute<String> nameAttr = new StringAttribute(BPMN_ATTRIBUTE_NAME, this);
  protected Attribute<String> expressionLanguageAttr = new StringAttribute(BPMN_ATTRIBUTE_EXPRESSION_LANGUAGE, this);
  protected Attribute<String> typeLanguageAttr = new StringAttribute(BPMN_ATTRIBUTE_TYPE_LANGUAGE, this);
  protected Attribute<String> exporterAttr = new StringAttribute(BPMN_ATTRIBUTE_EXPORTER, this);
  protected Attribute<String> exporterVersionAttr = new StringAttribute(BPMN_ATTRIBUTE_EXPORTER_VERSION, this);
  protected Attribute<String> targetNamespaceAttr = new StringAttribute(BPMN_ATTRIBUTE_TARGET_NAMESPACE, this);

  protected Collection<Import> importElementsColl = new NamedChildElementCollection<Import>(BPMN_ELEMENT_IMPORT, this);
  protected Collection<RootElement> rootElementsColl = new TypeChildElementCollection<RootElement>(RootElement.class, this);



  public DefinitionsImpl(ModelElementCreateContext context) {
    super(context);
  }

  // attributes /////////////////////////


  public String getId() {
    return idAttr.getValue();
  }

  public void setId(String id) {
    idAttr.setValue(id);
  }

  public String getName() {
    return nameAttr.getValue();
  }

  public void setName(String name) {
    nameAttr.setValue(name);
  }

  public String getExpressionLanguage() {
    return expressionLanguageAttr.getValue();
  }

  public void setExpressionLanguage(String expressionLanguage) {
    expressionLanguageAttr.setValue(expressionLanguage);
  }

  public String getTypeLanguage() {
    return typeLanguageAttr.getValue();
  }

  public void setTypeLanguage(String typeLanguage) {
    typeLanguageAttr.setValue(typeLanguage);
  }

  public String getExporter() {
    return exporterAttr.getValue();
  }

  public void setExporter(String exporter) {
    exporterAttr.setValue(exporter);
  }

  public String getExporterVersion() {
    return exporterVersionAttr.getValue();
  }

  public void setExporterVersion(String exporterVersion) {
    exporterVersionAttr.setValue(exporterVersion);
  }

  public String getTargetNamespace() {
    return targetNamespaceAttr.getValue();
  }

  public void setTargetNamespace(String namespace) {
    targetNamespaceAttr.setValue(namespace);
  }

  // collections /////////////////////////

  public Collection<Import> getImports() {
    return importElementsColl;
  }

  public Collection<RootElement> getRootElements() {
    return rootElementsColl;
  }

}
