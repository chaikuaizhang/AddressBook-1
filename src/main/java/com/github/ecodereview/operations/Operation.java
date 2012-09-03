package com.github.ecodereview.operations;

import java.util.List;
import com.github.ecodereview.bean.AddressEntry;

/**
 * This is an interface used to operate the storage data. The data storage
 * could be in the form of Text, XML, Database and so on. The interface
 * provides three methods to operate the storage data and they will throws
 * exceptions due to operating fails. 
 * 
 * Implementation of the interface must be guaranteed to be thread-safe,
 * because the user maybe put the operation into a thread to execute. 
 *
 * @author  Chai Kuaizhang
 * @see AddressEntry
 * @see OperationExecption
 */
public interface Operation {
	
	/**
     * Returns an string over the result of add operating.
     *
     * @param entry an address entry added into the storage data
     * @return an string over the result of add operating
     * @throws OperationException if the add operating fails
     */
	public String addOperation(AddressEntry entry) throws OperationException;
	
	/**
     * Returns an string over the result of deletion operating.
     *
     * @param para a lookup string and supporting the regular expression
     * @param field a lookup field name that must be included in AddressEntry
     * @return an string over the result of deletion operating
     * @throws OperationException if the delete operating fails
     */
	public String deleteOperation(String para, String filed) throws OperationException;
	
	/**
     * Returns an list of the AddressEntry over the result of query operating.
     *
     * @param para a lookup string and supporting the regular expression
     * @param field a lookup field name that must be included in AddressEntry
     * @return an list of AddressEntry over the result of query operating
     * @throws OperationException if the query operating fails
     */
	public List<AddressEntry> queryOperation(String para, String filed) throws OperationException;
}
