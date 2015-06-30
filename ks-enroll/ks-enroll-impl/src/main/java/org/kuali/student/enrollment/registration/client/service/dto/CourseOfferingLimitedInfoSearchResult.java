/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by vgadiyak on 7/21/14
 */
package org.kuali.student.enrollment.registration.client.service.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CourseOfferingLimitedInfoSearchResult", propOrder = {
        "courseOfferingId", "courseOfferingCode", "courseOfferingNumber", "courseOfferingSubjectArea"})
public class CourseOfferingLimitedInfoSearchResult implements Serializable{
    private String courseOfferingId;
    private String courseOfferingCode;
    private String courseOfferingNumber;
    private String courseOfferingSubjectArea;

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public void setCourseOfferingId(String courseOfferingId) {
        this.courseOfferingId = courseOfferingId;
    }

    public String getCourseOfferingCode() {
        return courseOfferingCode;
    }

    public void setCourseOfferingCode(String courseOfferingCode) {
        this.courseOfferingCode = courseOfferingCode;
    }

    public String getCourseOfferingNumber() {
        return courseOfferingNumber;
    }

    public void setCourseOfferingNumber(String courseOfferingNumber) {
        this.courseOfferingNumber = courseOfferingNumber;
    }

    public String getCourseOfferingSubjectArea() {
        return courseOfferingSubjectArea;
    }

    public void setCourseOfferingSubjectArea(String courseOfferingSubjectArea) {
        this.courseOfferingSubjectArea = courseOfferingSubjectArea;
    }
}