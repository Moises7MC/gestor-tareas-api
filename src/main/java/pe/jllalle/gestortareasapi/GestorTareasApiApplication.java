package pe.jllalle.gestortareasapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GestorTareasApiApplication {

    public static void main(String[] args) {

        System.setProperty("java.awt.headless", "false");

        SpringApplication.run(GestorTareasApiApplication.class, args);

    }
}