package server.machine;

import static common.AppConstants.formatter;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

import common.AppConstants;
import server.ServerTime;
import server.ServerTimeImpl;

/**
 * Reprezentarea ma?inii 3 pentru a-i ajusta ora.
 */
public class Machine3 {

	public static void main(String[] args) {
		try {
			LocalTime ora = LocalTime.parse(AppConstants.MACHINE_3_HOUR, formatter);
			ServerTime serverMasina = new ServerTimeImpl(ora);
			Registry registry = LocateRegistry.createRegistry(AppConstants.SERVER_PORT_3);
			registry.rebind(ServerTimeImpl.class.getSimpleName(), serverMasina);
			System.out.println(String.format("Ma?ina 3 a fost pornitã pe portul %s [ora localã: %s].",
					AppConstants.SERVER_PORT_3,
					AppConstants.formatter.format(ora)));
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}
}
