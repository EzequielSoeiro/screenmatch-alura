package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.traducao.ConsultaMyMemory;
import jakarta.persistence.*;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "Series")
public class Serie {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int id;

     @Column(unique = true)
     private String titulo;

     @Enumerated(EnumType.STRING)
     private Categoria genero;

     private Integer totalTemporadas;
     private Double avaliacao;
     private String atores;
     private String sinopse;
     private String poster;

     @Transient/*Esse atributo sera ignorado*/
     private List<Episodio> episodios = new ArrayList<>();

     public Serie(DadosSerie dadosSerie){
          this.titulo=dadosSerie.titulo();
          this.totalTemporadas=dadosSerie.totalTemporadas();
          /**
           * Double.valueOh() -  converte uma String(dadosSerie.avaliacao()) em double
           * OptionalDouble.of() - encapsula o valor dentro de um OptionalDouble e caso esteja vazio, usa 0 como valor padrão
           */
          this.avaliacao= OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
          this.genero=Categoria.fromString(dadosSerie.genero().substring(0, dadosSerie.genero().indexOf(",")));
          this.atores=dadosSerie.atores();
          //this.sinopse= ConsultaChatGpt.obterTraducao(dadosSerie.sinopse());
         this.sinopse= ConsultaMyMemory.obterTraducao(dadosSerie.sinopse());
     }


     public String getTitulo() {
          return titulo;
     }

     public void setTitulo(String titulo) {
          this.titulo = titulo;
     }

     public Integer getTotalTemporadas() {
          return totalTemporadas;
     }

     public void setTotalTemporadas(Integer totalTemporadas) {
          this.totalTemporadas = totalTemporadas;
     }

     public Double getAvaliacao() {
          return avaliacao;
     }

     public void setAvaliacao(Double avaliacao) {
          this.avaliacao = avaliacao;
     }

     public Categoria getGenero() {
          return genero;
     }

     public void setGenero(Categoria genero) {
          this.genero = genero;
     }

     public String getAtores() {
          return atores;
     }

     public void setAtores(String atores) {
          this.atores = atores;
     }

     public String getSinopse() {
          return sinopse;
     }

     public void setSinopse(String sinopse) {
          this.sinopse = sinopse;
     }

     public String getPoster() {
          return poster;
     }

     public void setPoster(String poster) {
          this.poster = poster;
     }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    @Override
     public String toString() {
          return  "genero=" + genero +
                  ", titulo='" + titulo + '\'' +
                  ", totalTemporadas=" + totalTemporadas +
                  ", avaliacao=" + avaliacao +
                  ", atores='" + atores + '\'' +
                  ", sinopse='" + sinopse + '\'' +
                  ", poster='" + poster + '\'';
     }
}
