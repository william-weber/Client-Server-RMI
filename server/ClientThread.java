import java.util.concurrent.atomic.AtomicLong;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class ClientThread extends Thread {
	// every clientThread is passed which command to send to the server
	int menuSelection;
	String hostName;
	
	// totalTime is used to keep the sum of response times for all threads. after all threads
	// have completed it is divided by the total number of threads to get an 
	// average response time
	AtomicLong totalTime;

	// runningThreads is the total number of running threads. it is set to numThreads (the number
	// of threads that are started) before any threads are started by the Client class. Every time
	// a ClientThread finishes it will decrement runningThreads by one, so runningThreads == 0 when
	// all threads have finished
	AtomicLong runningThreads;

	// each class is passed false for printOutput if the number of threads started is > 1. When running more
	// than one client thread the clientThreads should not print output, input order to not clutter the screen
	boolean printOutput;

	// startTime and endTime are used to keep track of the current time when the thread conects to the 
	// server and when the thread gets a response from the server. The difference between the two
	// (endTime - startTime) is the response time
	long startTime;
	long endTime;

	ClientThread(String hostName, int menuSelection, AtomicLong totalTime, boolean printOutput, AtomicLong runningThreads) {
		this.menuSelection = menuSelection;
		this.hostName = hostName;
		this.totalTime = totalTime;
		this.printOutput = printOutput;
		this.runningThreads = runningThreads;
	}

	public void run() {
		try {
			Registry registry = LocateRegistry.getRegistry(hostName, 15432);
			CommandExecutorInterface ce = (CommandExecutorInterface) registry.lookup("ce");
			if (printOutput) {
				System.out.print("Establishing connection.");
			}

			if (printOutput) System.out.println("\nRequesting output for the '" + menuSelection + "' command from " + hostName);
			
			// get the current time (before sending the request to the server)
			startTime = System.currentTimeMillis();

			// send the command to the server
			String outputString = ce.run(Integer.toString(menuSelection));
			if (printOutput) System.out.println("Sent output");

			// print the output from the server
			if (printOutput) System.out.println(outputString);

			// get the current time (after connecting to the server)
			endTime = System.currentTimeMillis();
			// endTime - startTime = the time it took to get the response from the sever
			totalTime.addAndGet(endTime - startTime);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		// finally, close the socket and decrement runningThreads
		finally {
			if (printOutput) System.out.println("Thread finished.");
			runningThreads.decrementAndGet();
		}

	}

}
