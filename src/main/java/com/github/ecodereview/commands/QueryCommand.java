package com.github.ecodereview.commands;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import com.github.ecodereview.bean.AddressEntry;
import com.github.ecodereview.operations.Operation;
import com.github.ecodereview.operations.OperationException;
import com.github.ecodereview.operations.XMLOperation;

/**
 * This is an implementation of the <tt>Command</tt> interface.
 * The implementation is used to execute the query command.
 *
 * @author  Chai Kuaizhang
 * @see ExecutorService
 * @see Exception
 */
public class QueryCommand implements Command, Callable<List<AddressEntry>> {

	private Operation operation = null;
	private String mode = null;
	private String para = null;
	private Logger logger = Logger.getLogger("com.github.ecodereview");

	/**
     * Execute the query command.
     *
     * @param executor the query command executes by the executor in a thread
     * @throws Exception if the command fails to execute
     */
	public void execute(ExecutorService executor) throws Exception {
		Scanner scanner = new Scanner(System.in);
		boolean inputCorrect = false;
		while (!inputCorrect) {
			System.out.print("by (name|mobile|address):");
			mode = scanner.nextLine().trim();
			if (!("name").equals(mode) && !("mobile").equals(mode)
					&& !("address").equals(mode)) {
				System.out.println("\r\nPlease check your input!");
				continue;
			}
			inputCorrect = true;
		}
		System.out.print(mode + ":");
		para = scanner.nextLine().trim();
		logger.info("Quering the address entry by " + mode + ". The condition is " + para);
		try {
			operation = XMLOperation.getInstance();
		} catch (OperationException e) {
			throw new Exception("Failed to query address entry.", e);
		}
		List<AddressEntry> entries = null;
		Future<List<AddressEntry>> future = executor.submit(this);
		try {
			entries = future.get();
		} catch (InterruptedException e) {
			throw new Exception("Failed to query address entry.", e);
		} catch (ExecutionException e) {
			throw new Exception("Failed to query address entry.", e);
		}
		if (entries == null || entries.size()==0) {
			logger.info("The result of the quering: no relatived record in the address book.");
			System.out.println("\r\nno relatived record in the address book");
		} else {
			int count = 1;
			logger.info("The result of the quering is as followed.");
			for(AddressEntry entry : entries) {
				logger.info("NO." + count + entry);
				System.out.println("NO." + count++);
				System.out.println("name:" + entry.getName());
				System.out.println("mobile:" + entry.getMobileNumber());
				System.out.println("address:" + entry.getHomeAddress());
			}
		}
	}

	/**
     * ExecutorService will call the method to execute the query command in a thread.
     *
     * @return a string that is the result of the command executing
     */
	public List<AddressEntry> call() throws Exception {
		return operation.queryOperation(para, mode);
	}

}
