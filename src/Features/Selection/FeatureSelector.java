/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Apr 30, 2017 , 1:19:59 PM
 */
package Features.Selection;

import Modules.Feature;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class FeatureSelector {

    Set<Feature> features;
    Set<Feature> pointlessFeatures;

    public FeatureSelector(Set<Feature> feature) {
        this.features = feature;
        this.pointlessFeatures = Collections.synchronizedSet(new HashSet<>());
    }

    public void run() {
        specifyPointlessFeatures();
        pointlessFeatures.stream().forEach(pointlessF -> {
            features.remove(pointlessF);
        });
    }

    abstract protected void specifyPointlessFeatures();

}
