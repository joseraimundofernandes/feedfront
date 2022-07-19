package com.ciandt.feedfront.repositories;

import com.ciandt.feedfront.employee.Employee;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository {
    public static Boolean isFile(String path) {
        File f = new File(path);
        return (f.isFile() && f.canRead()) ? true : false;
    }
    public static void saveFile(String path, List<Employee> employees) throws IOException {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(employees);
            oos.flush();
            fos.flush();
            fos.close();
            oos.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }
    public static Object getFile(String path) throws ArquivoException {
        Object file;
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            file = ois.readObject();
            fis.close();
            ois.close();
        }catch (IOException | ClassNotFoundException e) {
            throw new ArquivoException("");
        }
        return file;
    }
    public static Employee findById(String path, String id) throws ArquivoException, EmployeeNaoEncontradoException {
        List<Employee> fileList;
        Optional<Employee> objectFound;
        try {
            fileList = (ArrayList<Employee>) getFile(path);

            objectFound = Optional.ofNullable(fileList.stream()
                    .filter(x -> id.equals(x.getId()))
                    .findAny()
                    .orElse(null));
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return objectFound.get();
    }
    public static Employee update(String path, Employee employee) throws ArquivoException {
        List<Employee> newEmployeeList;
        Employee OldEmployee;
        try {
            OldEmployee = findById(path, employee.getId());
            if (OldEmployee != null) {
                newEmployeeList = deleteById(path, OldEmployee.getId());
                newEmployeeList.add(employee);
                saveFile(path, newEmployeeList);
            }
        } catch (IOException e) {
            throw new ArquivoException("");
        } catch (EmployeeNaoEncontradoException e) {
            throw new RuntimeException(e);
        }
        return OldEmployee;
    }
    public static List<Employee> deleteById(String path, String id) throws ArquivoException {
        List<Employee> fileList;
        List<Employee> newEmployeeList;
        try {
            fileList = (ArrayList<Employee>) getFile(path);

            newEmployeeList = fileList.stream()
                    .filter(x -> !id.equals(x.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            saveFile(path, newEmployeeList);

        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return newEmployeeList;
    }
}
