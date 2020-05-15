/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Feb 11, 2017 , 6:38:36 PM
 */
package Modules;

import java.util.ArrayList;

public class Record {

    public int id;
    public String labeledTarget;
    public String text;
    public float labeledScore;
    public float estimatedScore;
    public boolean labeledPolarity;
    public boolean estimatedPolarity;
    public ArrayList<Feature> machedFeatures = new ArrayList<>();

    public Record(Record record) {
        id = record.id;
        estimatedScore = record.estimatedScore;
        labeledScore = record.labeledScore;
        text = record.text;
        labeledTarget = record.labeledTarget;
        labeledPolarity = record.labeledPolarity;
        estimatedPolarity = record.estimatedPolarity;
        machedFeatures.addAll(record.machedFeatures);
    }

    public Record() {
    }

    public Record get() {
        return this;
    }

}
