package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.cddframework.CDD;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoSecurityAudit() {
        return args -> {
            System.out.println("🚀 Démarrage de l'application cliente...");

            // 1. Initialisation (Va créer tests/ratel/security.ratel dans le dossier du
            // projet client)
            System.out.println("--- PHASE 1 : INITIALISATION ---");
            CDD.init();

            // 2. Exécution (Va lancer ratel.exe qui lira le fichier généré ci-dessus)
            System.out.println("--- PHASE 2 : AUDIT ---");
            CDD.run();
        };
    }

}
