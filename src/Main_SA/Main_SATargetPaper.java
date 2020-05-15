/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Dec 9, 2016 , 1:41:40 PM
 */
package Main_SA;

import Features.Matcher.FeatureMatcher_Full;
import Modules.AnalysisConfiguration;
import Modules.DataSet;
import Modules.Enums.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main_SATargetPaper {

    public static void initiateConfigurations(ArrayList<AnalysisConfiguration> configurations) {

        //dataSet_Hotels
        configurations.add(new AnalysisConfiguration()
                .setDataSetURL(Constants.dataSet_Target_Perview_URL)
                .setMethodInUse(AnalysisMethodInUse.lexicon)
                .setLexicon(LexiconInUse.LookUp)
                .setFeatureMatcher(new FeatureMatcher_Full())
                .setLimitDataset(false)
                .create());
    }

    public static void main(String[] args) {
        Constants.initialization();

        ArrayList<AnalysisConfiguration> configurations = new ArrayList<>();
        initiateConfigurations(configurations);

        ArrayList<SentimentAnalysis_Target> activeAnalysis = new ArrayList<>();
        configurations.stream().forEach(config -> {
            activeAnalysis.add(new SentimentAnalysis_Target(config));
        });

        //Runing SentimentAnalysis_Target********************************************************************
        List<DataSet> resultsArrayList = Collections.synchronizedList(new ArrayList<DataSet>());
        activeAnalysis.stream().forEach(analysis -> {
            try {
                DataSet outPut = analysis.run();
                resultsArrayList.add(outPut);
                Thread.sleep(10000);
            }
            catch (Exception ex) {
                Logger.getLogger(Main_SATargetPaper.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //***********************************************************************************************
    }
}