package com.github.ecodereview.commands;

import java.util.concurrent.ExecutorService;

/**
 * This is an interface used to execute the command inputed by the user.
 * The command will execute in a thread, so the user must provide the 
 * reference of the ExecutorService. The interface will throw exceptions
 * if the command fails to execute.
 * 
 * @author  Chai Kuaizhang
 * @see ExecutorService
 * @see Exception
 */
public interface Command {
	
	/**
     * Execute the command.
     *
     * @param executor the command executes by the executor in a thread
     * @throws Exception if the command fails to execute
     */
	public void execute(ExecutorService executor) throws Exception;
}
