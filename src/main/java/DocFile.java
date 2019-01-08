import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.w3c.dom.ranges.Range;

import java.awt.font.NumericShaper;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Иванка on 28.12.2018.
 */
public class DocFile {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\Иванка\\Desktop\\(1) Договор поставки.docx";
        String fileName2 = "C:\\Users\\Иванка\\Desktop\\(1) Договор поставки-201812281438496350.DOCX";
        String patternFileName = "C:\\Users\\Иванка\\Desktop\\(1) Договор поставки эталон2.docx";
        String patternFileName3 = "C:\\Users\\Иванка\\Desktop\\(1) Договор поставки эталон3.docx";
        String docNum = "6-1-021/000433-18";
//        readFile(fileName);
//        System.out.println("\n\n\n\n\n\n");

        StringBuilder standard = readFilePOIFSFileSystem(patternFileName);
        StringBuilder standard3 = readFilePOIFSFileSystem(patternFileName3);
        StringBuilder file = readFilePOIFSFileSystem(fileName2);

        Pattern pattern = Pattern.compile(String.format(String.valueOf(standard3), "6-1-021/000730-18", "28 декабря 2018 г."));
        Matcher matcher = pattern.matcher(file);
        if (matcher.find()) {
            System.out.println("Goooooooooood, we've done it");
        } else {
            System.out.println("I have bad news, your method doesn't work");
        }



//        if (String.format(String.valueOf(readFilePOIFSFileSystem(patternFileName)), docNum).equals(String.valueOf(readFilePOIFSFileSystem(fileName)))) {
//            System.out.println("Goooooooooood, we've done it");
//        } else {
//            System.out.println("I have bad new, your method doesn't work");
//        }
//        System.out.println(String.format(String.valueOf(readFilePOIFSFileSystem(patternFileName)), docNum));
//        System.out.println(String.format(String.valueOf(readFile(patternFileName)),docNum));
//        System.out.println(String.format(String.valueOf(readFilePOIFSFileSystem(patternFileName)),docNumber));
    }

    static String rexExp1 = "^Договор поставки № %s";
    static String docNumber = "\\d-\\d-\\d{3}/\\d{6}-\\d{2}";

    public static StringBuilder readFilePOIFSFileSystem(String fileName) {
        StringBuilder documentText = new StringBuilder();
        List<XWPFTable> tables = new ArrayList<>();
        StringBuilder allText = new StringBuilder();
        try {
            FileInputStream fis = new FileInputStream(fileName);
            XWPFDocument xdoc = new XWPFDocument(OPCPackage.open(fis));
            XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(xdoc);
            XWPFFooter docFooter = headerFooterPolicy.getDefaultFooter();
            if (docFooter.getAllPictures().size() != 1) {
                System.out.println("Ooops, count of pictures in footer != 1");
            } else {
                System.out.println("Goood, count of pictures in footer == 1");
            }

            Iterator<IBodyElement> bodyElementIterator = xdoc.getBodyElementsIterator();
            while (bodyElementIterator.hasNext()) {
                IBodyElement element = bodyElementIterator.next();
                if ("TABLE".equalsIgnoreCase(element.getElementType().name())) {
                    XWPFTable table = (XWPFTable) element;
                    allText.append(table.getText());
                    tables.add(table);
                    System.out.println(table.getText());

                } else if ("PARAGRAPH".equalsIgnoreCase(element.getElementType().name())) {
                    XWPFParagraph paragraph = (XWPFParagraph) element;
                    allText.append(paragraph.getText());
                    documentText.append(paragraph.getText());
                    System.out.println(paragraph.getText());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        System.out.println("\n\n\n\n\n\n");
//        if (String.format(String.valueOf(textPatternStart), "6-1-021/000433-18").equals(allText)) {
//            System.out.println("Doooooooooood, we've done it");
//        } else {
//            System.out.println("I have bad new, your method doesn't work");
//        }
//        System.out.println(tables);
//        System.out.println(documentText);
        return allText;
    }


    public static StringBuilder readFile(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {

            // открываем файл и считываем его содержимое в объект XWPFDocument
            XWPFDocument docxFile = new XWPFDocument(OPCPackage.open(fileInputStream));


            XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(docxFile);

            // считываем верхний колонтитул (херед документа)
            XWPFHeader docHeader = headerFooterPolicy.getDefaultHeader();
//            System.out.println(docHeader.getText());

            // печатаем содержимое всех параграфов документа в консоль
            List<XWPFParagraph> paragraphs = docxFile.getParagraphs();
            System.out.println("Paragraphs count:  " + paragraphs.size());



            for (XWPFParagraph p : paragraphs) {
                stringBuilder.append(p.getText());
            }


//            System.out.println(String.format(String.valueOf(stringBuilder), "sda"));

            // считываем нижний колонтитул (футер документа)
//            XWPFFooter docFooter = headerFooterPolicy.getDefaultFooter();
//            System.out.println("Footer:  " + docFooter.getText());

//            System.out.println("_____________________________________");
            // печатаем все содержимое Word файла
//            XWPFWordExtractor extractor = new XWPFWordExtractor(docxFile);
//            System.out.println(extractor.getText());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stringBuilder;
    }


}
