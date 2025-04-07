import java.util.Random;

public class SerialConnection {
    private boolean conectado = false;
    private Random random = new Random();

    public void conectar() {
        conectado = true;
        System.out.println("✅ Conectado à porta serial (simulada)");
    }

    public String lerDados() {
        double temperatura = 25 + random.nextDouble() * 15;
        double umidade = 40 + random.nextDouble() * 30;
        double luminosidade = random.nextDouble() * 100;
        return String.format("%.2f,%.2f,%.2f", temperatura, umidade, luminosidade);
    }

    public boolean estaConectado() {
        return conectado;
    }
}