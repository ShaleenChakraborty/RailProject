package com.railways.entryportal.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class GoogleSheetsService {

    private static final String APPLICATION_NAME = "Railway Entry Portal";
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json";

    private Sheets sheetsService;

    public GoogleSheetsService() {
        try {
            this.sheetsService = createSheetsService();
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException("Failed to initialize Google Sheets service", e);
        }
    }

    private Sheets createSheetsService() throws IOException, GeneralSecurityException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredential credential = GoogleCredential
                .fromStream(new FileInputStream(CREDENTIALS_FILE_PATH))
                .createScoped(List.of("https://www.googleapis.com/auth/spreadsheets"));

        return new Sheets.Builder(httpTransport, JacksonFactory.getDefaultInstance(), credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public String createSheetWithFields(List<String> fieldNames, String sheetTitle) {
        try {
            // Step 1: Create the spreadsheet
            Spreadsheet spreadsheet = new Spreadsheet()
                    .setProperties(new SpreadsheetProperties().setTitle(sheetTitle));
            spreadsheet = sheetsService.spreadsheets().create(spreadsheet).execute();
            String spreadsheetId = spreadsheet.getSpreadsheetId();

            // Step 2: Add header row
            List<List<Object>> values = List.of(fieldNames.stream().map(Object.class::cast).toList());
            ValueRange body = new ValueRange().setValues(values);

            sheetsService.spreadsheets().values()
                    .update(spreadsheetId, "Sheet1!A1", body)
                    .setValueInputOption("RAW")
                    .execute();

            return spreadsheet.getSpreadsheetUrl(); // Get the viewable URL
        } catch (Exception e) {
            throw new RuntimeException("Failed to create and update Google Sheet: " + e.getMessage(), e);
        }
    }
}
