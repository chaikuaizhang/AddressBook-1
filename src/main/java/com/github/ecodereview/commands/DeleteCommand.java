package com.github.ecodereview.commands;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import org.apache.log4j.Logger;
import com.github.ecodereview.operations.Operation;
import com.github.ecodereview.operations.OperationException;
import com.github.ecodereview.operations.XMLOperation;

/**
 * This is an implementation of the <tt>Command</tt> interface.
 * The implementation is used to execute the deletion command.
 *
 * @author  Chai Kuaizhang
 * @see ExecutorService
 * @see Exception
 */
public class DeleteCommand implements Command, Callable<String> {

	private Operation operation = null;
	private String mode = null;
	private String para = null;
	private Logger logger = Logger.getLogger("com.github.ecodereview");

	/**
     * Execute the deletion command.
     *
     * @param executor the deletion command executes by the executor in a thread
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
		logger.info("Deleting the address entry by " + mode + ".The condition is " + para);
		try {
			operation = XMLOperation.getInstance();
		} catch (OperationException e) {
			throw new Exception("Failed to delete address entry.", e);
		}
		Future<String> future = executor.submit(this);
		String result = null;
		try {
			result = future.get();
			logger.info("Succeeding in deleting." + result);
		} catch (InterruptedException e) {
			throw new Exception("Failed to delete address entry.", e);
		} catch (ExecutionException e) {
			throw new Exception("Failed to delete address entry.", e);
		}
		System.out.println("\r\n" + result);
		
	}

	/**
     * ExecutorService will call the method to execute the deletion command in a thread.
     *
     * @return a string that is the result of the command executing
     */
	public String call() throws Exception {
		return operation.deleteOperation(para, mode);
	}
}
