/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.common.ui.client.widgets.counting.impl;

import org.kuali.student.common.ui.client.widgets.KSLabel;
import org.kuali.student.common.ui.client.widgets.KSTextBox;
import org.kuali.student.common.ui.client.widgets.counting.KSTextBoxAbstract;


import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.VerticalPanel;
import static com.google.gwt.event.dom.client.KeyCodes.*;



/**
 * This is a description of what this class does - Gary Struthers don't forget to fill this in. 
 * 
 * @author Kuali Student Team (gstruthers@berkeley.edu)
 *
 */
public class KSTextBoxImpl extends KSTextBoxAbstract {
    private final VerticalPanel panel = new VerticalPanel();
    private KSTextBox textBox = new KSTextBox();
    private KSLabel label = new KSLabel();
    private String label2ndPart = " Characters remaining | "; //TODO get from resource
    private String label4thPart = " Character Maximum"; //TODO get from resource
    private int maxTextLength = -1;

    private final KeyDownHandler keyDownHandler = new KeyDownHandler(){

        public void onKeyDown(KeyDownEvent event) {
            String text = textBox.getText();
            if(text.length() >= maxTextLength){
                int keyCode = event.getNativeKeyCode();
                if ((keyCode != (char) KEY_BACKSPACE) && (keyCode != (char) KEY_CTRL)
                        && (keyCode != (char) KEY_DELETE) && (keyCode != (char) KEY_DOWN)
                        && (keyCode != (char) KEY_END) && (keyCode != (char) KEY_ENTER)
                        && (keyCode != (char) KEY_ESCAPE)  && (keyCode != (char) KEY_HOME) 
                        && (keyCode != (char) KEY_LEFT) && (keyCode != (char) KEY_PAGEDOWN)
                        && (keyCode != (char) KEY_PAGEUP) && (keyCode != (char) KEY_RIGHT)
                        && (keyCode != (char) KEY_SHIFT)
                        && (keyCode != (char) KEY_TAB)&& (keyCode != (char) KEY_UP)) {
                    textBox.cancelKey();
                } 
            } 
        }            
    };
    private final KeyUpHandler keyUpHandler = new KeyUpHandler(){
        public void onKeyUp(KeyUpEvent event) {
            label.setText(textBox.getText().length() + label2ndPart + maxTextLength + label4thPart); 
        }
    };

    public KSTextBoxImpl() {
        super();
        initWidget(panel);
        textBox.addKeyDownHandler(keyDownHandler);
        textBox.addKeyUpHandler(keyUpHandler);
        panel.add(textBox);
        panel.add(label);
    }



    /**
     * Maximum number of characters application allows for this field.
     * @return the maxTextLength
     */
    @Override
    public int getMaxTextLength() {
        return maxTextLength;
    }

    /**
     * Maximum character length application allows in this field.
     * Should be called in the wrapper class constructor
     * @param maxTextLength the maxTextLength to set
     */
    @Override
    protected void setMaxTextLength(int maxTextLength) {
        this.maxTextLength = maxTextLength;
        label.setText(textBox.getText().length() + label2ndPart + maxTextLength + label4thPart);
    }

}
