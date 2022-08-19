package com.ciandt.feedfront2.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    @Length(min = 3, message = "A descrição deve ter mais de 2 caracteres")
    private String descricao;
    @Column
    private String oqueMelhora;
    @Column
    private String comoMelhora;
    @Column(nullable = false)
    private LocalDate data;
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Employee autor;
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proprietario_id", nullable = false)
    private Employee proprietario;

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao) {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
    }

    public Feedback(LocalDate data, Employee autor, Employee proprietario, String descricao, String oqueMelhora, String comoMelhora) {
        setData(data);
        setAutor(autor);
        setProprietario(proprietario);
        setDescricao(descricao);
        setoqueMelhora(oqueMelhora);
        setComoMelhora(comoMelhora);
    }

    private void setoqueMelhora(String oqueMelhora) {
        this.oqueMelhora = oqueMelhora;
    }

    private String getoqueMelhora(String oqueMelhora) {
        return this.oqueMelhora;
    }

}
