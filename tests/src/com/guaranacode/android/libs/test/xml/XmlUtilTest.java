package com.guaranacode.android.libs.test.xml;

import java.util.HashMap;
import java.util.Map;

import com.guaranacode.android.libs.xml.XmlUtil;

import junit.framework.TestCase;

public class XmlUtilTest extends TestCase {
    private final String XML_TAG_FORMAT = "<%1$s>%2$s</%1$s>";
    
    /*
     * Sample input
     */
    private final String mTag = "footag";
    private final String mValue = "barvalue";
    
    private Map<String,String> mSampleMap = null;
    private XmlUtil mXmlUtil = null;
    
    public XmlUtilTest(String name) {
        super(name);
    }
    
    public void setUp() {
        mSampleMap = new HashMap<String,String>();
        mXmlUtil = new XmlUtil();
    }
    
    public void tearDown() {
        mSampleMap.clear();
    }
    
    /**
     * Outputs a XML tag with its value.
     * @param tag
     * @param value
     * @return
     */
    private String makeTag(String tag, String value) {
        return String.format(XML_TAG_FORMAT, tag, value);
    }

    public void testToXmlNullOrEmptyMap() {
        String xml = mXmlUtil.toXml(null, null);
        
        assertNull("A null map should result in null XML.", xml);
        
        xml = mXmlUtil.toXml(new HashMap<String,String>(), null);
        assertNull("An empty map should result in null XML.", xml);
    }
    
    /**
     * Test that the output of a map with one tag is correct.
     */
    public void testToXmlOneTag() {
        mSampleMap.put(mTag, mValue);
        
        String xml = mXmlUtil.toXml(mSampleMap, null);
        
        assertEquals(makeTag(mTag, mValue), xml);
    }
    
    public void testToXmlTwoTags() {
        final String tagB = "baztag";
        final String valueB = "";
        
        mSampleMap.put(mTag, mValue);
        mSampleMap.put(tagB, valueB);
        
        String xml = mXmlUtil.toXml(mSampleMap, null);
        
        assertEquals(makeTag(mTag, mValue) + makeTag(tagB, valueB), xml);
    }
    
    public void testToXmlTagWithEmptyValue() {
        final String emptyValue = "";
        
        mSampleMap.put(mTag, emptyValue);
        
        String xml = mXmlUtil.toXml(mSampleMap, null);
        
        assertEquals(makeTag(mTag, emptyValue), xml);
    }
    
    public void testToXmlNullOrEmptyRootTag() {
        mSampleMap.put(mTag, mValue);
        
        String xml = mXmlUtil.toXml(mSampleMap, null);
        assertEquals(makeTag(mTag, mValue), xml);
        
        xml = mXmlUtil.toXml(mSampleMap, "");
        assertEquals(makeTag(mTag, mValue), xml);
    }
    
    /**
     * Verify that a root tag is included in the output if specified.
     */
    public void testToXmlRootTag() {
        final String rootTag = "root";
        
        mSampleMap.put(mTag, mValue);
        
        String xml = mXmlUtil.toXml(mSampleMap, rootTag);
        assertEquals(makeTag(rootTag, makeTag(mTag, mValue)), xml);
    }
    
    /**
     * Test that tags with values that represent XML characters are properly encoded.
     */
    public void testToXmlEncodingXmlValue() {
        final String xmlValue = "<\">";
        final String encodedXmlValue = "&lt;\"&gt;";
        
        mSampleMap.put(mTag, xmlValue);
        
        String xml = mXmlUtil.toXml(mSampleMap, null);
        assertEquals(makeTag(mTag, encodedXmlValue), xml);
    }
}
