package fr.taqmac;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;

@SpringBootApplication(scanBasePackages = { "fr.taqmac" })
public class Main {

    public static void main(String[] args) {
        System.out.println("run");
        SpringApplication.run(Main.class, args);

    }


}
