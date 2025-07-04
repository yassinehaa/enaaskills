package org.enaa.enaaskills.Services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.enaa.enaaskills.DTO.CompetenceDTO;
import org.enaa.enaaskills.DTO.SubCompetenceDTO;
import org.enaa.enaaskills.Models.Competence;
import org.enaa.enaaskills.Models.SubCompetence;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelExportService {

    public void exportCompetencesToExcel(List<Competence> competences, OutputStream outputStream) throws IOException {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Rapport de Compétences");

            // Style pour les en-têtes
            CellStyle headerStyle = workbook.createCellStyle();
            XSSFFont headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeight(14);
            headerStyle.setFont(headerFont);

            // Style pour cellule validée (vert)
            CellStyle greenStyle = workbook.createCellStyle();
            greenStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            greenStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Style pour cellule non validée (rouge)
            CellStyle redStyle = workbook.createCellStyle();
            redStyle.setFillForegroundColor(IndexedColors.ROSE.getIndex());
            redStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            // Ligne d'en-tête
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                    "ID Compétence", "Code", "Titre Compétence",
                    "ID Sous-Compétence", "Titre Sous-Compétence", "Validation"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // Données
            int rowNum = 1;
            for (Competence competence : competences) {
                for (SubCompetence sousCompetence : competence.getSousCompetences()) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(competence.getId());
                    row.createCell(1).setCellValue(competence.getNom());
                    row.createCell(2).setCellValue(sousCompetence.getId());
                    row.createCell(3).setCellValue(sousCompetence.getNom());

                    // Colonne "Validation" avec couleur
                    Cell validationCell = row.createCell(4);
                    boolean estValide = sousCompetence.isValide();
                    validationCell.setCellValue(estValide ? "Oui" : "Non");
                    validationCell.setCellStyle(estValide ? greenStyle : redStyle);
                }
            }

            // Écriture du fichier Excel
            workbook.write(outputStream);
        }
    }
}