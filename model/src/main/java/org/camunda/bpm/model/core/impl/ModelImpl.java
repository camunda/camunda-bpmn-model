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
import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeBuilderImpl;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.camunda.bpm.model.core.impl.util.QName;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.camunda.bpm.model.core.type.ModelElementType;
import org.camunda.bpm.model.core.type.ModelElementTypeBuilder;

/**
 * @author Daniel Meyer
 *
 */
public class ModelImpl implements Model {

  protected Map<QName, ModelElementType> typesByName = new HashMap<QName, ModelElementType>();
  protected Map<Class<? extends ModelElementInstance>, ModelElementType> typesByClass = new HashMap<Class<? extends ModelElementInstance>, ModelElementType>();

  public Collection<ModelElementType> getTypes() {
    return new ArrayList<ModelElementType>(typesByName.values());
  }

  public ModelElementType getType(Class<? extends ModelElementInstance> type) {
    return typesByClass.get(type);
  }

  public <T extends ModelElementInstance> ModelElementType getTypeForName(String typeName) {
    return getTypeForName(typeName, null);
  }

  public ModelElementType getTypeForName(String typeName, String namespaceUri) {
    return typesByName.get(ModelUtil.getQName(typeName, namespaceUri));
  }

  public ModelElementTypeBuilder defineType(Class<? extends ModelElementInstance> modelInstanceType, String typeName) {
    return new ModelElementTypeBuilderImpl(modelInstanceType, typeName, this);
  }

  public void registerType(ModelElementType modelElementType, Class<? extends ModelElementInstance> instanceType) {
    QName qName = ModelUtil.getQName(modelElementType.getTypeName(), modelElementType.getTypeNamespace());
    typesByName.put(qName, modelElementType);
    typesByClass.put(instanceType, modelElementType);
  }

}
