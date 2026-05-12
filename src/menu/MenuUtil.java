package menu;

import entidade.CondicaoAtual;
import entidade.Paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * ========================================================================
 * MENU UTIL — Utilitários compartilhados por todas as telas do sistema.
 * Gerencia o JFrame (ícone na taskbar), inputs e exibição de dados.
 * ========================================================================
 */
public class MenuUtil {

    static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static JFrame frame;

    private MenuUtil() {
    }

    // ====================================================================
    // GERENCIAMENTO DO FRAME (ÍCONE NA TASKBAR)
    // ====================================================================

    static JFrame getFrame() {
        return frame;
    }

    static void inicializarFrame() {
        frame = criarFrameComIcone();
    }

    static void destruirFrame() {
        if (frame != null) frame.dispose();
    }

    private static JFrame criarFrameComIcone() {
        JFrame f = new JFrame("Triagem Hospitalar");
        f.setIconImage(gerarIconeHospitalar());
        f.setUndecorated(true);
        f.setSize(0, 0);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        f.setState(JFrame.ICONIFIED);
        f.setState(JFrame.NORMAL);
        f.setVisible(true);
        return f;
    }

    private static Image gerarIconeHospitalar() {
        int size = 64;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillOval(2, 2, size - 4, size - 4);

        g.setColor(new Color(200, 0, 0));
        g.setStroke(new BasicStroke(3));
        g.drawOval(2, 2, size - 4, size - 4);

        g.setColor(new Color(220, 30, 30));
        int espessura = 14, margem = 14;
        g.fillRoundRect(margem, (size - espessura) / 2, size - 2 * margem, espessura, 4, 4);
        g.fillRoundRect((size - espessura) / 2, margem, espessura, size - 2 * margem, 4, 4);

        g.dispose();
        return img;
    }

    // ====================================================================
    // INPUTS
    // ====================================================================

    /**
     * Pede um CPF ao usuário. Retorna a string somente com dígitos,
     * ou string vazia se o campo foi deixado em branco.
     * Retorna null apenas se o diálogo foi cancelado (X ou Cancel).
     */
    static String pedirCpf(String mensagem) {
        String cpf = JOptionPane.showInputDialog(frame, mensagem, "CPF", JOptionPane.PLAIN_MESSAGE);
        if (cpf == null) return null;
        return cpf.replaceAll("[^0-9]", "");
    }

    static char pedirSexo() {
        String[] opcoes = {"Masculino", "Feminino"};
        int escolha = JOptionPane.showOptionDialog(
                frame, "Sexo do paciente:", "Sexo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opcoes, opcoes[0]);
        if (escolha == 0) return 'M';
        if (escolha == 1) return 'F';
        return 0;
    }

    // ====================================================================
    // EXIBIÇÃO
    // ====================================================================

    static String formatarPacienteDetalhado(Paciente p) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome: ").append(p.getNome()).append("\n");

        if (p.temCadastro()) {
            sb.append("CPF: ").append(p.getCpf()).append("\n");
        } else if (p.getCpf() != null && !p.getCpf().isEmpty()) {
            sb.append("CPF: ").append(p.getCpf()).append(" (sem cadastro completo)\n");
        } else {
            sb.append("ID Temporário: ").append(p.getId()).append("\n");
        }

        sb.append("Idade: ").append(p.getIdade()).append(" anos\n");
        sb.append("Score de Prioridade: ").append(p.getScorePrioridade()).append("\n");

        if (!p.getCondicoesAtuais().isEmpty()) {
            sb.append("\nCondições Atuais:\n");
            for (CondicaoAtual c : p.getCondicoesAtuais()) {
                sb.append("  • ").append(c.getDescricao()).append("\n");
            }
        }

        return sb.toString();
    }

    static void exibirTextoRolavel(String texto, String titulo) {
        JTextArea textArea = new JTextArea(texto);
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(600, 400));
        JOptionPane.showMessageDialog(frame, scrollPane, titulo, JOptionPane.PLAIN_MESSAGE);
    }

    // ====================================================================
    // PAINEL UNIFICADO DE DADOS DO PACIENTE
    // ====================================================================

    /**
     * Exibe um painel único com CPF, Nome, Sexo e Data de Nascimento.
     * Valida todos os campos e retorna apenas quando tudo estiver correto.
     * Erros mostram mensagem e reexibem o painel (com valores preservados).
     *
     * @param titulo             Título da janela
     * @param cpfInicial         CPF pré-preenchido (pode ser null)
     * @param nomeInicial        Nome pré-preenchido (pode ser null)
     * @param sexoInicial        Sexo pré-selecionado ('M', 'F' ou '\0')
     * @param dataNascInicial    Data pré-preenchida (pode ser null)
     * @param ignorarNaValidacao Paciente sendo editado (ignora na checagem de duplicidade de CPF)
     * @return Object[]{String cpf, String nome, char sexo, LocalDate dataNasc} ou null se cancelou
     */
    static Object[] pedirDadosBasicos(String titulo, String cpfInicial, String nomeInicial,
                                      char sexoInicial, LocalDate dataNascInicial,
                                      Paciente ignorarNaValidacao) {

        JTextField campoCpf = new JTextField(cpfInicial != null ? cpfInicial : "", 15);
        JTextField campoNome = new JTextField(nomeInicial != null ? nomeInicial : "", 25);
        JTextField campoData = new JTextField(
                dataNascInicial != null ? dataNascInicial.format(FMT) : "", 12);

        JRadioButton rbMasc = new JRadioButton("Masculino", sexoInicial != 'F');
        JRadioButton rbFem = new JRadioButton("Feminino", sexoInicial == 'F');
        ButtonGroup grupoSexo = new ButtonGroup();
        grupoSexo.add(rbMasc);
        grupoSexo.add(rbFem);
        JPanel painelSexo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelSexo.add(rbMasc);
        painelSexo.add(rbFem);

        JPanel painel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        painel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campoCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campoNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        painel.add(new JLabel("Sexo:"), gbc);
        gbc.gridx = 1;
        painel.add(painelSexo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        painel.add(new JLabel("Data Nasc. (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campoData, gbc);

        while (true) {
            String[] opcoesPainel = {"Confirmar", "Cancelar"};
            int result = JOptionPane.showOptionDialog(frame, painel, titulo,
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opcoesPainel, opcoesPainel[0]);
            if (result != 0) return null;

            String cpf = campoCpf.getText().replaceAll("[^0-9]", "");
            String nome = campoNome.getText().trim();
            String dataStr = campoData.getText().trim();
            char sexo = rbFem.isSelected() ? 'F' : 'M';

            if (cpf.length() != 11) {
                JOptionPane.showMessageDialog(frame,
                        "CPF deve ter exatamente 11 dígitos.", "Erro", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            Paciente existente = servico.PacienteService.buscarCliente(cpf);
            if (existente != null && existente != ignorarNaValidacao) {
                JOptionPane.showMessageDialog(frame,
                        "Este CPF já pertence a outro paciente: " + existente.getNome(),
                        "CPF Duplicado", JOptionPane.WARNING_MESSAGE);
                continue;
            }

            if (nome.isBlank()) {
                JOptionPane.showMessageDialog(frame,
                        "Nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            LocalDate dataNasc;
            try {
                dataNasc = LocalDate.parse(dataStr, FMT);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(frame,
                        "Data inválida. Use o formato dd/MM/yyyy.", "Erro", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            return new Object[]{cpf, nome, sexo, dataNasc};
        }
    }

    // ====================================================================
    // SELEÇÃO COM CHECKBOXES POR CATEGORIA
    // ====================================================================

    /**
     * Exibe checkboxes para CondicaoAtual (sem pré-seleção).
     */
    static List<entidade.CondicaoAtual> selecionarCondicoes(
            String[][] categorias, String titulo) {
        return checkboxPanel(categorias, entidade.CondicaoAtual.class, titulo, null);
    }

    /**
     * Exibe checkboxes para HistoricoClinico, com pré-seleção opcional.
     */
    static List<entidade.HistoricoClinico> selecionarHistorico(
            String[][] categorias, String titulo, List<entidade.HistoricoClinico> preSelected) {
        return checkboxPanel(categorias, entidade.HistoricoClinico.class, titulo, preSelected);
    }


    /**
     * Implementação genérica compartilhada — use as facades nomeadas acima.
     */
    private static <E extends Enum<E>> List<E> checkboxPanel(
            String[][] categorias, Class<E> enumClass, String titulo, List<E> preSelected) {

        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        List<JCheckBox> checkboxes = new ArrayList<>();
        List<E> enumsCorrespondentes = new ArrayList<>();

        Font fonteCategoria = new Font("SansSerif", Font.BOLD, 13);
        Font fonteItem = new Font("SansSerif", Font.PLAIN, 12);
        Color corCategoria = new Color(30, 60, 120);

        for (int cat = 0; cat < categorias.length; cat++) {
            String[] grupo = categorias[cat];
            String nomeCategoria = grupo[0];

            if (cat > 0) {
                painel.add(Box.createVerticalStrut(6));
                JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
                sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
                painel.add(sep);
                painel.add(Box.createVerticalStrut(4));
            }

            JLabel label = new JLabel("■  " + nomeCategoria);
            label.setFont(fonteCategoria);
            label.setForeground(corCategoria);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            label.setBorder(BorderFactory.createEmptyBorder(4, 0, 2, 0));
            painel.add(label);

            for (int i = 1; i < grupo.length; i++) {
                E enumVal = Enum.valueOf(enumClass, grupo[i]);
                String descricao = obterDescricao(enumVal);

                JCheckBox cb = new JCheckBox("     " + descricao);
                cb.setFont(fonteItem);
                cb.setAlignmentX(Component.LEFT_ALIGNMENT);

                // Pré-seleciona se estiver na lista
                if (preSelected != null && preSelected.contains(enumVal)) {
                    cb.setSelected(true);
                }

                painel.add(cb);

                checkboxes.add(cb);
                enumsCorrespondentes.add(enumVal);
            }
        }

        JScrollPane scrollPane = new JScrollPane(painel);
        scrollPane.setPreferredSize(new Dimension(480, 420));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        String[] opcoesCheckbox = {"OK", "Cancelar"};
        int resultado = JOptionPane.showOptionDialog(
                frame, scrollPane, titulo,
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opcoesCheckbox, opcoesCheckbox[0]
        );

        if (resultado != 0) return null;

        List<E> selecionados = new ArrayList<>();
        for (int i = 0; i < checkboxes.size(); i++) {
            if (checkboxes.get(i).isSelected()) {
                selecionados.add(enumsCorrespondentes.get(i));
            }
        }
        return selecionados;
    }

    private static String obterDescricao(Enum<?> enumVal) {
        try {
            return (String) enumVal.getClass().getMethod("getDescricao").invoke(enumVal);
        } catch (Exception e) {
            return enumVal.name();
        }
    }
}
