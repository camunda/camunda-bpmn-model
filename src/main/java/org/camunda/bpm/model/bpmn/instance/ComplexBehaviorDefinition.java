/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.model.bpmn.instance;

/**
 * The BPMN 2.0 complexBehaviorDefinition element
 * 
 * @author Filip Hrisafov	
 */
public interface ComplexBehaviorDefinition extends BaseElement {
	// Use condition since tFormalExpression is not fully supported by
	// the model API
	// See https://groups.google.com/d/msg/camunda-bpm-users/n6j13SjbjqE/ORE6u3WT5NkJ 
	
	Condition getCondition();
	
	void setCondition(Condition condition);
	
	ImplicitThrowEvent getEvent();
	
	void setEvent (ImplicitThrowEvent event);

}
