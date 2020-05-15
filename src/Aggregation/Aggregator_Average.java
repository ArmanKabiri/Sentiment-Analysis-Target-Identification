/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 22, 2016 , 9:04:12 PM
 */
package Aggregation;

import java.util.ArrayList;

public class Aggregator_Average implements IAggregator {

    @Override
    public int run(ArrayList<Double> scores, int minScore, int maxScore) {
        double result = scores.stream().mapToDouble(Double::valueOf).average().getAsDouble();
        if (result > 3) {
            return (int) Math.ceil(result);
        } else if (result < 3) {
            return (int) Math.floor(result);
        } else {
            return 3;
        }
    }
}
