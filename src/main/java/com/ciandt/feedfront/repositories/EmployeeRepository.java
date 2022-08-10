package com.ciandt.feedfront.repositories;

import com.ciandt.feedfront.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: IMPLEMENTE A INTERFACE JPAREPOSITORY E MAPEIE A CLASSE PARA O SPRINGBOOT
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    String findByEmail(String email);
}