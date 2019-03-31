public class Ticket implements Runnable {

    private int tickets = 10;

    @Override
    public void run() {
        while (tickets > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出一张火车票,还剩" + --tickets + "张");
        }
    }

}