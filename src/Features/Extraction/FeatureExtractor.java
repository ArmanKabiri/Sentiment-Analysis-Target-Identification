/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 25, 2017 , 2:49:25 PM
 */
package Features.Extraction;

import Modules.DataSet;
import Modules.Feature;
import Modules.MutableInteger;
import Modules.Record;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FeatureExtractor {

    private static final Logger LOGGER = Logger.getLogger(FeatureExtractor.class.getName());
    protected DataSet dataSet;
    protected String regexWord = "[آابپتثجچحخدذرزژسشصضطظعغفقکگلنموهی" + (char) (8204) + "]+";   //Persian
//    protected String regexWord = "[a-zA-Z_]{2,}";       //English

    public FeatureExtractor(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public abstract Set<Feature> run();

    protected Set<Feature> processFeatures(Set<String> rawFeatures) {
        LOGGER.info("Extracting Features.");
        Set<Feature> features = Collections.synchronizedSet(new HashSet<Feature>());

        MutableInteger progressCounter = new MutableInteger(0);
        rawFeatures.parallelStream().forEach(word -> {
            Feature feature = new Feature(word);
            Pattern pattern = Pattern.compile("((^)|(\\s)|" + (char) (8204) + ")" + word + "(($)|(\\s)|(\\z)|[.!؟?]|" + (char) (8204) + ")");
            dataSet.records.stream().forEach((Record record) -> {
                Matcher matcher = pattern.matcher(record.text);
                if (matcher.find()) {
                    String gp = matcher.group();    //DONT REMOVE THIS LINE
                    feature.occurrence_reviews++;
                    feature.occurrence_total++;
                }
                while (matcher.find()) {
                    String gp = matcher.group();    //DONT REMOVE THIS LINE
                    feature.occurrence_total++;
                }
            });
            if (feature.occurrence_total > 0) {
                features.add(feature);
            }
            int step = progressCounter.get_incOne();
            if (step % 100 == 0) {
                String logStr = (step / (float) rawFeatures.size() * 100) + "%";
                LOGGER.info(logStr);
            }
        });
        LOGGER.info("Features Have been Extracted.");

        return features;
    }
}