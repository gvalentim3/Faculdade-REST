package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Professor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {

    private int codigo;
    private String nome;
    private LocalDate dataNascimento;
    private String cpf;

    public static ProfessorDTO fromEntity(Professor professor) {
        ProfessorDTO dto = new ProfessorDTO();
        dto.setCodigo(professor.getCodigo());
        dto.setNome(professor.getNome());
        dto.setDataNascimento(professor.getDataNascimento());
        dto.setCpf(professor.getCpf());
        return dto;
    }
}