package br.com.alura.Forumone;

import br.com.alura.Forumone.model.Topico;
import br.com.alura.Forumone.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Menu {

    private final TopicoRepository topicoRepository;

    @Autowired
    public Menu(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    public void exibirMenu() {
        System.out.println("=== FÓRUM ===");
        System.out.println("1. Visualizar Tópicos");
        System.out.println("2. Criar Novo Tópico");
        System.out.println("3. Pesquisar Tópico");
        System.out.println("4. Detalhar Tópico");
        System.out.println("5. Atualizar Tópico");
        System.out.println("6. Excluir Tópico");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");
    }

    public void visualizarTopicos() {
        List<Topico> topicos = topicoRepository.findAll();
        System.out.println("=== LISTA DE TÓPICOS ===");
        for (Topico topico : topicos) {
            System.out.println("ID: " + topico.getId() + " | Título: " + topico.getTitulo());
        }
    }

    public void criarNovoTopico(Scanner scanner) {
        System.out.println("=== CRIAR NOVO TÓPICO ===");
        System.out.print("Digite o título do tópico: ");
        String titulo = scanner.nextLine();

        System.out.print("Digite o conteúdo do tópico: ");
        String conteudo = scanner.nextLine();

        Topico novoTopico = new Topico(titulo, conteudo);
        // Aqui você pode definir o autor e o curso, por exemplo:
        // Usuario autor = new Usuario();
        // Curso curso = new Curso();
        // novoTopico.setAutor(autor);
        // novoTopico.setCurso(curso);

        topicoRepository.save(novoTopico);

        System.out.println("Novo tópico criado com sucesso!");
    }

    public void pesquisarTopico(Scanner scanner) {
        System.out.print("Digite o título do tópico que deseja pesquisar: ");
        String titulo = scanner.nextLine();

        List<Topico> topicosEncontrados = topicoRepository.findByTituloContainingIgnoreCase(titulo);

        if (topicosEncontrados.isEmpty()) {
            System.out.println("Nenhum tópico encontrado com o título informado.");
        } else {
            System.out.println("=== TÓPICOS ENCONTRADOS ===");
            for (Topico topico : topicosEncontrados) {
                System.out.println("ID: " + topico.getId() + " | Título: " + topico.getTitulo());
            }
        }
    }

    public void detalharTopico(Scanner scanner) {
        System.out.print("Digite o ID do tópico que deseja detalhar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            System.out.println("=== DETALHAMENTO DO TÓPICO ===");
            System.out.println("ID: " + topico.getId());
            System.out.println("Título: " + topico.getTitulo());
            System.out.println("Conteúdo: " + topico.getMensagem());
            // Exibir mais informações se necessário, como autor, curso, etc.
        } else {
            System.out.println("Tópico não encontrado com o ID informado.");
        }
    }

    public void atualizarTopico(Scanner scanner) {
        System.out.print("Digite o ID do tópico que deseja atualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            Topico topico = optionalTopico.get();
            System.out.println("=== ATUALIZAÇÃO DO TÓPICO ===");
            System.out.print("Digite o novo título do tópico: ");
            String novoTitulo = scanner.nextLine();
            topico.setTitulo(novoTitulo);

            System.out.print("Digite o novo conteúdo do tópico: ");
            String novoConteudo = scanner.nextLine();
            topico.setMensagem(novoConteudo);

            topicoRepository.save(topico);
            System.out.println("Tópico atualizado com sucesso!");
        } else {
            System.out.println("Tópico não encontrado com o ID informado.");
        }
    }

    public void excluirTopico(Scanner scanner) {
        System.out.print("Digite o ID do tópico que deseja excluir: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Limpar o buffer

        Optional<Topico> optionalTopico = topicoRepository.findById(id);

        if (optionalTopico.isPresent()) {
            topicoRepository.deleteById(id);
            System.out.println("Tópico excluído com sucesso!");
        } else {
            System.out.println("Tópico não encontrado com o ID informado.");
        }
    }

    public void executar() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1:
                    visualizarTopicos();
                    break;
                case 2:
                    criarNovoTopico(scanner);
                    break;
                case 3:
                    pesquisarTopico(scanner);
                    break;
                case 4:
                    detalharTopico(scanner);
                    break;
                case 5:
                    atualizarTopico(scanner);
                    break;
                case 6:
                    excluirTopico(scanner);
                    break;
                case 7:
                    System.out.println("Saindo do Fórum...");
                    return;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
        }
    }
}
