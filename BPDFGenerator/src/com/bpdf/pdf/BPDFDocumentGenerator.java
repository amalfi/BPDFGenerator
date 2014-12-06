package com.bpdf.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSStream;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
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
		PDDocument document =  generateProperDocumentWithMargin(sPDFContent, sPDFPath);
	}
	private PDDocument generateProperDocumentWithMargin(String sPDFContent , String sPDFPath) throws IOException, COSVisitorException
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

		    doc.save(sPDFPath);
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
	
}
