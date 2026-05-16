package com.example.screenSoundMusic.principal;

import com.example.screenSoundMusic.model.Artista;
import com.example.screenSoundMusic.model.GeneroMusical;
import com.example.screenSoundMusic.model.Musica;
import com.example.screenSoundMusic.repository.ArtistaRepository;
import com.example.screenSoundMusic.repository.MusicaRepository;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private final Scanner scanner = new Scanner(System.in);
    private final ArtistaRepository artistaRepository;
    private final MusicaRepository musicaRepository;

    public Principal(ArtistaRepository artistaRepository, 
                     MusicaRepository musicaRepository) {
        this.artistaRepository = artistaRepository;
        this.musicaRepository = musicaRepository;
    }

    public void menu() {
        System.out.println("Bem-vindo ao Screen Sound Music!");

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n*** Screen Sound Music ***");
            System.out.println("1 - Cadastrar artistas");
            System.out.println("2 - Cadastrar música");
            System.out.println("3 - Listar músicas");
            System.out.println("4 - Buscar música por artista");
            System.out.println("5 - Pesquisar dados sobre um artista");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    cadastrarArtista();
                    break;
                case 2:
                    cadastrarMusica();
                    break;
                case 3:
                    listarMusicas();
                    break;
                case 4:
                    buscarMusicaPorArtista();
                    break;
                case 5:
                   pesquisarDadosArtista();
                    break;
                case 0:
                    System.out.println("Saindo... Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    private void cadastrarArtista() {
        try {
            System.out.println("Digite o nome do artista:");
            String nomeArtista = scanner.nextLine();

            artistaRepository.save(new Artista(nomeArtista.toUpperCase(), null));
            System.out.println("Artista cadastrado com sucesso!");
        } catch (DataIntegrityViolationException e){
            System.out.println("Erro: Artista já cadastrado! ");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar artista: " + e.getMessage());
        }

    }

    private void cadastrarMusica() {
        try {
            System.out.println("Digite o nome da música:");
            String nomeMusica = scanner.nextLine();

            System.out.println("Digite o gênero musical da música (opções: " + java.util.Arrays.toString(GeneroMusical.values()) + "):");
            String generoMusicalTexto = scanner.nextLine();
            GeneroMusical generoMusical = GeneroMusical.fromUserInput(generoMusicalTexto);
            System.out.println("Digite o nome do artista:");
            String nomeArtista = scanner.nextLine();

            Optional<Artista> artistaEcontrado = artistaRepository.findByNomeIgnoreCase(nomeArtista);
            if(artistaEcontrado.isEmpty()){
                System.out.println("Artista não encontrado! Cadastre o artista antes de cadastrar a música.");
                return;
            }

            String nomeAlbum = null;
            System.out.println("A musica pertece a um álbum? (S/N)");
            String resposta = scanner.nextLine();
            if(resposta.equalsIgnoreCase("S")){
                System.out.println("Digite o nome do álbum:");
                nomeAlbum = scanner.nextLine();
            }

            musicaRepository.save(new Musica(nomeMusica, nomeAlbum, generoMusical, artistaEcontrado.get()));
            System.out.println("Música cadastrada com sucesso!");
        }catch (IllegalArgumentException e) {
            System.out.println("Gênero musical inválido! Use uma das opções exibidas.");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar música: " + e.getMessage());
        }
    }

    private void listarMusicas() {
        List<Musica> musicas = musicaRepository.findAll();
        if (musicas.isEmpty()) {
            System.out.println("Nenhuma música cadastrada.");
        } else {
            musicas.forEach(musica -> {
                System.out.println("\nNome: " + musica.getTitulo());
                System.out.println("Artista: " + musica.getArtista().getNome());
                System.out.println("Gênero: " + musica.getGeneroMusical());
                if (musica.getAlbum() != null) {
                    System.out.println("Álbum: " + musica.getAlbum());
                }
                System.out.println("-------------------------");
            });
        }
    }

    private void buscarMusicaPorArtista() {
        System.out.println("Digite o nome do artista:");
        String nomeArtista = scanner.nextLine();

        Optional<Artista> artistaEncontrado = artistaRepository.findByNomeIgnoreCase(nomeArtista);
        if (artistaEncontrado.isPresent()) {
            List<Musica> musicasDoArtista = musicaRepository.findByArtista(artistaEncontrado.get());
            if (musicasDoArtista.isEmpty()) {
                System.out.println("Nenhuma música encontrada para o artista " + nomeArtista);
            } else {
                System.out.println("Músicas do artista " + nomeArtista + ":");
                musicasDoArtista.forEach(musica -> System.out.println("- " + musica.getTitulo()));
            }
        } else {
            System.out.println("Artista não encontrado: " + nomeArtista);
        }
    }

    private void pesquisarDadosArtista() {

    }





}
