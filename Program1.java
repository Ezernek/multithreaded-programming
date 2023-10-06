
class CustomThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i <= 100; i += 10) {
            System.out.println(i);
            try {
                Thread.sleep(100); // Пауза 100 миллисекунд
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Program1 {
    public static void main(String[] args) {
        CustomThread customThread = new CustomThread();
        customThread.start();
    }
}
