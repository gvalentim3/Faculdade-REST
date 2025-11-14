package br.edu.ibmec.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.edu.ibmec.service.DisciplinaService;
import br.edu.ibmec.dto.DisciplinaDTO;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaResource {

    @Autowired
    private DisciplinaService disciplinaService;

    @GetMapping(path = "/{codigo}", produces = "application/json")
    public ResponseEntity<DisciplinaDTO> buscarDisciplinaPeloCodigo(@PathVariable int codigo) {
        DisciplinaDTO disciplinaDTO = disciplinaService.buscarDisciplina(codigo);
        return ResponseEntity.ok(disciplinaDTO);
    }

    @PostMapping
    public ResponseEntity<DisciplinaDTO> cadastrarDisciplina(@RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO disciplinaSalva = disciplinaService.cadastrarDisciplina(disciplinaDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{codigo}")
                .buildAndExpand(disciplinaSalva.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(disciplinaSalva);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<DisciplinaDTO> alterarDisciplina(@PathVariable int codigo, @RequestBody DisciplinaDTO disciplinaDTO) {
        DisciplinaDTO disciplinaAtualizado = disciplinaService.alterarDisciplina(codigo, disciplinaDTO);
        return ResponseEntity.ok(disciplinaAtualizado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> removerDisciplina(@PathVariable int codigo) {
        disciplinaService.removerDisciplina(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<DisciplinaDTO>> listarDisciplinas() {
        List<DisciplinaDTO> listaDisciplinas = disciplinaService.listarDisciplinas();
        return ResponseEntity.ok(listaDisciplinas);
    }

}
