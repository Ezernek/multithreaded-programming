import java.util.Scanner;


public class MultiplyMatrix
{
    static class Implication extends Thread
    {
        private double result;
        private final int columnsMatrix;
        private final double[] matrix1;
        private final double[] matrix2;


        public Implication(int columnsMatrix, double[] matrix1, double[] matrix2) {
            this.columnsMatrix = columnsMatrix;
            this.matrix1 = matrix1;
            this.matrix2 = matrix2;
        }

        public double getResult()
        {
            return result;
        }


        public void run()
        {
            double tt = 0;
            for(int j=0; j<columnsMatrix; j++) {
                tt += matrix1[j] * matrix2[j];
            }
            System.out.println("Работал поток " + Thread.currentThread().getName());
            result = tt;
        }
    }



    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите количество строк первой матрицы:");
        int rowsMatrix = scanner.nextInt();
        System.out.println("Введите количество столбцов для первой матрицы и количество строк для второй:");
        int columnsMatrix1 = scanner.nextInt();
        System.out.println("Введите количество столбцов для второй матрицы:");
        int columnsMatrix2 = scanner.nextInt();


        double[][] matrix1 = new double[rowsMatrix][columnsMatrix1];
        System.out.println("Введите элементы первой матрицы:");
        for(int i=0; i<rowsMatrix; i++) {
            for(int j=0; j<columnsMatrix1; j++) {
                matrix1[i][j] = scanner.nextDouble();
            }
        }

        double[][] matrix2 = new double[columnsMatrix2][columnsMatrix1];
        System.out.println("Введите элементы второй матрицы:");
        for(int i=0; i<columnsMatrix1; i++) {
            for(int j=0; j<columnsMatrix2; j++) {
                matrix2[j][i] = scanner.nextDouble();
            }
        }


        System.out.println("\nПервая матрица:");
        for(int i=0; i<rowsMatrix; i++) {
            for(int j=0; j<columnsMatrix1; j++) {
                System.out.printf("%.2f ", matrix1[i][j]);
            }
            System.out.print("\n");
        }

        System.out.println("\nВторая матрица:");
        for(int i=0; i<columnsMatrix1; i++) {
            for(int j=0; j<columnsMatrix2; j++) {
                System.out.printf("%.2f ", matrix2[j][i]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        Implication[] thread12 = new Implication[rowsMatrix*columnsMatrix2];
        int n = 0;
        for(int i=0; i<rowsMatrix; i++) {
            for(int j=0; j<columnsMatrix2; j++) {
                thread12[n] = new Implication(columnsMatrix1, matrix1[i], matrix2[j]);
                thread12[n].start();
                n++;
            }
        }

        for(int i=0; i<rowsMatrix*columnsMatrix2; i++) {
            thread12[i].join();
        }

        System.out.println("\nРезультат умножения матрицы на матрицу:");
        for(int i=0; i<rowsMatrix*columnsMatrix2; i++) {
            System.out.printf("%.2f ", thread12[i].getResult());
            if ((i+1)%columnsMatrix2 == 0)
            {
                System.out.print("\n");
            }
        }

    }
}