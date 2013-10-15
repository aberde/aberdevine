package com.hanjin.cmm;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;

public class ReadXml {
	
	/**
	 * xml������ ���ڸ� Object������� ����.
	 * @param xml xml������ ����
	 * @return
	 */
	public static Object getStringXMLtoObject(String xml, Object cls) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { cls.getClass() }); 
		Object obj = jaxbContext.createUnmarshaller().unmarshal(new StringReader(xml));

		return obj;
	}
	
}