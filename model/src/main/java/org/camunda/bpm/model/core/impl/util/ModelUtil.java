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

import org.camunda.bpm.model.core.impl.AbstractModel;
import org.camunda.bpm.model.core.impl.AbstractModelElement;
import org.camunda.bpm.model.core.impl.ModelElementCreateContext;
import org.camunda.bpm.model.core.impl.ModelException;
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
   * Returns the {@link AbstractModelElement ModelElement} for a DOM element.
   * If the model element does not yet exist, it is created and linked to the DOM.
   *
   * @param domElement the child element to create a new {@link AbstractModelElement ModelElement} for
   * @return the child model element
   */
  public static AbstractModelElement getModelElement(Element domElement, AbstractModel model) {
    AbstractModelElement modelElement = (AbstractModelElement) domElement.getUserData(MODEL_ELEMENT_KEY);
    if(modelElement == null) {
      Class<? extends AbstractModelElement> modelElementType = model.getModelElementTypeForDomElement(domElement);
      modelElement = ReflectUtil.createIntance(modelElementType, new ModelElementCreateContext(domElement, model));
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

  public static String getQName(String localName, String namespaceUri) {
    return ((namespaceUri == null) ? "" : (namespaceUri + ":")) + localName;
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
  public static <T> Collection<T> getModelElementCollection(Collection<Element> view, AbstractModel model) {
    List<AbstractModelElement> resultList = new ArrayList<AbstractModelElement>();
    for (Element element : view) {
      resultList.add(getModelElement(element, model));
    }
    return (List) resultList;
  }

}
