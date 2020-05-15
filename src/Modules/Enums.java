/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Feb 11, 2017 , 6:32:14 PM
 */
package Modules;

public class Enums {

    public enum AnalysisLevel {
        review, sentence
    }

    public enum AnalysisMethodInUse {
        ml, lexicon
    };

    public enum FeatureExtractionType {
        uniGrams, biGrams, lexicon
    }

    public enum FeatureSelectionType {
        occurrenceFilter, stopWordsFilter, LimitNGramsToLexicon
    }

    public enum LexiconInUse {
        LookUp, Adjectives, NRC, CNRC, CommonLex, LookupAdjLex, PerLex, PerSent, LexiPers
    }

    public enum AggregationMethod {
        DS, Average
    }

    public enum LexiconBasedRule {
        QuestionSentences, NegationWords
    }

    public enum POSTags {
        adjective, adverb, noun, verb, implicitTag, other
    }

}
