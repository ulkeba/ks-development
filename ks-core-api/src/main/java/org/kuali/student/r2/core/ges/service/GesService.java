/*
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.student.r2.core.ges.service;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.ges.dto.ParameterInfo;
import org.kuali.student.r2.core.ges.dto.ValueInfo;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.util.Date;
import java.util.List;

/**
 * This service supports the management of settings and default values.
 * These values are distinguished using a parameter and other optional
 * qualifiers (ATP typekey, population, organization, and program).
 *
 * @author Kuali Student Services
 */

@WebService(name = "GesService", serviceName = "GesService", portName = "GesService", targetNamespace = GesServiceNamespace.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface GesService {

    /**
     * Retrieves a single Parameter by Parameter Id.
     *
     * @param parameterId the identifier for the parameter to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return the Parameter requested
     * @throws DoesNotExistException     parameterId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ParameterInfo getParameter(@WebParam(name = "parameterId") String parameterId,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Parameters from a list of
     * Parameter Ids. The returned list may be in any order and
     * if duplicates Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param parameterIds a list of Parameter identifiers
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of the service operation
     * @return a list of Parameters
     * @throws DoesNotExistException     a parameterId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException parameterIds, an Id in
     *                                   parameterIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ParameterInfo> getParametersByIds(@WebParam(name = "parameterIds") List<String> parameterIds,
                                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Parameter Ids by Parameter Type.
     *
     * @param parameterTypeKey an identifier for a
     *                         Parameter Type
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of the service operation
     * @return a list of Parameter identifiers matching
     *         parameterTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getParameterIdsByType(@WebParam(name = "parameterTypeKey") String parameterTypeKey,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Parameters based on the criteria and returns
     * a list of Parameter identifiers which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Parameter Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForParameterIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Parameters based on the criteria and returns
     * a list of Parameters which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Parameters matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ParameterInfo> searchForParameters(@WebParam(name = "criteria") QueryByCriteria criteria,
                                                   @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Parameter. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current Parameter and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * Parameter. If an identifier is present for the
     * Parameter (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the Parameter can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param valueTypeKey      the identifier for types of values that this parameter may be associated with.
     * @param parameterTypeKey  the identifier for the parameter Type
     * @param parameterInfo     the Parameter information to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey, valueTypeKey, or
     *                                   parameterTypeKey is not found
     * @throws InvalidParameterException parameterInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, valueTypeKey, parameterTypeKey,
     *                                   parameterInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateParameter(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                        @WebParam(name = "valueTypeKey") String valueTypeKey,
                                                        @WebParam(name = "parameterTypeKey") String parameterTypeKey,
                                                        @WebParam(name = "parameterInfo") ParameterInfo parameterInfo,
                                                        @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Parameter. The Parameter Id, Value Type, Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param valueTypeKey     the identifier for types of values that this parameter may be associated with.
     * @param parameterTypeKey the identifier for the Type of
     *                         the new Parameter
     * @param parameterInfo    the data with which to create the
     *                         Parameter
     * @param contextInfo      information containing the principalId and
     *                         locale information about the caller of service operation
     * @return the new Parameter
     * @throws DoesNotExistException     valueTypeKey or
     *                                   parameterTypeKey does not exist or is not
     *                                   supported
     * @throws InvalidParameterException parameterInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException valueTypeKey, parameterTypeKey, parameterInfo or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws DataValidationErrorException supplied data is invalid
     */
    public ParameterInfo createParameter(@WebParam(name = "valueTypeKey") String valueTypeKey,
                                         @WebParam(name = "parameterTypeKey") String parameterTypeKey,
                                         @WebParam(name = "parameterInfo") ParameterInfo parameterInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Parameter. The Parameter Id,
     * Type, and Meta information may not be changed.
     *
     * @param parameterId   the identifier for the Parameter
     *                      to be updated
     * @param parameterInfo the new data for the Parameter
     * @param contextInfo   information containing the principalId and
     *                      locale information about the caller of service operation
     * @return the updated Parameter
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        parameterId not found
     * @throws InvalidParameterException    parameterInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    parameterId,
     *                                      parameterInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public ParameterInfo updateParameter(@WebParam(name = "parameterId") String parameterId,
                                         @WebParam(name = "parameterInfo") ParameterInfo parameterInfo,
                                         @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;


    /**
     * Deletes an existing Parameter.
     *
     * @param parameterId the identifier for the Parameter
     *                    to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     parameterId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteParameter(@WebParam(name = "parameterId") String parameterId,
                                      @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a single Value by Value Id.
     *
     * @param valueId     the identifier for the value to be retrieved
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return the Value requested
     * @throws DoesNotExistException     valueId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException valueId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public ValueInfo getValue(@WebParam(name = "valueId") String valueId,
                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Values from a list of
     * Value Ids. The returned list may be in any order and
     * if duplicates Ids are supplied, a unique set may or may not be
     * returned.
     *
     * @param valueIds    a list of Value identifiers
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Values
     * @throws DoesNotExistException     a valueId in the list not found
     * @throws InvalidParameterException contextInfo is invalid
     * @throws MissingParameterException valueIds, an Id in
     *                                   valueIds, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> getValuesByIds(@WebParam(name = "valueIds") List<String> valueIds,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of Value Ids by Value Type.
     *
     * @param valueTypeKey an identifier for a
     *                     Value Type
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of the service operation
     * @return a list of Value identifiers matching
     *         valueTypeKey or an empty list if none found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException valueTypeKey or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> getValueIdsByType(@WebParam(name = "valueTypeKey") String valueTypeKey,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Values based on the criteria and returns
     * a list of Value identifiers which match the search
     * criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Value Ids matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<String> searchForValueIds(@WebParam(name = "criteria") QueryByCriteria criteria,
                                          @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Searches for Values based on the criteria and returns
     * a list of Values which match the search criteria.
     *
     * @param criteria    the search criteria
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of the service operation
     * @return a list of Values matching the criteria
     * @throws InvalidParameterException criteria or contextInfo is
     *                                   not valid
     * @throws MissingParameterException criteria or contextInfo is
     *                                   missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> searchForValues(@WebParam(name = "criteria") QueryByCriteria criteria,
                                           @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Validates a Value. Depending on the value of
     * validationType, this validation could be limited to tests on
     * just the current Value and its directly contained
     * sub-objects or expanded to perform all tests related to this
     * Value. If an identifier is present for the
     * Value (and/or one of its contained sub-objects) and a
     * record is found for that identifier, the validation checks if
     * the Value can be updated to the new values. If an
     * identifier is not present or a record does not exist, the
     * validation checks if the object with the given data can be
     * created.
     *
     * @param validationTypeKey the identifier for the validation Type
     * @param valueTypeKey      the identifier for the value Type
     * @param valueInfo         the Value information to be validated
     * @param contextInfo       information containing the principalId and
     *                          locale information about the caller of service operation
     * @return a list of validation results or an empty list if
     *         validation succeeded
     * @throws DoesNotExistException     validationTypeKey or valueTypeKey is not found
     * @throws InvalidParameterException valueInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException validationTypeKey, valueTypeKey,
     *                                   valueInfo, or contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValidationResultInfo> validateValue(@WebParam(name = "validationTypeKey") String validationTypeKey,
                                                    @WebParam(name = "valueTypeKey") String valueTypeKey,
                                                    @WebParam(name = "valueInfo") ValueInfo valueInfo,
                                                    @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Creates a new Value. The Value Id, Value Type, and Meta information may
     * not be set in the supplied data.
     *
     * @param valueTypeKey the identifier for the Type of
     *                     the new Value
     * @param valueInfo    the data with which to create the
     *                     Value
     * @param contextInfo  information containing the principalId and
     *                     locale information about the caller of service operation
     * @return the new Value
     * @throws DoesNotExistException     valueTypeKey does not exist or is not
     *                                   supported
     * @throws InvalidParameterException valueInfo or
     *                                   contextInfo is not valid
     * @throws MissingParameterException valueTypeKey, valueInfo or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     * @throws ReadOnlyException         an attempt at supplying information
     *                                   designated as read-only
     * @throws DataValidationErrorException supplied data is invalid
     */
    public ValueInfo createValue(@WebParam(name = "valueTypeKey") String valueTypeKey,
                                 @WebParam(name = "valueInfo") ValueInfo valueInfo,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            DataValidationErrorException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException;

    /**
     * Updates an existing Value. The Value Id,
     * Type, and Meta information may not be changed.
     *
     * @param valueId     the identifier for the Value
     *                    to be updated
     * @param valueInfo   the new data for the Value
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the updated Value
     * @throws DataValidationErrorException supplied data is invalid
     * @throws DoesNotExistException        valueId not found
     * @throws InvalidParameterException    valueInfo or
     *                                      contextInfo is not valid
     * @throws MissingParameterException    valueId, valueInfo, or contextInfo is missing or null
     * @throws OperationFailedException     unable to complete request
     * @throws PermissionDeniedException    an authorization failure occurred
     * @throws ReadOnlyException            an attempt at changing information
     *                                      designated as read-only
     * @throws VersionMismatchException     optimistic locking failure or
     *                                      the action was attempted on an out of date version
     */
    public ValueInfo updateValue(@WebParam(name = "valueId") String valueId,
                                 @WebParam(name = "valueInfo") ValueInfo valueInfo,
                                 @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DataValidationErrorException,
            DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException,
            ReadOnlyException,
            VersionMismatchException;


    /**
     * Deletes an existing Value.
     *
     * @param valueId     the identifier for the Value
     *                    to be deleted
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return the status of the delete operation. This must always be
     *         true.
     * @throws DoesNotExistException     valueId not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException valueId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public StatusInfo deleteValue(@WebParam(name = "valueId") String valueId,
                                  @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws DoesNotExistException,
            InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;


    /**
     * Retrieves a list of values associated with the given particular parameter
     *
     * @param parameterId the identifier for the parameter associated with the values that will be returned.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return A list of values associated with the given parameter
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterId or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> getValuesByParameter(@WebParam(name = "parameterId") String parameterId,
                                                @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of values associated with a particular parameter
     * that are applicable based on the evaluation of the given person.  This will
     * restrict the values returned to those with a population in which the given person is a member.
     * Values that do not have an associated population will also be returned.
     *
     * The relevant values must also have rules that are either null or evaluate to true.
     *
     * @param parameterId the identifier for the parameter associated with the values that will be returned.
     * @param personId the identifier for the person that will be used for the evaluation.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return A List of applicable values.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterId, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValuesByParameterAndPerson(@WebParam(name = "parameterId") String parameterId,
                                                              @WebParam(name = "personId") String personId,
                                                              @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;

    /**
     * Retrieves a list of values associated with a particular parameter
     * that are applicable based on the evaluation of the given person, atp, and date. This will
     * restrict the values returned to those with a population in which the given person is a member and that
     * match the given atp and date.
     * Values that do not have an associated population will also be returned, assuming that the other criteria match
     * those that are given.
     *
     * The relevant values must also have rules that are either null or evaluate to true.
     *
     * The date parameter restricts the values returned to those with the smallest onDate field
     * that is greater than or equal to the given date.
     *
     * @param parameterId the identifier for the parameter associated with the values that will be returned.
     * @param personId the identifier for the person that will be used for the evaluation.
     * @param atpId the identifier for the atp that will be used for the evaluation
     * @param onDate the date that will be used for the evaluation.
     * @param contextInfo information containing the principalId and
     *                    locale information about the caller of service operation
     * @return A List of applicable values.
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException parameterId, personId, or
     *                                   contextInfo is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public List<ValueInfo> evaluateValuesByParameterAndPersonAndAtpAndOnDate(@WebParam(name = "parameterId") String parameterId,
                                                                             @WebParam(name = "personId") String personId,
                                                                             @WebParam(name = "atpId") String atpId,
                                                                             @WebParam(name = "onDate") Date onDate,
                                                                             @WebParam(name = "contextInfo") ContextInfo contextInfo)
            throws InvalidParameterException,
            MissingParameterException,
            OperationFailedException,
            PermissionDeniedException;
}