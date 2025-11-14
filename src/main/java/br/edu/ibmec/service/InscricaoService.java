package br.edu.ibmec.service;

import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.entity.Aluno;
import br.edu.ibmec.entity.Inscricao;
import br.edu.ibmec.entity.Turma;
import br.edu.ibmec.repository.AlunoRepository;
import br.edu.ibmec.repository.InscricaoRepository;
import br.edu.ibmec.repository.TurmaRepository;
import br.edu.ibmec.exception.EntidadeNaoEncontradaException;

import br.edu.ibmec.service.validacoesStrategy.inscricao.ValidacaoInscricao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscricaoService {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    @Autowired
    private List<ValidacaoInscricao> validacoes;

    public InscricaoDTO buscarInscricao(int codigo) {
        return inscricaoRepository.findById(codigo)
                .map(InscricaoDTO::fromEntity)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição com código " + codigo + " não encontrada."));
    }

    public List<InscricaoDTO> listarInscricoes() {
        return inscricaoRepository.findAll().stream()
                .map(InscricaoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public InscricaoDTO cadastrarInscricao(InscricaoDTO inscricaoDTO) {
        validarInscricao(inscricaoDTO);

        Inscricao novaInscricao = new Inscricao();

        novaInscricao.setCodigo(inscricaoDTO.getCodigo());

        atualizarEntidadeComDTO(novaInscricao, inscricaoDTO);

        inscricaoRepository.save(novaInscricao);

        return InscricaoDTO.fromEntity(novaInscricao);
    }

    public InscricaoDTO alterarInscricao(int codigo, InscricaoDTO inscricaoDTO) {
        Inscricao inscricaoExistente = inscricaoRepository.findById(codigo)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Inscrição com código " + codigo + " não encontrada para alteração."));

        validarInscricao(inscricaoDTO);

        atualizarEntidadeComDTO(inscricaoExistente, inscricaoDTO);

        inscricaoRepository.save(inscricaoExistente);

        return InscricaoDTO.fromEntity(inscricaoExistente);
    }

    public void removerInscricao(int codigo) {
        if (inscricaoRepository.findById(codigo).isEmpty()) {
            throw new EntidadeNaoEncontradaException("Inscrição com código " + codigo + " não encontrada para remoção.");
        }

        inscricaoRepository.deleteById(codigo);
    }

    private void validarInscricao(InscricaoDTO inscricaoDTO) {
        for (ValidacaoInscricao validacao : validacoes) {
            validacao.validar(inscricaoDTO);
        }
    }

    private void atualizarEntidadeComDTO(Inscricao inscricao, InscricaoDTO inscricaoDTO) {
        inscricao.setAvaliacao1(inscricaoDTO.getAvaliacao1());
        inscricao.setAvaliacao2(inscricaoDTO.getAvaliacao2());
        inscricao.setMedia(inscricaoDTO.getMedia());
        inscricao.setNumFaltas(inscricaoDTO.getNumFaltas());
        inscricao.setSituacao(inscricaoDTO.getSituacao());

        Aluno aluno = alunoRepository.findById(inscricaoDTO.getMatriculaAluno())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado."));

        Turma turma = turmaRepository.findById(inscricaoDTO.getTurmaId())
                .orElseThrow(() -> new EntidadeNaoEncontradaException("Turma não encontrada."));

        inscricao.setAluno(aluno);
        inscricao.setTurma(turma);
    }
}