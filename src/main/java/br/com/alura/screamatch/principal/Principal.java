package br.com.alura.screamatch.principal;

import br.com.alura.screamatch.model.DadosEpisodio;
import br.com.alura.screamatch.model.DadosSerie;
import br.com.alura.screamatch.model.DadosTemporadas;
import br.com.alura.screamatch.model.Episodio;
import br.com.alura.screamatch.service.ConsumoApi;
import br.com.alura.screamatch.service.ConverterDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        List<DadosEpisodio> dadosEpisodios = temporadasList.stream()
                .flatMap(t -> t.episodio().stream())
                .collect(Collectors.toList());

        System.out.println("\n Top 5 episodios");
        dadosEpisodios.stream().
                filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);
        List<Episodio> episodios = temporadasList.stream()
                .flatMap(t -> t.episodio().stream()
                        .map(d -> new Episodio(t.numero(),d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano voce deseja ver os episodios");
        var ano = in.nextInt();
        in.nextLine();

        LocalDate databusca = LocalDate.of(ano, 1,1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream().filter(e ->e.getDataLancamento() != null && e.getDataLancamento().isAfter(databusca))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() +
                        " Episódio: "+ e.getTitulo() +
                        " Data de lançamento: "+ e.getDataLancamento().format(formatador)
                ));
    }
}
