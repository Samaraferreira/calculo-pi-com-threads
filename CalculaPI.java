import java.lang.Math;

public class CalculaPI {

    public static final double t = 1000000;

    public static void main(String[] args) {
        double soma = 0, pi;
        long tempoInicial = System.currentTimeMillis();
        
        double parcela = 0;
        for(int i = 0; i <= t; i++){
            parcela = calcularParcela(i);
            soma += parcela;
        }

        pi = soma * 4;
        long tempoFinal = System.currentTimeMillis();
        long tempo = (tempoFinal - tempoInicial);
        
        System.out.println(pi);
        System.out.println(tempo + "ms");
    }

    private static double calcularParcela(int i) {
        double parcela = 0;
        
        parcela = (Math.pow(-1, i)) / (2*i + 1);
        
        return parcela;
    }
    
}