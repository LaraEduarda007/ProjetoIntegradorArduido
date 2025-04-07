import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ArduinoMonitor {
    private SerialConnection serialConnection;
    private List<SensorListener> ouvintes;
    private ScheduledExecutorService executor;

    public ArduinoMonitor() {
        this.serialConnection = new SerialConnection();
        this.ouvintes = new ArrayList<>();
    }

    public void iniciarMonitoramento() {
        serialConnection.conectar();

        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            String dadosBrutos = serialConnection.lerDados();
            DadosSensor dados = processarDados(dadosBrutos);
            notificarDados(dados);
            verificarAlertas(dados);
        }, 0, 3, TimeUnit.SECONDS);
    }

    private DadosSensor processarDados(String dadosBrutos) {
        String[] partes = dadosBrutos.split(",");
        double temperatura = Double.parseDouble(partes[0]);
        double umidade = Double.parseDouble(partes[1]);
        double luminosidade = Double.parseDouble(partes[2]);
        return new DadosSensor(temperatura, umidade, luminosidade);
    }

    private void verificarAlertas(DadosSensor dados) {
        if (dados.getTemperatura() > 35.0) {
            Alerta alerta = new Alerta("Temperatura alta: " + dados.getTemperatura() + "°C", TipoAlerta.PERIGO);
            notificarAlerta(alerta);
        } else if (dados.getTemperatura() > 30.0) {
            Alerta alerta = new Alerta("Temperatura acima do ideal: " + dados.getTemperatura() + "°C", TipoAlerta.ALERTA);
            notificarAlerta(alerta);
        }

        if (dados.getLuminosidade() < 20.0) {
            Alerta alerta = new Alerta("Luminosidade baixa: " + dados.getLuminosidade() + "%", TipoAlerta.ALERTA);
            notificarAlerta(alerta);
        }
    }

    public void adicionarOuvinte(SensorListener ouvinte) {
        ouvintes.add(ouvinte);
    }

    private void notificarDados(DadosSensor dados) {
        for (SensorListener ouvinte : ouvintes) {
            ouvinte.onDadosRecebidos(dados);
        }
    }

    private void notificarAlerta(Alerta alerta) {
        for (SensorListener ouvinte : ouvintes) {
            ouvinte.onAlertaGerado(alerta);
        }
    }

    public static void main(String[] args) {
        ArduinoMonitor monitor = new ArduinoMonitor();

        monitor.adicionarOuvinte(new SensorListener() {
            @Override
            public void onDadosRecebidos(DadosSensor dados) {
                System.out.printf("Dados recebidos: Temperatura = %.2f°C | Umidade = %.2f%% | Luminosidade = %.2f%%\n",
                        dados.getTemperatura(), dados.getUmidade(), dados.getLuminosidade());
            }

            @Override
            public void onAlertaGerado(Alerta alerta) {
                System.out.println("⚠ ALERTA: " + alerta);
            }
        });

        monitor.iniciarMonitoramento();
    }
}