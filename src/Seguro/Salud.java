package Seguro;

public class Salud {
    public enum opcionSeguro {
        BASICO("Básico", 50.0),
        MODERADO("Cobertura Media", 80.0),
        PREMIUM("Cobertura Superior", 250.0);

        String tipo;
        double precioMes;

        opcionSeguro(String tipo, double precio) {
            this.tipo = tipo;
            this.precioMes = precio;
        }
    }
    opcionSeguro seguro;
    double primaMes;
    String beneficiario;

    public Salud(opcionSeguro seguro, int numeroAsegurado, int edadPrincipal) {
        this.seguro = seguro;
        double factorEdad = (edadPrincipal > 60) ? 1.2 : 1.0;
        double multiplicadorFamilia = (numeroAsegurado > 1) ? numeroAsegurado * 0.85 : 1.0;

        this.primaMes = seguro.precioMes * factorEdad *  multiplicadorFamilia;
    }

    @Override
    public String toString() {
        return String.format("Seguro Salud [%s] - Cuota: %.2f€/mes", seguro, primaMes);
    }
}
