package entidade;

/**
 * ========================================================================
 * HISTÓRICO CLÍNICO
 * Doenças, antecedentes médicos e condições preexistentes do paciente.
 * Funcionam como agravantes na prioridade.
 * ========================================================================
 */
public enum HistoricoClinico {

    // ====================================================================
    // DOENÇAS CARDIOVASCULARES
    // ====================================================================

    DOENCA_CARDIACA("Possui doença cardíaca", 5),

    HISTORICO_INFARTO("Já teve infarto", 5),

    HISTORICO_AVC("Já teve AVC", 5),

    HIPERTENSAO("Hipertensão descompensada", 4),

    // ====================================================================
    // DOENÇAS METABÓLICAS E SISTÊMICAS
    // ====================================================================

    DIABETES("Diabetes", 4),

    INSUFICIENCIA_RENAL("Insuficiência renal crônica", 4),

    DOENCA_AUTOIMUNE("Doença autoimune", 3),

    // ====================================================================
    // DOENÇAS RESPIRATÓRIAS
    // ====================================================================

    DOENCA_PULMONAR("DPOC, asma grave ou doença pulmonar", 5),

    // ====================================================================
    // CONDIÇÕES NEUROLÓGICAS
    // ====================================================================

    EPILEPSIA("Epilepsia", 4),

    // ====================================================================
    // IMUNIDADE E CÂNCER
    // ====================================================================

    CANCER("Câncer em tratamento", 5),

    IMUNOSSUPRESSAO("Imunossuprimido", 5),

    // ====================================================================
    // ALERGIAS E COAGULAÇÃO
    // ====================================================================

    ALERGIA_GRAVE("Histórico de anafilaxia", 5),

    DISTURBIO_COAGULACAO("Distúrbio de coagulação", 5),

    // ====================================================================
    // PSIQUIATRIA E DEPENDÊNCIA
    // ====================================================================

    TRANSTORNO_PSIQUIATRICO("Transtorno psiquiátrico grave", 3),

    HISTORICO_SUICIDIO("Tentativa prévia de suicídio", 4),

    USO_DROGAS("Uso abusivo de álcool ou drogas", 3),

    // ====================================================================
    // EVENTOS RECENTES
    // ====================================================================

    CIRURGIA_RECENTE("Cirurgia nos últimos 30 dias", 3),

    INTERNACAO_RECENTE("Internação recente", 3),

    // ====================================================================
    // DEFICIÊNCIAS E CONDIÇÕES ESPECIAIS
    // ====================================================================

    AUTISMO("Autismo com dificuldade de comunicação", 3),

    DEFICIENCIA_INTELECTUAL("Deficiência intelectual", 2),

    DEFICIENCIA_FISICA("Mobilidade reduzida", 2),

    DEFICIENCIA_VISUAL("Deficiência visual grave", 1),

    DEFICIENCIA_AUDITIVA("Deficiência auditiva grave", 1);

    private final String descricao;
    private final int peso;

    HistoricoClinico(String descricao, int peso) {
        this.descricao = descricao;
        this.peso = peso;
    }

    public String getDescricao() { return descricao; }
    public int getPeso()         { return peso; }
}