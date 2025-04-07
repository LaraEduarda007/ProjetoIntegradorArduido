public class Alerta {
    private String mensagem;
    private TipoAlerta tipo;

    public Alerta(String mensagem, TipoAlerta tipo) {
        this.mensagem = mensagem;
        this.tipo = tipo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public TipoAlerta getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "[" + tipo + "] " + mensagem;
    }
}