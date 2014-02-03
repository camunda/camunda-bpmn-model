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

/**
 * Constants used in the BPMN 2.0 Language (DI + Semantic)
 *
 * @author Daniel Meyer
 *
 */
public final class BpmnModelConstants {

  /** The BPMN 2.0 namespace */
  public static final String BPMN20_NS = "http://www.omg.org/spec/BPMN/20100524/MODEL";

  /** The location of the BPMN 2.0 XML schema. */
  public static final String BPMN_20_SCHEMA_LOCATION = "BPMN20.xsd";

  /** Xml Schema is the default type language */
  public static final String XML_SCHEMA_NS = "http://www.w3.org/2001/XMLSchema";

  public static final String XPATH_NS = "http://www.w3.org/1999/XPath";

  /** Aciviti namespace */
  public static final String ACTIVITI_NS = "http://activiti.org/bpmn";

  // elements ////////////////////////////////////////

  public static final String BPMN_ELEMENT_BASE_ELEMENT = "baseElement";
  public static final String BPMN_ELEMENT_DEFINITIONS = "definitions";
  public static final String BPMN_ELEMENT_DOCUMENTATION = "documentation";
  public static final String BPMN_ELEMENT_EXTENSION = "extension";
  public static final String BPMN_ELEMENT_EXTENSION_ELEMENTS = "extensionElements";
  public static final String BPMN_ELEMENT_IMPORT = "import";
  public static final String BPMN_ELEMENT_RELATIONSHIP = "relationship";
  public static final String BPMN_ELEMENT_SOURCE = "source";
  public static final String BPMN_ELEMENT_TARGET = "target";
  public static final String BPMN_ELEMENT_ROOT_ELEMENT = "rootElement";
  public static final String BPMN_ELEMENT_AUDITING = "auditing";
  public static final String BPMN_ELEMENT_MONITORING = "monitoring";
  public static final String BPMN_ELEMENT_CATEGORY_VALUE = "categoryValue";
  public static final String BPMN_ELEMENT_FLOW_ELEMENT = "flowElement";
  public static final String BPMN_ELEMENT_FLOW_NODE = "flowNode";
  public static final String BPMN_ELEMENT_CATEGORY_VALUE_REF = "categoryValueRef";
  public static final String BPMN_ELEMENT_EXPRESSION = "expression";
  public static final String BPMN_ELEMENT_CONDITION_EXPRESSION = "conditionExpression";
  public static final String BPMN_ELEMENT_SEQUENCE_FLOW = "sequenceFlow";
  public static final String BPMN_ELEMENT_INCOMING = "incoming";
  public static final String BPMN_ELEMENT_OUTGOING = "outgoing";
  public static final String BPMN_ELEMENT_DATA_STATE = "dataState";
  public static final String BPMN_ELEMENT_ITEM_DEFINITION = "itemDefinition";
  public static final String BPMN_ELEMENT_ERROR = "error";
  public static final String BPMN_ELEMENT_IN_MESSAGE_REF = "inMessageRef";
  public static final String BPMN_ELEMENT_OUT_MESSAGE_REF = "outMessageRef";
  public static final String BPMN_ELEMENT_ERROR_REF = "errorRef";
  public static final String BPMN_ELEMENT_OPERATION = "operation";
  public static final String BPMN_ELEMENT_IMPLEMENTATION_REF = "implementationRef";
  public static final String BPMN_ELEMENT_OPERATION_REF = "operationRef";
  public static final String BPMN_ELEMENT_DATA_OUTPUT = "dataOutput";
  public static final String BPMN_ELEMENT_FROM = "from";
  public static final String BPMN_ELEMENT_TO = "to";
  public static final String BPMN_ELEMENT_ASSIGNMENT = "assignment";
  public static final String BPMN_ELEMENT_ITEM_AWARE_ELEMENT = "itemAwareElement";
  public static final String BPMN_ELEMENT_DATA_INPUT = "dataInput";
  public static final String BPMN_ELEMENT_FORMAL_EXPRESSION = "formalExpression";
  public static final String BPMN_ELEMENT_DATA_ASSOCIATION = "dataAssociation";
  public static final String BPMN_ELEMENT_SOURCE_REF = "sourceRef";
  public static final String BPMN_ELEMENT_TARGET_REF = "targetRef";
  public static final String BPMN_ELEMENT_TRANSFORMATION = "transformation";
  public static final String BPMN_ELEMENT_DATA_INPUT_ASSOCIATION = "dataInputAssociation";
  public static final String BPMN_ELEMENT_DATA_OUTPUT_ASSOCIATION = "dataOutputAssociation";
  public static final String BPMN_ELEMENT_INPUT_SET = "inputSet";
  public static final String BPMN_ELEMENT_OUTPUT_SET = "outputSet";
  public static final String BPMN_ELEMENT_DATA_INPUT_REFS = "dataInputRefs";
  public static final String BPMN_ELEMENT_OPTIONAL_INPUT_REFS = "optionalInputRefs";
  public static final String BPMN_ELEMENT_WHILE_EXECUTING_INPUT_REFS = "whileExecutingInputRefs";
  public static final String BPMN_ELEMENT_OUTPUT_SET_REFS = "outputSetRefs";
  public static final String BPMN_ELEMENT_DATA_OUTPUT_REFS = "dataOutputRefs";
  public static final String BPMN_ELEMENT_OPTIONAL_OUTPUT_REFS = "optionalOutputRefs";
  public static final String BPMN_ELEMENT_WHILE_EXECUTING_OUTPUT_REFS = "whileExecutingOutputRefs";
  public static final String BPMN_ELEMENT_INPUT_SET_REFS = "inputSetRefs";
  public static final String BPMN_ELEMENT_CATCH_EVENT = "catchEvent";
  public static final String BPMN_ELEMENT_THROW_EVENT = "throwEvent";
  public static final String BPMN_ELEMENT_END_EVENT = "endEvent";
  public static final String BPMN_ELEMENT_IO_SPECIFICATION = "ioSpecification";
  public static final String BPMN_ELEMENT_LOOP_CHARACTERISTICS = "loopCharacteristics";
  public static final String BPMN_ELEMENT_RESOURCE_PARAMETER = "resourceParameter";
  public static final String BPMN_ELEMENT_RESOURCE = "resource";
  public static final String BPMN_ELEMENT_RESOURCE_PARAMETER_BINDING = "resourceParameterBinding";
  public static final String BPMN_ELEMENT_RESOURCE_ASSIGNMENT_EXPRESSION = "resourceAssignmentExpression";
  public static final String BPMN_ELEMENT_RESOURCE_ROLE = "resourceRole";
  public static final String BPMN_ELEMENT_RESOURCE_REF = "resourceRef";
  public static final String BPMN_ELEMENT_ACTIVITY = "activity";
  public static final String BPMN_ELEMENT_IO_BINDING = "ioBinding";
  public static final String BPMN_ELEMENT_INTERFACE = "interface";
  public static final String BPMN_ELEMENT_EVENT = "event";
  public static final String BPMN_ELEMENT_MESSAGE = "message";
  public static final String BPMN_ELEMENT_START_EVENT = "startEvent";
  public static final String BPMN_ELEMENT_PROPERTY = "property";
  public static final String BPMN_ELEMENT_EVENT_DEFINITION = "eventDefinition";
  public static final String BPMN_ELEMENT_EVENT_DEFINITION_REF = "eventDefinitionRef";
  public static final String BPMN_ELEMENT_MESSAGE_EVENT_DEFINITION = "messageEventDefinition";
  public static final String BPMN_ELEMENT_SUPPORTED_INTERFACE_REF = "supportedInterfaceRef";
  public static final String BPMN_ELEMENT_CALLABLE_ELEMENT = "callableElement";
  public static final String BPMN_ELEMENT_PARTITION_ELEMENT = "partitionElement";
  public static final String BPMN_ELEMENT_FLOW_NODE_REF = "flowNodeRef";
  public static final String BPMN_ELEMENT_CHILD_LANE_SET = "childLaneSet";
  public static final String BPMN_ELEMENT_LANE_SET = "laneSet";
  public static final String BPMN_ELEMENT_LANE = "lane";
  public static final String BPMN_ELEMENT_ARTIFACT = "artifact";
  public static final String BPMN_ELEMENT_CORRELATION_PROPERTY_RETRIEVAL_EXPRESSION = "correlationPropertyRetrievalExpression";
  public static final String BPMN_ELEMENT_MESSAGE_PATH = "messagePath";
  public static final String BPMN_ELEMENT_DATA_PATH = "dataPath";
  public static final String BPMN_ELEMENT_CORRELATION_PROPERTY_BINDING = "correlationPropertyBinding";
  public static final String BPMN_ELEMENT_CORRELATION_PROPERTY = "correlationProperty";
  public static final String BPMN_ELEMENT_CORRELATION_PROPERTY_REF = "correlationPropertyRef";
  public static final String BPMN_ELEMENT_CORRELATION_KEY = "correlationKey";
  public static final String BPMN_ELEMENT_CORRELATION_SUBSCRIPTION = "correlationSubscription";
  public static final String BPMN_ELEMENT_SUPPORTS = "supports";
  public static final String BPMN_ELEMENT_PROCESS = "process";
  public static final String BPMN_ELEMENT_TASK = "task";
  public static final String BPMN_ELEMENT_SEND_TASK = "sendTask";
  public static final String BPMN_ELEMENT_SERVICE_TASK = "serviceTask";
  public static final String BPMN_ELEMENT_SCRIPT_TASK = "scriptTask";
  public static final String BPMN_ELEMENT_USER_TASK = "userTask";
  public static final String BPMN_ELEMENT_RECEIVE_TASK = "receiveTask";
  public static final String BPMN_ELEMENT_BUSINESS_RULE_TASK = "businessRuleTask";
  public static final String BPMN_ELEMENT_MANUAL_TASK = "manualTask";
  public static final String BPMN_ELEMENT_SCRIPT = "script";
  public static final String BPMN_ELEMENT_RENDERING = "rendering";
  public static final String BPMN_ELEMENT_BOUNDARY_EVENT = "boundaryEvent";
  public static final String BPMN_ELEMENT_SUB_PROCESS = "subProcess";
  public static final String BPMN_ELEMENT_GATEWAY = "gateway";
  public static final String BPMN_ELEMENT_PARALLEL_GATEWAY = "parallelGateway";
  public static final String BPMN_ELEMENT_EXCLUSIVE_GATEWAY = "exclusiveGateway";


  // attributes //////////////////////////////////////

  public static final String BPMN_ATTRIBUTE_EXPORTER = "exporter";
  public static final String BPMN_ATTRIBUTE_EXPORTER_VERSION = "exporterVersion";
  public static final String BPMN_ATTRIBUTE_EXPRESSION_LANGUAGE = "expressionLanguage";
  public static final String BPMN_ATTRIBUTE_ID = "id";
  public static final String BPMN_ATTRIBUTE_NAME = "name";
  public static final String BPMN_ATTRIBUTE_TARGET_NAMESPACE = "targetNamespace";
  public static final String BPMN_ATTRIBUTE_TYPE_LANGUAGE = "typeLanguage";
  public static final String BPMN_ATTRIBUTE_NAMESPACE = "namespace";
  public static final String BPMN_ATTRIBUTE_LOCATION = "location";
  public static final String BPMN_ATTRIBUTE_IMPORT_TYPE = "importType";
  public static final String BPMN_ATTRIBUTE_TEXT_FORMAT = "textFormat";
  public static final String BPMN_ATTRIBUTE_PROCESS_TYPE = "processType";
  public static final String BPMN_ATTRIBUTE_IS_CLOSED = "isClosed";
  public static final String BPMN_ATTRIBUTE_IS_EXECUTABLE = "isExecutable";
  public static final String BPMN_ATTRIBUTE_MESSAGE_REF = "messageRef";
  public static final String BPMN_ATTRIBUTE_DEFINITION = "definition";
  public static final String BPMN_ATTRIBUTE_MUST_UNDERSTAND = "mustUnderstand";
  public static final String BPMN_ATTRIBUTE_TYPE = "type";
  public static final String BPMN_ATTRIBUTE_DIRECTION = "direction";
  public static final String BPMN_ATTRIBUTE_SOURCE_REF = "sourceRef";
  public static final String BPMN_ATTRIBUTE_TARGET_REF = "targetRef";
  public static final String BPMN_ATTRIBUTE_IS_IMMEDIATE = "isImmediate";
  public static final String BPMN_ATTRIBUTE_VALUE = "value";
  public static final String BPMN_ATTRIBUTE_STRUCTURE_REF = "structureRef";
  public static final String BPMN_ATTRIBUTE_IS_COLLECTION = "isCollection";
  public static final String BPMN_ATTRIBUTE_ITEM_KIND = "itemKind";
  public static final String BPMN_ATTRIBUTE_ITEM_REF = "itemRef";
  public static final String BPMN_ATTRIBUTE_ITEM_SUBJECT_REF = "itemSubjectRef";
  public static final String BPMN_ATTRIBUTE_ERROR_CODE = "errorCode";
  public static final String BPMN_ATTRIBUTE_LANGUAGE = "language";
  public static final String BPMN_ATTRIBUTE_EVALUATES_TO_TYPE_REF = "evaluatesToTypeRef";
  public static final String BPMN_ATTRIBUTE_PARALLEL_MULTIPLE = "parallelMultiple";
  public static final String BPMN_ATTRIBUTE_IS_INTERRUPTING = "isInterrupting";
  public static final String BPMN_ATTRIBUTE_IS_REQUIRED = "isRequired";
  public static final String BPMN_ATTRIBUTE_PARAMETER_REF = "parameterRef";
  public static final String BPMN_ATTRIBUTE_IS_FOR_COMPENSATION = "isForCompensation";
  public static final String BPMN_ATTRIBUTE_START_QUANTITY = "startQuantity";
  public static final String BPMN_ATTRIBUTE_COMPLETION_QUANTITY = "completionQuantity";
  public static final String BPMN_ATTRIBUTE_DEFAULT = "default";
  public static final String BPMN_ATTRIBUTE_OPERATION_REF = "operationRef";
  public static final String BPMN_ATTRIBUTE_INPUT_DATA_REF = "inputDataRef";
  public static final String BPMN_ATTRIBUTE_OUTPUT_DATA_REF = "outputDataRef";
  public static final String BPMN_ATTRIBUTE_IMPLEMENTATION_REF = "implementationRef";
  public static final String BPMN_ATTRIBUTE_PARTITION_ELEMENT_REF = "partitionElementRef";
  public static final String BPMN_ATTRIBUTE_CORRELATION_PROPERTY_REF = "correlationPropertyRef";
  public static final String BPMN_ATTRIBUTE_CORRELATION_KEY_REF = "correlationKeyRef";
  public static final String BPMN_ATTRIBUTE_IMPLEMENTATION = "implementation";
  public static final String BPMN_ATTRIBUTE_SCRIPT_FORMAT = "scriptFormat";
  public static final String BPMN_ATTRIBUTE_INSTANTIATE = "instantiate";
  public static final String BPMN_ATTRIBUTE_CANCEL_ACTIVITY = "cancelActivity";
  public static final String BPMN_ATTRIBUTE_ATTACHED_TO_REF = "attachedToRef";
  public static final String BPMN_ATTRIBUTE_TRIGGERED_BY_EVENT = "triggeredByEvent";
  public static final String BPMN_ATTRIBUTE_GATEWAY_DIRECTION = "gatewayDirection";

  public static final String ACTIVITI_ATTRIBUTE_FORM_KEY = "formKey";
  public static final String ACTIVITI_ATTRIBUTE_ASSIGNEE = "assignee";
  public static final String ACTIVITI_ATTRIBUTE_CANDIDATE_USERS = "candidateUsers";
  public static final String ACTIVITI_ATTRIBUTE_CANDIDATE_GROUPS = "candidateGroups";
  public static final String ACTIVITI_ATTRIBUTE_CLASS = "class";

}
