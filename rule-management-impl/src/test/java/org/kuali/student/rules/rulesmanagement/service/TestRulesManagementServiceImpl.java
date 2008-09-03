package org.kuali.student.rules.rulesmanagement.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.kuali.student.poc.common.test.spring.AbstractServiceTest;
import org.kuali.student.poc.common.test.spring.Client;
import org.kuali.student.poc.common.ws.exceptions.DoesNotExistException;
import org.kuali.student.poc.common.ws.exceptions.InvalidParameterException;
import org.kuali.student.poc.common.ws.exceptions.MissingParameterException;
import org.kuali.student.poc.common.ws.exceptions.OperationFailedException;

public class TestRulesManagementServiceImpl extends AbstractServiceTest {
	@Client(value = "org.kuali.student.rules.rulesmanagement.service.impl.RulesManagementServiceImpl", port = "8181")
	public RulesManagementService client;


	@Test
	public void testFetchBusinessRuleEnglish() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
		String englishValue = client.fetchBusinessRuleEnglish("1");
		assertEquals("Rule1", englishValue);
	}

    @Test
    public void testFindAgendaTypes() throws OperationFailedException, DoesNotExistException, InvalidParameterException, MissingParameterException {
        List<String> agendaTypes = client.findAgendaTypes();
        assertTrue(agendaTypes.size()== 2);
        assertTrue(agendaTypes.contains("kuali.studentDropsCourse"));
        assertTrue(agendaTypes.contains("kuali.studentEnrollsInCourse"));
    }

}
