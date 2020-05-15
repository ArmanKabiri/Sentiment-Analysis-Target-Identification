/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 10, 2016 , 4:58:55 AM
 */
package ML_Approach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.ArrayUtils;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instances;

public class Train_Evaluate_Runable implements Runnable {

    private final Class classifierClass;
    private final Instances dataSet;
    private final Evaluation eval;
    private final ArrayList<Double> predictionResult;
    private final ArrayList<Lock> predictionArrayLock;
    private Semaphore semaphore;
    private final int folds;
    private final int foldNumber;

    public Train_Evaluate_Runable(Class classifierClass, Instances dataSet, Evaluation eval, ArrayList<Double> predictionResult, ArrayList<Lock> predictionArrayLock, Semaphore semaphore, int folds, int foldNumber) {
        this.classifierClass = classifierClass;
        this.dataSet = dataSet;
        this.eval = eval;
        this.predictionResult = predictionResult;
        this.predictionArrayLock = predictionArrayLock;
        this.semaphore = semaphore;
        this.folds = folds;
        this.foldNumber = foldNumber;
    }

    @Override
    public void run() {
        System.out.println("Thread " + foldNumber + " started.");
        try {
            if (foldNumber < folds - 1) {
                predictionArrayLock.get(foldNumber + 1).lock();
            }
            Instances train = dataSet.trainCV(folds, foldNumber);
            Instances test = dataSet.testCV(folds, foldNumber);

            // build and evaluate classifier
            Classifier classifier = (Classifier) classifierClass.newInstance();
            classifier.buildClassifier(train);
            double[] partialPrediction = eval.evaluateModel(classifier, test);
            Double[] partialPrediction_obj = ArrayUtils.toObject(partialPrediction);

            try {
                predictionArrayLock.get(foldNumber).lock();
                System.out.println("fold " + foldNumber + " evaluated");
                predictionResult.addAll(Arrays.asList(partialPrediction_obj));
            } finally {
                if (foldNumber < folds - 1) {
                    predictionArrayLock.get(foldNumber + 1).unlock();
                }
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Train_Evaluate_Runable.class.getName()).log(Level.SEVERE, null, ex);
        }
        semaphore.release();
    }
}