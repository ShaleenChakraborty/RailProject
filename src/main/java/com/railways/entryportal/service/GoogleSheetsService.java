package com.railways.entryportal.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.*;

import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleSheetsService {

    private static final String APPLICATION_NAME = "Railway Entry Portal";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList("https://www.googleapis.com/auth/spreadsheets");

    private final Sheets sheetsService;

    public GoogleSheetsService() {
        try {
            this.sheetsService = createSheetsService();
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to initialize Google Sheets service: " + e.getMessage(), e);
        }
    }

    private Sheets createSheetsService() throws Exception {
        InputStream credentialsStream = getClass().getClassLoader().getResourceAsStream("credentials.json");
        if (credentialsStream == null) {
            throw new RuntimeException("❌ credentials.json not found in classpath (resources folder).");
        }

        GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream).createScoped(SCOPES);

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials)
        ).setApplicationName(APPLICATION_NAME).build();
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

            return spreadsheet.getSpreadsheetUrl(); // return sheet URL
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to create/update Google Sheet: " + e.getMessage(), e);
        }
    }
}
