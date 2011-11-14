import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CommandExecutorInterface extends Remote {
	public String run(String commandString) throws RemoteException;
}
