import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

// Класс, представляющий задачу
class Task {
    private String name;
    private int ID;
    private String status;

    // Конструктор задачи
    public Task(String name) {
        this.name = name;
        this.status = "Не выполняется";
    }

    // Получить название задачи
    public String getName() {
        return name;
    }

    // Выполнить задачу
    public void executeTask() {
        System.out.println("Выполнение задачи: " + name);
        this.status = "Выполняется";
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Задача выполнена: " + name);
        this.status = "Выполнен";
    }

    // Проверить статус задачи
    public void checkStatus() {
        System.out.println("Статус задачи " + name + ": " + status);
    }
}

// Сервис для управления планировщиком задач
class ExecutorSchedulerService {
    private ExecutorService executorService;
    private List<Task> taskList = new ArrayList<>();
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    // Конструктор сервиса
    public ExecutorSchedulerService(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    // Добавить задачу в список
    public void addTask(Task task) {
        taskList.add(task);
    }

    // Запустить выполнение задач
    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            for (Task task : taskList) {
                executorService.submit(task::executeTask);
            }
        }
    }

    // Остановить выполнение задач
    public void stop() {
        if (isRunning.compareAndSet(true, false)) {
            executorService.shutdown();
        }
    }
}

// Основной класс приложения
public class Main {
    public static void main(String[] args) {
        ExecutorSchedulerService schedulerService = new ExecutorSchedulerService(2);
        Scanner scanner = new Scanner(System.in);
        List<Task> taskList = new ArrayList<>();
        int counter;

        // Бесконечный цикл для взаимодействия с пользователем
        while (true) {
            System.out.println("Выберите пункт меню:\n1. Добавить задачу\n2. Запустить планировщик\n3. Проверить статус всех задач\n4. Выход");
            counter = scanner.nextInt();

            switch (counter) {
                // Добавить задачу
                case (1): {
                    schedulerService.stop();
                    System.out.println("Введите название задачи\n");
                    String task = scanner.next();
                    Task currentTask = new Task(task);
                    taskList.add(currentTask);
                    break;
                }
                // Запустить планировщик
                case (2): {
                    if (!taskList.isEmpty()) {
                        for (int i = 0; i < taskList.size(); i++) {
                            schedulerService.addTask(taskList.get(i));
                        }
                        schedulerService.start();
                    }
                    break;
                }
                // Проверить статус всех задач
                case (3): {
                    for (int i = 0; i < taskList.size(); i++) {
                        taskList.get(i).checkStatus();
                    }
                    break;
                }
                // Выход из программы
                case (4): {
                    return;
                }
            }
        }
    }
}
