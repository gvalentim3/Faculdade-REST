package br.edu.ibmec.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;

import br.edu.ibmec.dao.UniversidadeDAO;
import br.edu.ibmec.dto.AlunoResponseDTO;
import br.edu.ibmec.dto.AlunoRequestDTO;
import br.edu.ibmec.dto.EstadoCivilDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Curso;
import br.edu.ibmec.entity.EstadoCivil;
import br.edu.ibmec.repository.AlunoRepository;
import br.edu.ibmec.repository.CursoRepository;
import org.springframework.stereotype.Service;

import br.edu.ibmec.exception.RegraDeNegocioException;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;

@Service
public class AlunoService {
	private AlunoRepository alunoRepository;
    private CursoRepository cursoRepository;

    public AlunoService(AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    public AlunoResponseDTO buscarAluno(String matricula) {
        return alunoRepository.findById(matricula)
                .map(AlunoResponseDTO::fromEntity)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno com matrícula " + matricula + " não encontrado"));
    }

    public List<AlunoResponseDTO> listarAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream().map(AlunoResponseDTO::fromEntity).collect(Collectors.toList());
    }

    public AlunoResponseDTO cadastrarAluno(AlunoRequestDTO alunoRequestDTO) {
        validarDadosDoAluno(alunoRequestDTO);

        Aluno novoAluno = new Aluno();

        String novaMatricula = gerarMatriculaUnica();
        novoAluno.setMatricula(novaMatricula);

        atualizarEntidadeComDTO(novoAluno, alunoRequestDTO);

        alunoRepository.save(novoAluno);

        return AlunoResponseDTO.fromEntity(novoAluno);
    }

    public AlunoResponseDTO alterarAluno(String matricula, AlunoRequestDTO alunoRequestDTO) {
        Aluno alunoExistente = alunoRepository.findById(matricula)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno com matrícula " + matricula + " não encontrado para atualização."));

        validarDadosDoAluno(alunoRequestDTO);
        atualizarEntidadeComDTO(alunoExistente, alunoRequestDTO);

        alunoRepository.save(alunoExistente);

        return AlunoResponseDTO.fromEntity(alunoExistente);
    }

    public void removerAluno(String matricula) {
        if (alunoRepository.findById(matricula).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Aluno com matrícula " + matricula + " não encontrado para remoção.");
        }

        alunoRepository.deleteById(matricula);
    }

    private void validarDadosDoAluno(AlunoRequestDTO dto) {
        if (dto.getNome() == null || dto.getNome().trim().length() < 1 || dto.getNome().length() > 20) {
            throw new RegraDeNegocioException("O nome do aluno é obrigatório e deve ter entre 1 e 20 caracteres.");
        }
        if (dto.getDataNascimento() == null) {
            throw new RegraDeNegocioException("A data de nascimento é obrigatória.");
        }
    }

    private void atualizarEntidadeComDTO(Aluno aluno, AlunoRequestDTO alunoRequestDTO) {
        aluno.setNome(alunoRequestDTO.getNome());
        aluno.setDataNascimento(alunoRequestDTO.getDataNascimento());
        aluno.setMatriculaAtiva(alunoRequestDTO.isMatriculaAtiva());


        EstadoCivil estadoCivil = converterEstadoCivil(alunoRequestDTO.getEstadoCivil());
        aluno.setEstadoCivil(estadoCivil);

        if (aluno.getCurso() == null || aluno.getCurso().getCodigo() != alunoRequestDTO.getCurso()) {
            Curso novoCurso = cursoRepository.findById(alunoRequestDTO.getCurso())
                    .orElseThrow(() -> new RegraDeNegocioException("O curso com código " + alunoRequestDTO.getCurso() + " não foi encontrado."));

            aluno.setCurso(novoCurso);
        }
    }

    private String gerarMatriculaUnica() {
        LocalDate agora = LocalDate.now();

        int anoAtual = agora.getYear();
        int mesAtual = agora.getMonthValue();

        String mesFormatado = String.format("%02d", mesAtual);

        int numeroAleatorio = ThreadLocalRandom.current().nextInt(10000, 99999);

        return "" + anoAtual + mesFormatado + numeroAleatorio;
    }

    private EstadoCivil converterEstadoCivil(EstadoCivilDTO estadoCivilDTO) {
        if (estadoCivilDTO == null) {
            return null;
        }
        return EstadoCivil.valueOf(estadoCivilDTO.name());
    }
}