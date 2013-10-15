package com.hanjin.cmm;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;

public class ReadXml {
	
	/**
	 * xml형식의 문자를 Object방식으로 변경.
	 * @param xml xml형식의 문자
	 * @return
	 */
	public static Object getStringXMLtoObject(String xml, Object cls) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { cls.getClass() }); 
		Object obj = jaxbContext.createUnmarshaller().unmarshal(new StringReader(xml));

		return obj;
	}
	
}