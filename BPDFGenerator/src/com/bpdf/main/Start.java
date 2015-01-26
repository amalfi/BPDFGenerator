package com.bpdf.main;

import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;

import com.bpdf.input.TXTFileLoader;
import com.bpdf.pdf.BPDFDocumentGenerator;

/**
 * Main class 
 * @author Marcin Berendt
 *
 */
public class Start {

	public static void main(String[] args) throws COSVisitorException 
	{
		TXTFileLoader txtFileLoader = new TXTFileLoader();
		try 
		{
			//String sFileContent = txtFileLoader.loadTXTFileFromDisc("D:\\text.txt");
			//System.out.println(sFileContent);
			BPDFDocumentGenerator bpdfdocumentGenerator = new BPDFDocumentGenerator();
			//bpdfdocumentGenerator.generatePDFDocumentWithContentAsParameter("D:\\text.pdf", sFileContent);
		
			bpdfdocumentGenerator.generatePDFDocumentWithHTMLPathAsParameter("C:\\sampleHTML.html", "C:\\samplepdf.pdf");
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}

}
