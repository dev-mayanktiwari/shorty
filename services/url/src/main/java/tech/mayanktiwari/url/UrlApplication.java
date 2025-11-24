package tech.mayanktiwari.url;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "tech.mayanktiwari.auth", "tech.mayanktiwari.common", "tech.mayanktiwari.database" })
@EnableJpaRepositories(basePackages = "tech.mayanktiwari.database.repositories")
@EntityScan(basePackages = "tech.mayanktiwari.database.entities")
public class UrlApplication {
    public static void main(String[] args) {
        SpringApplication.run(UrlApplication.class, args);
    }
}
