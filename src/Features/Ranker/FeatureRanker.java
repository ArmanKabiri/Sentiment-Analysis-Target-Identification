/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 28, 2017 , 7:08:47 PM
 */
package Features.Ranker;

import Modules.RankedFeature;
import java.util.ArrayList;

public abstract class FeatureRanker {

    public abstract ArrayList<RankedFeature> run();

    protected abstract ArrayList<RankedFeature> rank();
    
}