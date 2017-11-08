package aline.a2101036808.dataModel;

/**
 * Created by aline on 2017-11-06.
 */

public class Test {
    private int testId, patientId, bpl, bph;
    private double temperature, glucose, cholesterol;

    public Test() {
    }

    public Test(int patientId, int bpl, int bph, double temperature, double glucose, double cholesterol) {
        this.patientId = patientId;
        this.bpl = bpl;
        this.bph = bph;
        this.temperature = temperature;
        this.glucose = glucose;
        this.cholesterol = cholesterol;
    }

    public Test(int testId, int patientId, int bpl, int bph, double temperature, double glucose, double cholesterol) {
        this.testId= testId;
        this.patientId = patientId;
        this.bpl = bpl;
        this.bph = bph;
        this.temperature = temperature;
        this.glucose = glucose;
        this.cholesterol = cholesterol;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getBpl() {
        return bpl;
    }

    public void setBpl(int bpl) {
        this.bpl = bpl;
    }

    public int getBph() {
        return bph;
    }

    public void setBph(int bph) {
        this.bph = bph;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getGlucose() {
        return glucose;
    }

    public void setGlucose(double glucose) {
        this.glucose = glucose;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }
}
