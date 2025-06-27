package com.railways.entryportal.controller;
import com.railways.entryportal.dto.SheetFormDto;

import com.railways.entryportal.entity.Sheet;
import com.railways.entryportal.service.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class SheetController {

    @Autowired
    private SheetService sheetService;

    @GetMapping("/admin/sheets-dashboard")
    public String sheetDashboard(Model model) {
        List<Sheet> sheets = sheetService.getAllSheets();
        model.addAttribute("sheets", sheets);
        return "sheet-dashboard";
    }

    @GetMapping("/create-sheet")
    public String createSheetForm(Model model) {
        model.addAttribute("sheet", new Sheet());
        return "create-sheet";
    }

    @PostMapping("/create-sheet")
    public String saveSheet(@ModelAttribute Sheet sheet) {
        sheet.setNumberOfEntries(0);  // Default
        sheet.setActive(true);        // Default active
        sheetService.saveSheet(sheet);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/open-sheet/{id}")
    public String openSheet(@PathVariable Long id, Model model) {
        Sheet sheet = sheetService.getSheetById(id);
        model.addAttribute("sheet", sheet);
        return "view-sheet"; // Create this page later
    }

    @GetMapping("/admin/create-sheet")
    public String showCreateSheetForm(Model model) {
        model.addAttribute("sheetForm", new SheetFormDto());
        return "admin/create-sheet";  // This must match the HTML file under templates
    }


}
