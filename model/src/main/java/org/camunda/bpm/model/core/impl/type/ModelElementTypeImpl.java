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
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.ModelInstance;
import org.camunda.bpm.model.core.impl.ModelImpl;
import org.camunda.bpm.model.core.impl.ModelInstanceImpl;
import org.camunda.bpm.model.core.impl.instance.ModelTypeInstanceContext;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.Attribute;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder.ModelTypeIntanceProvider;
import org.camunda.bpm.model.core.type.Reference;
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

  protected ModelElementTypeImpl baseType;

  protected List<ModelElementType> extendingTypes = new ArrayList<ModelElementType>();

  protected List<Attribute<?>> attributes = new ArrayList<Attribute<?>>();

  protected List<Class<?>> childElementTypes = new ArrayList<Class<?>>();

  protected List<Reference<?>> references = new ArrayList<Reference<?>>();

  protected List<Class<? extends ModelElementInstance>> registeredReferencingTypes = new ArrayList<Class<? extends ModelElementInstance>>();

  protected Collection<ModelElementType> referencingTypes = null;

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

  public void registerReference(Reference<?> reference) {
    references.add(reference);
  }

  public void registerExtendingType(ModelElementType modelType) {
    extendingTypes.add(modelType);
  }

  public void registerReferencingType(Class<? extends ModelElementInstance> modelInstanceType) {
    registeredReferencingTypes.add(modelInstanceType);
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

  public void setBaseType(ModelElementTypeImpl baseType) {
    if (this.baseType == null) {
      this.baseType = baseType;
    }
    else {
      throw new ModelException("Type can not have multiple base types. " + this.getClass() + " already extends type " + this.baseType.getClass()
          + " and can not also extend type " + baseType.getClass());
    }
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
    return Collections.unmodifiableCollection(extendingTypes);
  }

  /**
   * Resolve all types recursively which are extending this type
   *
   * @param allExtendingTypes set of calculated extending types
   */
  public void resolveExtendingTypes(Set<ModelElementType> allExtendingTypes) {
    for(ModelElementType modelElementType : extendingTypes) {
      ModelElementTypeImpl modelElementTypeImpl = (ModelElementTypeImpl) modelElementType;
      if (!allExtendingTypes.contains(modelElementTypeImpl)) {
        allExtendingTypes.add(modelElementType);
        modelElementTypeImpl.resolveExtendingTypes(allExtendingTypes);
      }
    }
  }

  /**
   * Resolve all types which are base types of this type
   *
   * @param baseTypes list of calculated base types
   */
  public void resolveBaseTypes(List<ModelElementType> baseTypes) {
    if (baseType != null) {
      baseTypes.add(baseType);
      baseType.resolveBaseTypes(baseTypes);
    }
  }

  public Collection<ModelElementType> getReferencingTypes() {
    if (referencingTypes == null) {
      Collection<ModelElementType> allReferencingTypes = ModelUtil.calculateAllExtendingTypes(model, registeredReferencingTypes);
      referencingTypes = Collections.unmodifiableCollection(allReferencingTypes);
    }
    return referencingTypes;
  }

  public ModelElementType getBaseType() {
    return baseType;
  }

  public Model getModel() {
    return model;
  }

  public List<Class<?>> getChildElementTypes() {
    List<Class<?>> allChildElementTypes = new ArrayList<Class<?>>();
    if (baseType != null) {
      allChildElementTypes.addAll(baseType.getChildElementTypes());
    }
    allChildElementTypes.addAll(childElementTypes);
    return allChildElementTypes;
  }

}
