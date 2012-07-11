package com.tekelec.nanjing.stages.example;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.tekelec.nanjing.stages.AbstractTask;

import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

/**
 * User: Sun Ning
 * Date: 7/16/11
 * Time: 9:28 PM
 */
public class DataParsingTask extends AbstractTask {

    private InputStream inputData;

    public DataParsingTask(InputStream inputData) {
        this.inputData = inputData;
    }

    @Override
    protected void doRun() throws XMLStreamException {
        System.out.println("start to process xml");
        XMLEventReader xer = XMLInputFactory.newFactory().createXMLEventReader(inputData);
        while(xer.hasNext()) {
            XMLEvent xe = xer.nextEvent();

            if (xe.getEventType() == XMLEvent.START_ELEMENT) {
                StartElement se = xe.asStartElement();
                if (se.getName().toString().equals("gd:rating")) {
                    System.out.println(se.getAttributeByName(new QName("average")));
                }
            }
        }
        System.out.println("finished process xml");
    }

    @Override
    protected void onTaskSuccess() {
        super.onTaskSuccess();
        getAttribute(Main.COUNT_DOWN_LATCH, CountDownLatch.class).countDown();
    }
}
