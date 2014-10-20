/**
 * Copyright 2012 The Kuali Foundation Licensed under the
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
 * Created by David Yin on 8/1/12
 */
package org.kuali.student.enrollment.class2.courseoffering.keyvalue;

import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingManagementUtil;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a key value finder for Milestone types for SeatPool expirations
 *
 * @author Kuali Student Team
 */
public class SeatPoolExpirationMilestoneTypeKeyValues extends UifKeyValuesFinderBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public List<KeyValue> getKeyValues(ViewModel viewModel) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        keyValues.add(new ConcreteKeyValue("NONE", "NONE"));
        try {
            List<TypeInfo> typeInfos = CourseOfferingManagementUtil.getTypeService().getTypesForGroupType(AtpServiceConstants.MILESTONE_SEATPOOL_GROUPING_TYPE_KEY, ContextUtils.createDefaultContextInfo());

            if (typeInfos != null) {
                for (TypeInfo typeInfo : typeInfos) {
                    keyValues.add(new ConcreteKeyValue(typeInfo.getKey(), typeInfo.getName()));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error getting Seat Pool Expiration Milestone Types", e);
        }

        return keyValues;
    }
}