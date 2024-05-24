package br.com.alura.screamatch;

import br.com.alura.screamatch.model.DadoSerie;
import br.com.alura.screamatch.service.ConsumoApi;
import br.com.alura.screamatch.service.ConverterDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreamatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreamatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();
		var json = consumoApi.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=d76888aa");
		System.out.println(json);
		ConverterDados conversor = new ConverterDados();
		DadoSerie dados = conversor.obterDados(json, DadoSerie.class);
		System.out.println(dados);
	}
}
