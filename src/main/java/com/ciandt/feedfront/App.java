package com.ciandt.feedfront;

import com.ciandt.feedfront.employee.Employee;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ComprimentoInvalidoException, EmailInvalidoException, ArquivoException, EmployeeNaoEncontradoException {

        Employee novoEmployee1 = new Employee("Joao","Santos","joaosantos@mail.com");
        Employee novoEmployee2 = new Employee("Maria","Duarte","mariaduarte@mail.com");
        Employee novoEmployee3 = new Employee("Matheus","Cavalcante","matheuscavalcante@mail.com");
        Employee novoEmployee4 = new Employee("Daniel","Cerqueira","danielcerqueira@mail.com");
        Employee novoEmployee5 = new Employee("Rafael","Lopes","rafaellopes@mail.com");
        Employee novoEmployee6 = new Employee("Thiago","Dutra","thiagodutra@mail.com");

        System.out.println("-------------------------------");
        try {
            Employee.salvarEmployee(novoEmployee1);
            Employee.salvarEmployee(novoEmployee2);
            Employee.salvarEmployee(novoEmployee3);
            Employee.salvarEmployee(novoEmployee4);
            Employee.salvarEmployee(novoEmployee5);
            Employee.salvarEmployee(novoEmployee6);
            System.out.println("Criacao de Employee feito com sucesso!");
        } catch (EmailInvalidoException | ArquivoException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("-------------------------------");
        System.out.println("Lista de Employees:");
        Employee.listarEmployees().forEach(employee -> System.out.println(employee.toString()));

        System.out.println("-------------------------------");

        try {
            System.out.println("Busca com Employee com id: " + novoEmployee1.getId());
            System.out.println(Employee.buscarEmployee(novoEmployee1.getId()).toString());

        } catch (EmployeeNaoEncontradoException | ArquivoException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------------------------");
        try {
            novoEmployee1.setNome("Jose");
            novoEmployee1.setSobrenome("da Silva");

            Employee atualizaEmployee = Employee.atualizarEmployee(novoEmployee1);
            System.out.println("Employee Atualizado: "+ atualizaEmployee.toString());
        } catch (EmailInvalidoException | ArquivoException ex) {
            System.out.println(ex.getMessage());
        }

        System.out.println("-------------------------------");
        try {
            Employee.apagarEmployee(novoEmployee1.getId());
            System.out.println("Cadastro Deletado com sucesso!");
        } catch (EmployeeNaoEncontradoException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
