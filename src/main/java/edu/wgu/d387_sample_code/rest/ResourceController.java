package edu.wgu.d387_sample_code.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;


@RestController
@RequestMapping("/resources")
@CrossOrigin
public class ResourceController {

    static ExecutorService executorService = newFixedThreadPool(2);

    @GetMapping("welcome")
    public ResponseEntity<List<String>> getWelcomeMessage(){
        Properties properties = new Properties();

        List<String> welcomeMessage = new ArrayList<>();

        executorService.execute(
                () -> {
                    try {
                        InputStream stream = new ClassPathResource("welcome_en_US.properties").getInputStream();
                        properties.load(stream);
                        welcomeMessage.add(properties.getProperty("welcome"));

                        System.out.println("Welcome message: " + properties.getProperty("welcome"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        executorService.execute(
                () -> {
                    try {
                        InputStream stream = new ClassPathResource("welcome_fr_CA.properties").getInputStream();
                        properties.load(stream);
                        welcomeMessage.add(properties.getProperty("welcome"));
                        System.out.println("Welcome message: " + properties.getProperty("welcome"));

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        return ResponseEntity.ok(welcomeMessage);
    }
}
