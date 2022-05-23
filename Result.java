public class Result {
  private double pi;
  private double runtime;

  Result(double pi, double runtime) {
    this.pi = pi;
    this.runtime = runtime;
  }

  public double getPi() {
    return this.pi;
  }

  public double getRuntime() {
    return this.runtime;
  }

  public void showResult() {
    System.out.println(this.pi + " - " + this.runtime + " ms");
  }
}
