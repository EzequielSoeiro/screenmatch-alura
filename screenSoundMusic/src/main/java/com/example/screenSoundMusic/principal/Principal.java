package com.example.screenSoundMusic.principal;

import java.util.Scanner;

public class Principal {
    public static void main() {
        System.out.println("Bem-vindo ao Screen Sound Music!");

        Scanner scanner = new Scanner(System.in);
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
                    System.out.println("Cadastrar artistas...");
                    // TODO: Implementar cadastro de artistas
                    break;
                case 2:
                    System.out.println("Cadastrar música...");
                    // TODO: Implementar cadastro de música
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
}
