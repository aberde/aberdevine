package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


public class BulletinBoardCountBean {

	/** ROOT */
	private ROOT root;
	
	public ROOT getRoot() {
		return root;
	}

	public void setRoot(ROOT root) {
		this.root = root;
	}

	@XmlRootElement(name = "ROOT")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class ROOT {
		
		/** µ•¿Ã≈Õ */
		private String RESULT;

		@XmlElement(name = "RESULT")
		public String getRESULT() {
			return RESULT;
		}

		public void setRESULT(String rESULT) {
			RESULT = rESULT;
		}

	}
}
