package com.resource;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.FileNotFoundException;
import java.util.Map;

@Path("/pdf")
public class PdfResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response generatePdf(Map<String, String> inputData) {
        System.out.println("Received request to /pdf endpoint.");

        if (inputData == null || inputData.isEmpty()) {
            System.out.println("Input data is null or empty.");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Input data is required and cannot be empty.")
                    .build();
        }

        try {
            System.out.println("Input data received: " + inputData);
            String base64 = PdfGenerator.generateBase64Pdf(inputData);
            System.out.println("Base64 PDF generated successfully.");
            return Response.ok(base64).build();
        } catch (FileNotFoundException e) {
            System.out.println("Template file not found: " + e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Template file not found.")
                    .build();
        } catch (Exception e) {
            System.out.println("Unexpected error during PDF generation: " + e.getMessage());
            e.printStackTrace();  // Print full stack trace for debugging
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("An error occurred while generating the PDF: " + e.getMessage())
                    .build();
        }
    }
}
