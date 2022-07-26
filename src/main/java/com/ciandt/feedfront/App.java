package com.ciandt.feedfront;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.services.EmployeeService;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws BusinessException, IOException, EmployeeNaoEncontradoException {
        System.out.println( "Hello World!" );

        EmployeeService employeeService = new EmployeeService();
        employeeService.setDAO(new EmployeeDAO());

        Employee employee1 = new Employee("João", "Silveira", "j.silveira@email.com");
        Employee employee2 = new Employee("José", "Raimundo", "j.raimundo@email.com");

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

    }
}
