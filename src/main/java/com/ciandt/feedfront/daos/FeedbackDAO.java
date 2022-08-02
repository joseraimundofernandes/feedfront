package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedbackDAO implements DAO<Feedback> {

    private String repositorioPath = "src/main/resources/data/feedback/";
    @Override
    public boolean tipoImplementaSerializable() {return Employee.class instanceof Serializable;}

    public static ObjectOutputStream getOutputStream(String arquivo) throws IOException {
        return new ObjectOutputStream(new FileOutputStream(arquivo));
    }

    public static ObjectInputStream getInputStream(String arquivo) throws IOException {
        return new ObjectInputStream(new FileInputStream(arquivo));
    }

    @Override
    public List<Feedback> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Feedback> feedbacks = new ArrayList<>();

        try {
            Stream<Path> paths = Files.walk(Paths.get(repositorioPath));

            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());

            for (String file: files) {
                feedbacks.add(buscar(file));
            }

            paths.close();

        } catch (IOException e) {
            throw new ArquivoException("erro ao processar arquivos");
        }

        return feedbacks;
    }

    @Override
    public Feedback buscar(String id) throws IOException, EntidadeNaoSerializavelException {
        Feedback feedback;
        ObjectInputStream inputStream;

        try {
            inputStream = getInputStream(repositorioPath + id + ".byte");
            feedback = (Feedback) inputStream.readObject();

            inputStream.close();

        } catch (IOException | ClassNotFoundException e) {
            throw new IOException("");
        }

        return feedback;
    }

    @Override
    public boolean isEmailExistente(Feedback feedback) {
        return false;
    }

    @Override
    public Feedback salvar(Feedback feedback) throws IOException, EntidadeNaoSerializavelException {
        if (!this.tipoImplementaSerializable()) {
            throw new EntidadeNaoSerializavelException("Feedback não é serializável");
        }

        ObjectOutputStream outputStream;

        try {
            outputStream = getOutputStream(repositorioPath + feedback.getArquivo());
            outputStream.writeObject(feedback);

            outputStream.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new ArquivoException("");
        }

        return feedback;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException, EmployeeNaoEncontradoException {
        return false;
    }
}
