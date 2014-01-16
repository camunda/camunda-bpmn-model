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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.ModelInstance;
import org.camunda.bpm.model.core.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.impl.util.DomUtil;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Daniel Meyer
 * @author Sebastian Menski
 *
 */
public class ModelInstanceImpl implements ModelInstance {

  protected Document document;
  protected ModelImpl model;

  public ModelInstanceImpl(ModelImpl model, Document document) {
    this.model = model;
    this.document = document;
  }

  /**
   * @return the wrapped DOM {@link Document}
   */
  public Document getDocument() {
    return document;
  }

  /**
   * @return the {@link ModelElementInstanceImpl ModelElement} corresponding to the document
   * element of this model or null if no document element exists.
   */
  public ModelElementInstance getDocumentElement() {
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
  public void setDocumentElement(ModelElementInstance modelElement) {
    ModelUtil.ensureInstanceOf(modelElement, ModelElementInstanceImpl.class);
    Element domElement = ((ModelElementInstanceImpl)modelElement).getDomElement();
    DomUtil.setDocumentElement(document, domElement);
  }

  public <T extends ModelElementInstance> T newInstance(Class<T> type) {
    ModelElementType modelElementType = model.getType(type);
    if(modelElementType != null) {
      return newInstance(modelElementType);
    } else {
      throw new ModelException("Cannote create intance of ModelType "+type+": no such type registered.");
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends ModelElementInstance> T newInstance(ModelElementType type) {
    return (T) type.newInstance(this);
  }

  public Model getModel() {
    return model;
  }

  public ModelElementInstance getModelElementById(String id) {
    if (id == null) {
      return null;
    }

    Element element = DomUtil.findElementById(document, id);
    if(element != null) {
      return ModelUtil.getModelElement(element, this);
    } else {
      return null;
    }
  }

  public Collection<ModelElementInstance> getModelElementsByType(ModelElementType type) {
    HashSet<ModelElementType> extendingTypes = new HashSet<ModelElementType>();
    extendingTypes.add(type);
    ((ModelElementTypeImpl)type).resolveExtendingTypes(extendingTypes);

    List<ModelElementInstance> instances = new ArrayList<ModelElementInstance>();
    for (ModelElementType modelElementType : extendingTypes) {
      if(!modelElementType.isAbstract()) {
        instances.addAll(modelElementType.getInstances(this));
      }
    }
    return instances;
  }
}
