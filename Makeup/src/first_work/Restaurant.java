package first_work;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Amanda on 17/1/9.
 */
class Meal {
    private final int orderNum;
    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }
    public String toString() {
        return "Meal  "+orderNum;
    }
}

class WaitPerson implements Runnable {
    private Restaurant restaurant;

//    private boolean isCleaned = true;
    //busboy class need them
    public boolean isCleaned = true;
    public Meal meal;

    public WaitPerson(Restaurant r) {
        this.restaurant = r;
    }
    @Override
    public void run() {
        try {
//            Meal meal;
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal == null) {
                        wait();
                    }
                }
                meal = restaurant.meal;
                System.out.println("Waitperson got "+restaurant.meal);
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
                System.out.println(meal+" has delivered");
                synchronized (restaurant.busboy) {
                    isCleaned = false;
                    restaurant.busboy.notifyAll();  //cleanup
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Waitperson interrupted");
            e.printStackTrace();
        }
    }
}

class Chef implements Runnable{
    private Restaurant restaurant;
    private int count = 0;
    public Chef(Restaurant r) {
        this.restaurant = r;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal != null) {
                        wait();
                    }
                }
                if (++count == 10) {
                    System.out.println("Out of food ,closing");
                    restaurant.exec.shutdownNow();
                }
                System.out.println("Order up!");
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

/**
 * The code I added
 */
class Busboy implements Runnable {
    private Restaurant restaurant;
    public Busboy(Restaurant r) {
        this.restaurant = r;
    }
    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.waitPerson.isCleaned) {
                        wait();
                    }
                    //restaurant.meal is null
//                    System.out.println(restaurant.meal + " has been cleaned up");
                    System.out.println(restaurant.waitPerson.meal + " has been cleaned up");
                    restaurant.waitPerson.isCleaned = true;

                }
            }
        } catch (Exception e) {
            System.out.println("Busboy interrupted");
        }
    }
}

public class Restaurant {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);
    //The extra code
    Busboy busboy = new Busboy(this);

    public Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
        exec.execute(busboy);
    }

    public static void main(String[] args) {
        new Restaurant();
    }
}
