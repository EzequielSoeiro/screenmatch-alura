package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Categoria;
import br.com.alura.screenmatch.model.Episodio;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Integer> {
    Optional<Serie> findByTituloContainingIgnoreCase(String nomeSerie);

    List<Serie> findByAtoresContainingIgnoreCase(String nomeSerie);

    List<Serie> findTop5ByOrderByAvaliacaoDesc();

    List<Serie> findByGenero(Categoria genero);

    @Query("SELECT s FROM Serie s WHERE s.totalTemporadas <= :numeroTemporadas AND s.avaliacao >= :numeroAvaliacao")
    List<Serie> filtrarSeriePorTemporadaEAvaliacao(int numeroTemporadas, double numeroAvaliacao);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.titulo ILIKE %:trechoTitulo%")
    List<Episodio> filtraEpisodioPorTrechoNome(String trechoTitulo);

}
