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
package org.camunda.bpm.model.core.impl.parser;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.camunda.bpm.model.core.ModelInstance;
import org.camunda.bpm.model.core.ModelValidationException;
import org.camunda.bpm.model.core.impl.util.DomUtil;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author Daniel Meyer
 *
 */
public abstract class AbstractModelParser {

  protected DocumentBuilderFactory documentBuilderFactory;
  protected SchemaFactory schemaFactory;
  protected Schema schema;

  public AbstractModelParser() {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    configureFactory(dbf);
    this.documentBuilderFactory = dbf;
  }

  /**
   * allows subclasses to configure the {@link DocumentBuilderFactory}.
   * @param dbf the factory to configure
   */
  protected void configureFactory(DocumentBuilderFactory dbf) {
    dbf.setValidating(false);
    dbf.setIgnoringComments(false);
    dbf.setIgnoringElementContentWhitespace(false);
    dbf.setNamespaceAware(true);
  }

  public ModelInstance parseModelFromStream(InputStream inputStream) {
    Document document = DomUtil.parseInputStream(documentBuilderFactory, inputStream);
    validateModel(document);
    return createModelInstance(document);

  }

  public ModelInstance getEmptyModel() {
    Document document = DomUtil.getEmptyDocument(documentBuilderFactory);
    return createModelInstance(document);
  };

  /**
   * Validate DOM document
   *
   * @param document the DOM document to validate
   */
  public void validateModel(Document document) {
    if (schema == null) {
      return;
    }

    Validator validator = schema.newValidator();
    try {
      validator.validate(new DOMSource(document));
    } catch (IOException e) {
      throw new ModelValidationException("Error during DOM docuement validation", e);
    } catch (SAXException e) {
      throw new ModelValidationException("DOM document is not valid", e);
    }
  }

  protected abstract ModelInstance createModelInstance(Document document);

}
