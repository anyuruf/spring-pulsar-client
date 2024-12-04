package net.anyuruf.pulsar_client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;

import net.anyuruf.pulsar_client.models.Item;
import net.anyuruf.pulsar_client.models.User;
import net.anyuruf.pulsar_client.service.CsvService;


@SpringBootApplication
public class PulsarClientApplication {
	
	private final CsvService csvService;
	private final ReactivePulsarTemplate<User> pulsarUserTemplate;
	private final ReactivePulsarTemplate<Item> pulsarItemTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(PulsarClientApplication.class, args);
	}
	
	public PulsarClientApplication( CsvService csvService, ReactivePulsarTemplate<User> pulsarUserTemplate,
	    	ReactivePulsarTemplate<Item> pulsarItemTemplate ) {
		this.csvService = csvService;
		this.pulsarUserTemplate = pulsarUserTemplate;
		this.pulsarItemTemplate = pulsarItemTemplate;
		
	}
    
    
    @Bean
    ApplicationRunner runner( CsvService csvService, ReactivePulsarTemplate<User> pulsarUserTemplate,
    	ReactivePulsarTemplate<Item> pulsarItemTemplate ) {
        
    	return (args) -> UpLoadDataToPulsar(this.csvService, this.pulsarUserTemplate, this.pulsarItemTemplate);
    }
    	
    public void UpLoadDataToPulsar (CsvService csvService, ReactivePulsarTemplate<User> pulsarUserTemplate,
    	ReactivePulsarTemplate<Item> pulsarItemTemplate) throws IOException {
      
    	
    	List<User> userStream = CsvService.csvToUserList("data/users.csv");
    	List<Item> itemStream = CsvService.csvToItemList("data/items.csv");
    	
	   
	   AtomicInteger userCounter = new AtomicInteger();
	   for (User user: userStream ) {
	       pulsarUserTemplate.send("users-topic",user);
	       userCounter.incrementAndGet();
	   }
     
	  
	   AtomicInteger itemCounter = new AtomicInteger(); 
     for (Item item: itemStream) {
         pulsarItemTemplate.send("items-topic", item);
         itemCounter.incrementAndGet();
      }   
       
       System.out.println("Reactive listener sent: {} users" + userCounter.get());
       System.out.println("Reactive listener sent: {} items" + itemCounter.get());
      
    }
    
}


