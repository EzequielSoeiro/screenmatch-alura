package com.example.screenSoundMusic.repository;

import com.example.screenSoundMusic.model.Artista;
import com.example.screenSoundMusic.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

    List<Musica> findByArtista(Artista artista);
}
