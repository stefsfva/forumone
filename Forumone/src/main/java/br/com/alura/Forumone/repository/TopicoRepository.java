package br.com.alura.Forumone.repository;

import br.com.alura.Forumone.model.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

    List<Topico> findByTitulo(String titulo);
    List<Topico> findByAutorNome(String autorNome);

    @Query("SELECT t FROM Topico t WHERE t.titulo = :titulo")
    List<Topico> buscarPorTitulo(@Param("titulo") String titulo);

    @Query("SELECT t FROM Topico t WHERE t.autor.nome = :nomeAutor")
    List<Topico> buscarPorNomeAutor(@Param("nomeAutor") String nomeAutor);

    Page<Topico> findByTituloContaining(String titulo, Pageable pageable);

    List<Topico> findByTituloContaining(String titulo);
    List<Topico> findByAutorId(Long autorId);

    List<Topico> findByTituloContainingIgnoreCase(String titulo);

    boolean existsByTituloAndMensagem(String titulo, String mensagem);
}
