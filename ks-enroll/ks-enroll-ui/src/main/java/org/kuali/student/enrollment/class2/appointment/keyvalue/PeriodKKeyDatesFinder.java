package org.kuali.student.enrollment.class2.appointment.keyvalue;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.ConcreteKeyValue;
import org.kuali.rice.core.api.util.KeyValue;
import org.kuali.rice.krad.uif.control.UifKeyValuesFinderBase;
import org.kuali.rice.krad.uif.view.ViewModel;

import org.kuali.student.enrollment.acal.constants.AcademicCalendarServiceConstants;
import org.kuali.student.enrollment.acal.dto.KeyDateInfo;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.appointment.form.RegistrationWindowsManagementForm;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.test.utilities.TestHelper;

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeriodKKeyDatesFinder extends UifKeyValuesFinderBase implements Serializable {
    private static final long serialVersionUID = 1L;
    private transient AcademicCalendarService acalService;

    @Override
    public List<KeyValue> getKeyValues(ViewModel model) {
        List<KeyValue> keyValues = new ArrayList<KeyValue>();
        RegistrationWindowsManagementForm form = (RegistrationWindowsManagementForm)model;
        List<KeyDateInfo> periodMilestones = form.getPeriodMilestones();
        if (periodMilestones.isEmpty()){
            ContextInfo context = TestHelper.getContext1();

            try {
                TermInfo term = form.getTermInfo();
                if (term.getId() != null && !term.getId().isEmpty()) {
                    List<KeyDateInfo> keyDateInfoList = getAcalService().getKeyDatesForTerm(term.getId(), context);
                    for (KeyDateInfo keyDateInfo : keyDateInfoList) {
                        if (keyDateInfo.getTypeKey().equals("kuali.atp.milestone.RegistrationPeriod")){
                            System.out.println(">>>find reg Period while id ="+keyDateInfo.getId()+", and name ="+keyDateInfo.getName() );
                            periodMilestones.add (keyDateInfo);
                        }
                    }
                }
            }catch (DoesNotExistException dnee){
                System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get DoesNotExistException:  "+dnee.toString());
            }catch (InvalidParameterException ipe){
                System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get InvalidParameterException:  "+ipe.toString());
            }catch (MissingParameterException mpe){
                System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get MissingParameterException:  "+mpe.toString());
            }catch (OperationFailedException ofe){
                System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get OperationFailedException:  "+ofe.toString());
            }catch (PermissionDeniedException pde){
                System.out.println("call getAcalService().getKeyDatesForTerm(term.getId(), context), and get PermissionDeniedException:  "+pde.toString());
            }
        }
        if (!periodMilestones.isEmpty()){
            for (KeyDateInfo period : periodMilestones) {
                ConcreteKeyValue keyValue = new ConcreteKeyValue();
                keyValue.setKey(period.getId());
                keyValue.setValue(period.getName());
                keyValues.add(keyValue);
            }
        }
        return keyValues;
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(AcademicCalendarServiceConstants.NAMESPACE, AcademicCalendarServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return this.acalService;
    }
}
