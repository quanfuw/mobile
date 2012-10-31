package org.exoplatform.session11phi.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {

	private ArrayList<String> versions;
	private StringBuilder version;
	private boolean inVersion = false;

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		if (inVersion) {
			version.append(ch, start, length);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		if (inVersion) {
			versions.add(version.toString().trim());
		}
		version.setLength(0);
	}

	@Override
	public void startDocument() throws SAXException {
		versions = new ArrayList<String>();
		version = new StringBuilder();
		super.startDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		inVersion = "version".equals(localName);
		super.startElement(uri, localName, qName, attributes);
	}

	public ArrayList<String> getVersions() {
		return versions;
	}

}
