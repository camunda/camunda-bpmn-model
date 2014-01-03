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
package org.camunda.bpm.model.bpmn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.camunda.bpm.model.bpmn.impl.BpmnModelTypeMap;
import org.camunda.bpm.model.bpmn.impl.BpmnParser;
import org.camunda.bpm.model.core.impl.ModelParseException;
import org.camunda.bpm.model.core.impl.util.IoUtil;

/**
 * <p>Provides access to the camunda BPMN model api.</p>
 *
 * @author Daniel Meyer
 *
 */
public class Bpmn {

  /** the singleton instance of {@link Bpmn}. If you want to customize the behavior of Bpmn,
   * replace this instance with an instance of a custom subclass of {@link Bpmn}. */
  public static Bpmn INSTANCE = new Bpmn();

  /** the parser used by the Bpmn implementation. */
  protected BpmnParser bpmnParser = new BpmnParser();

  /** the instance of the BpmnModelPackage used. The {@link BpmnModelTypeMap} provides information
   * about which Java types to use for which elements. If you wna to use custom types, set your
   * custom package here. */
  protected BpmnModelTypeMap modelPackage = BpmnModelTypeMap.getINSTANCE();

  /**
   * Allows reading a {@link BpmnModel} from a File.
   *
   * @param file the {@link File} to read the {@link BpmnModel} from
   * @return the model read
   * @throws BpmnModelException if the model cannot be read
   */
  public static BpmnModel readModelFromFile(File file) {
    return INSTANCE.doReadModelFromFile(file);
  }

  /**
   * Allows reading a {@link BpmnModel} from an {@link InputStream}
   *
   * @param file the {@link InputStream} to read the {@link BpmnModel} from
   * @return the model read
   * @throws ModelParseException if the model cannot be read
   */
  public static BpmnModel readModelFromStream(InputStream stream) {
    return INSTANCE.doReadModelFromInputStream(stream);
  }

  /**
   * Allows creating an new, empty {@link BpmnModel}.
   *
   * @return the empty model.
   */
  public static BpmnModel createEmptyModel() {
    return INSTANCE.doCreateEmptyModel();
  }

  protected BpmnModel doReadModelFromFile(File file) {
    InputStream is = null;
    try {
      is = new FileInputStream(file);
      return doReadModelFromInputStream(is);

    } catch (FileNotFoundException e) {
      throw new BpmnModelException("Cannot read model from file "+file+": file does not exist.");

    } finally {
      IoUtil.closeSilently(is);

    }
  }

  protected BpmnModel doReadModelFromInputStream(InputStream is) {
    return bpmnParser.parseModelFromStream(is);
  }

  protected BpmnModel doCreateEmptyModel() {
    return bpmnParser.getEmptyModel();
  }

  /**
   * @return the {@link BpmnModelTypeMap} instance to use
   */
  public BpmnModelTypeMap getModelPackage() {
    return modelPackage;
  }

  public void setModelPackage(BpmnModelTypeMap modelPackage) {
    this.modelPackage = modelPackage;
  }

}