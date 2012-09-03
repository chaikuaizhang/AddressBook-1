package com.github.ecodereview.bean;

/**
 * This is an data object.
 *
 * @author  Chai Kuaizhang
 */
public class AddressEntry {
	
	private String name = null;
	private String mobileNumber = null;
	private String homeAddress = null;
	
	/**
	 * Return the name
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set the name
	 * 
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Return the mobile number
	 * 
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}
	
	/**
	 * Set the mobile number
	 * 
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	/**
	 * Return the home address
	 * 
	 * @return the homeAddress
	 */
	public String getHomeAddress() {
		return homeAddress;
	}

	/**
	 * Set home address
	 * 
	 * @param homeAddress the homeAddress to set
	 */
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	/**
     * Return the string of the object.
     *
     * @return the string of the object
     */
	@Override
	public String toString() {
		return "AddressEntry [name=" + name + ", mobileNumber=" + mobileNumber
				+ ", homeAddress=" + homeAddress + "]";
	}

	/**
     * Return the hash code of the object.
     *
     * @return the hash code of the object
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((homeAddress == null) ? 0 : homeAddress.hashCode());
		result = prime * result
				+ ((mobileNumber == null) ? 0 : mobileNumber.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
     * Return the result of the two objects comparison.
     *
     * @return the result of the two objects comparison
     */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressEntry other = (AddressEntry) obj;
		if (homeAddress == null) {
			if (other.homeAddress != null)
				return false;
		} else if (!homeAddress.equals(other.homeAddress))
			return false;
		if (mobileNumber == null) {
			if (other.mobileNumber != null)
				return false;
		} else if (!mobileNumber.equals(other.mobileNumber))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
