package com.hanjin.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class EmailMailCountBean {
	
	private Result result;
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	@XmlRootElement(name = "result")
	@XmlAccessorType(XmlAccessType.NONE)
	public static class Result {
		
		/** dirkey */
		private String dirkey;
		
		/** dirname */
		private String dirname;

		/** ���������� */
		private String current;
		
		/** ������������ */
		private String pagesize;
		
		/** ��ü���ϰ��� */
		private String total;
		
		/** �����ϰ���(?) */
		private String newcnt;
		
		private List<Mail> mail;
		
		@XmlElement(name = "dirkey")
		public String getDirkey() {
			return dirkey;
		}

		public void setDirkey(String dirkey) {
			this.dirkey = dirkey;
		}

		@XmlElement(name = "dirname")
		public String getDirname() {
			return dirname;
		}

		public void setDirname(String dirname) {
			this.dirname = dirname;
		}

		@XmlElement(name = "current")
		public String getCurrent() {
			return current;
		}

		public void setCurrent(String current) {
			this.current = current;
		}

		@XmlElement(name = "pagesize")
		public String getPagesize() {
			return pagesize;
		}

		public void setPagesize(String pagesize) {
			this.pagesize = pagesize;
		}

		@XmlElement(name = "total")
		public String getTotal() {
			return total;
		}

		public void setTotal(String total) {
			this.total = total;
		}

		@XmlElement(name = "newcnt")
		public String getNewcnt() {
			return newcnt;
		}

		public void setNewcnt(String newcnt) {
			this.newcnt = newcnt;
		}

		@XmlElement(name = "mail")
		public List<Mail> getMail() {
			return mail;
		}

		public void setMail(List<Mail> mail) {
			this.mail = mail;
		}

		public static class Mail {
			/** �߽����̸��� */
			private String from;
			
			/** ÷���������翩�� */
			private String isattach;
			
			/** �������� */
			private String isread;
			
			/** ������¥ */
			private String senddate;
			
			/** ����ũ�� */
			private String size;
			
			/** ���� */
			private String subject;
			
			/** ����Ű�� */
			private String ukey;

			@XmlElement(name = "from")
			public String getFrom() {
				return from;
			}

			public void setFrom(String from) {
				this.from = from;
			}

			public String getIsattach() {
				return isattach;
			}

			@XmlElement(name = "isattach")
			public void setIsattach(String isattach) {
				this.isattach = isattach;
			}

			@XmlElement(name = "isread")
			public String getIsread() {
				return isread;
			}

			public void setIsread(String isread) {
				this.isread = isread;
			}

			@XmlElement(name = "senddate")
			public String getSenddate() {
				return senddate;
			}

			public void setSenddate(String senddate) {
				this.senddate = senddate;
			}

			@XmlElement(name = "size")
			public String getSize() {
				return size;
			}

			public void setSize(String size) {
				this.size = size;
			}

			@XmlElement(name = "subject")
			public String getSubject() {
				return subject;
			}

			public void setSubject(String subject) {
				this.subject = subject;
			}

			@XmlElement(name = "ukey")
			public String getUkey() {
				return ukey;
			}

			public void setUkey(String ukey) {
				this.ukey = ukey;
			}
		}
	
	}

}