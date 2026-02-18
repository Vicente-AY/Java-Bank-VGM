package Seguro;

public class Vida {
    public enum opcionSeguro {
        BASICO("Riesgo simple", 20, 50, 18, 34),
        MODERADO("Capital moderado", 60, 180, 35, 50),
        PREMIUM("Cobertura Extra", 200, 1000, 51, 99);

        String tipo;
        double precioMin;
        double precioMax;
        int edadMin;
        int edadMax;

        opcionSeguro(String tipo, double precioMin, double precioMax, int edadMin, int edadMax) {
            this.tipo = tipo;
            this.precioMin = precioMin;
            this.precioMax = precioMax;
            this.edadMin = edadMin;
            this.edadMax = edadMax;
        }
        public static opcionSeguro planPorEdad(int edad) {
           if (edad < 35) return opcionSeguro.BASICO;
           if (edad <= 50) return opcionSeguro.MODERADO;
           return opcionSeguro.PREMIUM;
        }
    }
    opcionSeguro seguro;
    double precioAnual;
    boolean invalidez;
    String beneficiario;

    public Vida(int edadCliente, boolean coberturaExtra) {
        this.seguro = opcionSeguro.planPorEdad(edadCliente);
        this.invalidez = coberturaExtra || seguro == opcionSeguro.PREMIUM;
        this.precioAnual = calcularCoste(edadCliente);
    }
    public double calcularCoste(int edad) {
        if (seguro == opcionSeguro.BASICO) return 35.0;
        if (seguro == opcionSeguro.MODERADO) return 120.0;
        return 200.0 + (edad * 1.5);
    }
    @Override
    public String toString() {
        return String.format("Seguro Vida [%s] - Cuota: %.2f€/año", seguro, precioAnual);
    }

}
