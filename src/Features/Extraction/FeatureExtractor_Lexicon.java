/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 25, 2017 , 5:36:17 PM
 */
package Features.Extraction;

import Modules.DataSet;
import Modules.Feature;
import Modules.LexiconRecord;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FeatureExtractor_Lexicon extends FeatureExtractor {

    private final ArrayList<LexiconRecord> lexicon;

    public FeatureExtractor_Lexicon(DataSet dataSet, ArrayList<LexiconRecord> lexicon) {
        super(dataSet);
        this.lexicon = lexicon;
    }

    @Override
    public Set<Feature> run() {
        Set<Feature> features = processFeatures(lexicon.stream().map(record -> record.word).collect(Collectors.toCollection(HashSet::new)));
        features.stream().forEach(f -> {
            f.UsedFeatureExtraction = "Lexicon";
        });
        return features;
    }
}
