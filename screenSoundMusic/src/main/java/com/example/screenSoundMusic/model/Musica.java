package com.example.screenSoundMusic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "musicas")
public class Musica {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;

    private String album;

    @Enumerated(EnumType.STRING)
    private GeneroMusical generoMusical;

    @ManyToOne
    private Artista artista;

    public Musica() {}

    public Musica(String titulo, String album, GeneroMusical generoMusical, Artista artista) {
        this.titulo = titulo;
        this.album = album;
        this.generoMusical = generoMusical;
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public GeneroMusical getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(GeneroMusical generoMusical) {
        this.generoMusical = generoMusical;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }
}
