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
package org.camunda.bpm.model.core.impl;

import org.w3c.dom.Element;

/**
 * @author Daniel Meyer
 * @author Sebastian Menski
 *
 */
public final class ModelElementCreateContext {

  protected final AbstractModel model;
  private final Element domElement;

  public ModelElementCreateContext(Element domElement, AbstractModel model) {
    this.domElement = domElement;
    this.model = model;
  }

  /**
   * @return the element
   */
  public Element getDomElement() {
    return domElement;
  }

  /**
   * @return the model
   */
  public AbstractModel getModel() {
    return model;
  }
}
