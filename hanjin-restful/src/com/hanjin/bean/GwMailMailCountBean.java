package com.hanjin.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

public class GwMailMailCountBean {
	
	private Result result;
	
	public Result getResult() {
		return result;
	}
	
	public void setResult(Result result) {
		this.result = result;
	}
	
	@XmlRootElement(name = "Result")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class Result {
		
		@XmlElement(name = "Box")
		private Box_ Box;
	
		public Box_ getBox() {
			return this.Box;
		}
	
		public void setBox(Box_ Box) {
			this.Box = Box;
		}
		
		public static class Box_ {
			
			private FoldersAll_ FoldersAll;
			
			private List<Folder_> Folders;
			
			@XmlElement(name = "FoldersAll")
			public FoldersAll_ getFoldersAll() {
				return this.FoldersAll;
			}
			
			public void setFoldersAll(FoldersAll_ FoldersAll) {
				this.FoldersAll = FoldersAll;
			}
			
			@XmlElementWrapper(name = "Folders")
			@XmlElement(name = "Folder")
			public List<Folder_> getFolders() {
				return Folders;
			}

			public void setFolders(List<Folder_> folders) {
				Folders = folders;
			}

			public static class FoldersAll_ {
				private String AllCount;
				
				private String UnReadCount;
				
				private String FolderCount;

				@XmlElement(name = "AllCount")
				public String getAllCount() {
					return this.AllCount;
				}

				public void setAllCount(String AllCount) {
					this.AllCount = AllCount;
				}

				@XmlElement(name = "UnReadCount")
				public String getUnReadCount() {
					return this.UnReadCount;
				}

				public void setUnReadCount(String UnReadCount) {
					this.UnReadCount = UnReadCount;
				}

				@XmlElement(name = "FolderCount")
				public String getFolderCount() {
					return this.FolderCount;
				}

				public void setFolderCount(String FolderCount) {
					this.FolderCount = FolderCount;
				}
				
			}

			public static class Folder_ {
				private String Name;

				private String AllCount;
				
				private String UnReadCount;

				@XmlElement(name = "Name")
				public String getName() {
					return this.Name;
				}

				public void setName(String Name) {
					this.Name = Name;
				}

				@XmlElement(name = "AllCount")
				public String getAllCount() {
					return this.AllCount;
				}

				public void setAllCount(String AllCount) {
					this.AllCount = AllCount;
				}

				@XmlElement(name = "UnReadCount")
				public String getUnReadCount() {
					return this.UnReadCount;
				}

				public void setUnReadCount(String UnReadCount) {
					this.UnReadCount = UnReadCount;
				}
				
			}	
		}
	}
}
