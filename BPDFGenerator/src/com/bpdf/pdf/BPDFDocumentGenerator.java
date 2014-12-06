package com.bpdf.pdf;

import java.io.IOException;

import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * Class which contains methods for generating PDF files. Apache PDFBox used 
 * @author Marcin
 *
 */
public class BPDFDocumentGenerator 
{
	/**
	 * Method should be used for generating short pdf files
	 * @param sPDFPath - path for generated pdf file
	 * @param sPDFContent - content for generated pdf file
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public void generatePDFDocumentWithContentAsParameter(String sPDFPath, String sPDFContent) throws IOException, COSVisitorException
	{
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage( page );

		// Create a new font object selecting one of the PDF base fonts
		PDFont font = PDType1Font.HELVETICA_BOLD;

		// Start a new content stream which will "hold" the to be created content
		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		// Define a text content stream using the selected font, moving the cursor and drawing the text "Hello World"
		contentStream.beginText();
		contentStream.setFont( font, 12 );
		contentStream.moveTextPositionByAmount( 100, 700 );
		contentStream.drawString( sPDFContent );
		contentStream.endText();

		// Make sure that the content stream is closed:
		contentStream.close();

		// Save the results and ensure that the document is properly closed:
		document.save( sPDFPath);
		document.close();
		
	}
	
}
