package com.example.screenSoundMusic.principal;

import com.example.screenSoundMusic.model.Artista;
import com.example.screenSoundMusic.model.Musica;
import com.example.screenSoundMusic.repository.ArtistaRepository;
import com.example.screenSoundMusic.repository.MusicaRepository;
import org.aspectj.apache.bcel.Repository;

import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private static Scanner scanner = new Scanner(System.in);
    private ArtistaRepository artistaRepository;
    private MusicaRepository musicaRepository;

    public Principal(ArtistaRepository artistaRepository, MusicaRepository musicaRepository) {
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
                    System.out.println("Listar músicas...");
                    // TODO: Implementar listagem de músicas
                    break;
                case 4:
                    System.out.println("Buscar música por artista...");
                    // TODO: Implementar busca de música por artista
                    break;
                case 5:
                    System.out.println("Pesquisar dados sobre um artista...");
                    // TODO: Implementar pesquisa de dados sobre artista
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

    private void cadastrarMusica() {
        System.out.println("Digite o nome da música:");
        String nomeMusica = scanner.nextLine();

        System.out.println("Digite o nome do artista:");
        String nomeArtista = scanner.nextLine();

        Optional<Artista> artistaEcontrado = artistaRepository.findByNomeContainingIgnoreCase(nomeArtista);
        if(artistaEcontrado.isPresent()){
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

        musicaRepository.save(new Musica(nomeMusica, nomeAlbum, artistaEcontrado.get()));
    }

    private void cadastrarArtista() {
        try {
        System.out.println("Digite o nome do artista:");
        String nomeArtista = scanner.nextLine();

        System.out.println("Digite o gênero musical do artista:");
        String generoMusical = scanner.nextLine();

        artistaRepository.save(new Artista(nomeArtista.toUpperCase(), generoMusical, null));
        System.out.println("Artista cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar artista: " + e.getMessage());
        }

    }
}
