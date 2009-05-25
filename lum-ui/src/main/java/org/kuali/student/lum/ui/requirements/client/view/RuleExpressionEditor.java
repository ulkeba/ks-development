package org.kuali.student.lum.ui.requirements.client.view;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.Controller;
import org.kuali.student.common.ui.client.mvc.Model;
import org.kuali.student.common.ui.client.mvc.ModelRequestCallback;
import org.kuali.student.common.ui.client.mvc.ViewComposite;
import org.kuali.student.common.ui.client.widgets.KSButton;
import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextArea;
import org.kuali.student.common.ui.client.widgets.list.ListItems;
import org.kuali.student.common.ui.client.widgets.table.Node;
import org.kuali.student.lum.ui.requirements.client.RulesUtilities;
import org.kuali.student.lum.ui.requirements.client.controller.PrereqManager.PrereqViews;
import org.kuali.student.lum.ui.requirements.client.model.PrereqInfo;
import org.kuali.student.lum.ui.requirements.client.model.ReqComponentVO;
import org.kuali.student.lum.ui.requirements.client.model.RuleExpressionParser;
import org.kuali.student.lum.ui.requirements.client.model.StatementVO;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class RuleExpressionEditor extends ViewComposite {

    //view's widgets
    private Panel mainPanel = new SimplePanel();
    private KSTextArea taExpression = new KSTextArea();
    private KSButton btnPreview = new KSButton("Preview");
    private Panel pnlMissingExpressions = new VerticalPanel();
    private RuleTable ruleTable = new RuleTable();
    private KSButton btnDone = new KSButton("Done");
    private KSButton btnCancel = new KSButton("Cancel");
    private HTML htmlErrorMessage = new HTML();
    private HTML htmlMissingExpressionNotice = new HTML();


    // views's data
    private Model<PrereqInfo> model;
    
    // helper object
    private RuleExpressionParser ruleExpressionParser = new RuleExpressionParser();

    public RuleExpressionEditor(Controller controller) {
        super(controller, "Rule Expression Editor");
        super.initWidget(mainPanel);
        setupHandlers();
    }
    
    @Override
    public void beforeShow() {
        getController().requestModel(PrereqInfo.class, new ModelRequestCallback<PrereqInfo>() {
            public void onModelReady(Model<PrereqInfo> theModel) {
                model = theModel;    
            }

            public void onRequestFail(Throwable cause) {
                throw new RuntimeException("Unable to connect to model", cause);
            }
        }); 
        redraw();
    }
    
    private void setupHandlers() {
        taExpression.addKeyUpHandler(new KeyUpHandler() {
            public void onKeyUp(KeyUpEvent event) {
                // escape error keys
                if(event.getNativeKeyCode() == 37 
                        ||event.getNativeKeyCode() == 38
                        ||event.getNativeKeyCode() == 39
                        ||event.getNativeKeyCode() == 40){
                        return;
                }
                String expression = taExpression.getText();
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.setExpression(expression);
            }
        });
        btnPreview.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                String expression = null;
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                expression = prereqInfo.getExpression();
                prereqInfo.setPreviewedExpression(expression);
                redraw();
            }
        });
        
        btnDone.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                List<String> errorMessages = new ArrayList<String>();
                List<ReqComponentVO> rcs = 
                    (prereqInfo.getStatementVO() == null ||
                            prereqInfo.getStatementVO().getAllReqComponentVOs() == null)?
                                    new ArrayList<ReqComponentVO>() :
                                        prereqInfo.getStatementVO().getAllReqComponentVOs();
                ruleExpressionParser.setExpression(prereqInfo.getExpression());
                boolean validExpression = ruleExpressionParser.validateExpression(errorMessages, rcs);
                List<ReqComponentVO> missingRCs = new ArrayList<ReqComponentVO>();
                ruleExpressionParser.checkMissingRCs(missingRCs, rcs);
                if (validExpression && missingRCs.isEmpty()) {
                    StatementVO newStatementVO = ruleExpressionParser.parseExpressionIntoStatementVO(
                            prereqInfo.getExpression(),
                            prereqInfo.getStatementVO().getAllReqComponentVOs());
                    prereqInfo.setStatementVO(newStatementVO);
                    prereqInfo.setPreviewedExpression(null);
                    prereqInfo.getEditHistory().save(prereqInfo.getStatementVO());
                    getController().showView(PrereqViews.COMPLEX);
                } else {
                    String expression = prereqInfo.getExpression();
                    prereqInfo.setPreviewedExpression(expression);
                    redraw();
                }
            }
        });
        
        btnCancel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
                prereqInfo.setPreviewedExpression(null);
                getController().showView(PrereqViews.COMPLEX);
            }
        });
    }
    
    private void redraw() {
        FlexTable flexTable = new FlexTable();
        int rowNum = 0;
        // row 0
        flexTable.setWidget(rowNum,0,new KSLabel("Manually Edit Logic"));
        // row 1
        Label horizontalSpacer = new Label();
        horizontalSpacer.setWidth("5px");
        rowNum++;
        taExpression.getElement().getStyle().setProperty("width", "350");
        flexTable.setWidget(rowNum,0,taExpression);
        taExpression.setHeight("100px");
        flexTable.setWidget(rowNum,1,horizontalSpacer);
        flexTable.setWidget(rowNum,2,htmlErrorMessage);
        // row 2
        rowNum++;
        flexTable.setWidget(rowNum, 0, btnPreview);
        btnPreview.addStyleName("KS-Rules-Tight-Grey-Button");
        // row 3
        rowNum++;
        SimplePanel verticalSpacer = null;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        flexTable.setWidget(rowNum, 0, verticalSpacer);
        // row 4
        rowNum++;
        flexTable.setWidget(rowNum, 0, pnlMissingExpressions);
        pnlMissingExpressions.getElement().getStyle().setProperty("borderWidth", "1px");
        flexTable.setWidget(rowNum, 1, horizontalSpacer);
        flexTable.setWidget(rowNum, 2, htmlMissingExpressionNotice);
        // row 5
        rowNum++;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        flexTable.setWidget(rowNum, 0, verticalSpacer);
        // row 6
        rowNum++;
        flexTable.setWidget(rowNum, 0, new KSLabel("Preview"));
        // row 7
        rowNum++;
        ruleTable.setShowControls(false);
        flexTable.setWidget(rowNum, 0, ruleTable);
        flexTable.getFlexCellFormatter().setColSpan(rowNum, 0, 100);
        // row 8
        rowNum++;
        verticalSpacer = new SimplePanel();
        verticalSpacer.setHeight("30px");
        flexTable.setWidget(rowNum, 0, verticalSpacer);
        // row 9
        rowNum++;
        HorizontalPanel buttonsPanel = new HorizontalPanel();
        btnCancel.addStyleName("KS-Rules-Tight-Grey-Button");
        buttonsPanel.add(btnCancel);
        btnDone.addStyleName("KS-Rules-Tight-Grey-Button");
        buttonsPanel.add(btnDone);
        flexTable.setWidget(rowNum, 0, buttonsPanel);
        
        PrereqInfo prereqInfo = RulesUtilities.getPrereqInfoModelObject(model);
        if (prereqInfo != null) {
            taExpression.setText("");
            if (prereqInfo.getExpression() != null) {
                taExpression.setText(prereqInfo.getExpression());
            }
            if (prereqInfo.getPreviewedExpression() != null) {
                String previewExpression = prereqInfo.getPreviewedExpression();
                previewExpression = previewExpression.replaceAll("\n", " ");
                previewExpression = previewExpression.replaceAll("\r", " ");
                List<ReqComponentVO> rcs = 
                    (prereqInfo.getStatementVO() == null ||
                            prereqInfo.getStatementVO().getAllReqComponentVOs() == null)?
                                    new ArrayList<ReqComponentVO>() :
                                        prereqInfo.getStatementVO().getAllReqComponentVOs();
                List<String> errorMessages = new ArrayList<String>();
                List<ReqComponentVO> missingRCs = new ArrayList<ReqComponentVO>();
                ruleExpressionParser.setExpression(previewExpression);
                boolean validExpression = ruleExpressionParser.validateExpression(errorMessages, rcs);
                htmlErrorMessage.setHTML("");
                ruleTable.clear();
                if (!validExpression) {
                    showErrors(errorMessages);
                } else {
                    Node tree = ruleExpressionParser.parseExpressionIntoTree(
                        previewExpression, rcs);
                    if (tree != null) {
                        ruleTable.buildTable(tree);
                    }
                }
                ruleExpressionParser.checkMissingRCs(missingRCs, rcs);
                showMissingRCs(missingRCs);
            }
        }
        
        flexTable.addStyleName("Content-Margin");
        mainPanel.clear();
        mainPanel.add(flexTable);
    }
    
    private void showMissingRCs(List<ReqComponentVO> missingRCs) {
        VerticalPanel pnlRCList = new VerticalPanel();
        
        pnlMissingExpressions.clear();
        htmlMissingExpressionNotice.setHTML("");
        
        pnlMissingExpressions.add(new KSLabel("Rule(s) missing from expression"));
        if (missingRCs != null) {
            for (ReqComponentVO rc : missingRCs) {
                HorizontalPanel pnlRcListRow = new HorizontalPanel();
                KSLabel rcLabel = null;
                HTML rcText = new HTML();
                if (rc.getGuiReferenceLabelId() != null) {
                    rcLabel = new KSLabel(rc.getGuiReferenceLabelId());
                    rcLabel.getElement().getStyle().setProperty("fontWeight", "bold");
                    rcLabel.getElement().getStyle().setProperty("background", "#E0E0E0");
                    pnlRcListRow.add(rcLabel);
                }
                pnlRcListRow.getElement().getStyle().setProperty("padding", "3px");
                rcText.setHTML(rc.toString());
                rcText.getElement().getStyle().setProperty("color", "red");
                pnlRcListRow.add(rcText);
                pnlRCList.add(pnlRcListRow);
            }
        }
        pnlMissingExpressions.add(pnlRCList);
        
        if (missingRCs != null && !missingRCs.isEmpty()) {
            StringBuilder sb = new StringBuilder(
            		"All rules must be included <BR/>");
            sb.append("in the Logic Expression.");
            htmlMissingExpressionNotice.getElement().getStyle().setProperty("color", "red");
            htmlMissingExpressionNotice.setHTML(sb.toString());
        }
    }
    
    private void showErrors(List<String> errorMessages) {
        if (errorMessages != null) {
            StringBuilder sb = new StringBuilder("Error Message: <BR>");
            for (String errorMessage : errorMessages) {
                sb.append(errorMessage + ",<BR>");
            }
            htmlErrorMessage.getElement().getStyle().setProperty("color", "red");
            htmlErrorMessage.setHTML(sb.toString());
        }
    }
    
    class RCTableItems implements ListItems {
        private List<ReqComponentVO> rcs = new ArrayList<ReqComponentVO>();
        private final String ATTR_KEY_LABEL = "Label";
        private final String ATTR_KEY_DESCR = "Description";
        
        public List<ReqComponentVO> getRcs() {
            return rcs;
        }

        public void setRcs(List<ReqComponentVO> rcs) {
            this.rcs = rcs;
        }
        
        public ReqComponentVO lookup(String id) {
            ReqComponentVO result = null;
            if (rcs != null && !rcs.isEmpty()) {
                for (ReqComponentVO rc : rcs) {
                    if (rc.getGuiReferenceLabelId() != null &&
                            rc.getGuiReferenceLabelId().equals(id)) {
                        result = rc;
                    }
                }
            }
            return result;
        }

        @Override
        public List<String> getAttrKeys() {
            List<String> attrKeys = new ArrayList<String>();
            attrKeys.add(ATTR_KEY_LABEL);
            attrKeys.add(ATTR_KEY_DESCR);
            return attrKeys;
        }

        @Override
        public String getItemAttribute(String id, String attrkey) {
            String value = "";
            ReqComponentVO rc = lookup(id);
            if (attrkey != null) {
                if (attrkey.equals(ATTR_KEY_LABEL)) {
                    value = rc.getGuiReferenceLabelId();
                } else if (attrkey.equals(ATTR_KEY_DESCR)) {
                    value = rc.toString();
                }
            }
            return value;
        }

        @Override
        public int getItemCount() {
            int count = (rcs == null)? 0 : rcs.size();
            return count;
        }

        @Override
        public List<String> getItemIds() {
            List<String> itemIds = new ArrayList<String>();
            if (rcs != null && !rcs.isEmpty()) {
                for (ReqComponentVO rc : rcs) {
                    itemIds.add(rc.getGuiReferenceLabelId());
                }
            }
            return itemIds;
        }

        @Override
        public String getItemText(String id) {
            String itemText = null;
            ReqComponentVO rc = lookup(id);
            itemText = rc.getGuiReferenceLabelId() + ", " + rc.toString();
            return itemText;
        }
    }
    
}
