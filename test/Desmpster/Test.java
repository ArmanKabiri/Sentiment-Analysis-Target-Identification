package Desmpster;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author : Arman Kabiri
 * @Email : "arman73k@gmail.com"
 * @Linked in : https://www.linkedin.com/in/armankabiri73
 * @Created at Nov 17, 2016 , 12:43:27 PM
 */
public class Test {

    public static void main(String[] args) {
        boolean isTheFirstSentence = true;
        float m = 0;

        ArrayList<Float> list1 = new ArrayList<>(Arrays.asList(1.3f,2f,3f,4f,5f)); // result : 1
        ArrayList<Integer> list2 = new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1)); //result:1
        ArrayList<Integer> list3 = new ArrayList<>(Arrays.asList(4, 2, 2, 5)); //result:.008 ~0
        
        for (float num : list1) {
            float ms = (float) (num - 1) / (5 - 1);
            if (ms == 0) {
                ms = .001f;
            }
            if (isTheFirstSentence) {
                m = ms;
                isTheFirstSentence = false;
            } else if (((1 - m) * ms + m * (1 - ms)) != 1) {
                m = (m * ms) / (1 - ((1 - m) * ms + m * (1 - ms))); // this is for m(A)
            } else {
                m = 0;
            }
        }
        System.out.println(m);
    }
}
