package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static com.ciandt.feedfront.repositories.FileRepository.*;

public class Employee implements Serializable{
    private static final long serialVersionUID = -897856973823710492L;
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    private static String path = "src/main/resources/employees.txt"; //TODO: alterar de acordo com a sua implementação

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        if (nome.length() <= 2)
            throw new ComprimentoInvalidoException("Comprimento do nome deve ser maior que 2 caracteres.");
        if (sobrenome.length() <= 2)
            throw new ComprimentoInvalidoException("Comprimento do sobrenome deve ser maior que 2 caracteres.");

        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public static Employee salvarEmployee(Employee employee) throws EmailInvalidoException, IOException {
        List<Employee> employeeList = new ArrayList<>();

        if (isFile(path)){
            employeeList = listarEmployees();
            boolean isEmail = employeeList.stream()
                    .anyMatch(e -> e.email.equals(employee.email));
            if (isEmail) {
                throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
            }
            else {
                employeeList.add(employee);
                saveFile(path, employeeList);
            }
        } else {
            employeeList.add(employee);
            saveFile(path, employeeList);
        }
        return employee;
    }
    public static Employee atualizarEmployee(Employee employee) throws EmailInvalidoException, EmployeeNaoEncontradoException, ArquivoException {
//        update(path, employee);
        List<Employee> newEmployeeList;
        Employee oldEmployee;
        try {
            oldEmployee = findById(path, employee.getId());
            if (oldEmployee != null) {
                newEmployeeList = apagarEmployee(oldEmployee.getId());
                newEmployeeList.add(employee);
                saveFile(path, newEmployeeList);
            }
        } catch (IOException e) {
            throw new ArquivoException("");
        }

        return employee;
    }
    public static List<Employee> listarEmployees() throws ArquivoException {
        List<Employee> employeeList;

        employeeList = (ArrayList<Employee>) getFile(path);

        return employeeList;
    }
    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        List<Employee> employeeList;
        Employee employeeFound = null;

        if (isFile(path)){
            employeeList = listarEmployees();
            boolean isId = employeeList.stream()
                    .anyMatch(e -> e.id.equals(id));
            if (!isId) {
                throw new EmployeeNaoEncontradoException("Employee não encontrado");
            }
            else {
                employeeFound = findById(path, id);
            }
        }
        return employeeFound;
    }
    public static List<Employee> apagarEmployee(String id) throws EmployeeNaoEncontradoException, ArquivoException {
//        deleteById(path, id)
        List<Employee> fileList;
        List<Employee> newEmployeeList;
        try {
            fileList = (ArrayList<Employee>) getFile(path);

            newEmployeeList = fileList.stream()
                    .filter(x -> !id.equals(x.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            saveFile(path, newEmployeeList);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }
        return newEmployeeList;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        this.nome = nome;
    }

    public String getSobrenome() {
        return this.sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
