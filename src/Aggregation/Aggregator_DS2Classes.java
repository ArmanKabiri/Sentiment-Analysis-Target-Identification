/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 14, 2016 , 5:46:01 AM
 */
package Aggregation;

import java.util.ArrayList;

public class Aggregator_DS2Classes implements IAggregator {

    @Override
    public int run(ArrayList<Double> scores, int minScore, int maxScore) {
        float mTotal = 0;
        for (double score : scores) {
            float mScore = calcMScore(score, minScore, maxScore);
            if (mScore == 0) {
                mScore = 0.001f;// error prone
            }
            if (scores.indexOf(score) == 0) {
                mTotal = mScore;
            } else if (((1 - mTotal) * mScore + mTotal * (1 - mScore)) != 1) {
                mTotal = (mTotal * mScore) / (1 - ((1 - mTotal) * mScore + mTotal * (1 - mScore))); // this is for m(A)
            } else {
                mTotal = 0; /// ?? What is it here?
            }
        }
        int finalScore = scale_Round_FinalScore(mTotal, minScore, maxScore);
        return finalScore;
    }

    private float calcMScore(double score, int minScore, int maxScore) {
        float m = (float) ((score - minScore) / (maxScore - minScore));
        return m;
    }

    private int scale_Round_FinalScore(float score, int minScore, int maxScore) {
        float result = (score * (maxScore - minScore)) + minScore;
//        int resultInt = (int) (Math.round(result));
        int resultInt = (int) ((result < (maxScore + minScore) / 2) ? Math.floor(result) : Math.ceil(result));
        if (resultInt == 0) {
            resultInt = 1;
        } else if (resultInt == 6) {
            resultInt = 5;
        }
        return resultInt;
    }
}
