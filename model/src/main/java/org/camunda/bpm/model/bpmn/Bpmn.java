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

import org.camunda.bpm.model.bpmn.impl.BaseElementImp;
import org.camunda.bpm.model.bpmn.impl.BpmnParser;
import org.camunda.bpm.model.bpmn.impl.CallableElementImpl;
import org.camunda.bpm.model.bpmn.impl.DefinitionsImpl;
import org.camunda.bpm.model.bpmn.impl.ImportImpl;
import org.camunda.bpm.model.bpmn.impl.ProcessImpl;
import org.camunda.bpm.model.bpmn.impl.RootElementImpl;
import org.camunda.bpm.model.core.Model;
import org.camunda.bpm.model.core.impl.ModelImpl;
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

  /** The {@link Model}
   */
  protected Model bpmnModel = new ModelImpl();

  /**
   * Allows reading a {@link BpmnModelInstance} from a File.
   *
   * @param file the {@link File} to read the {@link BpmnModelInstance} from
   * @return the model read
   * @throws BpmnModelException if the model cannot be read
   */
  public static BpmnModelInstance readModelFromFile(File file) {
    return INSTANCE.doReadModelFromFile(file);
  }

  /**
   * Allows reading a {@link BpmnModelInstance} from an {@link InputStream}
   *
   * @param file the {@link InputStream} to read the {@link BpmnModelInstance} from
   * @return the model read
   * @throws ModelParseException if the model cannot be read
   */
  public static BpmnModelInstance readModelFromStream(InputStream stream) {
    return INSTANCE.doReadModelFromInputStream(stream);
  }

  /**
   * Allows creating an new, empty {@link BpmnModelInstance}.
   *
   * @return the empty model.
   */
  public static BpmnModelInstance createEmptyModel() {
    return INSTANCE.doCreateEmptyModel();
  }

  public Bpmn() {
    doRegisterTypes(bpmnModel);
  }

  protected BpmnModelInstance doReadModelFromFile(File file) {
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

  protected BpmnModelInstance doReadModelFromInputStream(InputStream is) {
    return bpmnParser.parseModelFromStream(is);
  }

  protected BpmnModelInstance doCreateEmptyModel() {
    return bpmnParser.getEmptyModel();
  }

  protected void doRegisterTypes(Model model) {
    DefinitionsImpl.registerType(model);
    ImportImpl.registerType(model);
    BaseElementImp.registerType(model);
    RootElementImpl.registerType(model);
    CallableElementImpl.registerType(model);
    ProcessImpl.registerType(model);
  }

  /**
   * @return the {@link BpmnModelTypeMap} instance to use
   */
  public Model getBpmnModel() {
    return bpmnModel;
  }

  /**
   * @param bpmnModel the bpmnModel to set
   */
  public void setBpmnModel(Model bpmnModel) {
    this.bpmnModel = bpmnModel;
  }

}