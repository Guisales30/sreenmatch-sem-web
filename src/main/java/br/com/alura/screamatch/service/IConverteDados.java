package br.com.alura.screamatch.service;

public interface IConverteDados {
    <T> T obterDados(String json, Class<T> classes);
}
