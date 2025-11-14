package br.edu.ibmec.service;

import br.edu.ibmec.dto.ProfessorDTO;
import br.edu.ibmec.entity.Professor;
import br.edu.ibmec.repository.ProfessorRepository;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;
import br.edu.ibmec.exception.RegraDeNegocioException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {

    @Autowired
    private ProfessorRepository professorRepository;

    public ProfessorDTO buscarProfessor(int codigo) {
        return professorRepository.findById(codigo)
                .map(ProfessorDTO::fromEntity)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor com código " + codigo + " não encontrado."));
    }

    public List<ProfessorDTO> listarProfessores() {
        return professorRepository.findAll().stream()
                .map(ProfessorDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProfessorDTO cadastrarProfessor(ProfessorDTO professorDTO) {
        validarProfessor(professorDTO);

        if (professorRepository.findById(professorDTO.getCodigo()).isPresent()) {
            throw new RegraDeNegocioException("Já existe um professor com o código " + professorDTO.getCodigo());
        }

        Professor novoProfessor = new Professor();

        novoProfessor.setCodigo(professorDTO.getCodigo());

        atualizarEntidadeComDTO(novoProfessor, professorDTO);

        professorRepository.save(novoProfessor);

        return ProfessorDTO.fromEntity(novoProfessor);
    }

    public ProfessorDTO alterarProfessor(int codigo, ProfessorDTO professorDTO) {
        Professor professorExistente = professorRepository.findById(codigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Professor com código " + codigo + " não encontrado para alteração."));

        validarProfessor(professorDTO);

        atualizarEntidadeComDTO(professorExistente, professorDTO);

        professorRepository.save(professorExistente);

        return ProfessorDTO.fromEntity(professorExistente);
    }

    public void removerProfessor(int codigo) {
        if (professorRepository.findById(codigo).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Professor com código " + codigo + " não encontrado para remoção.");
        }

        professorRepository.deleteById(codigo);
    }

    private void validarProfessor(ProfessorDTO professorDTO) {
        if (professorDTO.getCodigo() <= 0) {
            throw new RegraDeNegocioException("O código do professor é inválido.");
        }
        if (professorDTO.getNome() == null || professorDTO.getNome().trim().isEmpty()) {
            throw new RegraDeNegocioException("O nome do professor não pode ser vazio.");
        }
        if (professorDTO.getCpf() == null || professorDTO.getCpf().trim().isEmpty()) {
            throw new RegraDeNegocioException("O CPF do professor não pode ser vazio.");
        }
    }

    private void atualizarEntidadeComDTO(Professor professor, ProfessorDTO professorDTO) {
        professor.setNome(professorDTO.getNome());
        professor.setDataNascimento(professorDTO.getDataNascimento());
        professor.setCpf(professorDTO.getCpf());
    }
}