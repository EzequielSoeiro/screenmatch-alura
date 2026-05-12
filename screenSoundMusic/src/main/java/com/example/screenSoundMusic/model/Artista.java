package com.example.screenSoundMusic.model;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.util.List;
@Entity
@Table(name = "artistas")
public class Artista {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    private String gereroMusical;

    @OneToMany
    private List<Musica> musicas;

    public Artista() {}

    public Artista(String nome, String gereroMusical, List<Musica> musicas) {
        this.nome = nome;
        this.gereroMusical = gereroMusical;
        this.musicas = musicas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getGereroMusical() {
        return gereroMusical;
    }

    public void setGereroMusical(String gereroMusical) {
        this.gereroMusical = gereroMusical;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
}
