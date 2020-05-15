/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Jan 21, 2017 , 9:14:26 AM
 */
package ML_Approach;

import Modules.DataSet;
import java.io.FileNotFoundException;
import weka.core.Instances;

public abstract class AbstractMLMethod {

    protected Class classifierClass;
    protected String arffURI = null;
    protected Instances dataSetInstances = null;
    protected DataSet dataSetRecords = null;

    public AbstractMLMethod(Class classifierClass) {
        this.classifierClass = classifierClass;
    }

    abstract protected void updateDataSetScores() throws Exception;

    /**
     * run method. estimate scores and apply new scores to the reviews object
     * you passed in.
     *
     * @throws java.io.FileNotFoundException
     */
    abstract public void run() throws FileNotFoundException, Exception;
}
