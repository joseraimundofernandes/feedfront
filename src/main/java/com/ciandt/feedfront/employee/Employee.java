package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Employee implements Serializable{
    private static final long serialVersionUID = -897856973823710492L;
    private String id;
    private String nome;
    private String sobrenome;
    private String email;
    private static String arquivoCriado = "employees.ser"; //TODO: alterar de acordo com a sua implementação
    private static File file = null;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        if (nome.length() <= 2 || sobrenome.length() <= 2)
            throw new ComprimentoInvalidoException("O nome e sobrenome devem ter mais do que 2 caracteres");

        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;

        this.file = new File(arquivoCriado);
    }

    public static Employee salvarEmployee(Employee employee) throws EmailInvalidoException, IOException {
        List<Employee> employeeList;

        employeeList = listarEmployees();
        employeeList.add(employee);
        saveFile(employeeList);

        return employee;
    }

    public static Employee saveFile(List<Employee> object) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(new BufferedOutputStream(fos));
        oos.writeObject(object);
        oos.flush();
        fos.flush();

        oos.close();
        fos.close();

        return null;
    }

    public static Employee atualizarEmployee(Employee employee) throws EmailInvalidoException, EmployeeNaoEncontradoException, ArquivoException {
        try {
            Employee OldEmployee = buscarEmployee(employee.getId());
            if (OldEmployee != null) {
                apagarEmployee(OldEmployee.getId());
                salvarEmployee(employee);
            }
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return employee;
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        ArrayList<Employee> employeeList = new ArrayList<>();
        try {

            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(file)));
            employeeList = (ArrayList<Employee>) ois.readObject();
            ois.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employeeList;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        ArrayList<Employee> employeeList;
        Optional<Employee> employeeFound = null;

        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            employeeList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            employeeFound = Optional.ofNullable(employeeList.stream()
                    .filter(x -> id.equals(x.getId()))
                    .findAny()
                    .orElse(null));

            if (employeeFound.get() == null) {
                throw new EmployeeNaoEncontradoException("Employee não encontrado");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return employeeFound.get();
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        ArrayList<Employee> employeeList;
        try {
            FileInputStream fos = new FileInputStream(file);
            ois = new ObjectInputStream(new BufferedInputStream(fos));
            employeeList = (ArrayList<Employee>) ois.readObject();
            ois.close();

            List<Employee> newEmployeeList = employeeList.stream()
                    .filter(x -> !id.equals(x.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            if (newEmployeeList.size() == employeeList.size()) {
                throw new EmployeeNaoEncontradoException("Employee não existe no repositório");
            }

            saveFile(newEmployeeList);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
