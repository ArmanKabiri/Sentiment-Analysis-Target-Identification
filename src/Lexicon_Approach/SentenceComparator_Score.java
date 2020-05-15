/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Nov 17, 2016 , 11:07:00 AM
 */
package Lexicon_Approach;

import Modules.SentenceRecord;
import java.util.Comparator;

public class SentenceComparator_Score implements Comparator<SentenceRecord> {

    @Override
    public int compare(SentenceRecord o1, SentenceRecord o2) {
        if (o1.labeledScore > o2.labeledScore) {
            return 1;
        } else if (o1.labeledScore < o2.labeledScore) {
            return -1;
        } else {
            return 0;
        }
    }
}
