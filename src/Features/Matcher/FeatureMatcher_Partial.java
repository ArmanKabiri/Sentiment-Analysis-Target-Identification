/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at May 4, 2017 , 1:52:56 PM
 */
package Features.Matcher;

public class FeatureMatcher_Partial extends FeatureMatcher {

    @Override
    protected void buildRegex() {
        regex = "((^)|(\\s)|(" + (char) (8204) + "))" + regexCore
                + "("
                + "($)|(\\s)|(\\z)|[!.?]|(" + (char) (8204) + ")|"
                + "(م)|(ی)|(یم)|(ید)|(ند)|(ت)|(ش)|(مان)|(شان)|(تان)|(ه)|(ا)|(یه)|(گی)|(انگی)|(نگی)|(اش)|(هاش)|(های)|(ها)|(هایه)"
                + ")";
    }

    @Override
    public String toString() {
        return "PartialMatch";
    }

}
