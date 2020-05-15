/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Jul 12, 2018 , 12:39:05 PM
 */
package Modules;

public class Token {

    public String text;
    public Enums.POSTags POSTag;
    public boolean isPotentialTerm = false;
    public boolean isTargetTerm = false;
    public boolean potentialPolarity = false;   //fetched from lexicon
    public Token target = null;

    public Token(String text, Enums.POSTags POSTag) {
        this.text = text;
        this.POSTag = POSTag;
    }

    public void setTarget(Token target) {
        this.target = target;
    }
    
}
