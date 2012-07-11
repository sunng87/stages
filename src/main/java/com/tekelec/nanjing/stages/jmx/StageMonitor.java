package com.tekelec.nanjing.stages.jmx;

import com.tekelec.nanjing.stages.Stage;
import com.tekelec.nanjing.stages.StageManager;

import javax.management.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StageMonitor implements DynamicMBean {

    private StageManager sm;

    public StageMonitor(StageManager sm) {

    }

    private String[] getStages() {
        List<String> names = new ArrayList<String>();
        for (Stage s : this.sm.getStages()) {
            names.add(s.getName());
        }
        return names.toArray(new String[0]);
    }

    private int getPendingTasks(String stage) {
        return this.sm.getStage(stage).pendingTasks();
    }

    @Override
    public Object getAttribute(String attribute)
            throws AttributeNotFoundException, MBeanException,
            ReflectionException {
        return this.getPendingTasks(attribute);
    }

    @Override
    public void setAttribute(Attribute attribute)
            throws AttributeNotFoundException, InvalidAttributeValueException,
            MBeanException, ReflectionException {
        // unwritable
    }

    @Override
    public AttributeList getAttributes(String[] attributes) {
        AttributeList list = new AttributeList();
        for (String a : attributes) {
            list.add(new Attribute(a, getPendingTasks(a)));
        }
        return list;
    }

    @Override
    public AttributeList setAttributes(AttributeList attributes) {
        // unwritable
        return new AttributeList();
    }

    @Override
    public Object invoke(String actionName, Object[] params, String[] signature)
            throws MBeanException, ReflectionException {
        // donothing
        return null;
    }

    @Override
    public MBeanInfo getMBeanInfo() {
        String[] stages = this.getStages();
        MBeanAttributeInfo[] attributeInfo = new MBeanAttributeInfo[stages.length];
        for (int i=0; i<attributeInfo.length; i++) {
            attributeInfo[i] = new MBeanAttributeInfo(stages[i], "java.lang.Long",
                    "", true, false, false);
        }
        return new MBeanInfo(this.getClass().getName(),
                "MBean that monitors pending tasks of each stage.",
                attributeInfo,
                null, // constructors
                null, // operations
                null // notifications
        );

    }
}

