package org.aakashresearchlabs.messenger;

/**
 * Created by Kevin on 3/21/2017.
 */

public class SubjectClass {
    String subjectName;
    String fileName;
    String fileURL;
    String dateOfUpload;

    public String getDateOfUpload() {
        return String.valueOf(dateOfUpload);
    }

    public void setDateOfUpload(String dateOfUpload) {
        this.dateOfUpload = dateOfUpload;
    }

    public String getFileURL() {
        return fileURL;
    }

    public void setFileURL(String fileURL) {
        this.fileURL = fileURL;
    }


    SubjectClass(){
        subjectName=null;
        fileName=null;
        fileURL=null;
    }

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
