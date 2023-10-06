import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Program3 {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(2);

        // Запуск секундомера
        Thread chrono = new Thread(() -> {
            LocalDateTime start = LocalDateTime.now();
            while (true) {
                LocalDateTime now = LocalDateTime.now();
                Duration duration = Duration.between(start, now);
                System.out.println("Прошло: " + duration.getSeconds() + " секунд(а)");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        service.scheduleWithFixedDelay(chrono, 0, 1, TimeUnit.SECONDS);

        // Вывод сообщения каждые 7 секунд
        Thread secondThread = new Thread(() -> {
            while (true) {
                System.out.println("Сообщение каждые семь секунд!");
                try {
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        service.scheduleWithFixedDelay(secondThread, 0, 7, TimeUnit.SECONDS);
    }
}
