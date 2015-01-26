package com.bpdf.input;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class which contains method for loading TXT file from disc
 * @author Marcin Berendt
 *
 */
public class TXTFileLoader 
{
	/**
	 * 
	 * @param txtPath - disc path to file
	 * @return TXTFile content as a String object
	 * @throws IOException 
	 */
	public String loadTXTFileFromDisc(String txtPath) throws IOException
	{
		String txtFile="";
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txtPath)));
		try 
		{
		    String line;
		    while ((line = br.readLine()) != null)
		    {
		    	stringBuilder.append(line+" ");
		    }
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally 
		{
		    br.close();
		}
		txtFile=stringBuilder.toString();
		return txtFile;

	}
}