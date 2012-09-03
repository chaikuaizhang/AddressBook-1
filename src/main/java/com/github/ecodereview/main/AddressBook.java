package com.github.ecodereview.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import com.github.ecodereview.commands.Command;

/**
 * This is an startup class as the application. It will create all instances of
 * the <tt>Command<tt> interface and store them in a hash map. When the
 * user input a command, the application will search the hash map and invoke
 * the corresponding interface object to execute the command.
 * 
 * @author Chai Kuaizhang
 * @see ExecutorService
 */
public class AddressBook {

	private HashMap<String, Command> commands = new HashMap<String, Command>();
	private final ExecutorService exec = Executors.newCachedThreadPool();
	private Properties props = new Properties();
	private Logger logger = Logger.getLogger("com.github.ecodereview");

	/**
	 * The constructor is responsible for creating the instances of the command
	 * interface and initialing the configuration of the log4j.
	 */
	public AddressBook() throws Exception {
		
		PropertyConfigurator.configure(this.getClass().getResourceAsStream("/log4j.properties"));
		
		try {
			props.load(this.getClass().getResourceAsStream("/commands.properties"));
			commands.put("!help", null);
			commands.put("!quit", null);
			Enumeration<?> enumaration = props.propertyNames();
			while (enumaration.hasMoreElements()) {
				String commandName = (String) enumaration.nextElement();
				String commandClassName = props.getProperty(commandName);
				Class<?> commandClass = Class.forName(commandClassName);
				Command command = (Command) commandClass.newInstance();
				commands.put(commandName, command);
			}
		} catch (FileNotFoundException e) {
			logger.error("Not found the file:commands.properties.", e);
			System.out
					.println("The application will end due to failing to read the file commands.properties");
			throw new Exception(e);
		} catch (IOException e) {
			logger.error("Failed to load the file commands.properties.", e);
			System.out
					.println("The application will end due to failing to read the file commands.properties");
			throw new Exception(e);
		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException:", e);
			System.out
					.println("The application will end due to not creating Commands.");
			throw new Exception(e);
		} catch (InstantiationException e) {
			logger.error("InstantiationException:", e);
			System.out
					.println("The application will end due to not creating Commands.");
			throw new Exception(e);
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException:", e);
			System.out
					.println("The application will end due to not creating Commands.");
			throw new Exception(e);
		}
	}

	/**
	 * The main method is responsible for starting the application.
	 */
	public static void main(String[] args) {
		AddressBook addressBook = null;
		try {
			addressBook = new AddressBook();
			addressBook.start();
		} catch (Exception e) {
			// The exception has been handled in the constructor of the class
			// AddressBook.
			// So nothing need to do and the application will exit.
		}
	}

	/**
	 * The method is responsible for receiving the user's input. If the input is
	 * a valid command, it will get the corresponding instance of the command
	 * and invoke the execute method to execute the command.
	 */
	public void start() {
		displayWelcomeInfo();
		logger.info("The application have started successfully.");
		Scanner scanner = new Scanner(System.in);
		while (!exec.isShutdown()) {
			System.out.print("ab>");
			String command = scanner.nextLine();
			command = command.trim();
			logger.info("The user inputs the command:" + command);
			if (("").equals(command))
				continue;
			if (!commands.containsKey(command)) {
				System.out.println("Bad command line.");
				displayHelpInfo();
			} else if (("!help").equals(command)) {
				displayHelpInfo();
			} else if (("!quit").equals(command)) {
				stop();
				break;
			} else {
				try {
					commands.get(command).execute(exec);
				} catch (Exception e) {
					System.out.println(e.getMessage() + "\r\n"
							+ e.getCause().getCause().getMessage());
					logger.info(e.getMessage(), e);
				}
			}
		}
	}

	private void stop() {
		exec.shutdown();
	}

	private void displayHelpInfo() {
		String helpInfo = "--add Add a new address into the AddressBook\r\n"
				+ "--query Query an address in the AddressBook by the name, moblie phone or home address\r\n"
				+ "--delete Delete an address in the AddressBook by the name, moblie phone or home address\r\n"
				+ "--!help Display the help information\r\n"
				+ "--!quit Quit the system \r\n";
		System.out.println(helpInfo);
	}

	private void displayWelcomeInfo() {
		String welcomeInfo = "=======================================\r\n"
				+ "   Welcome to use this Address Book!\r\n"
				+ "   Version:1.0.0\r\n" + "   Author :Chaikuaizhang\r\n"
				+ "   Date   :2012/09/03\r\n"
				+ "=======================================\r\n";
		System.out.println(welcomeInfo);
	}
}
