/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 9, 2016 , 8:59:33 PM
 */
package ML_Approach;

import Modules.DataSet;
import Modules.MutableInteger;
import Modules.Record;
import Modules.SentenceRecord;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.Prediction;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Ml_method_BuiltIn_SingleTh extends AbstractMLMethod {

    private final int folds = 10;
    ArrayList<Prediction> predictionResult;

    public Ml_method_BuiltIn_SingleTh(DataSet dataSet, String arffURI, Class classifierClass) {
        super(classifierClass);
        this.dataSetRecords = dataSet;
        this.arffURI = arffURI;
    }

    /**
     * run method. estimate scores and apply new scores to the reviews object
     * you passed in.
     *
     * @throws java.io.FileNotFoundException
     */
    @Override
    public void run() throws FileNotFoundException, Exception {
        dataSetInstances = new Instances(DataSource.read(arffURI));
        System.out.println("arff file has been loaded");
        dataSetInstances.setClassIndex(dataSetInstances.numAttributes() - 1);

        Evaluation eval = new Evaluation(dataSetInstances);
        Classifier classifier = (Classifier) classifierClass.newInstance();
//        classifier.buildClassifier(dataSet);
        eval.crossValidateModel(classifier, dataSetInstances, folds, new Random(10));
        predictionResult = eval.predictions();

        System.out.println("precision " + 1 + ": " + eval.precision(1));
        System.out.println("precision " + 2 + ": " + eval.precision(2));
        System.out.println("precision " + 3 + ": " + eval.precision(3));
        System.out.println("precision " + 4 + ": " + eval.precision(4));
        System.out.println("precision " + 5 + ": " + eval.precision(5));

        System.out.println("recall " + 1 + ": " + eval.recall(1));
        System.out.println("recall " + 2 + ": " + eval.recall(2));
        System.out.println("recall " + 3 + ": " + eval.recall(3));
        System.out.println("recall " + 4 + ": " + eval.recall(4));
        System.out.println("recall " + 5 + ": " + eval.recall(5));

        System.out.println("FMeasure " + 1 + ": " + eval.fMeasure(1));
        System.out.println("FMeasure " + 2 + ": " + eval.fMeasure(2));
        System.out.println("FMeasure " + 3 + ": " + eval.fMeasure(3));
        System.out.println("FMeasure " + 4 + ": " + eval.fMeasure(4));
        System.out.println("FMeasure " + 5 + ": " + eval.fMeasure(5));

        System.out.println("Accuracy " + ": " + (1 - eval.errorRate()));

        updateDataSetScores();
    }

    @Override
    protected void updateDataSetScores() throws Exception {
        // Warning : it clears Review Text
        if (dataSetRecords.records.size() != predictionResult.size()) {
            throw new Exception("dataset size mismatches predictionResultSize");
        }

        MutableInteger counter = new MutableInteger(0);
        predictionResult.stream().forEach(prediction -> {
            Record record = dataSetRecords.records.get(counter.getVal());
            counter.increment(1);
            record.text = "";
//            record.model = "";
            if (record instanceof SentenceRecord) {
                ((SentenceRecord) record).reviewID = 0;
            }
            record.labeledScore = (int) prediction.actual();   // +1 is removed because of sparse format of arff
            record.estimatedScore = (int) prediction.predicted();  // +1 is removed because of sparse format of arff
            record.estimatedPolarity = record.estimatedScore >= 3;  //true : positive - false : negative
        });
    }
}
