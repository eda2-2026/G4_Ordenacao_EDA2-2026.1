import menu.TelaPrincipal;
import servico.PacienteService;

public class Main {
    public static void main(String[] args) {
        // Carrega a base de pacientes do CSV e ordena por CPF (Insertion Sort)
        PacienteService.recuperarPacientesCadastrados();

        // Inicia a interface gráfica (JOptionPane)
        TelaPrincipal.iniciar();
    }
}
