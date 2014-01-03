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

import org.camunda.bpm.model.bpmn.BpmnElementFactory;
import org.camunda.bpm.model.bpmn.BpmnModel;
import org.camunda.bpm.model.bpmn.BpmnModelElement;
import org.camunda.bpm.model.core.impl.AbstractModelElement;
import org.camunda.bpm.model.core.impl.ModelElementCreateContext;

/**
 * <p>Shared Base Class for all BPMN Model Elements. Provides
 * implementation of the {@link BpmnModelElement} interface.</p>
 *
 * @author Daniel Meyer
 *
 */
public abstract class AbstractBpmnModelElement extends AbstractModelElement implements BpmnModelElement {

  public AbstractBpmnModelElement(ModelElementCreateContext context) {
    super(context);
  }

  public BpmnModel getBpmnModel() {
    return (BpmnModelImpl) model;
  }

  public BpmnElementFactory getBpmnElementFactory() {
    return getBpmnModel().getBpmnElementFactory();
  }

}
