/**
 * Created by Amanda on 17/1/6.
 */
public class Test {
    public static void main(String[] args) {
        int[] a = new int[]{-1,2,4,-2,5,-9,8,4,-2,-4,-6,8,-1};
        deal(a);
        for (int k = 0;k < a.length;k++) {
            System.out.println(a[k]);
        }
    }

    private static void deal(int[] a) {
        int i = 0,j = 0;
        for (int m = 0;m < a.length;m++) {
            if (a[m] > 0) {
                i = m;
                j = m;
                break;
            }
        }
        while (j < a.length) {
            if (a[j] > 0) {
                j++;
            } else {
                int temp = a[i];
                a[i] = a[j];
                a[j] = temp;

                i++;
                j++;
            }

        }


    }
}
