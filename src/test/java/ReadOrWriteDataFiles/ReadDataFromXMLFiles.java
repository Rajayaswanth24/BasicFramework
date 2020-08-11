package ReadOrWriteDataFiles;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadDataFromXMLFiles {

	public void readFromXML(String filePath) {
		try {

			File fXmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("section");
			System.out.println("----------------------------");
			System.out.println("Node length :" + nList.getLength());
			for (int temp = 1; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("Section Name : " + eElement.getAttribute("name"));
					NodeList childNList = eElement.getElementsByTagName("item");
					System.out.println("No of child nodes :" + childNList.getLength());
					for (int temp1 = 0; temp1 < childNList.getLength(); temp1++) {
						Node childnNode = childNList.item(temp1);
						Element childeElement = (Element) childnNode;
						System.out.println(childeElement.getAttribute("key"));
						System.out.println(childeElement.getAttribute("value"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void readFromXMLTwo(String filePath) {
		try {
			File file = new File(filePath); // file location
											// should be
			// specified correctly
			// Prepare XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(file);
			document.getDocumentElement().normalize();
			NodeList node = document.getElementsByTagName("catalog");
			System.out.println("catalog Details");
			System.out.println("________________________________________________");

			// Read XML to get test data

			for (int i = 0; i < node.getLength(); i++) {
				Node currentNode = node.item(i);

				if (currentNode.getNodeType() == Node.ELEMENT_NODE) {

					Element element = (Element) currentNode;
					NodeList firstNameList = element.getElementsByTagName("author");
					Element firstName = (Element) firstNameList.item(0);
					NodeList firstName1 = firstName.getChildNodes();
					String fname = ((Node) firstName1.item(0)).getNodeValue();
					System.out.println("author:" + fname);

					NodeList lastNameList = element.getElementsByTagName("title");
					Element lastName = (Element) lastNameList.item(0);
					NodeList lastName1 = lastName.getChildNodes();
					String lname = ((Node) lastName1.item(0)).getNodeValue();
					System.out.println("title:" + lname);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
