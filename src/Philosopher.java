import java.util.concurrent.locks.Lock;

class Philosopher extends Thread {
    private final int id;
    private final Lock leftFork;
    private final Lock rightFork;

    public Philosopher(int id, Lock leftFork, Lock rightFork) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
    }

    private void think() {
        System.out.println("Philosopher " + id + " is thinking.");

        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void eat() {
        System.out.println("Philosopher " + id + " is eating.");

        try {
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < 3; i++) {
            think();

            leftFork.lock();
            try {
                rightFork.lock();
                try {
                    eat();
                } finally {
                    rightFork.unlock();
                }
            } finally {
                leftFork.unlock();
            }
        }
    }
}
