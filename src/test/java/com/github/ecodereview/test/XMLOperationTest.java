package com.github.ecodereview.test;

import java.util.List;
import junit.framework.Assert;
import org.junit.Test;
import com.github.ecodereview.bean.AddressEntry;
import com.github.ecodereview.operations.Operation;
import com.github.ecodereview.operations.OperationException;
import com.github.ecodereview.operations.XMLOperation;

/**
 * Test XMLOperation
 * 
 * @author chaikc
 *
 */
public class XMLOperationTest {

	/**
	 * Test method for {@link com.github.ecodereview.operations.XMLOperation#queryOperation(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testQueryOperation() {
		AddressEntry entry = new AddressEntry();
		entry.setHomeAddress("test query home address");
		entry.setMobileNumber("test query mobile number");
		entry.setName("test query name");
		Operation operation = null;
		List<AddressEntry> list = null;
		try {
			operation = XMLOperation.getInstance();
			operation.addOperation(entry);
			list = operation.queryOperation("test query name", "name");
			Assert.assertEquals(1, list.size());
			list = operation.queryOperation("test name", "name");
			Assert.assertEquals(0, list.size());
			operation.deleteOperation("test query name", "name");
		} catch (OperationException e) {
			Assert.assertTrue(false);
		}
	}

	/**
	 * Test method for {@link com.github.ecodereview.operations.XMLOperation#addOperation(com.github.ecodereview.bean.AddressEntry)}.
	 */
	@Test
	public void testAddOperation() {
		AddressEntry entry = new AddressEntry();
		entry.setHomeAddress("test add home address");
		entry.setMobileNumber("test add mobile number");
		entry.setName("test add name");
		Operation operation = null;
		List<AddressEntry> list = null;
		try {
			operation = XMLOperation.getInstance();
			operation.addOperation(entry);
			list = operation.queryOperation("test add name", "name");
			Assert.assertEquals(1, list.size());
			operation.deleteOperation("test add name", "name");
		} catch (OperationException e) {
			Assert.assertTrue(false);
		}
	}

	/**
	 * Test method for {@link com.github.ecodereview.operations.XMLOperation#deleteOperation(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDeleteOperation() {
		AddressEntry entry = new AddressEntry();
		entry.setHomeAddress("test delete home address");
		entry.setMobileNumber("test delete mobile number");
		entry.setName("test delete name");
		Operation operation = null;
		List<AddressEntry> list = null;
		try {
			operation = XMLOperation.getInstance();
			operation.addOperation(entry);
			operation.deleteOperation("test delete name", "name");
			list = operation.queryOperation("test delete name", "name");
			Assert.assertEquals(0, list.size());
			operation.deleteOperation("test delete name", "name");
		} catch (OperationException e) {
			Assert.assertTrue(false);
		}
	}

}
