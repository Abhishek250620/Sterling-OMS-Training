package filehandling;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CreateXML {

	public static void main(String[] args) throws Exception {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.newDocument();

		Element students = doc.createElement("Students");
		Element student = doc.createElement("Student");
		student.setAttribute("Name", "Tom");
		student.setAttribute("USN", "123");
		student.setAttribute("Marks", "85");
		students.appendChild(student);
		
		student = doc.createElement("Student");
		student.setAttribute("Name", "Jerry");
		student.setAttribute("USN", "124");
		student.setAttribute("Marks", "80");
		students.appendChild(student);
		
		doc.appendChild(students);
		
		TransformerFactory tf=TransformerFactory.newInstance();
		Transformer trn=tf.newTransformer();
        trn.setOutputProperty(OutputKeys.INDENT, "yes");

        FileOutputStream fos = new FileOutputStream("D:\\InnovationTool\\Demo.xml");
		trn.transform(new DOMSource(doc), new StreamResult(fos));
	}
}
