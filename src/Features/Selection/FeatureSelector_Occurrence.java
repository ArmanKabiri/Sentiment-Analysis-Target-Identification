/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Apr 30, 2017 , 1:43:54 PM
 */
package Features.Selection;

import Modules.Feature;
import java.util.Set;

public class FeatureSelector_Occurrence extends FeatureSelector {

    private int cutoff;

    public FeatureSelector_Occurrence(Set<Feature> feature, int cutoff) {
        super(feature);
        this.cutoff = cutoff;
    }

    @Override
    protected void specifyPointlessFeatures() {
        features.stream().forEach(feature -> {
            if (feature.occurrence_total < cutoff) {
                pointlessFeatures.add(feature);
            }
        });
    }

}
