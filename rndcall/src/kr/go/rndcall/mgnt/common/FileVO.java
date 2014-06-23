package kr.go.rndcall.mgnt.common;

public class FileVO {
    private String inputFileName;
    private String outputFileName;
    private String size;
    private String relativePath;
    private String absolutePath;
    private String fileBoxName;
    private String originalFileName;

    public String getOriginalFileName() {
		return originalFileName;
	}



	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}



	public FileVO() {
    }
    
    

    public String getFileBoxName() {
		return fileBoxName;
	}
	public void setFileBoxName(String fileBoxName) {
		this.fileBoxName = fileBoxName;
	}
	public String getAbsolutePath() {
        return absolutePath;
    }

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }

    public String getSize() {
        return size;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }

    public void setInputFileName(String inputFileName) {
        this.inputFileName = inputFileName;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
