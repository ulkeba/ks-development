/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.internal.common.statement.propositions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class AverageProposition<E extends Number> extends SumProposition<E> {
	private BigDecimal average;
    private BigDecimal listSize;
    
    public final static String DEFAULT_SUCCESS_MESSAGE = "Average constraint fulfilled";
    public final static String DEFAULT_FAILURE_MESSAGE = "Average of #average# is short by #needed#";
    
    public final static String AVERAGE_REPORT_TEMPLATE_TOKEN = "average";
    public final static String NEEDED_REPORT_TEMPLATE_TOKEN = "needed";
    
	public AverageProposition(String id, 
							  String propositionName, 
    						  ComparisonOperator operator, 
    						  BigDecimal expectedValue, 
    						  List<E> factSet,
    						  RulePropositionDTO ruleProposition) {
    	super(id, propositionName, operator, expectedValue, factSet, ruleProposition);
    	super.propositionType = PropositionType.AVERAGE;
    	if (factSet == null || factSet.size() == 0) {
    		throw new IllegalArgumentException("Fact set cannot be null");
    	}
    	listSize = new BigDecimal(factSet.size());
    }

    @Override
    public Boolean apply() {
        average = sum().divide(listSize);

        result = checkTruthValue(average, super.expectedValue);

        resultValues = new ArrayList<BigDecimal>();
        resultValues.add(average);

        return result;
    }

    @Override
    public PropositionReport buildReport() {
        // TODO: Use the operator to compute exact message
        addMessageToken(AVERAGE_REPORT_TEMPLATE_TOKEN, average.toString());
        BigDecimal needed = expectedValue.subtract(average);
        addMessageToken(NEEDED_REPORT_TEMPLATE_TOKEN, needed.toString());
        buildDefaultReport(DEFAULT_SUCCESS_MESSAGE, DEFAULT_FAILURE_MESSAGE);
		return report;
    }
}
