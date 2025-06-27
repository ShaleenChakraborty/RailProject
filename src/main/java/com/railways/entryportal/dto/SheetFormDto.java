package com.railways.entryportal.dto;

import java.time.LocalDate;

public class SheetFormDto {

    private String sheetName;
    private String summary;
    private LocalDate deadline;

    // Constructors
    public SheetFormDto() {}

    public SheetFormDto(String sheetName, String summary, LocalDate deadline) {
        this.sheetName = sheetName;
        this.summary = summary;
        this.deadline = deadline;
    }

    // Getters and Setters
    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "SheetFormDto{" +
                "sheetName='" + sheetName + '\'' +
                ", summary='" + summary + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}
