package br.edu.ibmec.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.ThreadLocalRandom;

import br.edu.ibmec.dto.AlunoResponseDTO;
import br.edu.ibmec.dto.AlunoRequestDTO;
import br.edu.ibmec.dto.EstadoCivilDTO;
import br.edu.ibmec.dto.MensalidadeDTO;
import br.edu.ibmec.entity.*;
import br.edu.ibmec.repository.AlunoRepository;
import br.edu.ibmec.repository.CursoRepository;
import br.edu.ibmec.repository.InscricaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ibmec.exception.RegraDeNegocioException;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InscricaoRepository inscricaoRepository;

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

    public MensalidadeDTO calcularMensalidade (String matriculaAluno) {
        final double VALOR_POR_CREDITO = 500.0;

        Aluno aluno = alunoRepository.findById(matriculaAluno)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno com matrícula " + matriculaAluno + " não encontrado."));

        Curso cursoAluno = aluno.getCurso();
        if (cursoAluno == null) {
            throw new RegraDeNegocioException("Aluno não está matriculado em nenhum curso.");
        }
        List<Inscricao> inscricoes = inscricaoRepository.findByAlunoMatricula(matriculaAluno);

        List<String> nomesDisciplinas = new ArrayList<>();

        int totalCreditos = 0;

        for (Inscricao inscricao : inscricoes) {

            Disciplina disciplina = inscricao.getTurma().getDisciplina();

            totalCreditos += disciplina.getCreditos();

            nomesDisciplinas.add(disciplina.getNome());
        }

        double valorTotal = totalCreditos * VALOR_POR_CREDITO;

        return new MensalidadeDTO(
                aluno.getMatricula(),
                aluno.getNome(),
                cursoAluno.getNome(),
                valorTotal,
                nomesDisciplinas
        );
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