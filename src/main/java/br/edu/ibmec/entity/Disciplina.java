package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Disciplinas")
public class Disciplina {

    @Id
    @Column (name = "codigo")
    private Integer codigo;

    @Column (name = "nome")
    private String nome;

    @Column (name = "creditos")
    private int creditos;

    @ManyToOne
    @JoinColumn (name = "curso_id")
    private Curso curso;

    @OneToMany (mappedBy = "disciplina")
    private List<Turma> turmas = new ArrayList<>();
}
