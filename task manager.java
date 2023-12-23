import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

class Task {
private String name;
private int ID;
private String status;
// Добавить id задания, статус задания.
//
public Task(String name) {
this.name = name;
this.status = "Не выполняется";
}

public String getName() {
return name;
}

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

public void checkStatus()
{
System.out.println("Статус задачи " + name + ": " + status);
}
}

class ExecutorSchedulerService {
private ExecutorService executorService;
private List<Task> taskList = new ArrayList<>();
private AtomicBoolean isRunning = new AtomicBoolean(false);

public ExecutorSchedulerService(int threadPoolSize) {
this.executorService = Executors.newFixedThreadPool(threadPoolSize);
}

public void addTask(Task task) {
taskList.add(task);
}

public void start() {
if (isRunning.compareAndSet(false, true)) {
for (Task task : taskList) {
executorService.submit(task::executeTask);
}
}
}

public void stop() {
if (isRunning.compareAndSet(true, false)) {
executorService.shutdown();
}
}
}

public class Main {
public static void main(String[] args) {
ExecutorSchedulerService schedulerService = new ExecutorSchedulerService(2);
Scanner scanner = new Scanner(System.in);
List<Task> taskList = new ArrayList<>();
int counter;
while(true) {
System.out.println("Выберите пункт меню:\n1. Добавить задачу\n2. Запустить планировщик\n3. Проверить статус всех задач\n4. Выход");
counter = scanner.nextInt();
switch (counter) {
case (1): {
schedulerService.stop();
System.out.println("Введите название задачи\n");
String task = scanner.next();
Task currentTask = new Task(task);
taskList.add(currentTask);
break;
}
case(2):{
if(!taskList.isEmpty()) {
for (int i = 0; i < taskList.size(); i++) {
schedulerService.addTask(taskList.get(i));
}
schedulerService.start();
}
break;
}
case (3): {
for(int i = 0; i < taskList.size(); i++)
{
taskList.get(i).checkStatus();
}
break;
}
case (4): {
return;
}
}
}
}
}