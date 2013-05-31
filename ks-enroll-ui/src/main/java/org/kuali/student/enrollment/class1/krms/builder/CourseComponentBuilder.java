package org.kuali.student.enrollment.class1.krms.builder;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class2.courseoffering.service.impl.ManageSOCViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.core.versionmanagement.dto.VersionDisplayInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.CourseService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2013/03/01
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public class CourseComponentBuilder implements ComponentBuilder<EnrolPropositionEditor> {

    private final static Logger LOG = Logger.getLogger(CourseComponentBuilder.class);
    private CourseService courseService;
    private CluService cluService;

    private static final String CLU_KEY = "kuali.term.parameter.type.course.clu.id";

    @Override
    public List<String> getComponentIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resolveTermParameters(EnrolPropositionEditor propositionEditor, Map<String, String> termParameters) {
        String courseId = termParameters.get(CLU_KEY);
        if (courseId != null) {
            try {
                VersionDisplayInfo versionInfo = this.getCluService().getCurrentVersion(CluServiceConstants.CLU_NAMESPACE_URI, courseId, null);
                CourseInfo courseInfo = this.getCourseService().getCourse(versionInfo.getId(), ContextUtils.getContextInfo());
                propositionEditor.setCourseInfo(courseInfo);
            } catch (DoesNotExistException e) {
                throw new RuntimeException("Clu does not exist");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public Map<String, String> buildTermParameters(EnrolPropositionEditor propositionEditor) {
        Map<String, String> termParameters = new HashMap<String, String>();
        if (propositionEditor.getCourseInfo() != null){

            termParameters.put(CLU_KEY, propositionEditor.getCourseInfo().getVersion().getVersionIndId());
        }
        return termParameters;
    }

    @Override
    public void onSubmit(EnrolPropositionEditor propositionEditor) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected CourseService getCourseService() {
        if (courseService == null) {
            courseService = CourseOfferingResourceLoader.loadCourseService();
        }
        return courseService;
    }

    protected CluService getCluService() {
        if (cluService == null) {
            cluService = CourseOfferingResourceLoader.loadCluService();
        }
        return cluService;
    }
}
