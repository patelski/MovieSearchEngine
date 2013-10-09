/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package moviesearchengine.tfidf;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * http://docs.oracle.com/javase/tutorial/jaxp/sax/parsing.html
 *
 * @author flavius
 */
public class SAXDocExtraction extends DefaultHandler {

    private Stack stack = new Stack();
    private StringBuilder builder;
    private String id;
    private static HashMap<String, ArrayList<String>> docIdToContent = new HashMap<String, ArrayList<String>>();

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(
            String namespaceURI,
            String localName,
            String qName,
            Attributes atts) throws SAXException {
        StringBuffer buffy = new StringBuffer();
        stack.push(buffy);

        if ("movie".equals(qName)) {
            builder = new StringBuilder();
            id = atts.getValue("id");
        }
    }

    @Override
    public void characters(
            char[] ch,
            int start,
            int length) throws SAXException {
        StringBuffer buffy = (StringBuffer) stack.peek();
        buffy.append(ch, start, length);
    }

    @Override
    public void endElement(
            String uri,
            String localName,
            String qName) throws SAXException {
        StringBuffer buffy = (StringBuffer) stack.pop();
//        System.out.println("The <" + qName + "> contains the following text:"
//                + "\"" + buffy + "\"");

        String s = buffy.toString().trim();
        if (!"".equals(s)) {
            builder.append(s).append(' ');
        }

        if ("movie".equals(qName)) {
            if (builder.length() > 0) {
                ArrayList<String> content = new ArrayList<String>();
                content.add(builder.deleteCharAt(builder.length() - 1).toString());
                docIdToContent.put(id, content);
                System.out.println("Processed movie " + id + ".");
            }
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }

    /**
     * The filename String that you give when you run the application will be
     * converted to a java.io.File URL.
     */
    private static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    static public HashMap<String, ArrayList<String>> extractDocs(String XMLFilename) throws Exception {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xmlReader = saxParser.getXMLReader();
        xmlReader.setContentHandler(new SAXDocExtraction());
        xmlReader.setErrorHandler(new MyErrorHandler(System.err));
        xmlReader.parse(convertToFileURL(XMLFilename));

        return docIdToContent;
    }

    /**
     * Because the default parser only generates exceptions for fatal errors,
     * and because the information about the errors provided by the default
     * parser is somewhat limited, the SAXflvs program defines its own error
     * handling, through the MyErrorHandler class.
     */
    private static class MyErrorHandler implements ErrorHandler {

        private PrintStream out;

        MyErrorHandler(PrintStream out) {
            this.out = out;
        }

        private String getParseExceptionInfo(SAXParseException spe) {
            String systemId = spe.getSystemId();

            if (systemId == null) {
                systemId = "null";
            }

            String info = "URI=" + systemId + " Line="
                    + spe.getLineNumber() + ": " + spe.getMessage();

            return info;
        }

        @Override
        public void warning(SAXParseException spe) throws SAXException {
            out.println("Warning: " + getParseExceptionInfo(spe));
        }

        @Override
        public void error(SAXParseException spe) throws SAXException {
            String message = "Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }

        @Override
        public void fatalError(SAXParseException spe) throws SAXException {
            String message = "Fatal Error: " + getParseExceptionInfo(spe);
            throw new SAXException(message);
        }
    }
}
