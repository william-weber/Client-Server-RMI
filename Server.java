import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.concurrent.atomic.AtomicInteger;
import java.rmi.server.UnicastRemoteObject;

/* 
 * Main server class. This class includes main(), and is the class that listens
 * for incoming connections and starts ServerThreads to handle those connections
 *
 */
public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CommandExecutor ce = new CommandExecutor();
			String objName = "ce";
			CommandExecutorInterface server = (CommandExecutorInterface) UnicastRemoteObject.exportObject(ce, 0);
			Registry registry = LocateRegistry.getRegistry(15432);
			registry.bind(objName, server);
			System.out.println("Registered command executor");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

