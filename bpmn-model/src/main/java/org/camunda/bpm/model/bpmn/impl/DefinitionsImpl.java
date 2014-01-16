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

import org.camunda.bpm.model.bpmn.Definitions;
import org.camunda.bpm.model.bpmn.Import;
import org.camunda.bpm.model.bpmn.RootElement;
import org.camunda.bpm.xml.model.ModelBuilder;
import org.camunda.bpm.xml.model.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.xml.model.type.*;

import java.util.Collection;

import static org.camunda.bpm.model.bpmn.impl.BpmnModelConstants.*;

/**
 * The BPMN Definitions Element
 *
 * @author Daniel Meyer
 *
 */
public class DefinitionsImpl extends AbstractBpmnModelElement implements Definitions {

  public static ModelElementType MODEL_TYPE;

  static Attribute<String> idAttr;
  static Attribute<String> nameAttr;
  static Attribute<String> expressionLanguageAttr;
  static Attribute<String> typeLanguageAttr;
  static Attribute<String> exporterAttr;
  static Attribute<String> exporterVersionAttr;
  static Attribute<String> targetNamespaceAttr;

  static ChildElementCollection<Import> importElementsColl;
  static ChildElementCollection<RootElement> rootElementsColl;

  public static void registerType(ModelBuilder bpmnModelBuilder) {

    ModelElementTypeBuilder typeBuilder = bpmnModelBuilder.defineType(Definitions.class, BPMN_ELEMENT_DEFINITIONS)
      .namespaceUri(BPMN20_NS)
      .instanceProvider(new ModelElementTypeBuilder.ModelTypeInstanceProvider<Definitions>() {
        public Definitions newInstance(ModelTypeInstanceContext instanceContext) {
          return new DefinitionsImpl(instanceContext);
        }
      });

    idAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_ID)
        .build();

    nameAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_NAME)
        .build();

    expressionLanguageAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_EXPRESSION_LANGUAGE)
        .defaultValue(XPATH_NS)
        .build();

    typeLanguageAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_TYPE_LANGUAGE)
        .defaultValue(XML_SCHEMA_NS)
        .build();

    exporterAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_EXPORTER)
        .build();

    exporterVersionAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_EXPORTER_VERSION)
        .build();

    targetNamespaceAttr = typeBuilder.stringAttribute(BPMN_ATTRIBUTE_TARGET_NAMESPACE)
        .required()
        .build();

    SequenceBuilder sequence = typeBuilder.sequence();

    importElementsColl = sequence.elementCollection(Import.class, BPMN_ELEMENT_IMPORT)
      .build();

    rootElementsColl = sequence.elementCollection(RootElement.class)
      .build();

    MODEL_TYPE = typeBuilder.build();
  }

  public DefinitionsImpl(ModelTypeInstanceContext instanceContext) {
    super(instanceContext);
  }

  public String getId() {
    return idAttr.getValue(this);
  }

  public void setId(String id) {
    idAttr.setValue(this, id);
  }

  public String getName() {
    return nameAttr.getValue(this);
  }

  public void setName(String name) {
    nameAttr.setValue(this, name);
  }

  public String getExpressionLanguage() {
    return expressionLanguageAttr.getValue(this);
  }

  public void setExpressionLanguage(String expressionLanguage) {
    expressionLanguageAttr.setValue(this, expressionLanguage);
  }

  public String getTypeLanguage() {
    return typeLanguageAttr.getValue(this);
  }

  public void setTypeLanguage(String typeLanguage) {
    typeLanguageAttr.setValue(this, typeLanguage);
  }

  public String getExporter() {
    return exporterAttr.getValue(this);
  }

  public void setExporter(String exporter) {
    exporterAttr.setValue(this, exporter);
  }

  public String getExporterVersion() {
    return exporterVersionAttr.getValue(this);
  }

  public void setExporterVersion(String exporterVersion) {
    exporterVersionAttr.setValue(this, exporterVersion);
  }

  public String getTargetNamespace() {
    return targetNamespaceAttr.getValue(this);
  }

  public void setTargetNamespace(String namespace) {
    targetNamespaceAttr.setValue(this, namespace);
  }

  public Collection<Import> getImports() {
    return importElementsColl.get(this);
  }

  public Collection<RootElement> getRootElements() {
    return rootElementsColl.get(this);
  }

}
