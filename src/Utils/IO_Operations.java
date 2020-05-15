/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 9, 2016 , 1:35:50 PM
 */
package Utils;

import Lexicon_Approach.Lexicon_Method;
import Modules.LexiconRecord;
import Modules.CommentRecord;
import Modules.DataSet;
import Modules.DatasetRecord;
import Modules.MutableInteger;
import Modules.Record;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class IO_Operations {

    public DataSet loadDataSet(String inputURL, boolean shuffle, boolean limit) {
        DataSet result = null;
//        switch (level) {
//            case review:
        result = new DataSet(loadDataSetReview(inputURL, shuffle, limit));
//                break;
//            case sentence:
//                result = loadDataSetSentence(inputURL, shuffle, limit);
//                break;
//        }
        return result;
    }

    private ArrayList<DatasetRecord> loadDataSetReview(String inputURL, boolean shuffle, boolean limit) {
        ArrayList<DatasetRecord> comments = new ArrayList<>();
        try {
            FileInputStream excelFile;
            excelFile = new FileInputStream(new File(inputURL));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if (rows.hasNext()) {
                rows.next(); //skip header
            }

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Cell idCell = currentRow.getCell(0);
                Cell textCell = currentRow.getCell(1);
                Cell scoreCell = currentRow.getCell(2);
                Cell targetCell = currentRow.getCell(3);

                CommentRecord record = new CommentRecord();
                record.id = (int) idCell.getNumericCellValue();
                record.text = textCell.getStringCellValue().trim();
                record.labeledScore = (int) scoreCell.getNumericCellValue();
                record.labeledTarget = targetCell.getStringCellValue();

                if (record.labeledScore >= 3) {
                    record.labeledScore = (int) Math.ceil(record.labeledScore);
                }
                else {
                    record.labeledScore = (int) Math.floor(record.labeledScore);
                }

                //Calculate Polarity:
                record.labeledPolarity = record.labeledScore >= 3;  //true : positive - false : negative 

                if (record.text.length() > 1) {
                    comments.add(record);
                }
            }
            excelFile.close();
        }
        catch (Exception ex) {
            Logger.getLogger(Lexicon_Method.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        comments = doPostProcessOnDataSet(comments, shuffle, limit);
        return comments;
    }

    private ArrayList<DatasetRecord> doPostProcessOnDataSet(ArrayList<DatasetRecord> dataSet, boolean shuffle, boolean limit) {
        if (shuffle) {
            Collections.shuffle(dataSet, new Random(10));  //Fix Seed
        }
        if (limit) {
            dataSet = Stream.of(
                    dataSet.stream().filter(x -> x.labeledScore == 1).limit(2000),
                    dataSet.stream().filter(x -> x.labeledScore == 2).limit(2000),
                    dataSet.stream().filter(x -> x.labeledScore == 3).limit(2000),
                    dataSet.stream().filter(x -> x.labeledScore == 4).limit(2000),
                    dataSet.stream().filter(x -> x.labeledScore == 5).limit(2000)
            ).flatMap(Function.identity()).collect(Collectors.toCollection(ArrayList::new));
        }
        if (shuffle) {
            Collections.shuffle(dataSet, new Random(22));  //Fix Seed
        }

        System.out.println("DataSet has been loaded.");
        System.out.println("DataSet Distribution:");
        System.out.println("class 1: " + dataSet.stream().filter(x -> x.labeledScore == 1).count());
        System.out.println("class 2: " + dataSet.stream().filter(x -> x.labeledScore == 2).count());
        System.out.println("class 3: " + dataSet.stream().filter(x -> x.labeledScore == 3).count());
        System.out.println("class 4: " + dataSet.stream().filter(x -> x.labeledScore == 4).count());
        System.out.println("class 5: " + dataSet.stream().filter(x -> x.labeledScore == 5).count());

        return dataSet;
    }

    public ArrayList<LexiconRecord> loadLexicon(String inputURL) {
        ArrayList<LexiconRecord> lexicons = new ArrayList<>();
        try {
            int idCounter = 1;
            FileInputStream excelFile;
            excelFile = new FileInputStream(new File(inputURL));
            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            if (rows.hasNext()) {
                rows.next(); //skip header
            }
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                Cell wordCell = currentRow.getCell(0);
                Cell scoreCell = currentRow.getCell(1);
                LexiconRecord record = new LexiconRecord();
                record.id = idCounter++;
                record.score = (int) scoreCell.getNumericCellValue();
                record.word = wordCell.getStringCellValue();
                lexicons.add(record);
            }
            excelFile.close();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(IO_Operations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(IO_Operations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return lexicons;
    }

    public ArrayList<String> loadStringList(String inputURL) {
        List<String> lines = new ArrayList<>();
        try {
            lines = FileUtils.readLines(new File(inputURL), StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            Logger.getLogger(IO_Operations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        return (ArrayList<String>) lines;
    }

    public void saveMachedLexiconFeaturesDetails(String outputURL, ArrayList<? extends Record> dataSet, ArrayList<LexiconRecord> lexicon) {
        Workbook workBook = new XSSFWorkbook();
        Sheet sheet = workBook.createSheet("Features");
        Row rowHeader = sheet.createRow(0);
        rowHeader.createCell(0).setCellValue("ID");
        rowHeader.createCell(1).setCellValue("Review");
        rowHeader.createCell(2).setCellValue("LabeledScore");
        rowHeader.createCell(3).setCellValue("EstimatedScore");
        rowHeader.createCell(4).setCellValue("Variance");

        dataSet.stream().forEach((Record rec) -> {
            int rowNumber = dataSet.indexOf(rec) + 1;
            Row row = sheet.createRow(rowNumber);
            row.createCell(0).setCellValue(rec.id);
            row.createCell(1).setCellValue(rec.text);
            row.createCell(2).setCellValue(rec.labeledScore);
            row.createCell(3).setCellValue(rec.estimatedScore);
            row.createCell(4).setCellValue(Math.abs(rec.estimatedScore - rec.labeledScore));
            MutableInteger cellCounter = new MutableInteger(5);
            rec.machedFeatures.stream().forEach(feature -> {
                int featureScore = 0;
                for (int i = 0; i < lexicon.size(); i++) {
                    if (feature.text.equals(lexicon.get(i).word)) {
                        featureScore = lexicon.get(i).score;
                        break;
                    }
                }
                if (featureScore != 0) {
                    row.createCell(cellCounter.get_incOne()).setCellValue(feature.text + " : " + feature.occurrence_total + " * (" + featureScore + ")");
                }
            });
        });

        try (FileOutputStream fileOut = new FileOutputStream(outputURL)) {
            workBook.write(fileOut);
            workBook.close();
            fileOut.close();
        }
        catch (Exception ex) {
            Logger.getLogger(IO_Operations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
}