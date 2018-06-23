package utils;

import clustering.model.ParagraphVector;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static List<ParagraphVector> getParagraphVectorsFromCSVFile(String filePath) throws IOException {
        List<ParagraphVector> paragraphVectors = new ArrayList<>();

        Reader reader = Files.newBufferedReader(Paths.get(filePath));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        Iterable<CSVRecord> csvRecords = csvParser.getRecords();
        for (CSVRecord csvRecord : csvRecords) {
            ParagraphVector paragraphVector = new ParagraphVector();
            String label = csvRecord.get(0);
            paragraphVector.setLabel(label);
            paragraphVector.setFeatures(new ArrayList<>());

            String classLabelString = label.split("-")[0];
            Integer classLabel;
            if (!StringUtils.isNumeric(classLabelString)) {
                classLabel = -1;
            } else {
                classLabel = Integer.valueOf(classLabelString);
            }
            paragraphVector.setClassLabel(classLabel);

            for (int i = 1; i < csvRecord.size(); i++) {
                paragraphVector.getFeatures().add(Double.parseDouble(csvRecord.get(i)));
            }

            paragraphVectors.add(paragraphVector);
        }

        return paragraphVectors;
    }
}
