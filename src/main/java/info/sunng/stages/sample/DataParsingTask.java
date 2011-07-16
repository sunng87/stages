package info.sunng.stages.sample;

import javax.xml.namespace.QName;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import info.sunng.stages.AbstractTask;
import info.sunng.stages.TaskException;

import java.io.FileReader;
import java.io.InputStream;

/**
 * User: Sun Ning<classicning@gmail.com>
 * Date: 7/16/11
 * Time: 9:28 PM
 */
public class DataParsingTask extends AbstractTask {

    private InputStream inputData;

    public DataParsingTask(InputStream inputData) {
        this.inputData = inputData;
    }

    @Override
    protected void doRun() throws TaskException {
        try {
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
        } catch (XMLStreamException e) {
            throw new TaskException(e);
        }
    }

    @Override
    protected void onTaskSuccess() {
        super.onTaskSuccess();
        Main.COUNT_DOWN_LATCH.countDown();
    }
}
