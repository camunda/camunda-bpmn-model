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

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelInstance;
import org.camunda.bpm.model.core.ModelReferenceException;
import org.camunda.bpm.model.core.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.Reference;

/**
 * @author Sebastian Menski
 *
 */
public abstract class ReferenceImpl<T extends ModelElementInstance> implements Reference<T> {

  protected final String referencedAttributeName;
  protected final Class<T> referencedElementType;
  protected final Model model;

  public ReferenceImpl(String referencedAttributeName, Class<T> referencedElementType, Model model) {
    this.referencedAttributeName = referencedAttributeName;
    this.referencedElementType = referencedElementType;
    this.model = model;
  }

  public T getReferencedElement(ModelElementInstance modelElement) {
    String referenceIdentifier = modelElement.getAttributeValue(referencedAttributeName);
    return resolveReference((ModelElementInstanceImpl) modelElement, referenceIdentifier);
  }


  public void setReferencedElement(ModelElementInstance modelElement, T referencedElement) {
    String referenceIdentifier = getReferenceIdentifier(referencedElement);
    ModelInstance modelInstance = modelElement.getModelInstance();

    ModelElementInstance existingElement = modelInstance.findModelElementById(referenceIdentifier);
    if(existingElement == null || !existingElement.equals(referencedElement)) {
      throw new ModelReferenceException("Cannot create reference to model element "+referencedElement+": element is not part of model. "
          + "Please connect element to the model first.");

    } else {
      modelElement.setAttributeValue(referencedAttributeName, referenceIdentifier, false);

    }

  }

  /**
   * Gets the reference identifier
   *
   * @param referencedElement
   */
  protected abstract String getReferenceIdentifier(T referencedElement);

  public String getReferenceAttributeName() {
    return referencedAttributeName;
  }

  /**
   * resolves the reference.
   *
   * @param modelElement
   * @param referenceIdentifier
   * @return
   */
  protected abstract T resolveReference(ModelElementInstanceImpl modelElement, String referenceIdentifier);

  public ModelElementType getReferencedElementType() {
    return model.getType(referencedElementType);
  }
}
