package com.github.ecodereview.commands;

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
 * The implementation is used to execute the add command.
 *
 * @author  Chai Kuaizhang
 * @see ExecutorService
 * @see Exception
 */
public class AddCommand implements Command, Callable<String> {

	private AddressEntry addressEntry = new AddressEntry();
	private Operation operation = null;
	private Logger logger = Logger.getLogger("com.github.ecodereview");

	/**
     * Execute the add command.
     *
     * @param executor the add command executes by the executor in a thread
     * @throws Exception if the command fails to execute
     */
	public void execute(ExecutorService executor) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.print("name:");
		String name = scanner.nextLine().trim();
		System.out.print("\r\nmobile:");
		String mobile = scanner.nextLine().trim();
		System.out.print("\r\naddress:");
		String address = scanner.nextLine().trim();
		addressEntry.setName(name);
		addressEntry.setMobileNumber(mobile);
		addressEntry.setHomeAddress(address);
		logger.info("Adding the address entry into the address book. The address entry is " + addressEntry);
		try {
			operation = XMLOperation.getInstance();
		} catch (OperationException e) {
			throw new Exception("Failed to add address entry.", e);
		}
		Future<String> future = executor.submit(this);
		String result = null;;
		try {
			result = future.get();
			logger.info("Succeed in adding the address entry.");
		} catch (InterruptedException e) {
			throw new Exception("Failed to add address entry.", e);
		} catch (ExecutionException e) {
			throw new Exception("Failed to add address entry.", e);
		}
		System.out.println("\r\n" + result);
	}

	/**
     * ExecutorService will call the method to execute the add command in a thread.
     *
     * @return a string that is the result of the command executing
     */
	public String call() throws Exception {
		return operation.addOperation(addressEntry);
	}
}
