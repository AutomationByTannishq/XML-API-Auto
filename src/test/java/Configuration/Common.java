package Configuration;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.Reporter;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Common{
	public WebDriver driver;
	public Common(WebDriver driver) {
		this.driver = driver;
	}

	public static void log(String msg) {
		Reporter.log("Step:- " +msg+ "<br>");
		System.out.println("Step:- " +msg);
	}

	private static void writeXMLToFile(Document document, String fileName) throws IOException {
		try (OutputStream os = new FileOutputStream(fileName)) {
			javax.xml.transform.TransformerFactory.newInstance()
					.newTransformer().transform(new javax.xml.transform.dom.DOMSource(document),
							new javax.xml.transform.stream.StreamResult(os));
		} catch (TransformerConfigurationException e) {
			throw new RuntimeException(e);
		} catch (TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	public static void Update_XML_File(){
		try {
			String tag_value="";
			// Load XML file
			File xmlFile = new File("src/xml/DataFile.xml");
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFile);

			// Find the element by tag name
			NodeList nodeList = doc.getElementsByTagName("OrigAssigneeFlag");

			// Iterate over all matching elements
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					//Get Element Value
					String value = (String) element.getChildNodes().item(0).getNodeValue();
					tag_value=value.trim();
					System.out.println(tag_value);

					// Edit data of the element
					if(tag_value.equalsIgnoreCase("y")){
						String newData = "N"; // Your new value
						element.setTextContent(newData);
					}

					if(tag_value.equalsIgnoreCase("n")){
						String newData = "Y"; // Your new value
						element.setTextContent(newData);
					}
				}
			}

			// Write the updated XML content back to the file
			writeXMLToFile(doc, "src/xml/DataFile.xml");

			System.out.println("XML file updated successfully.");

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void verify_XML_Sent_response(){
		RestAssured.baseURI = "Parth API";

		File xmlFile = new File("path_to_your_xml_file.xml");

		Response response = RestAssured.given()
				.contentType("application/xml")
				.body(xmlFile)
				.post();

		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Expected status code 200 but found" + statusCode);
	}
}