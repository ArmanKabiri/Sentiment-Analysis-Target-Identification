/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Jul 14, 2018 , 2:40:15 PM
 */
package TargetIdentification;

import static Main_SA.Constants.ROOTURI;
import Modules.CommentRecord;
import Modules.DataSet;
import Modules.Enums;
import Modules.SentenceRecord;
import Modules.Token;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

public class TargetIdentification {

    Set<String> temporaryTargets = new HashSet<>();//Temp

    public void run(DataSet dataSet) {
        dataSet.records.parallelStream().forEach(record -> {
            CommentRecord review = (CommentRecord) record;
            review.sentences.stream().forEach(sentence -> {
                identifySentenceTarget(sentence);
            });
            identifyFinalReviewTarget(review);
        });
        //TEMP
        exportTempTargets();
    }

    private void identifySentenceTarget(SentenceRecord sentenceRecord) {
        identifyTokensTargets(sentenceRecord.tokens);
        //Return first target as the sentence target:
        Token sentenceTarget = null;
        for (Token token : sentenceRecord.tokens) {
            if (token.isPotentialTerm) {
                sentenceTarget = token.target;
                sentenceTarget.isPotentialTerm = false;
                sentenceTarget.isTargetTerm = true;
                sentenceTarget.potentialPolarity = token.potentialPolarity;
                if (sentenceTarget.POSTag != Enums.POSTags.implicitTag) {
                    break;
                }
            }
        }
        if (sentenceTarget == null) {
            sentenceTarget = new Token("implicitTarget", Enums.POSTags.implicitTag);
            sentenceTarget.isPotentialTerm = false;
            sentenceTarget.isTargetTerm = true;
            sentenceTarget.potentialPolarity = false;
        }
        sentenceRecord.setTarget(sentenceTarget);
        temporaryTargets.add(sentenceTarget.text);
    }

    private void identifyTokensTargets(List<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).isPotentialTerm) { //must have target
                Token potentialTarget = getPreviousNoun(tokens, i);
                if (potentialTarget == null) {
                    potentialTarget = getNextNoun(tokens, i);
                }
                if (potentialTarget == null) {  //implicit target
                    potentialTarget = new Token("implicitTarget", Enums.POSTags.implicitTag);
                }
                tokens.get(i).setTarget(potentialTarget);
            }
        }
    }

    private Token getPreviousNoun(List<Token> tokens, int tokenIndex) {    //Noun POSTag
        for (int i = tokenIndex - 1; i >= 0; i--) {
            if (tokens.get(i).POSTag == Enums.POSTags.noun) {
                return tokens.get(i);
            }
        }
        return null;
    }

    private Token getNextNoun(List<Token> tokens, int tokenIndex) {    //Noun POSTag
        for (int i = tokenIndex + 1; i < tokens.size(); i++) {
            if (tokens.get(i).POSTag == Enums.POSTags.noun) {
                return tokens.get(i);
            }
        }
        return null;
    }

    private void identifyFinalReviewTarget(CommentRecord review) {
        //Five Methods...(Anthology)
        //Also Compute Final Polarity of each Review
    }

    private void exportTempTargets() {
        try {
            FileUtils.writeLines(new File(ROOTURI + "DataSet/Persian/DataSet-TargetIdentification/ExtractedTargets/PerviewTargets.txt"), temporaryTargets);
        }
        catch (IOException ex) {
            Logger.getLogger(TargetIdentification.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}