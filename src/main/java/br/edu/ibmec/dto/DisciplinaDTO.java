package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Disciplina;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisciplinaDTO {
    private int codigo;
    private String nome;
    private int creditos;
    private int cursoId;

    public static DisciplinaDTO fromEntity(Disciplina disciplina) {
        DisciplinaDTO dto = new DisciplinaDTO();
        dto.setCodigo(disciplina.getCodigo());
        dto.setNome(disciplina.getNome());
        dto.setCreditos(disciplina.getCreditos());
        dto.setCursoId(disciplina.getCurso().getCodigo());

        return dto;
    }
}
