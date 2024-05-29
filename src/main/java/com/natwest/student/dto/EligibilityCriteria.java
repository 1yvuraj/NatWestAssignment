package com.natwest.student.dto;

// Class representing eligibility criteria for student admission
public class EligibilityCriteria {
    // Threshold scores for different subjects
    private int scienceThreshold;
    private int mathsThreshold;
    private int computerThreshold;
    private int englishThreshold;

    // Constructor to set default criteria
    public EligibilityCriteria() {
        this.scienceThreshold = 85;
        this.mathsThreshold = 90;
        this.computerThreshold = 95;
        this.englishThreshold = 75;
    }

    // Getter and setter methods for each threshold
    public int getScienceThreshold() {
        return scienceThreshold;
    }

    public void setScienceThreshold(int scienceThreshold) {
        this.scienceThreshold = scienceThreshold;
    }

    public int getMathsThreshold() {
        return mathsThreshold;
    }

    public void setMathsThreshold(int mathsThreshold) {
        this.mathsThreshold = mathsThreshold;
    }

    public int getComputerThreshold() {
        return computerThreshold;
    }

    public void setComputerThreshold(int computerThreshold) {
        this.computerThreshold = computerThreshold;
    }

    public int getEnglishThreshold() {
        return englishThreshold;
    }

    public void setEnglishThreshold(int englishThreshold) {
        this.englishThreshold = englishThreshold;
    }
}
