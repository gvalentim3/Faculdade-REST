package br.edu.ibmec.service;

import br.edu.ibmec.dto.CursoDTO;
import br.edu.ibmec.dto.DisciplinaDTO;
import br.edu.ibmec.entity.Curso;
import br.edu.ibmec.entity.Disciplina;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;
import br.edu.ibmec.exception.RegraDeNegocioException;
import br.edu.ibmec.repository.CursoRepository;
import br.edu.ibmec.repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private CursoRepository cursoRepository;

    public DisciplinaDTO buscarDisciplina(int codigo) {
        return disciplinaRepository.findById(codigo)
                .map(DisciplinaDTO::fromEntity)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Disciplina com código " + codigo + " não encontrada."));
    }

    public List<DisciplinaDTO> listarDisciplinas() {
        List<Disciplina> disciplinas = disciplinaRepository.findAll();
        return disciplinas.stream().map(DisciplinaDTO::fromEntity).collect(Collectors.toList());
    }

    public DisciplinaDTO cadastrarDisciplina(DisciplinaDTO disciplinaDTO) {
        validarDisciplina(disciplinaDTO);

        if (disciplinaRepository.findById(disciplinaDTO.getCodigo()).isPresent()) {
            throw new RegraDeNegocioException("Já existe uma disciplina com o código " + disciplinaDTO.getCodigo());
        }

        Disciplina novaDisciplina = new Disciplina();

        novaDisciplina.setCodigo(disciplinaDTO.getCodigo());

        atualizarEntidadeComDTO(novaDisciplina, disciplinaDTO);

        disciplinaRepository.save(novaDisciplina);

        return DisciplinaDTO.fromEntity(novaDisciplina);
    }

    public DisciplinaDTO alterarDisciplina(int codigo, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplinaExistente = disciplinaRepository.findById(codigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Disciplina com código " + codigo + " não encontrada para alteração."));

        validarDisciplina(disciplinaDTO);

        atualizarEntidadeComDTO(disciplinaExistente, disciplinaDTO);

        disciplinaRepository.save(disciplinaExistente);

        return DisciplinaDTO.fromEntity(disciplinaExistente);
    }

    public void removerDisciplina(int codigo) {
        if (disciplinaRepository.findById(codigo).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Disciplina com código " + codigo + " não encontrada para remoção.");
        }

        disciplinaRepository.deleteById(codigo);
    }

    private void validarDisciplina(DisciplinaDTO disciplinaDTO) {
        if (disciplinaDTO.getCodigo() <= 0) {
            throw new RegraDeNegocioException("O código da disciplina é inválido.");
        }
        if (disciplinaDTO.getNome() == null || disciplinaDTO.getNome().trim().isEmpty()) {
            throw new RegraDeNegocioException("O nome da disciplina não pode ser vazio.");
        }
        if (disciplinaDTO.getCreditos() <= 0) {
            throw new RegraDeNegocioException("A disciplina deve ter ao menos 1 crédito.");
        }

        if (!cursoRepository.existsById(disciplinaDTO.getCursoId())) {
            throw new EntidadeNaoEncontradaException("O Curso com código " + disciplinaDTO.getCursoId() + " não existe.");
        }
    }

    private void atualizarEntidadeComDTO(Disciplina disciplina, DisciplinaDTO disciplinaDTO) {
        disciplina.setNome(disciplinaDTO.getNome());
        disciplina.setCreditos(disciplinaDTO.getCreditos());

        Curso curso = cursoRepository.findById(disciplinaDTO.getCursoId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Curso com código " + disciplinaDTO.getCursoId() + " não encontrado."));

        disciplina.setCurso(curso);
    }
}
