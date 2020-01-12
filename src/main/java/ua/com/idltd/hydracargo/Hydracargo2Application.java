package ua.com.idltd.hydracargo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.ApiContextInitializer;
import ua.com.idltd.hydracargo.repository.CustomRepositoryImpl;

import javax.annotation.PostConstruct;


@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(repositoryBaseClass = CustomRepositoryImpl.class)
public class Hydracargo2Application {

    public static void main(String[] args) {
//        ApiContextInitializer.init();
        SpringApplication.run(Hydracargo2Application.class, args);
    }

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();
    }
}
