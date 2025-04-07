public class DadosSensor {
    private double temperatura;
    private double umidade;
    private double luminosidade;

    public DadosSensor(double temperatura, double umidade, double luminosidade) {
        this.temperatura = temperatura;
        this.umidade = umidade;
        this.luminosidade = luminosidade;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getUmidade() {
        return umidade;
    }

    public double getLuminosidade() {
        return luminosidade;
    }
}