/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 28, 2017 , 9:25:00 PM
 */
package ML_Approach;

import Modules.Feature;
import Modules.MutableInteger;
import Modules.Record;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class InstancesFactory {

    private ArrayList<Feature> features;
    private ArrayList<Attribute> attributes = null;
    private ArrayList<? extends Record> dataSet;
    private Instances instances = null;

    public InstancesFactory(ArrayList<Feature> features, ArrayList<? extends Record> dataSet) {
        this.features = features;
        this.dataSet = dataSet;
    }

    private void initialAttributes() {
        attributes = new ArrayList<>();
        features.stream().forEach(feature -> {
            Attribute attrib = new Attribute(feature.text, Arrays.asList("0", "1"));
            attributes.add(attrib);
        });
        Attribute classAttrib = new Attribute("Class", Arrays.asList("0", "1", "2", "3", "4", "5"));
        attributes.add(classAttrib);
    }

    public Instances getInstances() {

        initialAttributes();
        instances = new Instances("SA", attributes, dataSet.size());
        instances.setClassIndex(attributes.size() - 1);

        dataSet.stream().forEach(record -> {
            Instance instance = new DenseInstance(attributes.size());
            int score = (int) record.labeledScore;
            instance.setValue(attributes.get(attributes.size() - 1), score);    //class
            instances.add(instance);
        });

        MutableInteger stepCounter = new MutableInteger(0);
        attributes.stream().forEach(attrib -> {
            String word = attrib.name();
            if (!word.equals("Class")) {
                String regexWord2 = "((^)|(\\s))" + word + "(($)|(\\s)|(\\z)|[.!؟?])";
                Pattern pattern = Pattern.compile(regexWord2);
                dataSet.parallelStream().forEach(record -> {    //SHOULD BE PARALLEL
                    Matcher matcher = pattern.matcher(record.text);
                    if (matcher.find()) {
                        instances.get(dataSet.indexOf(record)).setValue(attrib, "1");
                    } else {
                        instances.get(dataSet.indexOf(record)).setValue(attrib, "0");
                    }
                });

                double step = stepCounter.get_incOne();
                if (step % (dataSet.size() / 100) == 0) {
                    synchronized (stepCounter) {
                        System.out.println((step / attributes.size() * 100) + " %");
                    }
                }
            }
        });

        return instances;
    }

    public void writeToArffFile() {
        //
    }

}
