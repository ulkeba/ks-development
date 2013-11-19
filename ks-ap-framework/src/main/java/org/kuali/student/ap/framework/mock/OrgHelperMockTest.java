package org.kuali.student.ap.framework.mock;

import org.kuali.student.ap.framework.context.OrgHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.infc.SearchResultRow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: johglove
 * Date: 11/19/13
 * Time: 9:34 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrgHelperMockTest implements OrgHelper {
    @Override
    public HashMap<String, List<OrgInfo>> getOrgTypeCache() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets the cache to a set value
     */
    @Override
    public void setOrgTypeCache(HashMap<String, List<OrgInfo>> orgTypeCache) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets information for entries matching the passed in params.
     *
     * @return All entries found.
     */
    @Override
    public List<OrgInfo> getOrgInfo(String param, String searchRequestKey, String paramKey, ContextInfo context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets map of subject areas for the organizations
     *
     * @return Map of subjects
     */
    @Override
    public Map<String, String> getSubjectAreas() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Map<String, String> getTrimmedSubjectAreas() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Gets an individual value of a search result entry.
     *
     * @return Entry's value
     */
    @Override
    public String getCellValue(SearchResultRow row, String key) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
