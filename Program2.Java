class CustomRunnable implements Runnable {
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

public class Program2 {
    public static void main(String[] args) {
        CustomRunnable customRunnable = new CustomRunnable();
        Thread thread = new Thread(customRunnable);
        thread.start();
    }
}
