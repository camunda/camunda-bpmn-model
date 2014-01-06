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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.camunda.bpm.model.core.impl.ModelInstanceImpl;
import org.camunda.bpm.model.core.impl.ModelParseException;
import org.camunda.bpm.model.core.instance.ModelElementInstance;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Helper methods which abstract some gruesome DOM specifics.
 *
 * @author Daniel Meyer
 * @author Sebastian Menski
 *
 */
public class DomUtil {

  /**
   * A {@link NodeListFilter} allows to filter a {@link NodeList},
   * retaining only elements in the list which match the filter.
   *
   * @see DomUtil#filterNodeList(NodeList, NodeListFilter)
   */
  public static interface NodeListFilter<T extends Node> {

    /**
     *
     * @param node the node to match
     * @return true if the filter does match the node, false otherwise
     */
    public boolean matches(Node node);

  }

  /**
   * Filter retaining only Nodes of type {@link Node#ELEMENT_NODE}
   */
  public static class ElementNodeListFilter implements NodeListFilter<Element> {

    public boolean matches(Node node) {
      return node.getNodeType() == Node.ELEMENT_NODE;
    }

  }

  /**
   * Filters {@link Element Elements} by their nodeName + namespaceUri
   *
   */
  public static class ElementByNameListFilter extends ElementNodeListFilter {

    protected String localName;
    protected String namespaceUri;

    /**
     * @param localName the local name to filter for
     * @param namespaceUri the namespaceUri to filter for
     */
    public ElementByNameListFilter(String localName, String namespaceUri) {
      this.localName = localName;
      this.namespaceUri = namespaceUri;
    }

    @Override
    public boolean matches(Node node) {
      return super.matches(node)
        && localName.equals(node.getNodeName())
        && namespaceUri.equals(node.getNamespaceURI());
    }

  }

  public static class ElementByTypeListFilter extends ElementNodeListFilter {

    protected Class<?> type;
    protected ModelInstanceImpl model;

    public ElementByTypeListFilter(Class<?> type, ModelInstanceImpl modelInstance) {
      this.type =  type;
      this.model = modelInstance;
    }

    @Override
    public boolean matches(Node node) {
      if (! super.matches(node)) {
        return false;
      }
      ModelElementInstance modelElement = ModelUtil.getModelElement((Element) node, model);
      return type.isAssignableFrom(modelElement.getClass());
    }
  }

  /**
   * Allows to apply a {@link NodeListFilter} to a {@link NodeList}. This allows to remove all elements from a node list which do not match the Filter.
   *
   * @param nodeList the {@link NodeList} to filter
   * @param filter the {@link NodeListFilter} to apply to the {@link NodeList}
   * @return the List of all Nodes which match the filter
   */
  @SuppressWarnings("unchecked")
  public static <T extends Node> List<T> filterNodeList(NodeList nodeList, NodeListFilter<T> filter) {

    List<T> filteredList = new ArrayList<T>();
    for(int i = 0; i< nodeList.getLength(); i++) {
      Node node = nodeList.item(i);
      if(filter.matches(node)) {
        filteredList.add((T) node);
      }
    }

    return filteredList;

  }

  /**
   * Filter a {@link NodeList} retaining all elements with a specific name
   *
   * @param nodeList the {@link NodeList} to filter
   * @param localName the local element name to filter for
   * @param namespaceUri the namespace fot the elements
   * @return the List of all Elements which match the filter
   */
  public static List<Element> filterNodeListByName(NodeList nodeList, String localName, String namespaceUri) {
    return filterNodeList(nodeList, new ElementByNameListFilter(localName, namespaceUri));
  }

  /**
   * Returns the Document element for a Document
   *
   * @param document the document to retrieve the element for
   * @return the Element
   */
  public static Element getDocumentElement(Document document) {
    return document.getDocumentElement();
  }

  /**
   * @param document
   * @param domElement
   */
  public static void setDocumentElement(Document document, Element domElement) {
    Element existingDocumentElement = getDocumentElement(document);
    if(existingDocumentElement != null) {
      document.replaceChild(domElement, existingDocumentElement);
    }
    else {
      document.appendChild(domElement);
    }
  }

  /**
   * @param domElement
   * @return
   */
  public static NodeList getChildNodes(Element domElement) {
    return domElement.getChildNodes();
  }

  /**
   * @param domElement
   * @param element
   */
  public static boolean removeChild(Element domElement, Element element) {
    try {
      domElement.removeChild(element);
      return true;

    } catch(DOMException e) {
      return false;

    }

  }

  /**
   * @param domElement
   * @return
   */
  public static String getNamespaceUri(Element domElement) {
    return domElement.getNamespaceURI();
  }

  /**
   * @param documentBuilderFactory
   * @return
   */
  public static Document getEmptyDocument(DocumentBuilderFactory documentBuilderFactory) {
    DocumentBuilder documentBuilder;
    try {
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      return documentBuilder.newDocument();
    } catch (ParserConfigurationException e) {
      throw new ModelParseException("ParserConfigurationException while parsing input stream", e);
    }
  }

  /**
   * @param documentBuilderFactory
   * @param inputStream
   * @return
   */
  public static Document parseInputStream(DocumentBuilderFactory documentBuilderFactory, InputStream inputStream) {
    try {
      DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
      return documentBuilder.parse(inputStream);

    } catch (ParserConfigurationException e) {
      throw new ModelParseException("ParserConfigurationException while parsing input stream", e);

    } catch (SAXException e) {
      throw new ModelParseException("SAXException while parsing input stream", e);

    } catch (IOException e) {
      throw new ModelParseException("IOException while parsing input stream", e);

    }
  }


  /**
   * Returns the value for an attribute or 'null' if no such attribute exists.
   *
   * @param attributeName the name of the attribute to return the value for
   * @param domElement the element to get the attribute from
   * @return the value or 'null' if no such attribute exists
   */
  public static String getAttributeValue(String attributeName, Element domElement) {
    if(domElement.hasAttribute(attributeName)) {
      return domElement.getAttribute(attributeName);
    } else {
      return null;
    }
  }

  /**
   * Returns the value for an attribute or 'null' if no such attribute exists.
   *
   * @param attributeName the name of the attribute to return the value for
   * @param namespaceUri the namespace URI of the attribute
   * @param domElement the element to get the attribute from
   * @return the value or 'null' if no such attribute exists
   */
  public static String getAttributeValueNs(String attributeName, String namespaceUri, Element domElement) {
    if(domElement.hasAttributeNS(namespaceUri, attributeName)) {
      return domElement.getAttributeNS(namespaceUri, attributeName);
    } else {
      return null;
    }
  }

  /**
   * Sets the value for an attribute
   *
   * @param attributeName the name of the attribute to set the value for
   * @param xmlValue the value to set
   * @param domElement the DOM element to set the value on
   */
  public static void setAttributeValue(String attributeName, String xmlValue, Element domElement) {
    domElement.setAttribute(attributeName, xmlValue);
  }

  /**
   * Sets the value for an attribute
   *
   * @param attributeName the name of the attribute to set the value for
   * @param namespaceUri the namespace URI
   * @param xmlValue the value to set
   * @param domElement the DOM element to set the value on
   */
  public static void setAttributeValueNs(String attributeName, String namespaceUri, String xmlValue, Element domElement) {
    domElement.setAttributeNS(namespaceUri, attributeName, xmlValue);
  }

}
