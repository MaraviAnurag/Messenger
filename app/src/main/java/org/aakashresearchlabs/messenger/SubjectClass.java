package org.aakashresearchlabs.messenger;

/**
 * Created by Kevin on 3/21/2017.
 */

public class SubjectClass {
    String subjectName;
    String fileName;

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }

    String fileURL;

    SubjectClass(String subjectName,String fileName,String fileURL)
    {
        this.subjectName=subjectName;
        this.fileName=fileName;
        this.fileURL=fileURL;
    }
    SubjectClass(String subjectName,String fileName)
    {
        this.subjectName=subjectName;
        this.fileName=fileName;

    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
