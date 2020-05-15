/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 22, 2016 , 5:12:55 PM
 */
package Aggregation;

import java.util.ArrayList;

public interface IAggregator {

    public int run(ArrayList<Double> scores, int minScore, int maxScore);
}
