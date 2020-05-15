/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at May 4, 2017 , 1:52:03 PM
 */
package Features.Matcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class FeatureMatcher {

    protected String text;
    protected String regexCore;    //Pharase which is supposed to be matched either fully or partial
    protected String regex;
    protected Pattern pattern;
    protected Matcher matcher;

    public void setRegexCorePharase(String lookingPharase) {
        this.regexCore = lookingPharase;
        buildRegex();
        pattern = Pattern.compile(regex);
        matcher = null;
        text = null;
    }

    public void setText(String text) {
        this.text = text;
        matcher = pattern.matcher(text);
    }

    /**
     *
     * @return Matched String if found. else, Null is returned.
     */
    public String matchNext() {
        String resultString = null;
        if (matcher.find()) {
            resultString = matcher.group();
        }
        return resultString;
    }

    protected abstract void buildRegex();

}
