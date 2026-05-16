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

    @Column(unique = true)
    private String nome;

    @OneToMany(mappedBy = "artista")
    private List<Musica> musicas;

    public Artista() {}

    public Artista(String nome, List<Musica> musicas) {
        this.nome = nome;
        this.musicas = musicas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(List<Musica> musicas) {
        this.musicas = musicas;
    }
}
