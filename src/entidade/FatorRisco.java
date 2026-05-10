package entidade;

/**
 * ========================================================================
 * FATORES DE RISCO E VULNERABILIDADE
 * Não representam doença atual,
 * mas aumentam o risco clínico do paciente.
 * ========================================================================
 */
public enum FatorRisco {

    // ====================================================================
    // FAIXA ETÁRIA
    // ====================================================================

    RECEM_NASCIDO(
            "Recém-nascido (0–28 dias)",
            8
    ),

    BEBE(
            "Bebê (1 mês–2 anos)",
            5
    ),

    CRIANCA(
            "Criança (2–12 anos)",
            2
    ),

    IDOSO(
            "Idoso (60–79 anos)",
            3
    ),

    IDOSO_80_MAIS(
            "Idoso com mais de 80 anos",
            5
    ),

    // ====================================================================
    // CONDIÇÕES ESPECIAIS
    // ====================================================================

    GESTANTE("Gestante", 5),

    AUTISMO(
            "Autismo com dificuldade de comunicação",
            3
    ),

    DEFICIENCIA_INTELECTUAL(
            "Deficiência intelectual",
            2
    ),

    // ====================================================================
    // DEFICIÊNCIAS
    // ====================================================================

    DEFICIENCIA_FISICA(
            "Mobilidade reduzida",
            2
    ),

    DEFICIENCIA_VISUAL(
            "Deficiência visual grave",
            1
    ),

    DEFICIENCIA_AUDITIVA(
            "Deficiência auditiva grave",
            1
    );

    private final String descricao;
    private final int peso;

    FatorRisco(String descricao, int peso) {
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