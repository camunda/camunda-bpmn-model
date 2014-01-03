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

import org.camunda.bpm.model.core.impl.util.DomUtil;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Daniel Meyer
 * @author Sebastian Menski
 *
 */
public abstract class AbstractModel {

  protected Document document;

  public AbstractModel(Document document) {
    this.document = document;
  }

  public abstract Class<? extends AbstractModelElement> getModelElementTypeForDomElement(Element element);

  /**
   * @return the wrapped DOM {@link Document}
   */
  public Document getDocument() {
    return document;
  }

  /**
   * @return the {@link AbstractModelElement ModelElement} corresponding to the document
   * element of this model or null if no document element exists.
   */
  public AbstractModelElement getDocumentElement() {
    Element documentElement = DomUtil.getDocumentElement(document);
    if(documentElement != null) {
      return ModelUtil.getModelElement(documentElement, this);

    } else {
      return null;

    }
  }

  /**
   *1 Updates the document element.
   *
   * @param modelElement the new document element to set
   */
  public void setDocumentElement(AbstractModelElement modelElement) {
    Element domElement = modelElement.getDomElement();
    DomUtil.setDocumentElement(document, domElement);
  }

}
