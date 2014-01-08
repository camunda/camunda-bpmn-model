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
package org.camunda.bpm.model.core.impl.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelInstance;
import org.camunda.bpm.model.core.impl.ModelImpl;
import org.camunda.bpm.model.core.impl.ModelInstanceImpl;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder.ModelTypeIntanceProvider;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Daniel Meyer
 *
 */
public class ModelElementTypeImpl implements ModelElementType {

  protected ModelImpl model;

  protected String typeName;

  protected String typeNamespace;

  protected ModelElementTypeImpl partentType;

  private List<Attribute<?>> attributes = new ArrayList<Attribute<?>>();

  private final List<Class<?>> childElementTypes = new ArrayList<Class<?>>();

  protected ModelTypeIntanceProvider<?> instanceProvider;

  protected boolean isAbstract;


  /**
   * @param name
   */
  public ModelElementTypeImpl(ModelImpl model, String name) {
    this.model = model;
    this.typeName = name;
  }

  public ModelElementInstance newInstance(ModelInstance modelInstance) {
    ModelInstanceImpl modelInstanceImpl = (ModelInstanceImpl) modelInstance;
    Document document = modelInstanceImpl.getDocument();
    Element domElement = document.createElementNS(typeNamespace, typeName);
    return newInstance(modelInstanceImpl, domElement);
  }

  public ModelElementInstance newInstance(ModelInstanceImpl modelInstance, Element domElement) {
    return createModelElementIntance(new ModelTypeInstanceContext(domElement, modelInstance, this));
  }

  public void registerAttribute(Attribute<?> attribute) {
    attributes.add(attribute);
  }

  public void registerChildElementType(Class<?> childElementType) {
    childElementTypes.add(childElementType);
  }

  protected ModelElementInstance createModelElementIntance(ModelTypeInstanceContext instanceContext) {
    return instanceProvider.newInstance(instanceContext);
  }

  public final List<Attribute<?>> getAttributes() {
    return attributes;
  }

  final void setAttributes(List<Attribute<?>> attributes) {
    this.attributes = attributes;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeNamespace(String typeNamespace) {
    this.typeNamespace = typeNamespace;
  }

  public String getTypeNamespace() {
    return typeNamespace;
  }

  public void setPartentType(ModelElementTypeImpl partentType) {
    this.partentType = partentType;
  }

  public void setInstanceProvider(ModelTypeIntanceProvider<?> instanceProvider) {
    this.instanceProvider = instanceProvider;
  }

  public boolean isAbstract() {
    return isAbstract;
  }

  public void setAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
  }

  public Collection<ModelElementType> getExtendingTypes() {
    return null;
  }

  public ModelElementType getParentType() {
    return partentType;
  }

  public Model getModel() {
    return model;
  }

  public List<Class<?>> getChildElementTypes() {
    List<Class<?>> allChildElementTypes = new ArrayList<Class<?>>();
    if (partentType != null) {
      allChildElementTypes.addAll(partentType.getChildElementTypes());
    }
    allChildElementTypes.addAll(childElementTypes);
    return allChildElementTypes;
  }

}
