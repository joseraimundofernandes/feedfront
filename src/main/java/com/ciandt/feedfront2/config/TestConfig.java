package com.ciandt.feedfront2.config;

import com.ciandt.feedfront2.exceptions.ComprimentoInvalidoException;
import com.ciandt.feedfront2.models.Employee;
import com.ciandt.feedfront2.models.Feedback;
import com.ciandt.feedfront2.repositories.EmployeeRepository;
import com.ciandt.feedfront2.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;
import java.util.List;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private EmployeeRepository employeeRepositoryrepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Bean
    public void startDb() throws ComprimentoInvalidoException {
        Employee e1 = new Employee("Juquinha", "Braga", "bragajuca@email.com");
        Employee e2 = new Employee("Hermanoteu", "Godah", "godah@email.com");

        employeeRepositoryrepository.saveAll(List.of(e1, e2));

        Feedback f1 = new Feedback(
                LocalDate.now(),
                e1,
                e2,
                "Gostei muito do seu código",
                "Atributos com idiomas diferentes",
                "Estudar mais Clean Code");

        Feedback f2 = new Feedback(
                LocalDate.now(),
                e2,
                e1,
                "Acho que deve rever seus conceitos sobre trabalho em equipe",
                "Impaciente, às vezes",
                "Tomar suco de maracujá");

        feedbackRepository.saveAll(List.of(f1, f2));
    }
}
