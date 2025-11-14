package br.edu.ibmec.resource;

import br.edu.ibmec.dto.InscricaoDTO;
import br.edu.ibmec.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoResource {

    @Autowired
    private InscricaoService inscricaoService;

    @GetMapping(path = "/{codigo}", produces = "application/json")
    public ResponseEntity<InscricaoDTO> buscarInscricaoPeloCodigo(@PathVariable int codigo) {
        InscricaoDTO inscricaoDTO = inscricaoService.buscarInscricao(codigo);
        return ResponseEntity.ok(inscricaoDTO);
    }

    @PostMapping
    public ResponseEntity<InscricaoDTO> cadastrarInscricao(@RequestBody InscricaoDTO inscricaoDTO) {
        InscricaoDTO inscricaoSalva = inscricaoService.cadastrarInscricao(inscricaoDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{codigo}")
                .buildAndExpand(inscricaoSalva.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(inscricaoSalva);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<InscricaoDTO> alterarInscricao(@PathVariable int codigo, @RequestBody InscricaoDTO inscricaoDTO) {
        InscricaoDTO inscricaoAtualizada = inscricaoService.alterarInscricao(codigo, inscricaoDTO);
        return ResponseEntity.ok(inscricaoAtualizada);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> removerInscricao(@PathVariable int codigo) {
        inscricaoService.removerInscricao(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<InscricaoDTO>> listarInscricoes() {
        List<InscricaoDTO> listaInscricoes = inscricaoService.listarInscricoes();
        return ResponseEntity.ok(listaInscricoes);
    }
}