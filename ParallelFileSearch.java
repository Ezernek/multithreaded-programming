import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

public class ParallelFileSearch {

    private static final Logger logger = Logger.getLogger(ParallelFileSearch.class.getName());

    public static void main(String[] args) {
        String directoryPath = "C:\\Users\\Ezernek\\Documents\\poems";
        String searchWord = "word";

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        FileSearchTask searchTask = new FileSearchTask(new File(directoryPath), searchWord);
        Boolean result = forkJoinPool.invoke(searchTask);

        if (result) {
            logger.info("File containing the word found.");
        } else {
            logger.info("No file containing the word found.");
        }

        forkJoinPool.shutdown();
    }

    static class FileSearchTask extends RecursiveTask<Boolean> {
        private final File directory;
        private final String searchWord;

        FileSearchTask(File directory, String searchWord) {
            this.directory = directory;
            this.searchWord = searchWord;
        }

        @Override
        protected Boolean compute() {
            File[] files = directory.listFiles();

            if (files != null) {
                return Arrays.stream(files)
                        .parallel()
                        .anyMatch(this::containsWord);
            }

            return false;
        }

        private Boolean containsWord(File file) {
            if (file.isDirectory()) {
                FileSearchTask subtask = new FileSearchTask(file, searchWord);
                return subtask.fork().join();
            } else {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.contains(searchWord)) {
                            logger.info("Word found in file: " + file.getName());
                            return true;
                        }
                    }
                } catch (IOException e) {
                    logger.warning("Error reading file: " + file.getName());
                }

                logger.info("Word not found in file: " + file.getName());
                return false;
            }
        }
    }
}
