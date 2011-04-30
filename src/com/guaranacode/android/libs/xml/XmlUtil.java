package com.guaranacode.android.libs.xml;

import java.io.StringWriter;
import java.util.Map;

import org.xmlpull.v1.XmlSerializer;

import com.guaranacode.android.libs.string.StringUtil;

import android.util.Log;
import android.util.Xml;

public class XmlUtil {
    public XmlUtil() {
        
    }
    
    /**
     * Converts a map to XML. Each key is a tag name and each value is the contents of that tag.
     * 
     * The proper way to do this would be to use XStream for Android, but that requires a 367KB jar.
     * @param content
     * @param rootTagName   
     * @return The map in XML format, or null if the map is null or empty.
     */
    public String toXml(Map<String, String> content, String rootTagName) {
        if(null == content || content.size() == 0) {
            return null;
        }

        StringWriter writer = new StringWriter();
        
        try {
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(writer);
            
            if(!StringUtil.isNullOrEmpty(rootTagName))
            {
                serializer.startTag("", rootTagName);
            }
            
            for (String tagName : content.keySet()){
                serializer.startTag("", tagName);
                serializer.text(content.get(tagName));
                serializer.endTag("", tagName);
            }
            
            if(!StringUtil.isNullOrEmpty(rootTagName))
            {
                serializer.endTag("", rootTagName);
            }
            
            serializer.endDocument();
        } catch (Exception e) {
            Log.e("XmlUtil", "Error converting map to XML", e);
        } 
        
        return writer.toString();
    }
}
