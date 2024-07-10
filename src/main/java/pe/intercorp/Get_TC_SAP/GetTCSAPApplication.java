package pe.intercorp.Get_TC_SAP;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pe.intercorp.Get_TC_SAP.entity.TipoCambio;
import pe.intercorp.Get_TC_SAP.entity.TableTC;
import pe.intercorp.Get_TC_SAP.repository.RepoApi;
import pe.intercorp.Get_TC_SAP.repository.RepoData;

@SpringBootApplication
public class GetTCSAPApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GetTCSAPApplication.class, args);
	}

	@Autowired
	RepoApi repoApi;

	@Autowired
	RepoData repoData;

	private final Logger log = LoggerFactory.getLogger(GetTCSAPApplication.class);

	@Override
	public void run(String... args) throws Exception {

		carga_data();

		log.info("carga_data ");

		List<TableTC> tableTC = repoData.search2();

		if (tableTC.size() != 0) {

			repoData.process();

		}
		log.info("Final process ");

		 //repoData.process();

	}

	TipoCambio tipoCambio;

	public void carga_data() {
		try {
			tipoCambio = repoApi.getTipoCambio();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("Inicio E_TPCAMBIO : {} ", tipoCambio.E_TPCAMBIO);
		log.info("  ");
		for (int i = 0; i < tipoCambio.E_TIPO_CAMBIO.size(); i++) {

			String moneda = tipoCambio.E_TIPO_CAMBIO.get(i).MONEDA_TC.toString();
			String tipocamb = tipoCambio.E_TIPO_CAMBIO.get(i).U_TPCAMBIO.toString();
			String fecha = tipoCambio.E_TIPO_CAMBIO.get(i).GDATU.toString();

			List<TableTC> tableTC = repoData.search(fecha, moneda);
			if (tableTC.size() == 0) {

				log.info("Inicio Detalle...............................");
				log.info("Moneda Origen : {} ", moneda);
				log.info("Tipo Cambio : {} ", tipocamb);
				log.info("Fecha : {} ", fecha);
				log.info("Fin Detalle...........................");
				log.info("  ");

				log.info("Insert ");
				repoData.insert(moneda, fecha, tipocamb);
			}
		}
		log.info("  ");

		log.info("Final E_TPCAMBIO ");
	}

}
