/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Oct 21, 2016 , 5:18:40 PM
 */
package Utils;

import Modules.SentenceRecord;
import Modules.CommentRecord;
import java.util.ArrayList;

public class ExtractSentences_JustByPOS {

    public void perform(ArrayList<CommentRecord> comments) {

        //Unfortunately its not written with Regex!!!
        comments.stream().forEachOrdered((comment) -> {
            int lastSplitedCharIndex = 0;
            for (int i = 0; i < comment.text.length(); i++) {
                try {
                    if (i == comment.text.length() - 1) {
                        String sentence;
                        sentence = comment.text.substring(lastSplitedCharIndex, i + 1);
                        lastSplitedCharIndex = i + 1;
                        if (sentence.length() > 3) {
                            comment.sentences.add(new SentenceRecord(sentence));
                        }
                    } else if (comment.text.substring(i, i + 3).equalsIgnoreCase("<V>")) {
                        String sentence;
                        sentence = comment.text.substring(lastSplitedCharIndex, i);
                        String next2Char = "";//garbege
                        char nextChar = '-';//garbege
                        try {
                            nextChar = comment.text.charAt(i + 3);
                            next2Char = comment.text.substring(i + 3, i + 5);
                        } catch (Exception x) {//exception eccures but its OK
//                            System.out.println(x.getMessage());
//                            x.printStackTrace();
                        }
                        if (next2Char.equalsIgnoreCase(" .") || next2Char.equalsIgnoreCase(" !") || next2Char.equalsIgnoreCase(" ؟")) {
                            sentence = sentence + next2Char;
                            lastSplitedCharIndex = i + 5;
                        } else if (nextChar == '.' || nextChar == '!' || nextChar == '؟') {
                            sentence = sentence + nextChar;
                            lastSplitedCharIndex = i + 4;
                        } else if (i + 4 == sentence.length()) {
                            lastSplitedCharIndex = i + 4;
                        } else {
                            lastSplitedCharIndex = i + 3;
                        }

                        if (sentence.length() > 3) {
                            comment.sentences.add(new SentenceRecord(sentence));
                        }
                    }
                } catch (Exception x) {//exception eccures but its OK
//                    System.out.println(x.getMessage());
//                    x.printStackTrace();
                }
            }
            comment.text = comment.text.replaceAll("<V>", "");
        });
    }
}
