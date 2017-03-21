package org.aakashresearchlabs.messenger;

/**
 * Created by Kevin on 3/21/2017.
 */

public class SubjectClass {
    String subjectName;
    String fileName;

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
