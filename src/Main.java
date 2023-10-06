import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            File docu = new File("src/sales.xml");
            DocumentBuilderFactory fabricaDocu = DocumentBuilderFactory.newInstance();
            DocumentBuilder construDocus = fabricaDocu.newDocumentBuilder();
            Document docus = construDocus.parse(docu);

            Scanner escaner = new Scanner(System.in);
            System.out.print("Escribe un porcentaje entre 5% y 15%: ");
            double porciento = escaner.nextDouble();
            escaner.nextLine();

            if (porciento < 5 || porciento > 15) {
                System.out.println("Solo porcentajes de 5% y 15%");
                return;
            }

            System.out.print("Nombre del departamento: ");
            String depaNombre = escaner.nextLine();

            // Obtener todos los elementos de ventas
            NodeList ventaLista = docus.getElementsByTagName("sales");

            for (int j = 0; j < ventaLista.getLength(); j++) {
                Element ventaElement = (Element) ventaLista.item(j);
                Element parentElement = (Element) ventaElement.getParentNode();

                String nombreDepa = parentElement.getElementsByTagName("department").item(0).getTextContent();

                if (nombreDepa.equals(depaNombre)) {
                    double ventaActu = Double.parseDouble(ventaElement.getTextContent());
                    double ventaNueva = ventaActu * (1 + (porciento / 100));

                    ventaElement.setTextContent(String.format("%.2f", ventaNueva));
                }
            }

            TransformerFactory transFabrica = TransformerFactory.newInstance();
            Transformer transformer = transFabrica.newTransformer();
            DOMSource fuente = new DOMSource(docus);
            StreamResult resultado = new StreamResult(new File("new_sales.xml"));
            transformer.transform(fuente, resultado);

            System.out.println("El archivo new_sales.xml se ha generado con éxito.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
