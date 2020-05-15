/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 16, 2016 , 6:20:12 PM
 */
package Evaluation;

import Modules.LabelType;
import Modules.Record;
import java.util.ArrayList;
import java.util.Arrays;

public class Evaluator3Removed {

    ArrayList<? extends Record> dataSet;
    LabelType labelType;
    int numPossibleLabels;
    int posCount, negCount, reviewCount;
    int truePositiveCount, trueNegativeCount, falsePositiveCount, falseNegativeCount;
    double accuracy, errorRate, recall, percision, f_measure, MAE;

    int[] truePositiveArrayCount;
    int[] trueNegativeArrayCount;
    int[] falsePositiveArrayCount;
    int[] falseNegativeArrayCount;
    double[] recallArray;
    double[] percisionArray;

    public Evaluator3Removed(ArrayList<? extends Record> dataSet, LabelType labelType) {
        this.dataSet = dataSet;
        this.labelType = labelType;
        switch (labelType) {
            case fiveStar:
                numPossibleLabels = 4;
                break;
            case polarity:
                numPossibleLabels = 2;
                break;
        }

        truePositiveArrayCount = new int[numPossibleLabels];
        trueNegativeArrayCount = new int[numPossibleLabels];
        falsePositiveArrayCount = new int[numPossibleLabels];
        falseNegativeArrayCount = new int[numPossibleLabels];
        recallArray = new double[numPossibleLabels];
        percisionArray = new double[numPossibleLabels];
    }

    /**
     * measures the calculatedScore attribute in class.
     */
    public void measure() {
        switch (labelType) {
            case fiveStar:
                measure5Star();
                break;
            case polarity:
                measurePolarity();
                break;
        }
    }

    private void measurePolarity() {
// calculating TP,TN,FP,FN
        dataSet.stream().forEach(record -> {
            int labeledScore = (int) record.labeledScore;
            int estimatedScore = (int) record.estimatedScore;
            for (int i = 0; i < numPossibleLabels; i++) {
                boolean classPolarity = (i != 0);   //0 for neg(false) , 1 for pos(true)
                if (record.labeledPolarity == classPolarity && record.estimatedPolarity == classPolarity) {
                    truePositiveArrayCount[i]++;
                } else if (record.labeledPolarity != classPolarity && record.estimatedPolarity != classPolarity) {
                    trueNegativeArrayCount[i]++;
                } else if (classPolarity == record.estimatedPolarity && classPolarity != record.labeledPolarity) {
                    falsePositiveArrayCount[i]++;
                } else if (classPolarity != record.estimatedPolarity && classPolarity == record.labeledPolarity) {
                    falseNegativeArrayCount[i]++;
                }
            }
        });
        finalMeasurement();
    }

    private void measure5Star() {
        // calculating TP,TN,FP,FN
        dataSet.stream().forEach(record -> {
            int labeledScore = (int) record.labeledScore;
            int estimatedScore = (int) record.estimatedScore;
            for (int i = 0; i < numPossibleLabels; i++) {
                int classScore=0;
                switch (i) {
                    case 0:
                        classScore = 1;
                        break;
                    case 1:
                        classScore = 2;
                        break;
                    case 2:
                        classScore = 4;
                        break;
                    case 3:
                        classScore = 5;
                        break;
                }
                if (labeledScore == classScore && estimatedScore == classScore) {
                    truePositiveArrayCount[i]++;
                } else if (labeledScore != classScore && estimatedScore != classScore) {
                    trueNegativeArrayCount[i]++;
                } else if (classScore == estimatedScore && classScore != labeledScore) {
                    falsePositiveArrayCount[i]++;
                } else if (classScore != estimatedScore && classScore == labeledScore) {
                    falseNegativeArrayCount[i]++;
                }
            }
        });
        finalMeasurement();
    }

    private void finalMeasurement() {
        truePositiveCount = (int) Math.round(Arrays.stream(truePositiveArrayCount).average().getAsDouble());
        trueNegativeCount = (int) Math.round(Arrays.stream(trueNegativeArrayCount).average().getAsDouble());
        falsePositiveCount = (int) Math.round(Arrays.stream(falsePositiveArrayCount).average().getAsDouble());
        falseNegativeCount = (int) Math.round(Arrays.stream(falseNegativeArrayCount).average().getAsDouble());

        for (int i = 0; i < numPossibleLabels; i++) {
            percisionArray[i] = (double) truePositiveArrayCount[i] / (double) (truePositiveArrayCount[i] + falsePositiveArrayCount[i]);
            if (truePositiveArrayCount[i] + falsePositiveArrayCount[i] == 0) {
                percisionArray[i] = 0;
            }
            recallArray[i] = (double) truePositiveArrayCount[i] / (double) (truePositiveArrayCount[i] + falseNegativeArrayCount[i]);
        }

        recall = Arrays.stream(recallArray).average().getAsDouble();
        percision = Arrays.stream(percisionArray).average().getAsDouble();
        f_measure = 2 * percision * recall / (percision + recall);
        accuracy = (double) Arrays.stream(truePositiveArrayCount).reduce((x, y) -> x + y).getAsInt() / (double) dataSet.size();
        errorRate = 1 - accuracy;
        if (labelType.equals(LabelType.fiveStar)) {
            MAE = dataSet.stream().map(x -> Math.abs(x.labeledScore - x.estimatedScore)).mapToDouble(Double::valueOf).average().getAsDouble();
        }

        System.out.println("Evaluation on " + labelType + " :");
        System.out.println("recall: " + recall);
        System.out.println("precision: " + percision);
        System.out.println("F-Measure: " + f_measure);
        System.out.println("accuracy: " + accuracy);
        System.out.println("errorRate: " + errorRate);
        if (labelType.equals(LabelType.fiveStar)) {
            System.out.println("MAE: " + MAE);
        }
    }
}
