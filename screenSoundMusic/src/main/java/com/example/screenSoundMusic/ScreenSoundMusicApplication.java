package com.example.screenSoundMusic;

import com.example.screenSoundMusic.principal.Principal;
import com.example.screenSoundMusic.repository.ArtistaRepository;
import com.example.screenSoundMusic.repository.MusicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenSoundMusicApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenSoundMusicApplication.class, args);
	}

    @Autowired
    private ArtistaRepository artistaRepository;

    @Autowired
    private MusicaRepository musicaRepository;

    public void run(String... args) throws Exception {
        Principal principal = new Principal(artistaRepository, musicaRepository);
        principal.menu();
    }

}
