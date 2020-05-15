/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 9, 2016 , 12:51:36 PM
 */
package ML_Approach;

import Features.Matcher.FeatureMatcher;
import Modules.DataSet;
import Modules.DatasetRecord;
import Modules.Feature;
import Modules.MutableInteger;
import java.util.ArrayList;
import Modules.Record;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class ArffFileCreatorBase {

    DataSet dataSet;
    ArrayList<Feature> wholeFeatures;
    FeatureMatcher featureMatcher_SampleInstance;

    public ArffFileCreatorBase(DataSet dataSet, ArrayList<Feature> features, FeatureMatcher featureMatcher) {
        this.dataSet = dataSet;
        this.wholeFeatures = features;
        this.featureMatcher_SampleInstance = featureMatcher;
    }

    public boolean perform(String filePath) {
        System.out.println("Creating ARFF / Matching Features");
        String arffStr = createFileStructure();
        try {
            FileUtils.writeStringToFile(new File(filePath), arffStr, StandardCharsets.UTF_8);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(ArffFileCreatorBase.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private String createFileStructure() {
        System.out.println("Creating ARFF FIle");
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("@relation words\n\n");   //Header
        wholeFeatures.stream().forEach(feature -> {
            strBuilder.append("@attribute ").append("'").append(feature.text).append("'").append(" {0,1}\n");
        });
        strBuilder.append("% \"0\" is dummy value in class attribute for being fit in sparse format.\n");
        strBuilder.append("@attribute Class5Star {0,1,2,3,4,5}\n");
        strBuilder.append("\n@data\n");

        //@data part
        List<ArrayList<Byte>> allFeaturesCollection = Collections.synchronizedList(new ArrayList<ArrayList<Byte>>());   //Features Matrix
        for (DatasetRecord record : dataSet.records) {
            allFeaturesCollection.add(null);
        }

        MutableInteger stepCounter = new MutableInteger(0);
        dataSet.records.parallelStream().forEach(record -> {
            FeatureMatcher featureMatcher = null;
            try {
                featureMatcher = featureMatcher_SampleInstance.getClass().newInstance();
            }
            catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ArffFileCreatorBase.class.getName()).log(Level.SEVERE, null, ex);
            }
            int index = dataSet.records.indexOf(record);
            ArrayList<Byte> recordFeatures = new ArrayList<>();
            for (int i = 0; i < wholeFeatures.size() + 1; i++) {
                recordFeatures.add(new Byte("0"));
            }

            int score = (int) record.labeledScore;
            recordFeatures.set(recordFeatures.size() - 1, (byte) score); //class

            for (int i = 0; i < wholeFeatures.size(); i++) {
                String word = wholeFeatures.get(i).text;
                featureMatcher.setRegexCorePharase(word);
                featureMatcher.setText(record.text);
                if (featureMatcher.matchNext() != null) {
                    recordFeatures.set(i, Byte.parseByte("1"));
                }
            }

            double step = stepCounter.get_incOne();
            if (step % (dataSet.records.size() / 100) == 0) {
                synchronized (stepCounter) {
                    System.out.println((step / dataSet.records.size() * 100) + " %");
                }
            }
            allFeaturesCollection.set(index, recordFeatures);
        });

        System.out.println("");
        System.out.println("ARFF File has been created.");
        stepCounter.setVal(0);
        allFeaturesCollection.stream().forEachOrdered(features -> {
            strBuilder.append("{");
            for (int i = 0; i < features.size(); i++) {
                byte feature = features.get(i);
                if (feature != 0) {
                    strBuilder.append(i).append(" ").append(feature).append(",");
                }
            }
            strBuilder.deleteCharAt(strBuilder.length() - 1);
            strBuilder.append("}").append("\n");
            // show progress
            double step = stepCounter.get_incOne();
            if (step % (dataSet.records.size() / 100) == 0) {
                synchronized (stepCounter) {
                    System.out.println((step / dataSet.records.size() * 100) + " %");
                }
            }
        });
        System.out.println("");
        System.out.println("ARFF File has been stored.");
        return strBuilder.toString();
    }
}
