package entidade;

/**
 * ========================================================================
 * CONDIÇÃO ATUAL
 * Sintomas e condições ATUAIS apresentados pelo paciente na triagem.
 * Influenciam diretamente na prioridade.
 * ========================================================================
 */
public enum CondicaoAtual {

    // ====================================================================
    // EMERGÊNCIAS ABSOLUTAS (PESOS 20 a 30)
    // ====================================================================

    PARADA_CARDIORRESPIRATORIA("Parada cardiorrespiratória / inconsciência", 30),

    ANAFILAXIA("Reação alérgica grave (anafilaxia)", 28),

    DIFICULDADE_RESPIRATORIA_GRAVE("Dificuldade respiratória grave (não consegue falar)", 25),

    SATURACAO_BAIXA("Saturação de oxigênio abaixo de 90%", 24),

    CHOQUE("Pressão arterial muito baixa (choque)", 24),

    SUSPEITA_AVC("Suspeita de AVC (fala alterada, desvio facial, fraqueza súbita)", 24),

    DOR_TORACICA("Dor torácica (possível infarto)", 23),

    CONVULSAO("Convulsão em curso ou recente", 22),

    OVERDOSE("Overdose ou intoxicação grave", 22),

    SANGRAMENTO_ABUNDANTE("Sangramento abundante ativo", 22),

    TRAUMA_CRANIANO_GRAVE("Trauma craniano com perda de consciência", 21),

    GESTANTE_COM_SANGRAMENTO("Gestante com sangramento ou dor intensa", 20),

    QUEIMADURA_GRAVE("Queimadura grave (>20% do corpo ou vias aéreas)", 20),

    // ====================================================================
    // URGÊNCIAS IMPORTANTES (PESOS 12 a 19)
    // ====================================================================

    ALTERACAO_CONSCIENCIA("Confusão mental ou alteração de consciência", 18),

    PARALISIA_SUBITA("Paralisia ou fraqueza súbita", 18),

    FREQUENCIA_CARDIACA_CRITICA("Frequência cardíaca acima de 150 bpm ou abaixo de 40 bpm", 18),

    CEFALEIA_EXPLOSIVA("Dor de cabeça súbita e explosiva", 17),

    DOR_ABDOMINAL_INTENSA("Dor abdominal intensa", 16),

    FRATURA_EXPOSTA("Fratura exposta", 16),

    DESMAIO_RECENTE("Desmaio recente", 15),

    AGITACAO_PSICOMOTORA("Agitação psicomotora intensa", 15),

    TENTATIVA_SUICIDIO("Tentativa de suicídio ou autolesão grave", 15),

    // ====================================================================
    // CONDIÇÕES MODERADAS (PESOS 5 a 11)
    // ====================================================================

    DOR_MUITO_INTENSA("Dor nível 7–8", 12),

    FEBRE_ALTA("Febre acima de 39,5°C", 10),

    SANGRAMENTO_MODERADO("Sangramento moderado", 10),

    TRAUMA_CRANIANO_LEVE("Trauma craniano sem perda de consciência", 9),

    FRATURA_FECHADA("Fratura fechada", 9),

    VITIMA_VIOLENCIA("Vítima de violência doméstica", 9),

    QUEIMADURA_LEVE("Queimadura leve", 7),

    GESTANTE("Gestante", 5),

    DOR_MODERADA("Dor nível 4–6", 6),

    FEBRE_MODERADA("Febre entre 38°C e 39,5°C", 5),

    // ====================================================================
    // CONDIÇÕES LEVES (PESOS 1 a 4)
    // ====================================================================

    SANGRAMENTO_LEVE("Ferimento superficial / sangramento leve", 3),

    DOR_LEVE("Dor nível 1–3", 2),

    // ====================================================================
    // SINTOMAS GERAIS (PESOS 1 a 5)
    // ====================================================================

    DESIDRATACAO("Desidratação", 5),

    VOMITOS("Vômitos persistentes", 4),

    FALTA_DE_AR_LEVE("Falta de ar leve", 4),

    DIARREIA("Diarreia intensa", 3),

    TONTURA("Tontura / vertigem", 3),

    TOSSE_PERSISTENTE("Tosse persistente", 2),

    MAL_ESTAR_GERAL("Mal-estar geral", 1),

    DOR_DE_GARGANTA("Dor de garganta", 1),

    CONGESTAO_NASAL("Congestão nasal / coriza", 1);

    private final String descricao;
    private final int peso;

    CondicaoAtual(String descricao, int peso) {
        this.descricao = descricao;
        this.peso = peso;
    }

    public String getDescricao() { return descricao; }
    public int getPeso()         { return peso; }
}