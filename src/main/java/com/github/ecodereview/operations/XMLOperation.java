package com.github.ecodereview.operations;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import com.github.ecodereview.bean.AddressEntry;

/**
 * This is an implementation of the <tt>Operation</tt> interface. The
 * implementation guaranteed the thread-safe by adding the keyword synchronized
 * in the method heads.
 * 
 * @author Chai Kuaizhang
 * @see AddressEntry
 * @see OperationExecption
 */
public class XMLOperation implements Operation {

	private static XMLOperation xmlUtil = null;
	private Document document = null;
	private DocumentBuilder dombuilder = null;
	private File file = null;

	private XMLOperation() throws OperationException {
		try {
			file = new File("./address_book.xml");
			dombuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			document = dombuilder.parse(file);
			document.normalize();
		} catch (ParserConfigurationException e) {
			throw new OperationException(
					"Failed to create the the object of the Document object due to not creating the object of the DocumentBuilder .",
					e);
		} catch (SAXException e) {
			throw new OperationException(
					"Failed to create the the object of the Document object due to an error occuring during the parsing.",
					e);
		} catch (IOException e) {
			throw new OperationException(
					"Failed to create the the object of the Document object due to an I/O error.",
					e);
		}
	}

	/**
	 * Return an instance of the Operation interface and the method is a static
	 * method.
	 * 
	 * @exception OperationException
	 *                if fails to creating the instance of the Operation
	 *                interface
	 */
	public static XMLOperation getInstance() throws OperationException {
		if (xmlUtil == null) {
			try {
				xmlUtil = new XMLOperation();
			} catch (OperationException e) {
				throw e;
			}
		}
		return xmlUtil;
	}

	/**
	 * Returns an list of the AddressEntry over the result of query operating.
	 * 
	 * @param para
	 *            a lookup string and supporting the regular expression
	 * @param field
	 *            a lookup field name that must be included in AddressEntry
	 * @return an list of AddressEntry over the result of query operating
	 * @throws OperationException
	 *             if the query operating fails
	 */
	public List<AddressEntry> queryOperation(String para, String mode)
			throws OperationException {
		AddressEntry addressEntry = null;
		ArrayList<AddressEntry> addressEntries = new ArrayList<AddressEntry>();
		NodeList root = document.getDocumentElement().getChildNodes();
		if (root == null)
			return null;
		for (int i = 0; i < root.getLength(); i++) {
			if (!(root.item(i) instanceof Element))
				continue;
			Element addressElement = (Element) root.item(i);
			String addressName = addressElement.getElementsByTagName("Name")
					.item(0).getFirstChild().getNodeValue();
			String addressMobile = addressElement
					.getElementsByTagName("Mobile").item(0).getFirstChild()
					.getNodeValue();
			String addressHome = addressElement.getElementsByTagName("Home")
					.item(0).getFirstChild().getNodeValue();
			try {
				if ((("name").equals(mode) && !Pattern.matches(para,
						addressName))
						|| (("mobile").equals(mode) && !Pattern.matches(para,
								addressMobile))
						|| (("address").equals(mode) && !Pattern.matches(para,
								addressHome)))
					continue;
			} catch (PatternSyntaxException e) {
				throw new OperationException(
						"The regular expression that you input has a syntax error.Please check it!",
						e);
			}
			addressEntry = new AddressEntry();
			addressEntry.setName(addressName);
			addressEntry.setMobileNumber(addressMobile);
			addressEntry.setHomeAddress(addressHome);
			addressEntries.add(addressEntry);
		}
		return addressEntries;
	}

	/**
	 * Returns an string over the result of add operating.
	 * 
	 * @param entry
	 *            an address entry added into the storage data
	 * @return an string over the result of add operating
	 * @throws OperationException
	 *             if the add operating fails
	 */
	public synchronized String addOperation(AddressEntry entry)
			throws OperationException {
		Element root = document.getDocumentElement();
		Element address = document.createElement("Address");
		Element addressName = document.createElement("Name");
		Element addressMobile = document.createElement("Mobile");
		Element addressHome = document.createElement("Home");
		Text txtName = document.createTextNode(entry.getName());
		Text txtMobile = document.createTextNode(entry.getMobileNumber());
		Text txtHome = document.createTextNode(entry.getHomeAddress());
		addressName.appendChild(txtName);
		addressMobile.appendChild(txtMobile);
		addressHome.appendChild(txtHome);
		address.appendChild(addressName);
		address.appendChild(addressMobile);
		address.appendChild(addressHome);
		root.appendChild(address);
		try {
			updateXMLFile();
		} catch (OperationException e) {
			throw e;
		}
		return "address entry added.";
	}

	/**
	 * Returns an string over the result of deletion operating.
	 * 
	 * @param para
	 *            a lookup string and supporting the regular expression
	 * @param field
	 *            a lookup field name that must be included in AddressEntry
	 * @return an string over the result of deletion operating
	 * @throws OperationException
	 *             if the delete operating fails
	 */
	public synchronized String deleteOperation(String para, String mode)
			throws OperationException {
		int count = 0;
		NodeList root = document.getDocumentElement().getChildNodes();
		List<Node> removeNodeList = new ArrayList<Node>();
		for (int i = 0; i < root.getLength(); i++) {
			Node node = root.item(i);
			if (!(root.item(i) instanceof Element))
				continue;
			Element addressElement = (Element) node;
			String addressName = addressElement.getElementsByTagName("Name")
					.item(0).getFirstChild().getNodeValue();
			String addressMobile = addressElement
					.getElementsByTagName("Mobile").item(0).getFirstChild()
					.getNodeValue();
			String addressHome = addressElement.getElementsByTagName("Home")
					.item(0).getFirstChild().getNodeValue();
			try {
				if ((("name").equals(mode) && !Pattern.matches(para,
						addressName))
						|| (("mobile").equals(mode) && !Pattern.matches(para,
								addressMobile))
						|| (("address").equals(mode) && !Pattern.matches(para,
								addressHome)))
					continue;
			} catch (PatternSyntaxException e) {
				throw new OperationException(
						"The regular expression that you input has a syntax error.Please check it!",
						e);
			}
			removeNodeList.add(node);
		}
		for (Node node : removeNodeList) {
			node.getParentNode().removeChild(node);
			count++;
		}
		String result = null;
		try {
			updateXMLFile();
			if (count < 2)
				result = count + " address entry deleted";
			else
				result = count + " address entries deleted";
		} catch (OperationException e) {
			throw e;
		}
		return result;
	}

	private void updateXMLFile() throws OperationException {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		FileOutputStream out = null;
		Transformer transFormer;

		try {
			transFormer = transFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			out = new FileOutputStream(file);
			StreamResult xmlResult = new StreamResult(out);
			transFormer.transform(domSource, xmlResult);
			out.flush();
			//out.close();
			document = dombuilder.parse(file);
		} catch (TransformerConfigurationException e) {
			throw new OperationException(
					"Failed to update the data file due to not creating Transformer Object.",
					e);
		} catch (FileNotFoundException e) {
			throw new OperationException(
					"Failed to update the data file due to not found the data file.",
					e);
		} catch (TransformerException e) {
			throw new OperationException(
					"Failed to update the data file due to an error occuring during storing the data.",
					e);
		} catch (SAXException e) {
			throw new OperationException(
					"Failed to update the document object due to an error occuring during parsing the data file.",
					e);
		} catch (IOException e) {
			throw new OperationException(
					"An I/O error occurs during updating the data file.", e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					throw new OperationException(
							"An I/O error occurs during updating the data file.",
							e);
				}
		}
	}
}
