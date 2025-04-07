public interface SensorListener {
    void onDadosRecebidos(DadosSensor dados);
    void onAlertaGerado(Alerta alerta);
}