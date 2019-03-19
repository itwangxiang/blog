
class ThreadExample {

    public static void main(String[] args) {

        Ticket ticket = new Ticket();

        new Thread(ticket, "一号窗口：").start();
        new Thread(ticket, "二号窗口：").start();

    }

}