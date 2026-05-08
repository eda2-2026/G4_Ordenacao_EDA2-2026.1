package entidade;

public enum Condicao {

    FEBRE_ALTA("Febre muito alta (>39,5 °C)", 5),
    FEBRE_MODERADA("Febre moderada (38°C – 39,5°C)", 2);

    private final String descricao;
    private final int peso;

    Condicao(String descricao, int peso) {
        this.descricao = descricao;
        this.peso = peso;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPeso() {
        return peso;
    }
}