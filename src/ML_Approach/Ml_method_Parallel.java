/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 9, 2016 , 8:59:33 PM
 */
package ML_Approach;

import Modules.DataSet;
import Modules.Record;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

public class Ml_method_Parallel extends AbstractMLMethod {

    private final int folds;
    private final Semaphore semaphore = new Semaphore(8);//num cpu cores
    ArrayList<Double> predictionResult = new ArrayList();
    private final ArrayList<Lock> predictionArrayLock = new ArrayList<>();

    public Ml_method_Parallel(DataSet dataSet, String arffURI, Class classifierClass, int folds) {
        super(classifierClass);
        this.dataSetRecords = dataSet;
        this.arffURI = arffURI;
        this.folds=folds;
        for (int i = 0; i < folds; i++) {
            predictionArrayLock.add(new ReentrantLock());
        }
    }

    @Override
    public void run() throws FileNotFoundException, Exception {
        dataSetInstances = new Instances(DataSource.read(arffURI));
        System.out.println("arff file has been loaded");
        dataSetInstances.setClassIndex(dataSetInstances.numAttributes() - 1);

//**************************************************************************************************
//        if (dataSet.classAttribute().isNominal()) {
//            dataSet.stratify(folds);
//        }
        // perform cross-validation
        Evaluation eval = new Evaluation(dataSetInstances);
        ArrayList<Thread> mlTreads = new ArrayList<>();

        for (int n = 0; n < folds; n++) { //should be run by n threads
            semaphore.acquire();
            Runnable mlRunable = new Train_Evaluate_Runable(classifierClass, dataSetInstances, eval, predictionResult, predictionArrayLock, semaphore, folds, n);
            Thread thread = new Thread(mlRunable, "mlThread" + n);
            mlTreads.add(thread);
            thread.start();
        }

        for (int i = 0; i < mlTreads.size(); i++) {
            try {
                mlTreads.get(i).join();
            } catch (InterruptedException ex) {
                Logger.getLogger(Ml_method_Parallel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("occurrence class 1: " + predictionResult.stream().filter(p -> p.equals(1.0)).count());
        System.out.println("occurrence class 2: " + predictionResult.stream().filter(p -> p.equals(2.0)).count());
        System.out.println("occurrence class 3: " + predictionResult.stream().filter(p -> p.equals(3.0)).count());
        System.out.println("occurrence class 4: " + predictionResult.stream().filter(p -> p.equals(4.0)).count());
        System.out.println("occurrence class 5: " + predictionResult.stream().filter(p -> p.equals(5.0)).count());

        updateDataSetScores();
    }

    @Override
    protected void updateDataSetScores() throws Exception {
        if (dataSetRecords.records.size() != predictionResult.size()) {
            throw new Exception("dataset size mismatches predictionResultSize");
        }
        dataSetRecords.records.stream().forEach(record -> {
            int indexRecord = dataSetRecords.records.indexOf(record);
            record.estimatedScore = predictionResult.get(indexRecord).intValue();  // +1 is removed because of sparse format of arff
            record.estimatedPolarity = record.estimatedScore >= 3;  //true : positive - false : negative
        });
    }
}
