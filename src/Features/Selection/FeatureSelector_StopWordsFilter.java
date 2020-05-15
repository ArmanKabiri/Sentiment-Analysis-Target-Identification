/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Apr 30, 2017 , 1:43:54 PM
 */
package Features.Selection;

import Modules.Feature;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeatureSelector_StopWordsFilter extends FeatureSelector {
    
    private final ArrayList<String> stopWordsList;
    
    public FeatureSelector_StopWordsFilter(Set<Feature> feature, ArrayList<String> stopWordsList) {
        super(feature);
        this.stopWordsList = stopWordsList;
    }
    
    @Override
    protected void specifyPointlessFeatures() {
        stopWordsList.stream().forEach(stopW -> {
            Pattern pattern = Pattern.compile("((^)|(\\s))" + stopW + "(($)|(\\s)|(\\z)|[.!؟?])");
            features.stream().forEach(f -> {
                Matcher matcher = pattern.matcher(f.text);
                if (matcher.find()) {
                    pointlessFeatures.add(new Feature(f.text));
                }
            });
        });
    }
}
