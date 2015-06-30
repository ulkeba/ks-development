/**
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
 *
 * Created by Charles on 2/21/13
 */
package org.kuali.student.enrollment.class2.courseoffering.service.facade.issue;

import java.util.HashSet;
import java.util.Set;

/**
 * A list of RGs that could not have been generated by existing AOCs
 *
 * @author Kuali Student Team
 */
public class InvalidRegGroupSubissue implements FormatOfferingAutogenSubIssue {
    String courseOfferingId;
    String formatOfferingId;
    Set<String> regGroupIds;

    public InvalidRegGroupSubissue(String courseOfferingId, String formatOfferingId) {
        this.courseOfferingId = courseOfferingId;
        this.formatOfferingId = formatOfferingId;
        this.regGroupIds = new HashSet<String>();
    }

    @Override
    public String getName() {
        return FormatOfferingAutogenSubIssue.INVALID_REG_GROUPS;
    }

    public String getCourseOfferingId() {
        return courseOfferingId;
    }

    public Set<String> getRegGroupIds() {
        return regGroupIds;
    }

    public String getFormatOfferingId() {
        return formatOfferingId;
    }
}
