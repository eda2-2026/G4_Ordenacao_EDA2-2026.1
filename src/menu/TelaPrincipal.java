package menu;

import javax.swing.*;

/**
 * ========================================================================
 * TELA PRINCIPAL — Menu principal do sistema de triagem hospitalar.
 * Delega para as telas especializadas de cada módulo.
 * ========================================================================
 */
public class TelaPrincipal {

    private TelaPrincipal() {}

    public static void iniciar() {
        MenuUtil.inicializarFrame();

        while (true) {
            String[] opcoes = {"Atendimento", "Cadastro e Consultas", "Fila e Histórico", "Sair"};

            int escolha = JOptionPane.showOptionDialog(
                    MenuUtil.getFrame(),
                    "Selecione um módulo para continuar:",
                    "🏥 Triagem Hospitalar — Menu Principal",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null, opcoes, opcoes[0]
            );

            switch (escolha) {
                case 0 -> TelaAtendimento.exibir();
                case 1 -> TelaCadastro.exibir();
                case 2 -> TelaFilaHistorico.exibir();
                default -> {
                    JOptionPane.showMessageDialog(MenuUtil.getFrame(),
                            "Sistema encerrado. Até logo!",
                            "Encerramento", JOptionPane.INFORMATION_MESSAGE);
                    MenuUtil.destruirFrame();
                    return;
                }
            }
        }
    }
}
