
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1.2
 * Tue Jan 06 16:57:13 EST 2009
 * Generated source version: 2.1.2
 */

@XmlRootElement(name = "fetchAgendaInfoResponse", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAgendaInfoResponse", namespace = "http://student.kuali.org/wsdl/brms/RuleManagement")

public class FetchAgendaInfoResponse {

    @XmlElement(name = "return")
    private org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO _return;

    public org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO getReturn() {
        return this._return;
    }

    public void setReturn(org.kuali.student.rules.rulemanagement.dto.AgendaInfoDTO new_return)  {
        this._return = new_return;
    }

}

