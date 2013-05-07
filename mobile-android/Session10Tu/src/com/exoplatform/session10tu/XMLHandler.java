/*
 * Copyright (C) 2003-2013 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.exoplatform.session10tu;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

/**
 * Created by The eXo Platform SAS
 * Author : Anh-Tu Nguyen
 *          tuna@exoplatform.com
 * May 6, 2013  
 */
public class XMLHandler extends DefaultHandler {
  private static final String TAG = "XMLHandler";
  String elementValue = null;
  Boolean elementOn = false;
  public static XMLGettersSetters data = null;
  public static XMLGettersSetters getXMLData() {
      return data;
  }
  public static void setXMLData(XMLGettersSetters data) {
      XMLHandler.data = data;
  }
  
  /**
   * This will be called when the tags of the XML starts.
   **/
  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      elementOn = true;
      if (localName.equals("user"))
      {
          data = new XMLGettersSetters();
      } 
  }
  
  /**
   * This will be called when the tags of the XML end.
   **/
  @Override
  public void endElement(String uri, String localName, String qName)
          throws SAXException {
      elementOn = false;
      /**
       * Sets the values after retrieving the values from the XML tags
       * */
      if (localName.equalsIgnoreCase("url")) {
        Log.i(TAG, "url: " + elementValue);  
        data.setUrl(elementValue);
      }
      else if (localName.equalsIgnoreCase("name")) {
        Log.i(TAG, "name: " + elementValue);  
        data.setName(elementValue);
      }
      else if (localName.equalsIgnoreCase("screen_name")) {
        Log.i(TAG, "screen name: " + elementValue);    
        data.setScrName(elementValue);
      }
      else if (localName.equalsIgnoreCase("location")) {
        Log.i(TAG, "location: " + elementValue);    
        data.setLocation(elementValue);
      }
      else if (localName.equalsIgnoreCase("description")) {
        Log.i(TAG, "description: " + elementValue);    
        data.setDescription(elementValue);
      }
  }
  /**
   * This is called to get the tags value
   **/
  @Override
  public void characters(char[] ch, int start, int length)
          throws SAXException {
      if (elementOn) {
          elementValue = new String(ch, start, length);
          elementOn = false;
      }
  }
}
