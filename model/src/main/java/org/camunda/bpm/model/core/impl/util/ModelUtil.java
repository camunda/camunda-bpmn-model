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
package org.camunda.bpm.model.core.impl.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.camunda.bpm.model.core.ModelException;
import org.camunda.bpm.model.core.impl.ModelInstanceImpl;
import org.camunda.bpm.model.core.impl.instance.ModelElementInstanceImpl;
import org.camunda.bpm.model.core.impl.type.ModelElementTypeImpl;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Some Helpers useful when handling model elements.
 *
 * @author Daniel Meyer
 *
 */
public class ModelUtil {

  public final static String MODEL_ELEMENT_KEY = "camunda.modelElementRef";

  /**
   * Returns the {@link ModelElementInstanceImpl ModelElement} for a DOM element.
   * If the model element does not yet exist, it is created and linked to the DOM.
   *
   * @param domElement the child element to create a new {@link ModelElementInstanceImpl ModelElement} for
   * @return the child model element
   */
  public static ModelElementInstance getModelElement(Element domElement, ModelInstanceImpl modelInstance) {
    ModelElementInstance modelElement = (ModelElementInstanceImpl) domElement.getUserData(MODEL_ELEMENT_KEY);
    if(modelElement == null) {

      String namespaceUri = domElement.getNamespaceURI();
      String localName = domElement.getLocalName();

      ModelElementTypeImpl modelType = (ModelElementTypeImpl) modelInstance.getModel().getTypeForName(localName, namespaceUri);
      if(modelType == null) {
        throw new ModelException("Cannot create model type instance for type "+getQName(localName, namespaceUri)+" no instance type registered.");
      }
      modelElement = modelType.newInstance(modelInstance, domElement);
      domElement.setUserData(MODEL_ELEMENT_KEY, modelElement, null);
    }
    return modelElement;
  }

  public static void ensureSameDocument(Attr attributeToAdd, Document targetDocument) {
    if(attributeToAdd.getOwnerDocument() == targetDocument) {
      throw new WrongDocumentException(attributeToAdd, targetDocument);
    }
  }

  public static void ensureSameDocument(Element elementToAdd, Document targetDocument) {
    if(elementToAdd.getOwnerDocument() != targetDocument) {
      throw new WrongDocumentException(elementToAdd, targetDocument);
    }
  }

  public static void ensureNotNull(Object newElement, String name) {
    if(newElement == null) {
      throw new ModelException(name + " cannot be null.");
    }
  }

  public static QName getQName(String localName, String namespaceUri) {
    return new QName(localName, namespaceUri);
  }

  public static void ensureInstanceOf(Object instance, Class<?> type) {
    if(!type.isAssignableFrom(instance.getClass())) {
      throw new ModelException("Object is not instance of type "+type.getName());
    }
  }

  // String to primitive type converters ////////////////////////////////////

  public static boolean valueAsBoolean(String rawValue) {
    return Boolean.parseBoolean(rawValue);
  }

  public static int valueAsInteger(String rawValue) {
    try {
      return Integer.parseInt(rawValue);
    } catch(NumberFormatException e) {
      throw new ModelTypeException(rawValue, Integer.class);
    }
  }

  public static float valueAsFloat(String rawValue) {
    try {
      return Float.parseFloat(rawValue);
    }catch(NumberFormatException e) {
      throw new ModelTypeException(rawValue, Float.class);
    }
  }

  public static double valueAsDouble(String rawValue) {
    try {
      return Double.parseDouble(rawValue);
    }catch(NumberFormatException e) {
      throw new ModelTypeException(rawValue, Double.class);
    }
  }

  public static short valueAsShort(String rawValue) {
    try {
      return Short.parseShort(rawValue);
    }catch(NumberFormatException e) {
      throw new ModelTypeException(rawValue, Short.class);
    }
  }

  // primitive type to string converters //////////////////////////////////////

  public static String valueAsString(boolean booleanValue) {
      return Boolean.toString(booleanValue);
  }

  public static String valueAsString(int integerValue) {
    return Integer.toString(integerValue);
  }

  public static String valueAsString(float floatValue) {
    return Float.toString(floatValue);
  }

  public static String valueAsString(double doubleValue) {
    return Double.toString(doubleValue);
  }

  public static String valueAsString(short shortValue) {
    return Short.toString(shortValue);
  }

  /**
   * @param view
   * @param model
   * @return
   */
  public static <T> Collection<T> getModelElementCollection(Collection<Element> view, ModelInstanceImpl model) {
    List<ModelElementInstance> resultList = new ArrayList<ModelElementInstance>();
    for (Element element : view) {
      resultList.add(getModelElement(element, model));
    }
    return (List) resultList;
  }

  /**
   * Find the index of the type of a model ellement in a list of element types
   *
   * @param modelElement the model element which type is searched for
   * @param elementList the list to search the type
   * @return the index of the model element type in the list or -1 if it is not found
   */
  public static int getIndexOfElementType(ModelElementInstance modelElement, List<Class<?>> elementList) {
    for (int index = 0; index < elementList.size(); index++) {
      if(elementList.get(index).isAssignableFrom(modelElement.getClass())) {
        return index;
      }
    }
    return -1;
  }

}
