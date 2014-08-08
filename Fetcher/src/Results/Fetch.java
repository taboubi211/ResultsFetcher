package Results;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;


public class Fetch {

	public static  void createResultFile(String data) throws IOException 
	{
        FileWriter writer = new FileWriter("results.csv");
        JSONArray resultArray = new JSONArray(data);
        
        String [] header = {"_type" , "_id" ,"name" ,"type" ,"latitude" ,"longtitude"}; // Header elements
       
        for(int j = 0 ; j<header.length;j++)
        {
        	 writer.append(header[j]);
        	 if(j == header.length - 1)
        	 {
        		 writer.append('\n'); 
        	 }
        	 else
        	 {
        		 writer.append(','); 
        	 }		 
        }
     
  
        for (int i = 0 ; i < resultArray.length() ; i++)
        {
        	
        	if(resultArray.getJSONObject(i).has("_type")) // Check if the current json object contains the element _type 
        	{
     	    writer.append(resultArray.getJSONObject(i).getString("_type")); 
        	}
        	else
        	{
        	writer.append("NULL");	// the element doesn't exist
        	}	
     	    writer.append(',');
     	   if(resultArray.getJSONObject(i).has("_id") )
        	{
     	    writer.append(String.valueOf(resultArray.getJSONObject(i).getDouble("_id")));
        	}
        	else
        	{
        	writer.append("NULL");	
        	}	
     	    writer.append(',');
     	   if(resultArray.getJSONObject(i).has("name") )
        	{
     	    writer.append(resultArray.getJSONObject(i).getString("name"));
        	}
        	else
        	{
        	writer.append("NULL");	
        	}	
     	    writer.append(',');
     	   if(resultArray.getJSONObject(i).has("type"))
        	{
     	    writer.append(resultArray.getJSONObject(i).getString("type"));
        	}
        	else
        	{
        	writer.append("NULL");	
        	}	
     	    writer.append(',');
     	   if(resultArray.getJSONObject(i).getJSONObject("geo_position").has("latitude") )
        	{
     	    writer.append(String.valueOf(resultArray.getJSONObject(i).getJSONObject("geo_position").getDouble("latitude")));
        	}
        	else
        	{
        	writer.append("NULL");	
        	}	
     	    writer.append(',');
     	   if(resultArray.getJSONObject(i).getJSONObject("geo_position").has("latitude") )
        	{
     	    writer.append(String.valueOf(resultArray.getJSONObject(i).getJSONObject("geo_position").getDouble("latitude")));
        	}
        	else
        	{
        	writer.append("");	
        	}	
     	    
            writer.append('\n');
      
     	   
        }
        
        writer.flush();
	    writer.close();
	}
	 public static void main(String[] args) throws Exception {
		   
		 
		   if(args.length == 0 || args[0].equalsIgnoreCase(" ") )
		   {
			   System.out.println("Please give an argument ...");
			   System.exit(0);
		   } 
		 
	        URL goEuroURL = new URL("http://api.goeuro.com/api/v2/position/suggest/en/"+args[0]);
	        URLConnection yc = null;
			try {
				yc = goEuroURL.openConnection();
			} catch (Exception e) {
				
				 System.out.println("Cannot open the connection " +e.getMessage());
			}
	               
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream(),"UTF-8")); // get response
	        String inputLine= null;
            String data = "";
	        while ((inputLine = in.readLine()) != null) 
	            data = data + inputLine ; // build String response
	        in.close();
	        if(data.equals("[]"))
	        {	
	        System.out.println("No results Founds .. Try another key"); // There is no results from the endpoint
	        }
	        else
	        {	
	        createResultFile(data);  // Create csv File with response's data
	        System.out.println("The file results.csv is succefully created");
	        }
	        
	
	    }
}
