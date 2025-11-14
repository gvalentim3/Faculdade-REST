package br.edu.ibmec.service;

import br.edu.ibmec.dto.TurmaDTO;
import br.edu.ibmec.entity.Disciplina;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.entity.Turma;
import br.edu.ibmec.repository.DisciplinaRepository;
import br.edu.ibmec.repository.ProfessorRepository;
import br.edu.ibmec.repository.TurmaRepository;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;
import br.edu.ibmec.exception.RegraDeNegocioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private ProfessorRepository professorRepository;

    public TurmaDTO buscarTurma(int codigo) {
        return turmaRepository.findById(codigo)
                .map(TurmaDTO::fromEntity)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Turma com código " + codigo + " não encontrada."));
    }

    public List<TurmaDTO> listarTurmas() {
        return turmaRepository.findAll().stream()
                .map(TurmaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public TurmaDTO cadastrarTurma(TurmaDTO turmaDTO) {
        validarTurma(turmaDTO);

        Turma novaTurma = new Turma();

        atualizarEntidadeComDTO(novaTurma, turmaDTO);

        turmaRepository.save(novaTurma);

        return TurmaDTO.fromEntity(novaTurma);
    }

    public TurmaDTO alterarTurma(int codigo, TurmaDTO turmaDTO) {
        Turma turmaExistente = turmaRepository.findById(codigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Turma com código " + codigo + " não encontrada para alteração."));

        validarTurma(turmaDTO);

        atualizarEntidadeComDTO(turmaExistente, turmaDTO);

        turmaRepository.save(turmaExistente);

        return TurmaDTO.fromEntity(turmaExistente);
    }

    public void removerTurma(int codigo) {
        if (turmaRepository.findById(codigo).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Turma com código " + codigo + " não encontrada para remoção.");
        }

        turmaRepository.deleteById(codigo);
    }

    private void validarTurma(TurmaDTO turmaDTO) {
        if (turmaDTO.getCodigo() <= 0) {
            throw new RegraDeNegocioException("O código da turma deve ser um número positivo.");
        }
        if (turmaDTO.getSemestre() == null || turmaDTO.getSemestre().trim().isEmpty()) {
            throw new RegraDeNegocioException("O semestre da turma não pode ser vazio.");
        }

        if (!disciplinaRepository.existsById(turmaDTO.getDisciplinaId())) {
            throw new EntidadeNaoEncontradaException("Disciplina com código " + turmaDTO.getDisciplinaId() + " não encontrada.");
        }

        if (!professorRepository.existsById(turmaDTO.getProfessorId())) {
            throw new EntidadeNaoEncontradaException("Professor com código " + turmaDTO.getProfessorId() + " não encontrado.");
        }
    }

    private void atualizarEntidadeComDTO(Turma turma, TurmaDTO turmaDTO) {
        turma.setCodigo(turmaDTO.getCodigo());
        turma.setSemestre(turmaDTO.getSemestre());
        turma.setTurno(turmaDTO.getTurno());

        Disciplina disciplina = disciplinaRepository.findById(turmaDTO.getDisciplinaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Disciplina não encontrada."));

        Professor professor = professorRepository.findById(turmaDTO.getProfessorId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor não encontrado."));

        turma.setDisciplina(disciplina);
        turma.setProfessor(professor);
    }
}