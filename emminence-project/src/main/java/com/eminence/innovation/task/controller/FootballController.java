package com.eminence.innovation.task.controller;

import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("api/football")
public class FootballController {
	
	@Autowired
    private RestTemplate restTemplate;
	
     @Secured({"ROLE_USER", "ROLE_ADMIN"})
	 @GetMapping("/task1")
	    public CompletableFuture<ResponseEntity<?>> getDrawMatchesForYear(@RequestParam("year") int year) {
	        return CompletableFuture.supplyAsync(() -> {
	            try {
	               
	            	ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&page=1", String.class);
	                
	                if (responseEntity.getStatusCode() == HttpStatus.OK) {
	                    int drawMatchesCount = countDrawMatches(responseEntity.getBody());
	                    return ResponseEntity.ok(drawMatchesCount);
	                } else {
	                    return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
	                }
	            } catch (Exception e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
	            }
	        });
	    }
	    
        @PreAuthorize("hasRole('ROLE_USER')")
	    @GetMapping("/task2")
	    public CompletableFuture<ResponseEntity<?>> getDrawMatchesForYearDelayed(@RequestParam("year") int year) {
	        return CompletableFuture.supplyAsync(() -> {
	            try {
	               
	           ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://jsonmock.hackerrank.com/api/football_matches?year=" + year + "&page=1", String.class);
	                
	            if (responseEntity.getStatusCode() == HttpStatus.OK) {
	                    int drawMatchesCount = countDrawMatches(responseEntity.getBody());
	                    
	                  
	                 Thread.sleep((long) (Math.random() * 3000 + 3000)); 
	                 
	                    return ResponseEntity.ok(drawMatchesCount);
	                } else {
	                    return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
	                }
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
	            } catch (Exception e) {
	                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request");
	            }
	        });
	    }
       
    private int countDrawMatches(String responseBody) {
        int drawMatchesCount = 0;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = null;
			try {
				jsonNode = objectMapper.readTree(responseBody);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

            JsonNode dataNode = jsonNode.get("data");
       
            for (JsonNode matchNode : dataNode) {
                int team1Goals = matchNode.get("team1goals").asInt();
                int team2Goals = matchNode.get("team2goals").asInt();

                if (team1Goals == team2Goals) {
                    drawMatchesCount++;
                }
            }
        } catch (IOException  ex) {
            ex.printStackTrace(); 
        }

        return drawMatchesCount;
    }


}