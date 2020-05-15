/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at May 4, 2017 , 1:52:35 PM
 */


package Features.Matcher;


public class FeatureMatcher_Full extends FeatureMatcher{

    @Override
    protected void buildRegex() {
        regex = "((^)|(\\s)|(" + (char) (8204) + "))" + regexCore + "(($)|(\\s)|(\\z)|[.!؟?]|(" + (char) (8204) + "))";
    }
    
    @Override
    public String toString() {
        return "FullMatch";
    }

}
