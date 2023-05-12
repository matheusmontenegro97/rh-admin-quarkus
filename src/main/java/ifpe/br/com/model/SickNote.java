package ifpe.br.com.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SickNote {
    private String sickNoteCode;
    private String employeeCode;
    private String sickNote;

    public SickNote() {
    }

    public SickNote(String sickNoteCode, String employeeCode, String sickNote) {
        this.sickNoteCode = sickNoteCode;
        this.employeeCode = employeeCode;
        this.sickNote = sickNote;
    }

    public String getSickNoteCode() {
        return sickNoteCode;
    }

    public void setSickNoteCode(String sickNoteCode) {
        this.sickNoteCode = sickNoteCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getSickNote() {
        return sickNote;
    }

    public void setSickNote(String sickNote) {
        this.sickNote = sickNote;
    }
}
