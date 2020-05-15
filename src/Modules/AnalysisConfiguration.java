/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 10, 2017 , 2:12:08 PM
 */
package Modules;

import Features.Matcher.FeatureMatcher;
import Modules.Enums.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.FilenameUtils;

public class AnalysisConfiguration {

    public String description;
    public AnalysisLevel analysisLevel;
    public AnalysisMethodInUse methodInUse;
    public List<FeatureExtractionType> featureExtractions;
    public List<FeatureSelectionType> featureSelections = new ArrayList<>();
    public LexiconInUse Lexicon;
    public List<LexiconBasedRule> lexiconBasedRules = new ArrayList<>();
    public String dataSetURL;

    /**
     * Both for Lexicon-Based and ML-Based
     */
    public FeatureMatcher featureMatcher;
    public boolean limitDataset;

    public AnalysisConfiguration setFeatureMatcher(FeatureMatcher featureMatcher) {
        this.featureMatcher = featureMatcher;
        return this;
    }

    public AnalysisConfiguration setAnalysisLevel(AnalysisLevel analysisLevel) {
        this.analysisLevel = analysisLevel;
        return this;
    }

    public AnalysisConfiguration setMethodInUse(AnalysisMethodInUse methodInUse) {
        this.methodInUse = methodInUse;
        return this;
    }

    public AnalysisConfiguration setFeatureExtractions(FeatureExtractionType... featureExtractions) {
        this.featureExtractions = Arrays.asList(featureExtractions);
        return this;
    }

    public AnalysisConfiguration setDataSetURL(String dataSetURL) {
        this.dataSetURL = dataSetURL;
        return this;
    }

    public AnalysisConfiguration setFeatureSelections(FeatureSelectionType... featureSelections) {
        this.featureSelections = Arrays.asList(featureSelections);
        return this;
    }

    public AnalysisConfiguration setLexicon(LexiconInUse Lexicon) {
        this.Lexicon = Lexicon;
        return this;
    }

    public AnalysisConfiguration setLimitDataset(boolean limitDataset) {
        this.limitDataset = limitDataset;
        return this;
    }

    public AnalysisConfiguration setLexiconBasedRules(LexiconBasedRule... lexiconBasedRules) {
        this.lexiconBasedRules = Arrays.asList(lexiconBasedRules);
        return this;
    }

    public AnalysisConfiguration create() {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(methodInUse).append("_").append(analysisLevel).append("_").append(FilenameUtils.getBaseName(dataSetURL));
        if (methodInUse == AnalysisMethodInUse.ml) {
            this.featureExtractions.stream().forEach(f -> {
                strBuilder.append("_").append(f);
            });
            if (this.featureExtractions.contains(FeatureExtractionType.lexicon)) {
                strBuilder.append("_").append(Lexicon.toString());
            }
            this.featureSelections.stream().forEach(f -> {
                strBuilder.append("_").append(f);
            });
        }
        else {
            strBuilder.append("_").append(Lexicon.toString());
            lexiconBasedRules.stream().forEach(rule -> {
                strBuilder.append("_").append(rule);
            });
        }
        strBuilder.append("_").append(featureMatcher.toString());
        if (limitDataset) {
            strBuilder.append("_Limited");
        }
        else {
            strBuilder.append("_unlimited");
        }

        description = strBuilder.toString();
        return this;
    }
}
