package com.railways.entryportal.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Sheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sheetName;
    private String summary;
    private String sheetId;
    private String sheetUrl;

    private int numberOfEntries;

    private boolean active;

    private LocalDate dateCreated;  // Auto-set when created

    private LocalDate deadline;     // Entered by Admin

    @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDate.now();
    }

    // Getters and Setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getSheetName() { return sheetName; }

    public void setSheetName(String sheetName) { this.sheetName = sheetName; }

    public String getSummary() { return summary; }

    public void setSummary(String summary) { this.summary = summary; }

    public String getSheetId() { return sheetId; }

    public void setSheetId(String sheetId) { this.sheetId = sheetId; }

    public String getSheetUrl() { return sheetUrl; }

    public void setSheetUrl(String sheetUrl) { this.sheetUrl = sheetUrl; }

    public int getNumberOfEntries() { return numberOfEntries; }

    public void setNumberOfEntries(int numberOfEntries) { this.numberOfEntries = numberOfEntries; }

    public boolean isActive() { return active; }

    public void setActive(boolean active) { this.active = active; }

    public LocalDate getDateCreated() { return dateCreated; }

    public void setDateCreated(LocalDate dateCreated) { this.dateCreated = dateCreated; }

    public LocalDate getDeadline() { return deadline; }

    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
}
