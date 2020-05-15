/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Oct 21, 2016 , 5:18:40 PM
 */
package Modules;

import java.util.ArrayList;

public class CommentRecord extends DatasetRecord {

    public int score = 0;
    public ArrayList<SentenceRecord> sentences = new ArrayList<>();

    public CommentRecord() {
        super();
    }

    public CommentRecord(CommentRecord comment) {
        super(comment);
        score = comment.score;
        sentences.addAll(comment.sentences);  // its not a deep clone
    }

    @Override
    public CommentRecord get() {
        return this;
    }
}
