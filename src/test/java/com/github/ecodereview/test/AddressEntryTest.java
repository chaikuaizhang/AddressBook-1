package com.github.ecodereview.test;

import junit.framework.Assert;
import org.junit.Test;
import com.github.ecodereview.bean.AddressEntry;

/**
 * Test AddressEntry
 * 
 * @author chaikc
 *
 */
public class AddressEntryTest {

	private AddressEntry entry1 = new AddressEntry();
	private AddressEntry entry2 = new AddressEntry();
	
	/**
	 * Test method for {@link com.github.ecodereview.bean.AddressEntry#getName()}.
	 */
	@Test
	public void testGetName() {
		entry1.setName("Li si");
		Assert.assertEquals("Li si", entry1.getName());
	}

	/**
	 * Test method for {@link com.github.ecodereview.bean.AddressEntry#setName(java.lang.String)}.
	 */
	@Test
	public void testSetName() {
		entry1.setName("chai kuaizhang");
		Assert.assertEquals("chai kuaizhang", entry1.getName());
	}

	/**
	 * Test method for {@link com.github.ecodereview.bean.AddressEntry#getMobileNumber()}.
	 */
	@Test
	public void testGetMobileNumber() {
		entry1.setMobileNumber("15189063233");
		Assert.assertEquals("15189063233", entry1.getMobileNumber());
	}

	/**
	 * Test method for {@link com.github.ecodereview.bean.AddressEntry#setMobileNumber(java.lang.String)}.
	 */
	@Test
	public void testSetMobileNumber() {
		entry1.setMobileNumber("13661893025");
		Assert.assertEquals("13661893025", entry1.getMobileNumber());
	}

	/**
	 * Test method for {@link com.github.ecodereview.bean.AddressEntry#getHomeAddress()}.
	 */
	@Test
	public void testGetHomeAddress() {
		entry1.setHomeAddress("Xujiahui");
		Assert.assertEquals("Xujiahui", entry1.getHomeAddress());
	}

	/**
	 * Test method for {@link com.github.ecodereview.bean.AddressEntry#setHomeAddress(java.lang.String)}.
	 */
	@Test
	public void testSetHomeAddress() {
		entry1.setHomeAddress("Zhangjiang Park");
		Assert.assertEquals("Zhangjiang Park", entry1.getHomeAddress());
	}

	/**
	 * Test method for {@link com.github.ecodereview.bean.AddressEntry#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		entry1.setHomeAddress("Zhangjiang");
		entry1.setMobileNumber("13661893025");
		entry1.setName("chai kuaizhang");
		entry2.setHomeAddress("Zhangjiang");
		entry2.setMobileNumber("13661893025");
		entry2.setName("chai kuaizhang");
		Assert.assertEquals(entry1, entry2);
	}

}
