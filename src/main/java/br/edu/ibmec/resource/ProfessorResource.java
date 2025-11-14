package br.edu.ibmec.resource;

import br.edu.ibmec.dto.ProfessorDTO;
import br.edu.ibmec.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/professores")
public class ProfessorResource {

    @Autowired
    private ProfessorService professorService;

    @GetMapping(path = "/{codigo}", produces = "application/json")
    public ResponseEntity<ProfessorDTO> buscarProfessorPeloCodigo(@PathVariable int codigo) {
        ProfessorDTO professorDTO = professorService.buscarProfessor(codigo);
        return ResponseEntity.ok(professorDTO);
    }

    @PostMapping
    public ResponseEntity<ProfessorDTO> cadastrarProfessor(@RequestBody ProfessorDTO professorDTO) {
        ProfessorDTO professorSalvo = professorService.cadastrarProfessor(professorDTO);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{codigo}")
                .buildAndExpand(professorSalvo.getCodigo())
                .toUri();

        return ResponseEntity.created(location).body(professorSalvo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<ProfessorDTO> alterarProfessor(@PathVariable int codigo, @RequestBody ProfessorDTO professorDTO) {
        ProfessorDTO professorAtualizado = professorService.alterarProfessor(codigo, professorDTO);
        return ResponseEntity.ok(professorAtualizado);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> removerProfessor(@PathVariable int codigo) {
        professorService.removerProfessor(codigo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ProfessorDTO>> listarProfessores() {
        List<ProfessorDTO> listaProfessores = professorService.listarProfessores();
        return ResponseEntity.ok(listaProfessores);
    }
}
