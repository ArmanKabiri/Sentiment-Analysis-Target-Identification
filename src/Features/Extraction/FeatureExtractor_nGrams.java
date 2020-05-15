/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 25, 2017 , 2:59:40 PM
 */
package Features.Extraction;

import Modules.DataSet;
import Modules.Feature;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections4.set.ListOrderedSet;
import weka.core.tokenizers.NGramTokenizer;

public class FeatureExtractor_nGrams extends FeatureExtractor {

    private static final Logger LOGGER = Logger.getLogger(FeatureExtractor_nGrams.class.getName());
    private int minNGram, maxNGram;

    public FeatureExtractor_nGrams(DataSet dataSet, int minNGram, int maxNGram) {
        super(dataSet);
        this.minNGram = minNGram;
        this.maxNGram = maxNGram;
    }

    @Override
    public Set<Feature> run() {

        NGramTokenizer nGramTokenizer = new NGramTokenizer();
        nGramTokenizer.setNGramMaxSize(maxNGram);
        nGramTokenizer.setNGramMinSize(minNGram);

        StringBuilder strBuilderValidText = new StringBuilder();
        Pattern pattern = Pattern.compile(regexWord);
        dataSet.records.stream().forEach(review -> {
            Matcher matcher = pattern.matcher(review.text); //Match Persian Words
            while (matcher.find()) {
                String word = matcher.group();
                if (word.trim().length() > 1) {
                    strBuilderValidText.append(word).append(" ");
                }
            }
            strBuilderValidText.deleteCharAt(strBuilderValidText.length() - 1);
            strBuilderValidText.append("\n");
        });

        String fullText = strBuilderValidText.toString();
        LOGGER.info("Tokenizing Features");
        nGramTokenizer.tokenize(fullText);

        ListOrderedSet<Feature> featureVector = new ListOrderedSet<>();
        while (nGramTokenizer.hasMoreElements()) {
            String word = nGramTokenizer.nextElement();
            Feature newF = new Feature(word);
            if (featureVector.contains(newF)) {
                Feature oldF = featureVector.get(featureVector.indexOf(newF));
                oldF.occurrence_total++;
            } else {
                newF.occurrence_total++;
                newF.UsedFeatureExtraction = minNGram + "-" + maxNGram + " Grams";
                featureVector.add(newF);
            }
        }
//        return processFeatures(rawFeatures);
        LOGGER.info("Features Have been Extracted.");
        return featureVector;   //It Does not Calculate occurrence Review! Just Total.
    }
}