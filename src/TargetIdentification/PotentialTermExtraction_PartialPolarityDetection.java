/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Jul 13, 2018 , 4:26:50 PM
 */
package TargetIdentification;

import Modules.CommentRecord;
import Modules.DataSet;
import Modules.Enums;
import Modules.LexiconRecord;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jhazm.Lemmatizer;

public class PotentialTermExtraction_PartialPolarityDetection {

    private Lemmatizer lemmatizer=null;
    //Verb is also a PotentialTerm
    List<Enums.POSTags> potentialTags = Arrays.asList(Enums.POSTags.adjective, Enums.POSTags.adverb); //Verb is also a PotentialTerm

    public PotentialTermExtraction_PartialPolarityDetection() {
        try {
            this.lemmatizer = new Lemmatizer();
        }
        catch (IOException ex) {
            Logger.getLogger(PotentialTermExtraction_PartialPolarityDetection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run(DataSet dataSet, ArrayList<LexiconRecord> lexicon) {
        dataSet.records.parallelStream().forEach(record -> {
            CommentRecord reviewRecrd = (CommentRecord) record;
            reviewRecrd.sentences.stream().forEach(sentence -> {
                sentence.tokens.stream().forEach(token -> {
                    if (potentialTags.contains(token.POSTag)) {
                        for (LexiconRecord lexRecord : lexicon) {
                            if (lexRecord.word.equalsIgnoreCase(token.text)) {
                                token.isPotentialTerm = true;
                                token.potentialPolarity = lexRecord.score >= 3;
                                break;
                            }
                        }
                    }
                    else if (token.POSTag == Enums.POSTags.verb) {
                        try {
                            for (LexiconRecord lexRecord : lexicon) {
                                if (checkRootsEquality(token.text, lexRecord.word)) {
                                    token.isPotentialTerm = true;
                                    token.potentialPolarity = lexRecord.score >= 3;
                                    break;
                                }
                            }
                            if (token.text.startsWith("نن") || token.text.startsWith("نمی") || token.text.startsWith("نمي")) {
                                if (token.isPotentialTerm) {
                                    token.potentialPolarity = !token.potentialPolarity;
                                }
                            }
                            else if (token.text.startsWith("ن") && !doesWordRootsStartWithN(token.text)) {
                                if (token.isPotentialTerm) {
                                    token.potentialPolarity = !token.potentialPolarity;
                                }
                            }
                        }
                        catch (IOException ex) {
                            Logger.getLogger(PotentialTermExtraction_PartialPolarityDetection.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            });
        });
    }

    private boolean checkRootsEquality(String word1, String word2) throws IOException {
        boolean result = false;
        List<String> roots1 = Arrays.asList(lemmatizer.lemmatize(word1).split("#"));
        List<String> roots2 = Arrays.asList(lemmatizer.lemmatize(word2).split("#"));

        for (String root1 : roots1) {
            for (String root2 : roots2) {
                if (root1.equals(root2)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    private boolean doesWordRootsStartWithN(String word) throws IOException {
        String[] roots = lemmatizer.lemmatize(word).split("#");
        for (String root : roots) {
            if (root.startsWith("ن")) {
                return true;
            }
        }
        return false;
    }
}