/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Oct 21, 2016 , 5:18:40 PM
 */
package Lexicon_Approach;

import Features.Matcher.FeatureMatcher;
import Modules.CommentRecord;
import Modules.DataSet;
import Modules.Enums;
import Modules.LexiconRecord;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lexicon_Method {

    public enum PutLabelOn {
        estimatedScore, labeledScore
    }

    DataSet dataSet;
    ArrayList<LexiconRecord> lexiconWords;
    FeatureMatcher featureMatcher;
    ArrayList<String> negationWordsList;
    Enums.AnalysisLevel analysisLevel;
    PutLabelOn labelOn;
    boolean applyQuestionSentenceRule = false;
    int counter = 0;

    public Lexicon_Method setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
        return this;
    }

    public Lexicon_Method setLexiconWords(ArrayList<LexiconRecord> lexiconWords) {
        this.lexiconWords = lexiconWords;
        return this;
    }

    public Lexicon_Method setFeatureMatcher(FeatureMatcher featureMatcher) {
        this.featureMatcher = featureMatcher;
        return this;
    }

    public Lexicon_Method activeNegationRule(ArrayList<String> negationWordsList) {
        this.negationWordsList = negationWordsList;
        return this;
    }

    public Lexicon_Method activeQuestionSentencesRule(boolean activate) {
        applyQuestionSentenceRule = activate;
        return this;
    }

    public Lexicon_Method setAnalysisLevel(Enums.AnalysisLevel analysisLevel) {
        this.analysisLevel = analysisLevel;
        return this;
    }

    public Lexicon_Method setLabelOn(PutLabelOn labelOn) {
        this.labelOn = labelOn;
        return this;
    }

    /**
     * run method. estimate scores and apply new scores to the reviews object
     * you passed in.
     */
    public void run() {

        ScoreCalculator scoreCalculator = new ScoreCalculator().
                setDataSet(dataSet)
                .setLexiconWords(lexiconWords)
                .setAnalysisLevel(analysisLevel)
                .setFeatureMatcher(featureMatcher)
                .activeQuestionSentenceRule(applyQuestionSentenceRule)
                .activeNegationRule(negationWordsList);

        dataSet.records.parallelStream().forEach((record) -> {
            try {
//                if (++counter % 10 == 0) {
//                    System.out.println(counter + "");
//                }
                int score = scoreCalculator.run(record);
                switch (labelOn) {
                    case labeledScore:
                        record.labeledScore = score;
                        record.labeledPolarity = record.labeledScore >= 3;  //true : positive - false : negative
                        if (record instanceof CommentRecord) {
                            ((CommentRecord) record).score = score;
                        }
                        break;
                    case estimatedScore:
                        record.estimatedScore = score;
                        record.estimatedPolarity = record.estimatedScore >= 3;  //true : positive - false : negative
                }

            }
            catch (Exception ex) {
                Logger.getLogger(Lexicon_Method.class.getName()).log(Level.SEVERE, null, ex);
                record.estimatedScore = 3;
            }
        });
    }
}
