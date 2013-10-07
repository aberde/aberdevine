package com.hanjin.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class ApprovalCountBean {

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
		private DATA data;
	
		@XmlElement(name = "DATA")
		public DATA getData() {
			return data;
		}
	
		public void setData(DATA data) {
			this.data = data;
		}

		public static class DATA {
			
			/** CNTTYPE */
			private String CNTTYPE;
			
			/** CNT */
			private CNT_ CNT;
			
			@XmlElement(name = "CNTTYPE")
			public String getCNTTYPE() {
				return CNTTYPE;
			}
	
			public void setCNTTYPE(String CNTTYPE) {
				this.CNTTYPE = CNTTYPE;
			}
	
			@XmlElement(name = "CNT")
			public CNT_ getCNT() {
				return CNT;
			}
	
			public void setCNT(CNT_ CNT) {
				this.CNT = CNT;
			}
	
			public static class CNT_ {
				
				/** RESULT */
				private String RESULT;
	
				@XmlElement(name = "RESULT")
				public String getRESULT() {
					return RESULT;
				}
	
				public void setRESULT(String RESULT) {
					this.RESULT = RESULT;
				}
				
			}
		
		}
	}
}
