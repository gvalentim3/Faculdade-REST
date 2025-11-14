package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Turma;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TurmaDTO {

    private int codigo;
    private String semestre;
    private String turno;
    private Integer disciplinaId;
    private int professorId;

    public static TurmaDTO fromEntity(Turma turma) {
        TurmaDTO dto = new TurmaDTO();
        dto.setCodigo(turma.getCodigo());
        dto.setSemestre(turma.getSemestre());
        dto.setTurno(turma.getTurno());

        if (turma.getDisciplina() != null) {
            dto.setDisciplinaId(turma.getDisciplina().getCodigo());
        }
        if (turma.getProfessor() != null) {
            dto.setProfessorId(turma.getProfessor().getCodigo());
        }

        return dto;
    }
}