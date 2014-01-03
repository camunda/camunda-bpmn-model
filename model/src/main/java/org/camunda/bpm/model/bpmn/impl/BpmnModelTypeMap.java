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

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.model.core.impl.AbstractModelElement;

/**
 * <p>The BPMN type package</p>
 *
 * @author Daniel Meyer
 *
 */
public class BpmnModelTypeMap {

  private static BpmnModelTypeMap INSTANCE = new BpmnModelTypeMap();

  public Map<String, Class<? extends AbstractModelElement>> typeMap = new HashMap<String, Class<? extends AbstractModelElement>>();

  /**
   * Return
   * @param localName the the name of the element in the local namespace.
   * @return a custom type or 'null' if custom type mapping should be performed.
   */
  public Class<? extends AbstractModelElement> getType(String localName) {
    return typeMap.get(localName);
  }

  protected BpmnModelTypeMap() {
    typeMap.put(DefinitionsImpl.ELEMENT_NAME, DefinitionsImpl.class);
    typeMap.put(ImportImpl.ELEMENT_NAME, ImportImpl.class);
    typeMap.put(ProcessImpl.ELEMENT_NAME, ProcessImpl.class);
  }

  public String getNamespaceUri() {
    return BPMN20_NS;
  }

  /**
   * @return the Singleton instance of the {@link BpmnModelTypeMap}.
   */
  public static BpmnModelTypeMap getINSTANCE() {
    return INSTANCE;
  }

}
