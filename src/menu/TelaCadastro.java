package menu;

import entidade.HistoricoClinico;
import entidade.Paciente;
import servico.PacienteService;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

/**
 * ========================================================================
 * TELA DE CADASTRO E CONSULTAS — Cadastrar, buscar e listar pacientes.
 * ========================================================================
 */
public class TelaCadastro {

    private TelaCadastro() {
    }

    // ====================================================================
    // SUBMENU
    // ====================================================================

    static void exibir() {
        while (true) {
            String[] opcoes = {"Cadastrar Paciente", "Buscar Paciente por CPF",
                    "Ver Pacientes Cadastrados", "Voltar"};
            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Módulo de Cadastro e Consultas:",
                    "📂 Cadastro e Consultas",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> cadastrarPaciente();
                case 1 -> buscarPaciente();
                case 2 -> verCadastrados();
                default -> {
                    return;
                }
            }
        }
    }

    // ====================================================================
    // CADASTRAR PACIENTE
    // ====================================================================

    private static void cadastrarPaciente() {
        Object[] dados = MenuUtil.pedirDadosBasicos(
                "Cadastro de Paciente", null, null, '\0', null, null);
        if (dados == null) return;

        String cpf = (String) dados[0];
        String nome = (String) dados[1];
        char sexo = (char) dados[2];
        LocalDate dataNasc = (LocalDate) dados[3];

        List<HistoricoClinico> historico = selecionarHistoricoClinico();
        if (historico == null) return;

        PacienteService.cadastrarPaciente(cpf, nome, sexo, dataNasc, historico);

        JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                "Paciente cadastrado com sucesso!\n\nNome: " + nome + "\nCPF: " + cpf,
                "✅ Cadastro Realizado", JOptionPane.INFORMATION_MESSAGE);
    }

    // ====================================================================
    // SELEÇÃO DE HISTÓRICO CLÍNICO
    // ====================================================================

    static List<HistoricoClinico> selecionarHistoricoClinico() {
        return selecionarHistoricoClinico(null);
    }

    static List<HistoricoClinico> selecionarHistoricoClinico(List<HistoricoClinico> preSelected) {
        String[][] categorias = {
                {"Doenças Cardiovasculares",
                        "DOENCA_CARDIACA", "HISTORICO_INFARTO", "HISTORICO_AVC", "HIPERTENSAO"},
                {"Doenças Metabólicas e Sistêmicas",
                        "DIABETES", "INSUFICIENCIA_RENAL", "DOENCA_AUTOIMUNE"},
                {"Doenças Respiratórias",
                        "DOENCA_PULMONAR"},
                {"Condições Neurológicas",
                        "EPILEPSIA"},
                {"Imunidade e Câncer",
                        "CANCER", "IMUNOSSUPRESSAO"},
                {"Alergias e Coagulação",
                        "ALERGIA_GRAVE", "DISTURBIO_COAGULACAO"},
                {"Psiquiatria e Dependência",
                        "TRANSTORNO_PSIQUIATRICO", "HISTORICO_SUICIDIO", "USO_DROGAS"},
                {"Eventos Recentes",
                        "CIRURGIA_RECENTE", "INTERNACAO_RECENTE"},
                {"Deficiências e Condições Especiais",
                        "AUTISMO", "DEFICIENCIA_INTELECTUAL", "DEFICIENCIA_FISICA",
                        "DEFICIENCIA_VISUAL", "DEFICIENCIA_AUDITIVA"}
        };

        return MenuUtil.selecionarHistorico(
                categorias,
                "Selecione o histórico clínico do paciente", preSelected);
    }

    // ====================================================================
    // BUSCAR PACIENTE POR CPF
    // ====================================================================

    private static void buscarPaciente() {
        String cpf = MenuUtil.pedirCpf("Digite o CPF para buscar:");
        if (cpf == null || cpf.isEmpty()) return;

        Paciente p = PacienteService.buscarCliente(cpf);
        if (p == null) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                    "Nenhum paciente encontrado com o CPF: " + cpf,
                    "Resultado da Busca", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Paciente encontrado!\n\n");
        sb.append("Nome: ").append(p.getNome()).append("\n");
        sb.append("CPF: ").append(p.getCpf()).append("\n");
        sb.append("Sexo: ").append(p.getSexo() == 'M' ? "Masculino" : "Feminino").append("\n");
        sb.append("Data de Nascimento: ").append(p.getDataNascimento().format(MenuUtil.FMT)).append("\n");
        sb.append("Idade: ").append(p.getIdade()).append(" anos\n\n");

        List<HistoricoClinico> hist = p.getHistoricoClinico();
        if (hist.isEmpty()) {
            sb.append("Histórico Clínico: Nenhum registrado\n");
        } else {
            sb.append("Histórico Clínico:\n");
            for (HistoricoClinico h : hist) {
                sb.append("  • ").append(h.getDescricao()).append("\n");
            }
        }

        String[] opcoes = {"Editar", "Fechar"};
        int escolha = JOptionPane.showOptionDialog(MenuUtil.getFrame(), sb.toString(),
                "🔍 Resultado da Busca Binária",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, opcoes, opcoes[1]);

        if (escolha == 0) {
            editarPacienteCadastrado(p);
        }
    }

    // ====================================================================
    // EDITAR PACIENTE CADASTRADO (via busca)
    // ====================================================================

    private static void editarPacienteCadastrado(Paciente paciente) {
        Object[] dados = MenuUtil.pedirDadosBasicos(
                "Editar Paciente",
                paciente.getCpf(),
                paciente.getNome(),
                paciente.getSexo(),
                paciente.getDataNascimento(),
                paciente);
        if (dados == null) return;

        String cpf = (String) dados[0];
        String nome = (String) dados[1];
        char sexo = (char) dados[2];
        java.time.LocalDate dataNasc = (java.time.LocalDate) dados[3];

        List<HistoricoClinico> historico = selecionarHistoricoClinico(
                paciente.getHistoricoClinico().isEmpty() ? null : paciente.getHistoricoClinico());
        if (historico == null) return;

        PacienteService.atualizarPaciente(paciente, cpf, nome, sexo, dataNasc, historico);

        JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                "Cadastro atualizado com sucesso!\n\nNome: " + nome + "\nCPF: " + cpf,
                "✅ Cadastro Atualizado", JOptionPane.INFORMATION_MESSAGE);
    }

    // ====================================================================
    // VER PACIENTES CADASTRADOS (ordenados por CPF — Insertion Sort)
    // ====================================================================

    private static void verCadastrados() {
        List<Paciente> cadastrados = PacienteService.listarPacientesCadastrados();
        if (cadastrados.isEmpty()) {
            JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                    "Nenhum paciente cadastrado na base de dados.",
                    "Base de Dados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Pacientes Cadastrados — Ordenados por CPF (Insertion Sort)\n");
        sb.append("═══════════════════════════════════════════════════════════\n\n");

        for (Paciente p : cadastrados) {
            sb.append("CPF: ").append(p.getCpf());
            sb.append("  |  ").append(p.getNome());
            sb.append("  |  Sexo: ").append(p.getSexo());
            sb.append("  |  Nasc: ").append(p.getDataNascimento().format(MenuUtil.FMT));
            sb.append("  |  Idade: ").append(p.getIdade());
            sb.append("\n");
        }

        MenuUtil.exibirTextoRolavel(sb.toString(), "📂 Cadastrados (" + cadastrados.size() + ")");
    }
}
