package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

/**
 * Interfa?a pentru accesarea serverului de cãtre partea de client.
 */
public interface ServerTime extends Remote {

	/**
	 * @return ora localã
	 */
	LocalTime getLocalTime() throws RemoteException;

	/**
	 * Ajusteazã ora localã bazatã pe ora serverului cu media orelor.
	 *
	 * @param localTime ora localã a serverului
	 * @param avgDiff   media orelor
	 */
	void adjustTime(LocalTime localTime, long avgDiff) throws RemoteException;
}
