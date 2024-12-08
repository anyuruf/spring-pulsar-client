package net.anyuruf.pulsar_client;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.pulsar.client.api.Schema;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.pulsar.reactive.core.ReactivePulsarTemplate;
import org.springframework.stereotype.Component;

import net.anyuruf.pulsar_client.models.Item;
import net.anyuruf.pulsar_client.models.User;
import net.anyuruf.pulsar_client.service.CsvService;


@SpringBootApplication
public class PulsarClientApplication {
	
	

	
	public static void main(String[] args) {
		SpringApplication.run(PulsarClientApplication.class, args);
	}
	
	
	@Configuration(proxyBeanMethods = false)
	@Component
	static class ReactiveTemplateProducer {
		private final CsvService csvService;
		
		public ReactiveTemplateProducer(CsvService csvService) {
			this.csvService = csvService;
	
		}


    @Bean
    ApplicationRunner runner( CsvService csvService, ReactivePulsarTemplate<User> pulsarUserTemplate,
    	ReactivePulsarTemplate<Item> pulsarItemTemplate ) {
        
    	return (_) -> {
			try {
				UpLoadDataToPulsar(this.csvService, pulsarUserTemplate, pulsarItemTemplate);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};
    }
    
	}
    	
	public static void UpLoadDataToPulsar (CsvService csvService, ReactivePulsarTemplate<User> pulsarUserTemplate,
    	ReactivePulsarTemplate<Item> pulsarItemTemplate) throws IOException {
    	
    	List<User> userStream = CsvService.csvToUserList("data/users.csv");
    	List<Item> itemStream = CsvService.csvToItemList("data/items.csv");
    	Schema<User> userSchema = Schema.JSON(User.class);
    	Schema<Item> itemSchema = Schema.JSON(Item.class);
    	
	   
	   AtomicInteger userCounter = new AtomicInteger();
	   for (User user: userStream ) {
	       pulsarUserTemplate.newMessage(user).withTopic("users-topic")
	       .withSchema(userSchema).send().subscribe();
	       userCounter.incrementAndGet();
	   }
     
	  
	   AtomicInteger itemCounter = new AtomicInteger(); 
     for (Item item: itemStream) {
    	 pulsarItemTemplate.newMessage(item).withTopic("items-topic")
    	 .withSchema(itemSchema).send().subscribe();
         itemCounter.incrementAndGet();
      }   
       
       System.out.println("Reactive listener sent: {} users " + userCounter.get());
       System.out.println("Reactive listener sent: {} items " + itemCounter.get());
      
    }
    
}


