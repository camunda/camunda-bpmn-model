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
package org.camunda.bpm.model.core.type;

import java.util.Collection;
import java.util.List;

import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.ModelInstance;
import org.camunda.bpm.model.core.instance.ModelElementInstance;

/**
 * @author Daniel Meyer
 *
 */
public interface ModelElementType {

  public String getTypeName();

  public String getTypeNamespace();

  public List<Attribute<?>> getAttributes();

  public ModelElementInstance newInstance(ModelInstance modelInstance);

  public ModelElementType getParentType();

  boolean isAbstract();

  Collection<ModelElementType> getExtendingTypes();

  Model getModel();

}
