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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.kuali.student.rules.internal.common.entity.ComparisonOperator;
import org.kuali.student.rules.internal.common.statement.report.PropositionReport;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

/**
 * A constraint that compares a constrained property (fact) to a given criterion. The threshold may be defined as minimum or
 * maximum.
 * 
 * @param <T>
 *            the type being constrained.
 * @author <a href="mailto:randy@berkeley.edu">Randy Ballew</a>
 * @author Kuali Student Team (len.kuali@gmail.com)
 */
public class SimpleComparableProposition<T extends Comparable<T>> extends AbstractProposition<T> {
    // ~ Instance fields --------------------------------------------------------

    T fact;
    List<Boolean> resultValues;
    
    public final static String DEFAULT_SUCCESS_MESSAGE = "Comparison constraint fulfilled";
    public final static String DEFAULT_FAILURE_MESSAGE = "#fact# not #operator# #expectedValue#";

    public final static String FACT_REPORT_TEMPLATE_TOKEN = "fact";

    // ~ Constructors -----------------------------------------------------------

    public SimpleComparableProposition() {
        super();
    }

    public SimpleComparableProposition(String id, String propositionName, 
    		ComparisonOperator operator, T expectedValue, T fact, RulePropositionDTO ruleProposition) {
        super(id, propositionName, PropositionType.SIMPLECOMPARABLE, operator, expectedValue, ruleProposition);

        this.fact = fact;
    }

    // ~ Methods ----------------------------------------------------------------

    /* (non-Javadoc)
    * @see org.kuali.rules.constraint.Constraint#apply()
    */
    @Override
    public Boolean apply() {
        sanityCheck();

        result = checkTruthValue(this.fact, super.expectedValue);

        this.resultValues = new ArrayList<Boolean>();
        this.resultValues.add(result);

        return result;
    }

    /* (non-Javadoc)
     * @see org.kuali.rules.constraint.AbstractConstraint#cacheAdvice(java.lang.String, java.lang.Object[])
     */
    @Override
    public PropositionReport buildReport() {
        String s = getTypeAsString(this.fact);
    	addMessageToken(FACT_REPORT_TEMPLATE_TOKEN, s);
        buildDefaultReport(DEFAULT_SUCCESS_MESSAGE, DEFAULT_FAILURE_MESSAGE);
    	return report;
    }

    private void sanityCheck() {
        if (fact == null)
            throw new IllegalStateException(getClass().getName() + ":  No fact to compare");
    }

    /**
     * @return the fact
     */
    public T getFact() {
        return fact;
    }

    /**
     * @param fact
     *            the fact to set
     */
    public void setFact(T fact) {
        this.fact = fact;
    }

    /**
     * Gets results of proposition computation.
     * 
     * @return Proposition computation results
     */
    public Collection<?> getResultValues() {
    	return this.resultValues;
    }
}
