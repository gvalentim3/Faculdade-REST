package br.edu.ibmec.dto;

import br.edu.ibmec.entity.Inscricao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscricaoDTO {

    private int codigo;
    private float avaliacao1;
    private float avaliacao2;
    private float media;
    private int numFaltas;
    private String situacao;
    private String matriculaAluno;
    private int turmaId;

    public static InscricaoDTO fromEntity(Inscricao inscricao) {
        InscricaoDTO dto = new InscricaoDTO();
        dto.setCodigo(inscricao.getCodigo());
        dto.setAvaliacao1(inscricao.getAvaliacao1());
        dto.setAvaliacao2(inscricao.getAvaliacao2());
        dto.setMedia(inscricao.getMedia());
        dto.setNumFaltas(inscricao.getNumFaltas());
        dto.setSituacao(inscricao.getSituacao());

        if (inscricao.getAluno() != null) {
            dto.setMatriculaAluno(inscricao.getAluno().getMatricula());
        }
        if (inscricao.getTurma() != null) {
            dto.setTurmaId(inscricao.getTurma().getCodigo());
        }

        return dto;
    }
}