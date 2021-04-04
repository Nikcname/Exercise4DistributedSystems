import java.util.Random;
import java.io.Serializable;
// A dumb class with code taken from the example given
public class Sorter implements Serializable {
    // Use Integers instead of ints to have generic remoteCalls
    public int[] sort(Integer randValue, Integer sizeArray){
        int[] num = new int[sizeArray];
        Random r = new Random();
        for (int i = 0; i < num.length; i++) {
            num[i] = r.nextInt(randValue);
        }
        int j;
        boolean flag = true;
        int temp;

        while (flag) {
            flag = false;
            for (j = 0; j < num.length - 1; j++) {
                if (num[j] < num[j + 1])
                {
                    temp = num[j];
                    num[j] = num[j + 1];
                    num[j + 1] = temp;
                    flag = true;
                }
            }
        }

        return num;
    }
}