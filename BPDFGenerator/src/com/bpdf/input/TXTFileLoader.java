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
	 * @param sTXTPath - disc path to file
	 * @return TXTFile content as a String object
	 * @throws IOException 
	 */
	public String loadTXTFileFromDisc(String sTXTPath) throws IOException
	{
		String sTXTFile="";
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(sTXTPath)));
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
		sTXTFile=stringBuilder.toString();
		return sTXTFile;

	}
}