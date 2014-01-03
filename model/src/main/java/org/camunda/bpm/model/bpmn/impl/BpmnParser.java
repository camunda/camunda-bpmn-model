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
package org.camunda.bpm.model.bpmn.impl;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.camunda.bpm.model.core.impl.parser.AbstractModelParser;
import org.camunda.bpm.model.core.impl.util.ReflectUtil;
import org.w3c.dom.Document;

/**
 * <p>The parser used when parsing BPMN Files</p>
 *
 * @author Daniel Meyer
 *
 */
public class BpmnParser extends AbstractModelParser {

  private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";
  private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
  private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

  @Override
  protected void configureFactory(DocumentBuilderFactory dbf) {
    super.configureFactory(dbf);
    dbf.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
    dbf.setAttribute(JAXP_SCHEMA_SOURCE, ReflectUtil.getResource(BpmnModelConstants.BPMN_20_SCHEMA_LOCATION).toString());
  }

  @Override
  protected BpmnModelImpl createModelInstance(Document document) {
    return new BpmnModelImpl(document);
  }

  @Override
  public BpmnModelImpl parseModelFromStream(InputStream inputStream) {
    return (BpmnModelImpl) super.parseModelFromStream(inputStream);
  }

  @Override
  public BpmnModelImpl getEmptyModel() {
    return (BpmnModelImpl) super.getEmptyModel();
  }

}
