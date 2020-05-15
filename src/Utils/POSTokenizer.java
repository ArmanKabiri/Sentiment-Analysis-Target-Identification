/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Jul 12, 2018 , 12:42:28 PM
 */
package Utils;

import Modules.CommentRecord;
import Modules.DatasetRecord;
import Modules.Enums;
import Modules.SentenceRecord;
import Modules.Token;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class POSTokenizer {

    public void run(CommentRecord review) {
        extractSentences(review);
        review.sentences.stream().forEach(sentence -> tokenizeRecord(sentence));
    }

    private void extractSentences(CommentRecord review) {
        List<String> sentences = new ArrayList<>();
        String regex = "(.+?)(<V>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(review.text);
        while (matcher.find()) {
            String sentence = matcher.group(0);
            sentences.add(sentence);
        }
        review.sentences.addAll(sentences.stream().map(txt -> new SentenceRecord(txt, review.id)).collect(Collectors.toList()));

        //Removing POSTags form Review Text
        tokenizeRecord(review);
    }

    private void tokenizeRecord(DatasetRecord record) {
        List<Token> tokenizedSentece = new ArrayList<>();
        String regex = "(.+?)(<.+?>)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(record.text);
        while (matcher.find()) {
            String txt = matcher.group(1).trim();
            String tag = matcher.group(2).trim();
            Enums.POSTags posTag = tag.equalsIgnoreCase("<V>") ? Enums.POSTags.verb
                    : tag.equalsIgnoreCase("<N>") ? Enums.POSTags.noun
                    : tag.equalsIgnoreCase("<adv>") ? Enums.POSTags.adverb
                    : tag.equalsIgnoreCase("<adj>") ? Enums.POSTags.adjective
                    : Enums.POSTags.other;
            Token token = new Token(txt, posTag);
            tokenizedSentece.add(token);
        }

        //Removing POSTags form Sentence Text
        record.text = record.text.replaceAll("<.+?>", "");
        record.tokens.addAll(tokenizedSentece);
    }
}