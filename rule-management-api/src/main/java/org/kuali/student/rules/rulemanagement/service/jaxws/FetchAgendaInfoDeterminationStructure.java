
package org.kuali.student.rules.rulemanagement.service.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 2.1
 * Wed Sep 10 10:18:59 EDT 2008
 * Generated source version: 2.1
 */

@XmlRootElement(name = "fetchAgendaInfoDeterminationStructure", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fetchAgendaInfoDeterminationStructure", namespace = "http://student.kuali.org/poc/wsdl/brms/rulemanagement")

public class FetchAgendaInfoDeterminationStructure {

    @XmlElement(name = "agendaTypeKey")
    private java.lang.String agendaTypeKey;

    public java.lang.String getAgendaTypeKey() {
        return this.agendaTypeKey;
    }

    public void setAgendaTypeKey(java.lang.String newAgendaTypeKey)  {
        this.agendaTypeKey = newAgendaTypeKey;
    }

}

