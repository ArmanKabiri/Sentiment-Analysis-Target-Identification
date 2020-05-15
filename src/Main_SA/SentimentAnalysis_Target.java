/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 10, 2017 , 2:08:06 PM
 */
package Main_SA;

import Evaluation.Evaluator;
import Utils.IO_Operations;
import Lexicon_Approach.Lexicon_Method;
import Modules.AnalysisConfiguration;
import Modules.CommentRecord;
import Modules.DataSet;
import Modules.Enums;
import Modules.LabelType;
import Modules.LexiconRecord;
import TargetIdentification.PotentialTermExtraction_PartialPolarityDetection;
import TargetIdentification.TargetEvaluator;
import TargetIdentification.TargetIdentification;
import Utils.POSTokenizer;
import java.util.ArrayList;

public class SentimentAnalysis_Target {

    private final AnalysisConfiguration configuration;
    private final Class classifier = weka.classifiers.bayes.NaiveBayes.class;
    private String arff_URL;
    private final String dataSet_URL;
    private String lexicon_URL;

    public SentimentAnalysis_Target(AnalysisConfiguration configuration) {
        this.configuration = configuration;
        dataSet_URL = configuration.dataSetURL;
        if (configuration.Lexicon != null) {
            lexicon_URL = Constants.LexiconsURLS.get(configuration.Lexicon.toString());
        }
        if (configuration.methodInUse == Enums.AnalysisMethodInUse.ml) {
            arff_URL = Constants.ARFF_FOLDER_URL + "/" + configuration.description + ".arff";
        }
    }

    public DataSet run() throws Exception {
        System.out.println("Analysis : " + configuration.description + " started:");
        IO_Operations iO_Operations = new IO_Operations();

        final ArrayList<LexiconRecord> lexiconWords = new ArrayList<>();
        if (lexicon_URL != null) {
            lexiconWords.addAll(iO_Operations.loadLexicon(lexicon_URL));
        }

        final DataSet dataSet = iO_Operations.loadDataSet(dataSet_URL, true, configuration.limitDataset);

        Evaluator evaluator5Star = new Evaluator(dataSet, LabelType.fiveStar);
        Evaluator evaluatorPolarity = new Evaluator(dataSet, LabelType.polarity);
        TargetEvaluator targetEvaluator = new TargetEvaluator(dataSet);

//********************************************************************        
        POSTokenizer tokenizer = new POSTokenizer();
        dataSet.records.stream().forEach(review -> tokenizer.run((CommentRecord) review));
//********************************************************************        
        PotentialTermExtraction_PartialPolarityDetection potTermExt_PartPolDetec = new PotentialTermExtraction_PartialPolarityDetection();
        TargetIdentification targetIdentification = new TargetIdentification();
        
        potTermExt_PartPolDetec.run(dataSet, lexiconWords);
        targetIdentification.run(dataSet);
        
//********************************************************************
        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println("\nResults : " + configuration.description + " :");
        evaluator5Star.measure();
        System.out.println("");
        evaluatorPolarity.measure();
        System.out.println("");
        targetEvaluator.measure();
        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println("------------------------");
//********************************************************************
//        iO_Operations.saveResult(outPut_URL, dataSet, configuration.analysisLevel, true); //analysisLevel here can be different due to some purposes
//        iO_Operations.saveMachedLexiconFeaturesDetails(outPut_URL.substring(0, outPut_URL.lastIndexOf(".xlsx")) + "_Features.xlsx", dataSet, lexiconWords);
//********************************************************************
        return dataSet;
    }
}
