/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Apr 30, 2017 , 1:43:54 PM
 */
package Features.Selection;

import Modules.Feature;
import Modules.LexiconRecord;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class FeatureSelector_LimitNGramsToLexicon extends FeatureSelector {

    private final ArrayList<LexiconRecord> lexicon;

    public FeatureSelector_LimitNGramsToLexicon(Set<Feature> feature, ArrayList<LexiconRecord> lexicon) {
        super(feature);
        this.lexicon = lexicon;
    }

    @Override
    protected void specifyPointlessFeatures() {
        features.parallelStream().forEach(f -> {
            MutableBoolean isPointLess = new MutableBoolean(true);
            lexicon.stream().forEach(lexFeature -> {
                Pattern pattern = Pattern.compile("((^)|(\\s)|" + (char) (8204) + ")" + lexFeature.word + "(($)|(\\s)|(\\z)|[.!؟?]|" + (char) (8204) + ")");
                Matcher matcher = pattern.matcher(f.text);
                if (matcher.find()) {
                    isPointLess.setFalse();
                }
            });
            if (isPointLess.isTrue()) {
                pointlessFeatures.add(f);
            }
        });
    }
}
