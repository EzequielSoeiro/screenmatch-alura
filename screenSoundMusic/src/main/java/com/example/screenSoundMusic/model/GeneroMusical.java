package com.example.screenSoundMusic.model;

public enum GeneroMusical {
    PSYTRANCE,
    ROCK,
    POP,
    HIP_HOP,
    SERTANEJO,
    MPB;

    public static GeneroMusical fromUserInput(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Genero musical nao pode ser nulo.");
        }
        String normalized = input.trim().toUpperCase();
        normalized = normalized.replace("-", " ");
        normalized = normalized.replaceAll("\\s+", "_");
        return GeneroMusical.valueOf(normalized);
    }
}
