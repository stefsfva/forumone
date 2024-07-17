package br.com.alura.Forumone.repository;


import br.com.alura.Forumone.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    <T> ScopedValue<T> findByUsername(String username);

    // Adicione métodos personalizados de consulta, se necessário
}

