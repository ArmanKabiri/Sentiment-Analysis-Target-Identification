/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Oct 21, 2016 , 5:18:40 PM
 */
package Modules;

import java.util.Objects;

public class LexiconRecord {

    public int id;
    public String word;
    public int score;

    public LexiconRecord() {
    }

    public LexiconRecord(LexiconRecord lexRec) {
        this.id = lexRec.id;
        this.word = lexRec.word;
        this.score = lexRec.score;
    }

    public LexiconRecord get() {
        return this;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof LexiconRecord) {
//            boolean a = this.word.equals(((LexiconRecord) obj).word);
//            if (a == true) {
//                int a21 = 2;
//            }
//            return a;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 3;
//        hash = 83 * hash + Objects.hashCode(this.word);
//        return hash;
//    }
    
    @Override
    public String toString() {
        return word + " : " + score;
    }
}
