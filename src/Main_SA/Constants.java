/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Jun 25, 2018 , 5:14:24 PM
 */
package Main_SA;

import Modules.Enums;
import java.io.File;
import java.util.HashMap;

public class Constants {

    public static final String ROOTURI = new File(System.getProperty("user.dir")).getParentFile().getParent() + "/Data/";

    public static final String dataSet_AppleLG_URL = ROOTURI + "DataSet/Persian/DataSet-mobile reviews/v7- ICWR_2018/Apple-LG.xlsx";
    public static final String dataSet_HuaweiSonyHtcAdataCannon_URL = ROOTURI + "DataSet/Persian/DataSet-mobile reviews/v7- ICWR_2018/Huawei-Sony-HTC-Adata-Cannon.xlsx";
    public static final String dataSet_SamsungNote5_URL = ROOTURI + "DataSet/Persian/DataSet-mobile reviews/v7- ICWR_2018/Samsung Note 5.xlsx";
    public static final String dataSet_Samsung_URL = ROOTURI + "DataSet/Persian/DataSet-mobile reviews/v7- ICWR_2018/Samsung.xlsx";
    public static final String dataSet_Perview_URL = ROOTURI + "DataSet/Persian/DataSet-mobile reviews/v4 - JIS/collectedReviews_normalized_PosVerb.xlsx";
    public static final String dataSet_Target_Hotels_URL = ROOTURI + "DataSet/Persian/DataSet-TargetIdentification/Hotels/HotelsPOS.xlsx";
    public static final String dataSet_Target_Perview_URL = ROOTURI + "DataSet/Persian/DataSet-TargetIdentification/PerView_Target.xlsx";
//    public static String dataSet_URL = ROOTURI + "DataSet/Persian/DataSet-mobile reviews/v4 - JIS/"
//            + "collectedReviews_Normalized_PosVerb.xlsx";
    public static final HashMap<String, String> LexiconsURLS = new HashMap<>();
    public static final String LEXICON_URL_ADJECTIVES = ROOTURI + "Lexicon/Persian/AdjectiveLexicon/AdjectiveLexicon_Reduced.xlsx";
    public static final String LEXICON_URL_LOOKUP = ROOTURI + "Lexicon/Persian/Lookup Table Lexicon/PersianLookupTable-5Star.xlsx";
    public static final String LEXICON_URL_NRC = ROOTURI + "Lexicon/Persian/NRC Lexicon/NRC-Persian-5Star-pruned-charCorrected.xlsx";
    public static final String LEXICON_URL_CNRC = ROOTURI + "Lexicon/Persian/NRC Lexicon/CNRC.xlsx";
    public static final String LEXICON_URL_COMMON = ROOTURI + "Lexicon/Persian/Common/CommonAll.xlsx";
    public static final String LEXICON_URL_LOOKUPADJ = ROOTURI + "Lexicon/Persian/LookupAdjLexicon.xlsx";
    public static final String LEXICON_URL_PERSENT = ROOTURI + "Lexicon/Persian/PerSent/PerSent_Reduced.xlsx";
    public static final String LEXICON_URL_PERLEX = ROOTURI + "Lexicon/Persian/PerLex/PerLex_main.xlsx";
    public static final String LEXICON_URL_LEXIPERS = ROOTURI + "Lexicon/Persian/LexiPersV1.0/Data/adj-final.xlsx";
    public static final String RESULT_FOLDER_URL = null;
    public static final String ARFF_FOLDER_URL = ROOTURI + "ARFF_Temp";
    public static final String STOPWORDS_URL = ROOTURI + "Other Resources/Persian/perisanStopWords.txt";
    public static final String NEGATIONWORDS_LIST_URL = ROOTURI + "Other Resources/Persian/persianNegationWordsList.txt";

    public static void initialization() {
        LexiconsURLS.put(Enums.LexiconInUse.Adjectives.toString(), LEXICON_URL_ADJECTIVES);
        LexiconsURLS.put(Enums.LexiconInUse.LookUp.toString(), LEXICON_URL_LOOKUP);
        LexiconsURLS.put(Enums.LexiconInUse.CNRC.toString(), LEXICON_URL_CNRC);
        LexiconsURLS.put(Enums.LexiconInUse.NRC.toString(), LEXICON_URL_NRC);
        LexiconsURLS.put(Enums.LexiconInUse.CommonLex.toString(), LEXICON_URL_COMMON);
        LexiconsURLS.put(Enums.LexiconInUse.LookupAdjLex.toString(), LEXICON_URL_LOOKUPADJ);
        LexiconsURLS.put(Enums.LexiconInUse.PerSent.toString(), LEXICON_URL_PERSENT);
        LexiconsURLS.put(Enums.LexiconInUse.PerLex.toString(), LEXICON_URL_PERLEX);
        LexiconsURLS.put(Enums.LexiconInUse.LexiPers.toString(), LEXICON_URL_LEXIPERS);
    }
}
