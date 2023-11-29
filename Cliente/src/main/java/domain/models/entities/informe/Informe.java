package domain.models.entities.informe;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class Informe {
    public void exportarAPDF(String content, String outputPath) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));

            document.open();
            document.add(new Paragraph(content));
            document.close();

            System.out.println("El archivo PDF se ha generado correctamente.");
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
