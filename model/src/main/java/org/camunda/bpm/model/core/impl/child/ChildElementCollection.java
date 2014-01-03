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
package org.camunda.bpm.model.core.impl.child;

import java.util.Collection;
import java.util.Iterator;

import org.camunda.bpm.model.core.impl.AbstractModelElement;
import org.camunda.bpm.model.core.impl.ModelElement;
import org.camunda.bpm.model.core.impl.UnsupportedModelOperationException;
import org.camunda.bpm.model.core.impl.util.DomUtil.ElementNodeListFilter;
import org.camunda.bpm.model.core.impl.util.DomUtil;
import org.camunda.bpm.model.core.impl.util.ModelUtil;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * <p>This collection is a view on an the children of a Model Element.</p>
 *
 * @author Daniel Meyer
 *
 */
public abstract class ChildElementCollection<T extends ModelElement> implements Collection<T> {

  protected final boolean isMutable;

  /** the wrapped modelElement */
  protected final AbstractModelElement modelElement;

  /**
   * Initializes a new mutable collection
   *
   * @param modelElement the Element to wrap
   */
  public ChildElementCollection(AbstractModelElement modelElement) {
    this.modelElement = modelElement;
    this.isMutable = true;
  }

  /**
   * Initializes a new collection
   *
   * @param modelElement the modelElement to wrap
   * @param isMutable indicates whether the collection is mutable
   */
  public ChildElementCollection(AbstractModelElement modelElement, boolean isMutable) {
    this.modelElement = modelElement;
    this.isMutable = isMutable;
  }

  // view /////////////////////////////////////////////////////////

  /**
   * <p>To be overridden by subclasses: allows subclasses to filter the
   * actual child elements of the element in order to build the collection of
   * elements to be contained in the view represented by this collection.</p>
   *
   * @param modelElement the element to check
   * @return true if the element should be contained in the collection. False otherwise.
   */
  protected abstract ElementNodeListFilter getFilter();

  /**
   * Internal method providing access to the view represented by this collection.
   *
   * @return the view represented by this collection
   */
  protected Collection<Element> getView() {
    NodeList childNodes = DomUtil.getChildNodes(modelElement.getDomElement());
    return DomUtil.filterNodeList(childNodes, getFilter());
  }

  // interface implementation ////////////////////////////////

  public boolean contains(Object o) {
    if(o == null) {
      return false;

    } else if(!(o instanceof AbstractModelElement)) {
      return false;

    } else {
      return getView().contains(((AbstractModelElement)o).getDomElement());

    }
  }

  public boolean containsAll(Collection<?> c) {
    for (Object elementToCheck : c) {
      if(!contains(elementToCheck)) {
        return false;
      }
    }
    return true;
  }

  public boolean isEmpty() {
    return getView().isEmpty();
  }

  public Iterator<T> iterator() {
    Collection<T> modelElementCollection = ModelUtil.getModelElementCollection(getView(), modelElement.getModel());
    return modelElementCollection.iterator();
  }

  public Object[] toArray() {
    return getView().toArray();
  }

  public <U extends Object> U[] toArray(U[] a) {
    return getView().toArray(a);
  }

  public int size() {
    return getView().size();
  }

  public boolean add(T e) {
    if(!isMutable) {
      throw new UnsupportedModelOperationException("add()", "collection is immutable");
    }
    modelElement.addChildElement(e);
    return true;
  }

  public boolean addAll(Collection<? extends T> c) {
    if(!isMutable) {
      throw new UnsupportedModelOperationException("addAll()", "collection is immutable");
    }
    boolean result = false;
    for (T t : c) {
      result |= add(t);
    }
    return result;
  }

  public void clear() {
    if(!isMutable) {
      throw new UnsupportedModelOperationException("clear()", "collection is immutable");
    }
    Collection<Element> view = getView();
    for (Element element : view) {
      DomUtil.removeChild(modelElement.getDomElement(), element);
    }
  }

  public boolean remove(Object e) {
    if(!isMutable) {
      throw new UnsupportedModelOperationException("remove()", "collection is immutable");
    }
    ModelUtil.ensureInstanceOf(e, AbstractModelElement.class);
    return modelElement.removeChildElement((AbstractModelElement)e);
  }

  public boolean removeAll(Collection<?> c) {
    if(!isMutable) {
      throw new UnsupportedModelOperationException("removeAll()", "collection is immutable");
    }
    boolean result = false;
    for (Object t : c) {
      result |= remove(t);
    }
    return result;
  }

  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedModelOperationException("retainAll()", "not implemented");
  }

}
