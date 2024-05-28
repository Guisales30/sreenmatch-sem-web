package br.com.alura.screamatch.principal;

import br.com.alura.screamatch.model.DadosEpisodio;
import br.com.alura.screamatch.model.DadosSerie;
import br.com.alura.screamatch.model.DadosTemporadas;
import br.com.alura.screamatch.service.ConsumoApi;
import br.com.alura.screamatch.service.ConverterDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    Scanner in = new Scanner(System.in);
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private  ConsumoApi consumo = new ConsumoApi();
    private  ConverterDados conversor = new ConverterDados();
    private final String API_KEY = "&apikey=d76888aa";

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = in.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dados);

        List<DadosTemporadas> temporadasList = new ArrayList<>();

		for (int i=1; i <=dados.totalTemporadas(); i++){
			json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+")+"&season="+ i + API_KEY);
			DadosTemporadas dadosTemporadas = conversor.obterDados(json, DadosTemporadas.class);
			temporadasList.add(dadosTemporadas);
		}
		temporadasList.forEach(System.out::println);

//        for (int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadasList.get(i).episodio();
//            for (int j = 0; j< episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        temporadasList.forEach(t -> t.episodio().forEach(e -> System.out.println(e.titulo())));
    }
}
