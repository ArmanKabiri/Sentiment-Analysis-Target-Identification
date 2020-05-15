/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 14, 2016 , 5:46:01 AM
 */
package Aggregation;

import java.util.ArrayList;

public class Aggregator_CrossRatio implements IAggregator {

    private final double compensationValue = 0;   //0 means that scores 0 and 1 in the range [0-1] are allowed.
    // When set to .1, score 0 and score 1 are considered as 0.1 and 0.9, respectively.
    private double e_Parameter;

    public Aggregator_CrossRatio(double e_Parameter) {
        this.e_Parameter = e_Parameter;
    }

    @Override
    public int run(ArrayList<Double> scores, int minScore, int maxScore) {

        double n_Parameter = scores.size();
        double aggregatedScore = 0; // [0-1]
        int finalScore = 3;     //[1-5]
        if (scores.isEmpty()) {
            throw new IllegalArgumentException("There is no scores to be aggregated");
        } else if (scores.size() == 1) {
            finalScore = scores.get(0).intValue();
        } else {
            ArrayList<Double> scaledScores = new ArrayList<>();
            double range_15 = (maxScore - minScore);    //old range
            double range_01 = (1 - 0);  // new Range is [0 - 1]
            scores.stream().forEach(score -> {
                double newScore = (((score - minScore) * range_01) / range_15) + 0;
                newScore = (newScore == 0) ? compensationValue
                        : (newScore == 1) ? (1 - compensationValue) : newScore;
                scaledScores.add(newScore);
            });

            //Check if the first state of the formula is met.
            boolean[] statusOfTheFirstStateOfTheFormula = new boolean[]{false, false};   // index 0 is for the occurence of  the Score 0, and index 1 is for the occurence of the score 1.
            double scoresProduct = 1;   // P (xi)
            double scoresMinus1Product = 1;     // P (1 - xi)
            for (int i = 0; i < scaledScores.size(); i++) {
                scoresProduct *= scaledScores.get(i);
                scoresMinus1Product *= (1 - scaledScores.get(i));
                if (scaledScores.get(i) == 0) {
                    statusOfTheFirstStateOfTheFormula[0] = true;
                } else if (scaledScores.get(i) == 1) {
                    statusOfTheFirstStateOfTheFormula[1] = true;
                }
            }

            if (statusOfTheFirstStateOfTheFormula[0] && statusOfTheFirstStateOfTheFormula[1]) {
                //The first state of the formula is met.
                aggregatedScore = 0;
            } else {
                double denominator;     //bottom part of a fraction
                double numerator;        //The number on the top of a fraction
                numerator = Math.pow((1 - e_Parameter), (n_Parameter - 1)) * scoresProduct;
                denominator = numerator + (Math.pow(e_Parameter, (n_Parameter - 1)) * scoresMinus1Product);
                aggregatedScore = numerator / denominator;
            }

            //Normalize to 1-5
            double finalScoreDouble = (((aggregatedScore - 0) * range_15) / range_01) + 1;
            finalScore = (int) finalScoreDouble;

        }
        return finalScore;
    }
}
