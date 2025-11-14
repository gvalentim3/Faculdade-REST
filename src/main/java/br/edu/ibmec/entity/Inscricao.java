package br.edu.ibmec.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Inscricoes")
public class Inscricao {

    @Id
    @Column (name = "codigo")
    private int codigo;

    private float avaliacao1;
    private float avaliacao2;
    private float media;
    private int numFaltas;
    private String situacao;

    @ManyToOne
    @JoinColumn (name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn (name = "turma_id")
    private Turma turma;


}
