package ifpe.br.com.model;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

public class Clocking {
    private String clockingCode;
    private String employeeCode;
    private LocalTime clockIn;
    private LocalTime clockInLunch;
    private LocalTime clockOutLunch;
    private LocalTime clockOut;
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;

    public Clocking() {
    }

    public Clocking(String clockingCode, String employeeCode, LocalTime clockIn, LocalTime clockInLunch, LocalTime clockOutLunch, LocalTime clockOut, LocalDate date) {
        this.clockingCode = clockingCode;
        this.employeeCode = employeeCode;
        this.clockIn = clockIn;
        this.clockInLunch = clockInLunch;
        this.clockOutLunch = clockOutLunch;
        this.clockOut = clockOut;
        this.date = date;
    }

    public String getClockingCode() {
        return clockingCode;
    }

    public void setClockingCode(String clockingCode) {
        this.clockingCode = clockingCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public LocalTime getClockIn() {
        return clockIn;
    }

    public void setClockIn(LocalTime clockIn) {
        this.clockIn = clockIn;
    }

    public LocalTime getClockInLunch() {
        return clockInLunch;
    }

    public void setClockInLunch(LocalTime clockInLunch) {
        this.clockInLunch = clockInLunch;
    }

    public LocalTime getClockOutLunch() {
        return clockOutLunch;
    }

    public void setClockOutLunch(LocalTime clockOutLunch) {
        this.clockOutLunch = clockOutLunch;
    }

    public LocalTime getClockOut() {
        return clockOut;
    }

    public void setClockOut(LocalTime clockOut) {
        this.clockOut = clockOut;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
