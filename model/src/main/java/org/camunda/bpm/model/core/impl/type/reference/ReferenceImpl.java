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

import java.util.Collection;
import java.util.Collections;

import org.camunda.bpm.model.core.ModelInstance;
import org.camunda.bpm.model.core.ModelReferenceException;
import org.camunda.bpm.model.core.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.impl.type.attribute.AttributeImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.Reference;

/**
 * @author Sebastian Menski
 *
 */
public abstract class ReferenceImpl<T extends ModelElementInstance> implements Reference<T> {

  protected final AttributeImpl<String> referencingAttribute;
  protected AttributeImpl<String> referencedAttribute;

  /** the actual type, may be different (a subtype of) {@link AttributeImpl#getOwningElementType()} */
  protected ModelElementTypeImpl referencedElementType;

  public ReferenceImpl(AttributeImpl<String> referencingAttribute) {
    this.referencingAttribute = referencingAttribute;
  }

  public T getReferencedElement(ModelElementInstance modelElement) {
    String referenceIdentifier = referencingAttribute.getValue(modelElement);
    if (referenceIdentifier == null) {
      return null;
    }
    else {
      return resolveReference((ModelElementInstanceImpl) modelElement, referenceIdentifier);
    }
  }


  public void setReferencedElement(ModelElementInstance modelElement, T referencedElement) {
    ModelInstance modelInstance = modelElement.getModelInstance();
    ModelElementInstance existingElement = modelInstance.findModelElementById(referencedAttribute.getValue(referencedElement));

    if(existingElement == null || !existingElement.equals(referencedElement)) {
      throw new ModelReferenceException("Cannot create reference to model element "+referencedElement+": element is not part of model. "
          + "Please connect element to the model first.");

    } else {
      referencingAttribute.setValue(modelElement, referencedAttribute.getValue(existingElement));

    }

  }

  public void setReferencedAttribute(AttributeImpl<String> referencedAttribute) {
    this.referencedAttribute = referencedAttribute;
  }

  public Attribute<String> getReferencingAttribute() {
    return referencingAttribute;
  }

  public AttributeImpl<String> getReferencedAttribute() {
    return referencedAttribute;
  }

  /**
   * @param referencedElementType the referencedElementType to set
   */
  public void setReferencedElementType(ModelElementTypeImpl referencedElementType) {
    this.referencedElementType = referencedElementType;
  }

  /**
   * resolves the reference.
   *
   * @param modelElement
   * @param referenceIdentifier
   * @return
   */
  protected abstract T resolveReference(ModelElementInstanceImpl modelElement, String referenceIdentifier);

  /**
   * @param modelElement
   * @param oldValue
   * @param newValue
   */
  public void referencedElementUpdated(ModelElementInstance modelElement, String oldValue, String newValue) {
    for (ModelElementInstance referencingElement : findReferencingElements(modelElement)) {
      String referencingAttributeValue = referencingAttribute.getValue(referencingElement);
      if(oldValue != null && oldValue.equals(referencingAttributeValue)) {
        referencingAttribute.setValue(referencingElement, newValue);
      }
    }
  }

  private Collection<ModelElementInstance> findReferencingElements(ModelElementInstance modelElement) {
    if(referencedElementType.isBaseTypeOf(modelElement.getElementType())) {
      ModelElementType owningElementType = referencingAttribute.getOwningElementType();
      return modelElement.getModelInstance().findModelElementsByType(owningElementType);
    }
    else {
      return Collections.emptyList();
    }
  }

  public void referencedElementRemoved(ModelElementInstance modelElement) {
    for (ModelElementInstance referencingElement : findReferencingElements(modelElement)) {
      referencingAttribute.removeAttribute(referencingElement);
    }
  }

}
