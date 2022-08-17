package com.ciandt.feedfront2.repositories;

import com.ciandt.feedfront2.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: IMPLEMENTE A INTERFACE JPAREPOSITORY E MAPEIE A CLASSE PARA O SPRINGBOOT
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
}