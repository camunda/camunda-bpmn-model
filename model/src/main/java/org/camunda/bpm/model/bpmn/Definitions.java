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
package org.camunda.bpm.model.bpmn;

import java.util.Collection;

/**
 * The BPMN Definitions Element.
 *
 * @author Daniel Meyer
 *
 */
public interface Definitions extends BpmnModelElement {

  String getExporter();

  void setExporter(String exporter);

  String getExporterVersion();

  void setExporterVersion(String exporterVersion);

  String getExpressionLanguage();

  void setExpressionLanguage(String expressionLanguage);

  String getId();

  void setId(String id);

  String getName();

  void setName(String name);

  String getTypeLanguage();

  void setTypeLanguage(String typeLanguage);

  String getTargetNamespace();

  void setTargetNamespace(String namespace);

  Collection<Import> getImports();

  Collection<RootElement> getRootElements();

}