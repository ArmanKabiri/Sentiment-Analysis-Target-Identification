/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Nov 21, 2016 , 7:38:44 PM
 */
package Modules;

import Modules.LexiconRecord;

public class LexiconRecord_Probabilites {

    public LexiconRecord lexWord;
    public float class1Prob;
    public float class2Prob;
    public float class3Prob;
    public float class4Prob;
    public float class5Prob;
    public int class1Visited;
    public int class2Visited;
    public int class3Visited;
    public int class4Visited;
    public int class5Visited;

    public LexiconRecord_Probabilites(LexiconRecord lexWord) {
        this.lexWord = lexWord;
        this.class1Prob = 0;
        this.class2Prob = 0;
        this.class3Prob = 0;
        this.class4Prob = 0;
        this.class5Prob = 0;
    }

    public LexiconRecord_Probabilites(LexiconRecord lexWord, float class1Prob, float class2Prob, float class3Prob, float class4Prob, float class5Prob) {
        this.lexWord = lexWord;
        this.class1Prob = class1Prob;
        this.class2Prob = class2Prob;
        this.class3Prob = class3Prob;
        this.class4Prob = class4Prob;
        this.class5Prob = class5Prob;
    }

    public void setProbabilities(float class1Prob, float class2Prob, float class3Prob, float class4Prob, float class5Prob) {
        this.class1Prob = class1Prob;
        this.class2Prob = class2Prob;
        this.class3Prob = class3Prob;
        this.class4Prob = class4Prob;
        this.class5Prob = class5Prob;
    }

}
