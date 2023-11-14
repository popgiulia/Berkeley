package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.time.LocalTime;

/**
 * Interfa?a pentru accesarea serverului de c�tre partea de client.
 */
public interface ServerTime extends Remote {

	/**
	 * @return ora local�
	 */
	LocalTime getLocalTime() throws RemoteException;

	/**
	 * Ajusteaz� ora local� bazat� pe ora serverului cu media orelor.
	 *
	 * @param localTime ora local� a serverului
	 * @param avgDiff   media orelor
	 */
	void adjustTime(LocalTime localTime, long avgDiff) throws RemoteException;
}
