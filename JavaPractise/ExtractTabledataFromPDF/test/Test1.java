import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class Test1 {
    public static void main(String args[]){
        File pdffile = new File("/home/huabingood/沈之春重疾10万.pdf");

        PDDocument document = null;

        try {
            document = PDDocument.load(pdffile);

            int totalPage = document.getNumberOfPages();

            PDFTextStripper stripper = new PDFTextStripper();

            stripper.setSortByPosition(true);
            stripper.setStartPage(6);
            stripper.setEndPage(6);


            String content = stripper.getText(document);

            System.out.println(content);

        } catch (IOException e) {
            e.printStackTrace();
        }
    } // main
}
