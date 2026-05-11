package menu;

import entidade.HistoricoClinico;
import entidade.Paciente;
import servico.PacienteService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * ========================================================================
 * TELA DE FILA E HISTÓRICO — Visualizar fila de espera e pacientes
 * atendidos, com botão de edição ao lado de cada paciente.
 * ========================================================================
 */
public class TelaFilaHistorico {

    private TelaFilaHistorico() {
    }

    // ====================================================================
    // SUBMENU
    // ====================================================================

    static void exibir() {
        while (true) {
            String[] opcoes = {"Ver Fila de Espera", "Ver Pacientes Atendidos", "Voltar"};
            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Módulo de Fila e Histórico:",
                    "📋 Fila e Histórico",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> verFilaEspera();
                case 1 -> verAtendidos();
                default -> {
                    return;
                }
            }
        }
    }

    // ====================================================================
    // FILA DE ESPERA (com botão Editar por paciente)
    // ====================================================================

    private static void verFilaEspera() {
        while (true) {
            List<Paciente> fila = PacienteService.listarPacientesFila();
            if (fila.isEmpty()) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "A fila de espera está vazia.",
                        "Fila de Espera", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Paciente selecionado = exibirListaComBotaoEditar(fila,
                    "📋 Fila de Espera (" + fila.size() + " pacientes)", true);

            if (selecionado == null) return; // fechou sem editar
            editarPaciente(selecionado);
        }
    }

    // ====================================================================
    // PACIENTES ATENDIDOS (com botão Editar por paciente)
    // ====================================================================

    private static void verAtendidos() {
        while (true) {
            List<Paciente> atendidos = PacienteService.listarPacientesAtendidos();
            if (atendidos.isEmpty()) {
                JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                        "Nenhum paciente foi atendido ainda nesta sessão.",
                        "Pacientes Atendidos", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Paciente selecionado = exibirListaComBotaoEditar(atendidos,
                    "✅ Atendidos (" + atendidos.size() + ")", false);

            if (selecionado == null) return;
            editarPaciente(selecionado);
        }
    }

    // ====================================================================
    // PAINEL DE LISTA COM BOTÃO "EDITAR" POR LINHA
    // ====================================================================

    /**
     * Exibe uma lista de pacientes em um painel rolável, com um botão "Editar"
     * ao lado de cada paciente. Retorna o paciente selecionado para edição,
     * ou null se o usuário apenas fechou a janela.
     *
     * @param pacientes      Lista de pacientes a exibir
     * @param titulo         Título da janela
     * @param mostrarPosicao Se true, mostra "1º —", "2º —" etc.
     * @return Paciente selecionado para edição, ou null
     */
    private static Paciente exibirListaComBotaoEditar(
            List<Paciente> pacientes, String titulo, boolean mostrarPosicao) {

        final Paciente[] selecionado = {null};

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        Font fonteInfo = new Font("SansSerif", Font.PLAIN, 12);
        Font fonteBotao = new Font("SansSerif", Font.BOLD, 11);

        for (int i = 0; i < pacientes.size(); i++) {
            Paciente p = pacientes.get(i);

            // Linha do paciente
            JPanel row = new JPanel(new BorderLayout(12, 0));
            row.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)
            ));
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

            // Texto do paciente
            StringBuilder info = new StringBuilder();
            if (mostrarPosicao) {
                info.append(i + 1).append("º — ");
            }
            info.append(p.getNome());

            if (p.getCpf() != null && !p.getCpf().isEmpty()) {
                info.append("   |   CPF: ").append(p.getCpf());
            } else {
                info.append("   |   ID: ").append(p.getId());
            }

            info.append("   |   Score: ").append(p.getScorePrioridade());

            if (mostrarPosicao && p.getChegada() != null) {
                info.append("   |   Chegada: ").append(
                        p.getChegada().format(DateTimeFormatter.ofPattern("HH:mm")));
            }

            JLabel label = new JLabel(info.toString());
            label.setFont(fonteInfo);
            row.add(label, BorderLayout.CENTER);

            // Botão Editar
            JButton btnEditar = new JButton("Editar");
            btnEditar.setFont(fonteBotao);
            btnEditar.setFocusPainted(false);
            btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            btnEditar.addActionListener(_ -> {
                selecionado[0] = p;
                // Fecha o diálogo do JOptionPane
                Window janela = SwingUtilities.getWindowAncestor(btnEditar);
                if (janela != null) janela.dispose();
            });
            row.add(btnEditar, BorderLayout.EAST);

            mainPanel.add(row);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setPreferredSize(new Dimension(680, 400));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JOptionPane.showMessageDialog(
                MenuUtil.getFrame(), scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);

        return selecionado[0];
    }

    // ====================================================================
    // EDITAR / CADASTRAR PACIENTE (com dados pré-preenchidos)
    // ====================================================================

    private static void editarPaciente(Paciente paciente) {
        Object[] dados = MenuUtil.pedirDadosBasicos(
                "Editar Paciente",
                paciente.getCpf(),
                paciente.getNome(),
                paciente.getSexo(),
                paciente.temCadastro() ? paciente.getDataNascimento() : null,
                paciente);
        if (dados == null) return;

        String cpf = (String) dados[0];
        String nome = (String) dados[1];
        char sexo = (char) dados[2];
        LocalDate dataNasc = (LocalDate) dados[3];

        List<HistoricoClinico> historicoAtual = paciente.getHistoricoClinico();
        List<HistoricoClinico> historico = TelaCadastro.selecionarHistoricoClinico(
                historicoAtual.isEmpty() ? null : historicoAtual);
        if (historico == null) return;

        PacienteService.atualizarPaciente(paciente, cpf, nome, sexo, dataNasc, historico);

        JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                "Cadastro atualizado com sucesso!\n\nNome: " + nome + "\nCPF: " + cpf,
                "✅ Cadastro Atualizado", JOptionPane.INFORMATION_MESSAGE);
    }
}
