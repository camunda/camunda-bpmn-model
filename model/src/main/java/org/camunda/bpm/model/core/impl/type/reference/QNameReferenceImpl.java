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
package org.camunda.bpm.model.core.impl.type.reference;

import org.camunda.bpm.model.core.ModelReferenceException;
import org.camunda.bpm.model.core.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.model.core.impl.type.attribute.AttributeImpl;
import org.camunda.bpm.model.core.impl.util.QName;
import org.camunda.bpm.model.core.instance.ModelElementInstance;

/**
 * @author Sebastian Menski
 *
 */
public class QNameReferenceImpl<T extends ModelElementInstance> extends ReferenceImpl<T> {

  protected final String idAttributeName = "id";

  /**
   * @param referencingAttribute
   * @param referencedElementType
   */
  public QNameReferenceImpl(AttributeImpl<String> referencingAttribute) {
    super(referencingAttribute);
  }

  protected String getReferenceIdentifier(ModelElementInstance referencedElement) {
    String id = referencedElement.getAttributeValue(getIdAttributeName());
    if(id != null) {
      return id;
    } else {
      throw new ModelReferenceException("Cannot create reference to element "+referencedElement+ ": element has no attribute named "+getIdAttributeName());
    }
  }

  @SuppressWarnings("unchecked")
  protected T resolveReference(ModelElementInstanceImpl modelElement, String referenceIdentifier) {
    QName qName = QName.parseQName(referenceIdentifier, modelElement);
    String id = qName.getLocalName();

    ModelElementInstance referencedElement = modelElement.getModelInstance().getModelElementById(id);
    if(referencedElement != null) {
      try {
        return (T) referencedElement;

      } catch(ClassCastException e) {
        throw new ModelReferenceException("Element " + modelElement + " references element " + referencedElement + " of wrong type. "
            + "Expecting " + referencedAttribute.getOwningElementType() + " got " + referencedElement.getElementType());
      }

    } else {
      return null;
    }
  }

  public String getIdAttributeName() {
    return idAttributeName;
  }

}
