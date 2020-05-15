/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 22, 2016 , 9:04:12 PM
 */
package Aggregation;

import java.util.ArrayList;
import java.util.Comparator;

public class Aggregator_Maximum implements IAggregator {

    @Override
    public int run(ArrayList<Double> scores, int minScore, int maxScore) {
        double max = scores.stream().max(Comparator.naturalOrder()).get();
        return (int) max;
    }
}
