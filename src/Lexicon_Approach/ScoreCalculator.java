/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Oct 30, 2016 , 5:38:43 PM
 */
package Lexicon_Approach;

import Aggregation.Aggregator_Average;
import Aggregation.IAggregator;
import Features.Matcher.FeatureMatcher;
import Modules.LexiconRecord;
import Modules.SentenceRecord;
import Modules.CommentRecord;
import Modules.DataSet;
import Modules.Enums;
import Modules.Feature;
import Modules.Record;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ScoreCalculator {

    DataSet dataSet;
    ArrayList<LexiconRecord> lexiconWords;
    Enums.AnalysisLevel analysisLevel;
    FeatureMatcher featureMatcher_SampleInstance;
    ArrayList<String> negationWordsList;
    boolean activeQuestionSentencesRule = false;

    public ScoreCalculator setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
        return this;
    }

    public ScoreCalculator setLexiconWords(ArrayList<LexiconRecord> lexiconWords) {
        this.lexiconWords = lexiconWords;
        return this;
    }

    public ScoreCalculator setAnalysisLevel(Enums.AnalysisLevel analysisLevel) {
        this.analysisLevel = analysisLevel;
        return this;
    }

    public ScoreCalculator activeNegationRule(ArrayList<String> negationWordsList) {
        this.negationWordsList = negationWordsList;
        return this;
    }

    public ScoreCalculator setFeatureMatcher(FeatureMatcher featureMatcher) {
        this.featureMatcher_SampleInstance = featureMatcher;
        return this;
    }

    public ScoreCalculator activeQuestionSentenceRule(boolean active) {
        this.activeQuestionSentencesRule = active;
        return this;
    }

    public int run(Record record) throws Exception {
        int result = 3;
        if (record instanceof CommentRecord) {
            CommentRecord comment = (CommentRecord) record;
            switch (analysisLevel) {
                case sentence:
                    if (comment.sentences.size() > 0) {
                        comment.sentences.stream().forEach((sentence) -> {
                            int sentenceScore;
                            if (activeQuestionSentencesRule && isQuestionSentence(sentence)) {
                                sentenceScore = 3;
                            }
                            else {
                                sentenceScore = calcStringScore(sentence);
                                if (negationWordsList != null) {
                                    sentenceScore = applyNegationWordsRule(sentence.text, sentenceScore);
                                }
                            }
                            sentence.labeledScore = sentenceScore;
                        });
                        mergeFeaturesLists(comment);
                        IAggregator aggregator = new Aggregator_Average();
                        ArrayList<Double> scores = comment.sentences.stream().mapToDouble(x -> x.labeledScore).
                                boxed().collect(Collectors.toCollection(ArrayList::new));
                        result = aggregator.run(scores, 1, 5);
                    }
                    else {
                        result = 3;
                    }
                    break;
                case review:
                    result = calcStringScore(comment);
                    if (negationWordsList != null) {
                        result = applyNegationWordsRule(comment.text, result);
                    }
                    break;
            }

        }
        else if (record instanceof SentenceRecord) {
            SentenceRecord sentence = (SentenceRecord) record;
            if (activeQuestionSentencesRule && isQuestionSentence(sentence)) {
                result = 3;
            }
            else {
                result = calcStringScore(sentence);
                if (negationWordsList != null) {
                    result = applyNegationWordsRule(sentence.text, result);
                }
            }
        }
        else {
            throw new Exception("data set is built of neither Comment nor Sentence");
        }
        return result;
    }

    public int calcStringScore(Record record) {
        String text = record.text;
        ArrayList<Integer> scores = new ArrayList<>();
        for (LexiconRecord lexicon : lexiconWords) {
            try {
                FeatureMatcher featureMatcher = featureMatcher_SampleInstance.getClass().newInstance();
                featureMatcher.setRegexCorePharase(lexicon.word);
                featureMatcher.setText(record.text);
                Feature feature = new Feature(lexicon.word);
                while (featureMatcher.matchNext() != null) {
                    scores.add(lexicon.score);
                    feature.occurrence_total++;
                }
                if (feature.occurrence_total > 0) {
                    record.machedFeatures.add(feature);
                }
            }
            catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(ScoreCalculator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (scores.isEmpty()) {
            return 3;
        }
        else {
            IAggregator aggregator = new Aggregator_Average();
            int score = aggregator.run(scores.stream().map(x -> (double) x).collect(Collectors.toCollection(ArrayList::new)), 1, 5);
            return score;
        }
    }

    private void mergeFeaturesLists(CommentRecord comment) {
        ArrayList<Feature> featuresList = new ArrayList<>();
        comment.sentences.stream().forEach(sentence -> {
            sentence.machedFeatures.stream().forEach(feature -> {
                if (featuresList.contains(feature)) {
                    featuresList.get(featuresList.indexOf(feature)).occurrence_total += feature.occurrence_total;
                }
                else {
                    featuresList.add(feature);
                }
            });
        });
        comment.machedFeatures.addAll(featuresList);
    }

    private int applyNegationWordsRule(String text, int calculatedScore) {

        int result = calculatedScore;
        for (int i = 0; i < negationWordsList.size(); i++) {
            String regex = "((^)|(\\s)|(" + (char) (8204) + "))" + negationWordsList.get(i) + "(($)|(\\s)|(\\z)|[.!؟?]|(" + (char) (8204) + "))";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);

            if (matcher.find()) {
                int distance = 3 - calculatedScore;
                result = 3 + distance;
                if (result == 3) {
                    result = 2;
                }
                break;
            }
        }
        return result;
    }

    private boolean isQuestionSentence(SentenceRecord sentence) {
        String regex = "[?؟]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sentence.text);
        boolean result = matcher.find();
        return result;
    }

}
