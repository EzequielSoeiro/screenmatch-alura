package br.com.alura.screenmatch.model;

import java.util.OptionalDouble;

public class Serie {

     private String titulo;
     private Integer totalTemporadas;
     private Double avaliacao;
     private Categoria genero;
     private String atores;
     private String sinopse;
     private String poster;

     private Serie(DadosSerie dadosSerie){
          this.titulo=dadosSerie.titulo();
          this.totalTemporadas=dadosSerie.totalTemporadas();
          /**
           * Double.valueOh() -  converte uma String(dadosSerie.avaliacao()) em double
           * OptionalDouble.of() - encapsula o valor dentro de um OptionalDouble e caso esteja vazio, usa 0 como valor padrão
           */
          this.avaliacao= OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
          this.genero=fromString(dadosSerie.genero());
     }

     public static Categoria fromString(String text) {
          for (Categoria categoria : Categoria.values()) {
               if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                    return categoria;
               }
          }
          throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
     }
}
