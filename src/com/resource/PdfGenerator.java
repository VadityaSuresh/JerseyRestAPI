package com.resource;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class PdfGenerator {
	
	public static String generateBase64Pdf(Map<String, String> data) throws IOException {
	    System.out.println("Starting styled PDF generation with PDFBox...");

	    PDDocument document = new PDDocument();
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try {
	        PDPage page = new PDPage(PDRectangle.LETTER);
	        document.addPage(page);
	        PDPageContentStream content = new PDPageContentStream(document, page);

	        float margin = 50;
	        float width = page.getMediaBox().getWidth() - 2 * margin;
	        float startY = 750;
	        float currentY = startY;

	        content.setNonStrokingColor(Color.BLACK);
	        content.setStrokingColor(Color.BLACK);
	        content.setLineWidth(1);

	        // Title
	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA_BOLD, 18);
	        content.newLineAtOffset(margin, currentY);
	        content.showText("SURESH CUSTOMER MANAGEMENT SERVICE AMEERPET");
	        content.endText();
	        currentY -= 30;

	        // Left Service Box
	        float boxHeight = 80;
	        content.addRect(margin, currentY - boxHeight, 250, boxHeight);
	        content.stroke();

	        float textY = currentY - 15;
	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA, 11);
	        content.newLineAtOffset(margin + 10, textY);
	        content.showText(data.getOrDefault("headerInput1", ""));
	        content.newLineAtOffset(0, -15);
	        content.showText(data.getOrDefault("headerInput2", ""));
	        content.newLineAtOffset(0, -15);
	        content.showText(data.getOrDefault("headerInput3", ""));
	        content.newLineAtOffset(0, -15);
	        content.showText(data.getOrDefault("headerInput4", ""));
	        content.endText();

	        // WORK ORDER Label
	        currentY -= boxHeight + 15;
	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA_BOLD, 14);
	        content.newLineAtOffset(margin + width - 150, currentY + 5);
	        content.showText("WORK ORDER");
	        content.endText();

	        // Contact Info Table
	        currentY -= 30;
	        float rowHeight = 20;
	        float[] colWidths = {130, 150, 130, 150};

	        drawRow(content, margin, currentY, colWidths, rowHeight,
	                new String[]{"REQUESTER NAME", data.getOrDefault("requesterName", ""),
	                        "LOCATION ADDRESS", data.getOrDefault("locationAddress", "")}, true);
	        currentY -= rowHeight;

	        drawRow(content, margin, currentY, colWidths, rowHeight,
	                new String[]{"PHONE", data.getOrDefault("phone", ""),
	                        "LOCATION DETAILS", data.getOrDefault("locationDetails", "")}, false);
	        currentY -= rowHeight;

	        drawRow(content, margin, currentY, colWidths, rowHeight,
	                new String[]{"EMAIL", data.getOrDefault("email", ""), "", ""}, false);
	        currentY -= rowHeight + 10;

	        // Dates and Priority
	        drawRow(content, margin, currentY, colWidths, rowHeight,
	                new String[]{"PRIORITY LEVEL", data.getOrDefault("priorityLevel", ""),
	                        "ORDER DATE & TIME", data.getOrDefault("orderDateTime", "")}, true);
	        currentY -= rowHeight;

	        drawRow(content, margin, currentY, colWidths, rowHeight,
	                new String[]{"DATE NEEDED", data.getOrDefault("dateNeeded", ""),
	                        "DATE DELIVERED", data.getOrDefault("dateDelivered", "")}, false);
	        currentY -= rowHeight + 10;

	        // Assignment Info
	        drawRow(content, margin, currentY, colWidths, rowHeight,
	                new String[]{"WORK ASSIGNED TO", data.getOrDefault("workAssignedTo", ""),
	                        "WORK BILLED TO", data.getOrDefault("workBilledTo", "")}, true);
	        currentY -= rowHeight + 20;

	        // Description Title
	        content.setNonStrokingColor(new Color(196, 145, 0));
	        content.addRect(margin, currentY, width, 20);
	        content.fill();

	        content.setNonStrokingColor(Color.WHITE);
	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA_BOLD, 12);
	        content.newLineAtOffset(margin + 10, currentY + 5);
	        content.showText("REQUEST DESCRIPTION");
	        content.endText();
	        currentY -= 30;

	        // Description Box
	        content.setNonStrokingColor(Color.WHITE);
	        content.setStrokingColor(Color.BLACK);
	        content.addRect(margin, currentY - 100, width, 100);
	        content.stroke();

	        content.beginText();
	        content.setFont(PDType1Font.HELVETICA, 11);
	        content.setNonStrokingColor(Color.BLACK);
	        content.newLineAtOffset(margin + 10, currentY - 15);
	        content.showText(wrapText(data.getOrDefault("description", ""), 90));
	        content.endText();

	        content.close();

	        document.save(outputStream);
	        document.close();

	        byte[] pdfBytes = outputStream.toByteArray();
	        System.out.println("Styled PDF generated successfully. Size: " + pdfBytes.length + " bytes");
	        return Base64.getEncoder().encodeToString(pdfBytes);

	    } catch (IOException e) {
	        System.err.println("PDF generation failed: " + e.getMessage());
	        throw e;
	    }
	}

	private static void drawRow(PDPageContentStream content, float x, float y, float[] colWidths,
	                            float height, String[] cells, boolean shaded) throws IOException {
	    if (shaded) {
	        content.setNonStrokingColor(new Color(240, 240, 240));
	        content.addRect(x, y, sum(colWidths), height);
	        content.fill();
	    }
	    content.setStrokingColor(Color.BLACK);
	    content.setNonStrokingColor(Color.BLACK);
	    float cursor = x;

	    for (int i = 0; i < cells.length; i++) {
	        content.addRect(cursor, y, colWidths[i], height);
	        content.stroke();
	        content.beginText();
	        content.setFont(i % 2 == 0 ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA, 10);
	        content.newLineAtOffset(cursor + 3, y + 5);
	        content.showText(cells[i]);
	        content.endText();
	        cursor += colWidths[i];
	    }
	}

	private static float sum(float[] arr) {
	    float total = 0;
	    for (float val : arr) total += val;
	    return total;
	}

	private static String wrapText(String text, int maxLen) {
	    if (text == null) return "";
	    if (text.length() <= maxLen) return text;
	    return text.substring(0, maxLen) + "...";
	}

}