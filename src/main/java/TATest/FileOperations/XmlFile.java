package TATest.FileOperations;

import TATest.Action;
import TATest.Enums.Actions;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

public class XmlFile extends DefaultHandler implements ConfigFile {
    private ArrayList<Action> actions = new ArrayList<>();


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("action")) {
            Action action = new Action(Actions.valueOf(attributes.getValue("name").toLowerCase()));

            if (attributes.getValue("param") != null)
                action.setParameter(attributes.getValue("param"));

            if (attributes.getValue("value") != null)
                action.setValue(attributes.getValue("value"));
            actions.add(action);
        }
        super.startElement(uri, localName, qName, attributes);
    }

    @Override
    public ArrayList<Action> getActionsFromFile(String textFile) {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(true);
        factory.setNamespaceAware(false);
        SAXParser parser;
        try {
            parser = factory.newSAXParser();
            InputSource xmlData = new InputSource(new StringReader(textFile));
            parser.parse(xmlData, this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return actions;
    }
}
