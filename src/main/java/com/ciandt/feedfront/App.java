package com.ciandt.feedfront;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.daos.FeedbackDAO;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.services.EmployeeService;
import com.ciandt.feedfront.services.FeedbackService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws BusinessException, IOException, EmployeeNaoEncontradoException {
        System.out.println( "Hello World!" );
        //Domínio Employee
        EmployeeService employeeService = new EmployeeService();
        employeeService.setDAO(new EmployeeDAO());

        Employee employee1 = new Employee("João", "Silveira", "j.silveira@email.com");
        Employee employee2 = new Employee("José", "Raimundo", "j.raimundo@email.com");
        //salvar
        employeeService.salvar(employee1);
        employeeService.salvar(employee2);
        //lista
        List employees = employeeService.listar();
        employees.stream().forEach(System.out::println);

        //busca
        Employee buscaEmployee = employeeService.buscar(employee2.getId());
        System.out.println("busca");
        System.out.println(buscaEmployee);

        //alterar
        employee2.setSobrenome("Fernandes");
        Employee atualizado = employeeService.atualizar(employee2);
        System.out.println("altera");
        System.out.println(atualizado);

        //deletar
        employeeService.apagar(atualizado.getId());
        List employeesAtualizados = employeeService.listar();
        System.out.println("apaga");
        employeesAtualizados.stream().forEach(System.out::println);

        //Feedback
        FeedbackService feedbackService = new FeedbackService();
        feedbackService.setDAO(new FeedbackDAO());
        Service<Employee> employeeService2 = new EmployeeService();
        DAO<Employee> employeeDAO = new EmployeeDAO();
        employeeService2.setDAO(employeeDAO);
        feedbackService.setEmployeeService(employeeService2);

        LocalDate localDate = LocalDate.now();
        String LOREM_IPSUM_FEEDBACK = "Lorem Ipsum is simply dummy text of the printing and typesetting industry";
        Employee autor = new Employee("João", "Silveira", "j.silveira@email.com");
        Employee proprietario = new Employee("Mateus", "Santos", "m.santos@email.com");

        Feedback feedback = new Feedback(localDate, autor, proprietario, LOREM_IPSUM_FEEDBACK);

        //salvar
        employeeService.salvar(proprietario);
        Feedback f = feedbackService.salvar(feedback);
        System.out.println(f);
    }
}
