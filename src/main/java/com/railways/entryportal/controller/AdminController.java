package com.railways.entryportal.controller;
import com.railways.entryportal.dto.SheetFormDto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // Admin main dashboard
    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin-dashboard"; // Points to src/main/resources/templates/admin-dashboard.html
    }

    // Page showing active users (e.g. list of logged-in users or roles)
    @GetMapping("/active-users")
    public String activeUsers() {
        return "active-users"; // Points to src/main/resources/templates/active-users.html
    }

    // Sheet dashboard with table of active sheets
    @GetMapping("/sheet-dashboard")
    public String sheetDashboard(Model model) {
        // You can add logic to fetch and attach sheet list to the model if needed
        return "sheet-dashboard"; // Points to src/main/resources/templates/sheet-dashboard.html
    }


}
