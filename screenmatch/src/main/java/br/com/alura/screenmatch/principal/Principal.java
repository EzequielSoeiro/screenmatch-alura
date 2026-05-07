package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private SerieRepository repositorio;

    private List<Serie> listaSeries;

    public Principal(SerieRepository repositorio){
        this.repositorio=repositorio;
    }

    public void exibeMenu() {
        int opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries;
                    2 - Buscar episódios;
                    3 - Listar séries pesquisadas;
                    4 - Buscar série por Título;
                    5 - Buscar séries por Ator;
                    6 - Listar top 5 séries mais bem avaliadas;
                    7 - Buscar série por categoria/gênero;
                    8 - Filtrar séries
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesPesquisadas();
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriePorAtor();
                    break;
                case 6:
                    buscarTop5();
                    break;
                case 7:
                    buscarSeriesPorCategoria();
                    break;
                case 8:
                    filtrarSeriresPorTemporadaEAvaliacao();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void filtrarSeriresPorTemporadaEAvaliacao() {
        System.out.println("Filtrar série até quentas temporadas?");
        int numeroTemporadas = leitura.nextInt();
        System.out.println("Filtrar série com avaliação maior que:");
        int numeroAvaliacao = leitura.nextInt();
        List<Serie> seriesFiltradas = repositorio.filtrarSeriePorTemporadaEAvaliacao(numeroTemporadas, numeroAvaliacao);
        seriesFiltradas.forEach(sf -> System.out.println(sf.getTitulo()+":\n"+"total de temporadas: "+ sf.getTotalTemporadas()+"\n"+"avaliação: " + sf.getAvaliacao()));
    }

    private void buscarSeriesPorCategoria() {
        System.out.println("Deseja buscar séries de que categoria/gênero?");
        var categoriaEscolhida = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(categoriaEscolhida);
        List<Serie> seriesPorCategoria = repositorio.findByGenero(categoria);
        seriesPorCategoria.forEach(System.out::println);


    }

    private void buscarTop5() {
       List<Serie> seriesTop5 =  repositorio.findTop5ByOrderByAvaliacaoDesc();
       seriesTop5.forEach(s->
               System.out.println(s.getTitulo()+": "+s.getAvaliacao()));
    }

    private void buscarSeriePorAtor() {
        System.out.println("Digite o nome do ator para busca");
        String nomeAtor = leitura.nextLine();

        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCase(nomeAtor);
        System.out.println("Séries em que " + nomeAtor + " trabalhou: ");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo()+": "+s.getAvaliacao()));

    }

    private void buscarSeriePorTitulo() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();

        Optional<Serie> serieEncontrada = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if(serieEncontrada.isPresent()){
            System.out.println("Dados da série: " + serieEncontrada.get());
        }else
            System.out.println("Série não encontrada!");
    }

    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        repositorio.save(new Serie(dados));
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();

        String enderecoCompleto = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;

        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        return dados;
    }

    private void buscarEpisodioPorSerie(){
        listarSeriesPesquisadas();
        System.out.println("Escolha uma série pelo nome:");
        String nomeSerie = leitura.nextLine();

        Optional<Serie> first = listaSeries.stream()
                .filter(serie -> serie.getTitulo().toLowerCase().contains(nomeSerie.toLowerCase()))
                .findFirst();

        if(first.isPresent()){
            var serieEncontrada = first.get();
            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(t -> t.episodios().forEach(System.out::println));

            List<Episodio> episodios = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(e -> new Episodio(t.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);

        }else
            System.out.println("[ERRO] - SÉRIE NÃO ENCONTRADA!");
    }

    private void listarSeriesPesquisadas(){
        listaSeries = repositorio.findAll();
        listaSeries.stream().sorted(Comparator.comparing(Serie::getGenero)).forEach(System.out::println);
    }

}