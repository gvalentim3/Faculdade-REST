package br.edu.ibmec.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensalidadeDTO {
    private String matriculaAluno;
    private String alunoNome;
    private String cursoNome;
    private double valorTotalMensalidade;
    private List<String> disciplinasMatriculadas;
}