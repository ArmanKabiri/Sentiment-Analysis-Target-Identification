/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Mar 25, 2017 , 2:51:18 PM
 */
package Modules;

import java.util.Objects;

public class Feature {

    public String text;
    public int occurrence_total = 0;
    public int occurrence_reviews = 0;  //the number of reviews containing this feature at least one time
    public String UsedFeatureExtraction;

    public Feature(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Feature) {
            return text.equals(((Feature) obj).text);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.text);
        return hash;
    }

}
