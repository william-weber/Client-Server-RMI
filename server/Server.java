import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
			//install a security manager
			System.setSecurityManager(new RMISecurityManager());
			Registry.LocateRegistry();

			//make a command executor with the given name
			CommandExecutor ce = new CommandExecutor();
			//register it with the local naming registry
			Naming.rebind("executor", ce);
			System.out.println("Registered command executor");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

