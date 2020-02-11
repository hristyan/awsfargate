package de.mathema.codedays.greetings.provider;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin
public class GreetingService {

    private final AmazonDynamoDB dynamoDB;
    private final String TABLE_NAME = "greetings";

    public GreetingService() {
        dynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withRegion("eu-central-1")
                .build();
    }

    @GetMapping("/greetings")
    public Map<String, Collection<Greeting>> listGreetings() {

        ScanRequest scanRequest = new ScanRequest()
                .withTableName(TABLE_NAME);

        Map<String, Greeting> greetings = new HashMap<>();

        ScanResult result = dynamoDB.scan(scanRequest);
        for (Map<String, AttributeValue> item : result.getItems()){
            Greeting greeting = new Greeting(item.get("type").getS(), item.get("phrase").getS());
            greetings.put(item.get("type").getS(), greeting);
        }

        return Collections.singletonMap("greetings", greetings.values());
    }

    @PutMapping("/greetings/{type}")
    public void saveGreeting(@PathVariable("type") String type, @RequestBody Greeting greeting) {
        Map<String, AttributeValue> item = new HashMap<String, AttributeValue>();
        item.put("type", new AttributeValue(greeting.getType()));
        item.put("phrase", new AttributeValue(greeting.getPhrase()));

        PutItemRequest putItemRequest = new PutItemRequest(TABLE_NAME, item);
        PutItemResult putItemResult = dynamoDB.putItem(putItemRequest);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello there, General Kenobi!";
    }

    @GetMapping("/loop")
    public Map<String, Collection<Greeting>> loop() {
        Map<String, Greeting> greetings = new HashMap<>();

        boolean flag = true;
        while (flag) {}

        return Collections.singletonMap("greetings", greetings.values());
    }
}