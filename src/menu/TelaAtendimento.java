package menu;

import entidade.CondicaoAtual;
import entidade.Paciente;
import servico.PacienteService;

import javax.swing.*;
import java.util.List;

/**
 * ========================================================================
 * TELA DE ATENDIMENTO — Triagem e chamada do próximo paciente.
 * ========================================================================
 */
public class TelaAtendimento {

    private TelaAtendimento() {
    }

    // ====================================================================
    // SUBMENU
    // ====================================================================

    static void exibir() {
        while (true) {
            String[] opcoes = {"Nova Triagem", "Atender Próximo Paciente", "Voltar"};
            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Módulo de Atendimento:",
                    "🏥 Atendimento",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> menuTriagem();
                case 1 -> atenderProximo();
                default -> {
                    return;
                }
            }
        }
    }

    // ====================================================================
    // TRIAGEM — Adicionar paciente à fila de prioridade
    // ====================================================================

    private static void menuTriagem() {
        // Passo 1: Pedir CPF (com validação de 11 dígitos e retry)
        String cpf = pedirCpfTriagem();
        if (cpf == null) return; // cancelou

        Paciente paciente;

        if (!cpf.isEmpty()) {
            // Verifica se já está na fila
            if (PacienteService.cpfNaFila(cpf)) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "Este CPF já está na fila de espera.",
                        "Paciente já na fila", JOptionPane.WARNING_MESSAGE);
                return;
            }

            paciente = PacienteService.buscarCliente(cpf);

            if (paciente != null) {
                if (PacienteService.pacienteNaFila(paciente)) {
                    JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                            "Este paciente já está na fila de espera.",
                            "Paciente já na fila", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "Paciente localizado na base de dados!\n\n" +
                                "Nome: " + paciente.getNome() + "\nCPF: " + paciente.getCpf(),
                        "✅ Paciente Encontrado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "CPF não encontrado. O paciente será registrado como temporário.",
                        "Paciente Não Cadastrado", JOptionPane.INFORMATION_MESSAGE);
                paciente = criarPacienteTemporario();
                if (paciente == null) return;
                paciente.setCpf(cpf);
            }
        } else {
            paciente = criarPacienteTemporario();
            if (paciente == null) return;
        }

        // Passo 2: Selecionar condições atuais (pode ser vazio)
        List<CondicaoAtual> condicoes = selecionarCondicoesAtuais();
        if (condicoes == null) return; // cancelou

        // Passo 3: Adicionar à fila (mesmo sem condições — score será só por idade)
        PacienteService.adicionarPacienteFila(paciente, condicoes);

        StringBuilder resumo = new StringBuilder();
        resumo.append("Paciente adicionado à fila com sucesso!\n\n");
        resumo.append("Nome: ").append(paciente.getNome()).append("\n");
        resumo.append("Score de Prioridade: ").append(paciente.getScorePrioridade()).append("\n");

        if (!condicoes.isEmpty()) {
            resumo.append("\nCondições registradas:\n");
            for (CondicaoAtual c : condicoes) {
                resumo.append("  • ").append(c.getDescricao()).append("\n");
            }
        } else {
            resumo.append("\nNenhuma condição específica registrada.\n");
        }

        JOptionPane.showMessageDialog(MenuUtil.getFrame(), resumo.toString(),
                "✅ Triagem Concluída", JOptionPane.INFORMATION_MESSAGE);
    }

    // ====================================================================
    // PEDIR CPF NA TRIAGEM (com retry se inválido)
    // ====================================================================

    private static String pedirCpfTriagem() {
        while (true) {
            String cpf = MenuUtil.pedirCpf(
                    "Digite o CPF do paciente.\n(Deixe em branco se não tiver documentos)");
            if (cpf == null) return null; // cancelou
            if (cpf.isEmpty()) return ""; // sem documentos

            if (cpf.length() != 11) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "CPF deve ter exatamente 11 dígitos.",
                        "CPF Inválido", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            return cpf;
        }
    }

    // ====================================================================
    // CRIAÇÃO DE PACIENTE TEMPORÁRIO (com retry em erros)
    // ====================================================================

    private static Paciente criarPacienteTemporario() {
        String nome;
        while (true) {
            nome = JOptionPane.showInputDialog(MenuUtil.getFrame(),
                    "Nome do paciente:", "Paciente Temporário", JOptionPane.PLAIN_MESSAGE);
            if (nome == null) return null;
            if (!nome.isBlank()) break;
            JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                    "Nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        int idade;
        while (true) {
            String idadeStr = JOptionPane.showInputDialog(MenuUtil.getFrame(),
                    "Idade do paciente:", "Paciente Temporário", JOptionPane.PLAIN_MESSAGE);
            if (idadeStr == null) return null;
            try {
                idade = Integer.parseInt(idadeStr.trim());
                if (idade >= 0 && idade <= 150) break;
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "Idade deve ser entre 0 e 150.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "Idade inválida. Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        char sexo = MenuUtil.pedirSexo();
        if (sexo == 0) return null;

        return PacienteService.criarPacienteTemporario(nome.trim(), idade, sexo);
    }

    // ====================================================================
    // SELEÇÃO DE CONDIÇÕES ATUAIS — Categorias reorganizadas
    // ====================================================================

    private static List<CondicaoAtual> selecionarCondicoesAtuais() {
        String[][] categorias = {
                {"Cardiorrespiratório",
                        "PARADA_CARDIORRESPIRATORIA", "DIFICULDADE_RESPIRATORIA_GRAVE",
                        "SATURACAO_BAIXA", "CHOQUE", "FREQUENCIA_CARDIACA_CRITICA"},
                {"Neurológico",
                        "SUSPEITA_AVC", "CONVULSAO", "ALTERACAO_CONSCIENCIA",
                        "PARALISIA_SUBITA", "CEFALEIA_EXPLOSIVA", "DESMAIO_RECENTE"},
                {"Dor",
                        "DOR_TORACICA", "DOR_ABDOMINAL_INTENSA",
                        "DOR_MUITO_INTENSA", "DOR_MODERADA", "DOR_LEVE"},
                {"Sangramento",
                        "SANGRAMENTO_ABUNDANTE", "SANGRAMENTO_MODERADO", "SANGRAMENTO_LEVE"},
                {"Trauma, Fraturas e Queimaduras",
                        "TRAUMA_CRANIANO_GRAVE", "TRAUMA_CRANIANO_LEVE",
                        "FRATURA_EXPOSTA", "FRATURA_FECHADA",
                        "QUEIMADURA_GRAVE", "QUEIMADURA_LEVE"},
                {"Febre",
                        "FEBRE_ALTA", "FEBRE_MODERADA"},
                {"Alergias e Intoxicações",
                        "ANAFILAXIA", "OVERDOSE"},
                {"Psiquiátrico",
                        "AGITACAO_PSICOMOTORA", "TENTATIVA_SUICIDIO"},
                {"Obstétrico",
                        "GESTANTE_COM_SANGRAMENTO", "GESTANTE"},
                {"Violência",
                        "VITIMA_VIOLENCIA"},
                {"Sintomas Gerais",
                        "DESIDRATACAO", "VOMITOS", "FALTA_DE_AR_LEVE",
                        "DIARREIA", "TONTURA", "TOSSE_PERSISTENTE",
                        "MAL_ESTAR_GERAL", "DOR_DE_GARGANTA", "CONGESTAO_NASAL"}
        };

        return MenuUtil.selecionarCondicoes(
                categorias,
                "Selecione as condições atuais do paciente");
    }

    // ====================================================================
    // ATENDER PRÓXIMO
    // ====================================================================

    private static void atenderProximo() {
        Paciente proximo = PacienteService.lerProximoFila();
        if (proximo == null) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                    "A fila de espera está vazia. Nenhum paciente para atender.",
                    "Fila Vazia", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] opcoesAtender = {"Sim", "Não"};
        int confirmar = JOptionPane.showOptionDialog(MenuUtil.getFrame(),
                "Próximo paciente:\n\n" + MenuUtil.formatarPacienteDetalhado(proximo) +
                        "\n\nDeseja atender este paciente agora?",
                "Confirmar Atendimento",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, opcoesAtender, opcoesAtender[0]);

        if (confirmar == 0) {
            Paciente atendido = PacienteService.atenderProximoFila();
            assert atendido != null;
            JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                    "Paciente " + atendido.getNome() + " foi removido da fila e está sendo atendido.",
                    "✅ Atendimento Iniciado", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
