import java.util.Scanner;

public class CalcPI {

  static final int NUM_EXEC = 5;

  public static void main(String[] args) throws InterruptedException {
    Result[] results = new Result[NUM_EXEC];
    Scanner scanner = new Scanner(System.in);

    System.out.print("\n\n### Calculo de PI ###\n\n");

    System.out.print("Digite a quantidade de termos: ");
    int numTerms = scanner.nextInt();
    System.out.print("Digite a quantidade de threads: ");
    int numThreads = scanner.nextInt();

    for (int i = 0; i < NUM_EXEC; i++) {
      long start = System.nanoTime();

      CalcPiThread[] threads = new CalcPiThread[numThreads];
      int numTermsPerThread = numTerms / numThreads;
      int numberOfTermsProcessed = 0;

      for (int j = 0; j < numThreads; j++) {
        threads[j] = new CalcPiThread(numberOfTermsProcessed, numTermsPerThread);
        threads[j].start();
        numberOfTermsProcessed += numTermsPerThread;
      }

      double pi = 0;
      for (int j = 0; j < numThreads; j++) {
        threads[j].join();
        pi += threads[j].getPartialValueOfPi(); // soma os resultados parciais de cada thread chegando no valor de pi
      }

      double runtime = (double) (System.nanoTime() - start) / (double) 1000_000;

      results[i] = new Result(pi, runtime);
    }

    double averageRuntime = getAverageRuntime(results);
    double standardDeviation = getStandardDeviation(results, averageRuntime);

    System.out.println("\nResultados ( PI - Tempo de execução ):");
    for (int i = 0; i < NUM_EXEC; i++) {
      results[i].showResult();
    }
    System.out.printf("\nDuração média: %.2f ms\n", averageRuntime);
    System.out.printf("Desvio padrão: %.2f\n", standardDeviation);
    System.out.printf("Coeficiente de variação: %.2f%%\n", (standardDeviation / averageRuntime) * 100);
  }

  private static class CalcPiThread extends Thread {
    private double partial_pi;
    private int numberOfTermsProcessed;
    private int numberOfTermsToProcess;

    CalcPiThread(int numberOfTermsProcessed, int numTerms) {
      this.numberOfTermsProcessed = numberOfTermsProcessed;
      this.numberOfTermsToProcess = numTerms;
      this.partial_pi = 0;
    }

    @Override
    public void run() {
      for (int currentTerm = this.numberOfTermsProcessed; currentTerm < (this.numberOfTermsProcessed
          + this.numberOfTermsToProcess); currentTerm++) {
        partial_pi += Math.pow(-1, currentTerm) / (2 * currentTerm + 1);
      }

      partial_pi *= 4;
    }

    public double getPartialValueOfPi() {
      return this.partial_pi;
    }
  }

  private static double getStandardDeviation(Result[] results, double averageRuntime) {
    double deviation = 0;
    for (Result result : results) {
      double aux = result.getRuntime() - averageRuntime;
      deviation += Math.pow(aux, 2) / (double) results.length;
    }
    return Math.sqrt(deviation);
  }

  private static double getAverageRuntime(Result[] results) {
    double sum = 0;
    for (Result result : results) {
      sum += result.getRuntime();
    }
    return sum / (double) results.length;
  }
}