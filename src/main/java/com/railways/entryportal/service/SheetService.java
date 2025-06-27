package com.railways.entryportal.service;

import com.railways.entryportal.entity.Sheet;
import com.railways.entryportal.repository.SheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheetService {

    @Autowired
    private SheetRepository sheetRepository;

    public List<Sheet> getAllSheets() {
        return sheetRepository.findAll();
    }

    public Sheet saveSheet(Sheet sheet) {
        return sheetRepository.save(sheet);
    }

    public Sheet getSheetById(Long id) {
        return sheetRepository.findById(id).orElse(null);
    }
}
