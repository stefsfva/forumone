package br.com.alura.Forumone.controller;

import br.com.alura.Forumone.model.Topico;
import br.com.alura.Forumone.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@Validated
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public ResponseEntity<List<Topico>> listarTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        return ResponseEntity.ok(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topico> detalharTopico(@PathVariable Long id) {
        // Verifica se o ID foi informado corretamente
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        // Busca o tópico pelo ID
        return topicoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/top10")
    public ResponseEntity<List<Topico>> listarTop10() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("dataCriacao").ascending());
        List<Topico> top10 = topicoRepository.findAll(pageable).getContent();
        return ResponseEntity.ok(top10);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Topico> atualizarTopico(@PathVariable Long id, @Valid @RequestBody Topico topicoAtualizado) {
        // Verifica se o tópico existe
        return topicoRepository.findById(id)
                .map(topico -> {
                    topico.setTitulo(topicoAtualizado.getTitulo());
                    topico.setMensagem(topicoAtualizado.getMensagem());
                    topico.setStatus(topicoAtualizado.getStatus());
                    // Atualiza outros campos conforme necessário
                    Topico topicoSalvo = topicoRepository.save(topico);
                    return ResponseEntity.ok(topicoSalvo);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarTopico(@PathVariable Long id) {
        // Verifica se o tópico existe
        return topicoRepository.findById(id)
                .map(topico -> {
                    topicoRepository.delete(topico);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Topico> criarTopico(@Valid @RequestBody Topico novoTopico) {
        Topico topicoSalvo = topicoRepository.save(novoTopico);
        return ResponseEntity.ok(topicoSalvo);
    }
}
