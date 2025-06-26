package com.railways.entryportal.controller;

import com.railways.entryportal.model.Datasheet;
import com.railways.entryportal.repository.DataSheetRepository;
import com.railways.entryportal.service.GoogleSheetsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private GoogleSheetsService sheetService;

    @Autowired
    private DataSheetRepository sheetRepo;

    // 🔹 Old: Create Sheet from Admin Form (Title + Fields)
    @PostMapping("/createSheet")
    public String createSheet(@RequestParam String title, @RequestParam String fields) {
        List<String> headers = Arrays.asList(fields.split(","));

        // Create sheet and get spreadsheetId
        String spreadsheetId = sheetService.createSheetWithFields(headers, title);


        // Save to DB
        Datasheet dataSheet = new Datasheet();
        dataSheet.setTitle(title);
        dataSheet.setHeaders(headers);
        dataSheet.setSpreadsheetId(spreadsheetId);
        dataSheet.setCreatedAt(LocalDateTime.now());

        sheetRepo.save(dataSheet);

        return "redirect:/admin/dashboard";
    }

    // 🔹 New: Quick Test Sheet Generator (Hardcoded Fields)
    @GetMapping("/create-sheet")
    @ResponseBody
    public String createSheetTest() {
        try {
            List<String> fields = List.of("Name", "Designation", "Department", "Shift Timing");
            String url = sheetService.createSheetwithFields("Test Sheet " + System.currentTimeMillis(), fields);


            return "Google Sheet Created: <a href='" + url + "' target='_blank'>" + url + "</a>";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
