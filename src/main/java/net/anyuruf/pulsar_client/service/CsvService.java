package net.anyuruf.pulsar_client.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.anyuruf.pulsar_client.models.Item;
import net.anyuruf.pulsar_client.models.User;

@Service
public class CsvService {
        public static String TYPE = "text/csv";
        private static ResourceLoader resourceLoader;
        
        public CsvService(ResourceLoader resourceLoader) {
        	CsvService.resourceLoader = resourceLoader;
        }
          public static boolean hasCsvFormat(MultipartFile file) {
            if (!TYPE.equals(file.getContentType())) {
              return false;
            }
            return true;
          }
          
          public static List<User> csvToUserList(String path) throws IOException {
        	  Resource userResource = resourceLoader.getResource("classpath:" + path);
        	  BufferedReader reader= null;
			try {
				reader = new BufferedReader(
						  new InputStreamReader(userResource.getInputStream(), StandardCharsets.UTF_8));
			} catch (IOException e) {
					
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
			if(reader != null) {
        	  Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.builder().setHeader().
        			  setSkipHeaderRecord(true).build().parse(reader);
				  List<User> userList = new ArrayList<User>();
				  for (CSVRecord csvRec : csvRecords) {
					 Long id = Long.parseLong(csvRec.get(0));
					 String first_name = csvRec.get(1);
					 String last_name  = csvRec.get(2);
					 String email_address = csvRec.get(3);
					 Long date = Long.parseLong(csvRec.get(4));
					 Long deleted_at = Long.parseLong(csvRec.get(5));
					 Long modified_at = Long.parseLong(csvRec.get(6));
					 Long parent_id = Long.parseLong(csvRec.get(7));
					 
					 
				     User user = new User(id, first_name, last_name, email_address,
				    		date, deleted_at, modified_at, parent_id);
				
				     userList.add(user);
				  }
				  return userList;
				}
				return null;
               
          }
            
    		
    public static List<Item> csvToItemList(String path) throws IOException {
    	Resource itemResource = resourceLoader.getResource("classpath:" + path);
    	
  	  	BufferedReader reader = null;
		try {
			reader = new BufferedReader(
				  new InputStreamReader(itemResource.getInputStream(), StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (reader != null) {
  	  
	    Iterable<CSVRecord> csvRecords = CSVFormat.RFC4180.builder().setHeader()
	    		.setSkipHeaderRecord(true).build().parse(reader);
		  List<Item> itemList = new ArrayList<Item>();
		  for (CSVRecord csvRec : csvRecords) {
			  Long id = Long.parseLong(csvRec.get(0));
			  LocalDateTime at_created = LocalDateTime.parse(csvRec.get(1).replace( " " , "T" ));
			  Long created_at = at_created.getLong(ChronoField.EPOCH_DAY);
			  String adjective = csvRec.get(3);
			  String category  = csvRec.get(4);
		      String modifier = csvRec.get(5);
			  String name = csvRec.get(6);
		      Double price = Double.parseDouble(csvRec.get(7));
				 
		      Item item = new Item(id, created_at, adjective, category,
		    		 modifier,name,  price);
		       itemList.add(item);
		  }
		  return itemList;
		}
		
		return null;
    }
}

