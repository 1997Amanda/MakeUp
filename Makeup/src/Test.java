import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * Created by Amanda on 17/1/6.
 */
public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Future<String>> list = new ArrayList<>();
        ExecutorService exec = Executors.newCachedThreadPool(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
        TimeUnit.MILLISECONDS.sleep(1000);
        for (int i = 0;i < 5;i++)
            list.add(exec.submit(new CallableDemo(i)));
        for (Future<String> f:list) {
              System.out.print(f.get());

        }
        exec.shutdown();
    }

//    private static void deal(int[] a) {
//        int i = 0,j = 0;
//        for (int m = 0;m < a.length;m++) {
//            if (a[m] > 0) {
//                i = m;
//                j = m;
//                break;
//            }
//        }
//        while (j < a.length) {
//            if (a[j] > 0) {
//                j++;
//            } else {
//                int temp = a[i];
//                a[i] = a[j];
//                a[j] = temp;
//
//                i++;
//                j++;
//            }
//
//        }
//    }
    static class CallableDemo implements Callable<String> {
        private int id;
        public CallableDemo(int id) {
            this.id = id;
        }
        @Override
        public String call() throws Exception {
            return "id= "+id;
        }
    }
}
