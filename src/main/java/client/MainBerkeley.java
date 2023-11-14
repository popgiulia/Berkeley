package client;

import static common.AppConstants.formatter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

import common.AppConstants;
import server.ServerTime;
import server.ServerTimeImpl;

/**
 * Partea client a aplica?iei.
 */
public class MainBerkeley {

	public static void main(String[] args) {
		try {
			LocalTime oraLocala = LocalTime.parse(AppConstants.LOCAL_HOUR, formatter);
			System.out.println("Ora local�: " + formatter.format(oraLocala));

			// Crearea serverelor (ma?inilor)
			ServerTime serverMasina1 = createMachineServer(1);
			ServerTime serverMasina2 = createMachineServer(2);
			ServerTime serverMasina3 = createMachineServer(3);

			// Calcularea mediei orelor
			var diferentaMedie = generateAverageTime(oraLocala,
					serverMasina1.getLocalTime(),
					serverMasina2.getLocalTime(),
					serverMasina3.getLocalTime());

			// Ajustarea timpului serverelor
			serverMasina1.adjustTime(oraLocala, diferentaMedie);
			serverMasina2.adjustTime(oraLocala, diferentaMedie);
			serverMasina3.adjustTime(oraLocala, diferentaMedie);
			oraLocala = oraLocala.plusNanos(diferentaMedie);

			System.out.println("\nOre actualizate!");
			System.out.println("Ora local�: " + formatter.format(oraLocala));
			System.out.println("Ora serverului 1: " + formatter.format(serverMasina1.getLocalTime()));
			System.out.println("Ora serverului 2: " + formatter.format(serverMasina2.getLocalTime()));
			System.out.println("Ora serverului 3: " + formatter.format(serverMasina3.getLocalTime()));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	/**
	 * Creeaz� un {@link ServerTime} asociat cu o ma?in� pentru a-i ajusta ora.
	 *
	 * @param numarulMasinii num�rul ma?inii
	 * @return serverul ma?inii cu ora sa
	 * @throws Exception la �ncercarea de a crea serverul sau registrul
	 */
	private static ServerTime createMachineServer(int numarulMasinii) throws Exception {
		String numeServer = AppConstants.SERVER_NAME;
		int portServer = switch (numarulMasinii) {
			case 1 -> AppConstants.SERVER_PORT_1;
			case 2 -> AppConstants.SERVER_PORT_2;
			case 3 -> AppConstants.SERVER_PORT_3;
			default -> -1;
		};
		Registry registryMasina = LocateRegistry.getRegistry(numeServer, portServer);
		ServerTime serverTimpMasina = (ServerTime) registryMasina.lookup(ServerTimeImpl.class.getSimpleName());
		LocalTime timpMasina = serverTimpMasina.getLocalTime();
		System.out.println("Conexiune cu ma?ina " + numarulMasinii + " stabilit� cu succes. Ora: "
				+ formatter.format(timpMasina));
		return serverTimpMasina;
	}

	/**
	 * Calculeaz� media orei care trebuie ajustat�.<br>
	 * Ora sumat� a ma?inilor (fiecare sc�zut� din ora local�) �mp�r?it� la
	 * num�rul total de ma?ini.
	 *
	 * @param oraLocala ora local�
	 * @param ore       ora ma?inilor
	 * @return ora medie calculat�
	 */
	private static long generateAverageTime(LocalTime oraLocala, LocalTime... ore) {
		long nanoOraLocala = oraLocala.toNanoOfDay();
		long diferentaServer = 0;
		for (LocalTime t : ore) {
			diferentaServer += t.toNanoOfDay() - nanoOraLocala;
		}
		return diferentaServer / ore.length;
	}

}
