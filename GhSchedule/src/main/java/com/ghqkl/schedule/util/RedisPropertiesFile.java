package com.ghqkl.schedule.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class RedisPropertiesFile
{
	private static String fileName = "redis.properties";
    private static HashMap hm = null;
    public static String getValue(String name)
    {  
    	String line = "";
    	String returnValue = "";
    	if (hm != null && hm.containsKey(name))
    	{
    		returnValue = (String)hm.get(name);
    		return returnValue;
    	}
    	try
		{
			InputStream file = RedisPropertiesFile.class.getClassLoader()
					.getResourceAsStream(fileName);
			InputStreamReader file1 = new InputStreamReader(file);
			BufferedReader br = new BufferedReader(file1);
			while ((line = br.readLine()) != null)
			{
				int index = line.indexOf("=");
				if (index == -1)
				{
					continue;
				}
				String key = line.substring(0, index).trim();
				String value = line.substring(index + 1).trim();
				if (hm == null)
				{
					hm = new HashMap();
				}
				hm.put(key, value);
			}
			br.close();
			file1.close();
			file.close();		
		}
    	
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return (String)hm.get(name);
    }
    
    
    /**
     * for test
     * @param args
     * @throws Exception
     */
    public static void main(String []args)throws Exception
    {
    	String a =RedisPropertiesFile.getValue("DEBUG");
    	System.out.println(a);
    }
}
