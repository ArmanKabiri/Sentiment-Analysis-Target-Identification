/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 28, 2017 , 7:25:20 PM
 */
package Features.Ranker;

import Modules.Feature;
import Modules.RankedFeature;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Instances;

public class FeatureRanker_Weka extends FeatureRanker {

    private Instances instances;
    private ArrayList<Feature> features;

    public FeatureRanker_Weka(Instances instances, ArrayList<Feature> features) {
        this.instances = instances;
        this.features = features;
    }

    @Override
    public ArrayList<RankedFeature> run() {
        return rank();
    }

    @Override
    protected ArrayList<RankedFeature> rank() {
        ArrayList<RankedFeature> rankedFeatures = new ArrayList<>();
        try {
            CfsSubsetEval eval = new CfsSubsetEval();
            GreedyStepwise greedyStepwise = new GreedyStepwise();
            greedyStepwise.setGenerateRanking(true);
            greedyStepwise.search(eval, instances);
            double[][] rankedAttribs = greedyStepwise.rankedAttributes();

            //WARNING HERE .... The Order of Attributes are unknown:
            for (int i = 0; i < rankedAttribs.length; i++) {
                Feature feature = features.get((int) rankedAttribs[i][0]);
                double rank = rankedAttribs[i][1];
                RankedFeature rankedFeature = new RankedFeature(feature, rank);
                rankedFeatures.add(rankedFeature);
            }
        } catch (Exception ex) {
            Logger.getLogger(FeatureRanker_Weka.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rankedFeatures;
    }
}
