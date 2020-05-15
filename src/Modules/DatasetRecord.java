/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Jul 12, 2018 , 5:01:54 PM
 */
package Modules;

import java.util.ArrayList;

public class DatasetRecord extends Record {

    public ArrayList<Token> tokens = new ArrayList<>();
    public Token target = null;

    public DatasetRecord(DatasetRecord datasetRecord) {
        super(datasetRecord);
    }

    public DatasetRecord() {
        super();
    }

    public void setTarget(Token target) {
        this.target = target;
    }

}
