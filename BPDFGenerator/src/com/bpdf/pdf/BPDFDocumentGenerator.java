package com.bpdf.pdf;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

/**
 * Class which contains methods for generating PDF files. Apache PDFBox used for generating pdf with txt content as a paremeter
 * (generatePDFDocumentWithContentAsParameter)
 * IText library used for generating PDF with HTML file path as a parameter 
 * (generatePDFDocumentWithHTMLPathAsParameter)
 * 
 * @author Marcin
 *
 */
public class BPDFDocumentGenerator 
{
	/**
	 * Method should be used for generating short pdf files
	 * @param outputPDFPath - path for generated pdf file
	 * @param pdfContent - content for generated pdf file
	 * @throws IOException 
	 * @throws COSVisitorException 
	 */
	public void generatePDFDocumentWithContentAsParameter(String outputPDFPath, String pdfContent) throws IOException, COSVisitorException
	{
		@SuppressWarnings("unused")
		PDDocument document =  generateProperDocumentWithMargin(pdfContent, outputPDFPath);
	}
	private PDDocument generateProperDocumentWithMargin(String sPDFContent , String outputPDFPath) throws IOException, COSVisitorException
	{
		PDDocument doc = null;
		try
		{
		    doc = new PDDocument();
		    PDPage page = new PDPage();
		    doc.addPage(page);
		    PDPageContentStream contentStream = new PDPageContentStream(doc, page);

		    PDFont pdfFont = PDType1Font.HELVETICA;
		    float fontSize = 25;
		    float leading = 1.5f * fontSize;

		    PDRectangle mediabox = page.findMediaBox();
		    float margin = 72;
		    float width = mediabox.getWidth() - 2*margin;
		    float startX = mediabox.getLowerLeftX() + margin;
		    float startY = mediabox.getUpperRightY() - margin;

		    String text = sPDFContent; 
		    List<String> lines = new ArrayList<String>();
		    int lastSpace = -1;
		    while (text.length() > 0)
		    {
		        int spaceIndex = text.indexOf(' ', lastSpace + 1);
		        if (spaceIndex < 0)
		        {
		            lines.add(text);
		            text = "";
		        }
		        else
		        {
		            String subString = text.substring(0, spaceIndex);
		            float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
		            if (size > width)
		            {
		                if (lastSpace < 0) // So we have a word longer than the line... draw it anyways
		                    lastSpace = spaceIndex;
		                subString = text.substring(0, lastSpace);
		                lines.add(subString);
		                text = text.substring(lastSpace).trim();
		                lastSpace = -1;
		            }
		            else
		            {
		                lastSpace = spaceIndex;
		            }
		        }
		    }

		    contentStream.beginText();
		    contentStream.setFont(pdfFont, fontSize);
		    contentStream.moveTextPositionByAmount(startX, startY);            
		    for (String line: lines)
		    {
		        contentStream.drawString(line);
		        contentStream.moveTextPositionByAmount(0, -leading);
		    }
		    contentStream.endText(); 
		    contentStream.close();

		    doc.save(outputPDFPath);
		}
		finally
		{
		    if (doc != null)
		    {
		        doc.close();
		    }
		}
		return doc;
	}
	/**
	 * Method should be used if we want to generate pdf files based on html input files.
	 * WARNING : Input html file should have all tags closed ! (like proper xhtml/xml file)
	 * @param inputHtmlFilePath
	 * @param outputPDFPath
	 * @throws FileNotFoundException
	 */
	public void generatePDFDocumentWithHTMLPathAsParameter(String inputHtmlFilePath, String outputPDFPath) throws FileNotFoundException
	{
        Document document = new Document();
        PdfWriter writer;
        
		try 
		{
			writer = PdfWriter.getInstance(document, new FileOutputStream(outputPDFPath));
		    document.open();
	        XMLWorkerHelper.getInstance().parseXHtml(writer, document, new FileInputStream(inputHtmlFilePath)); 
	        document.close();
		} 
		catch (DocumentException | IOException e)
		{
			e.printStackTrace();
		}
    
        System.out.println( "PDF Created!" );
	}
	
}
