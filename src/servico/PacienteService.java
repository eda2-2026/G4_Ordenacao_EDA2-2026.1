package servico;

import entidade.Condicao;
import entidade.Paciente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class PacienteService {
    private static List<Paciente> pacientesFila = new ArrayList<>(),
                                  pacientesCadastrados = new ArrayList<>();
    private static int contadorId = 0;

    private PacienteService() {}

    public static Paciente cadastrarPaciente(String nome, int idade, char sexo) {
        // Cadastro rápido
        Paciente paciente = new Paciente("", nome, idade, sexo, null, -1, new ArrayList<>(), false);
        inserirOrdenado(paciente);
        return paciente;
    }

    public static Paciente cadastrarPaciente(String cpf, String nome, char sexo, LocalDate dataNascimento, List<Condicao> historicoCondicoes) {
        // Cadastro completo
        int idade = calcularIdade(dataNascimento);
        Paciente paciente = new Paciente("", nome, idade, sexo, dataNascimento, -1, historicoCondicoes, true);
        inserirOrdenado(paciente);
        return paciente;
    }

    public static void adicionarPacienteFila(Paciente paciente, List<Condicao> condicoesAtuais, int gravidade) {
        paciente.setId(++contadorId);
        paciente.setChegada(LocalDateTime.now());
        paciente.setCondicoesAtuais(condicoesAtuais);
        paciente.setGravidade(gravidade);
        pacientesFila.add(paciente); // TODO: Implementar MaxHeap
    }

    private static void inserirOrdenado(Paciente paciente) {
        // Insertion sort
        pacientesCadastrados.add(paciente);
        int i = pacientesCadastrados.size() - 1;
        while (i > 0) {
            Paciente atual = pacientesCadastrados.get(i);
            Paciente anterior = pacientesCadastrados.get(i - 1);
            if (atual.getCpf().compareTo(anterior.getCpf()) < 0) {
                pacientesCadastrados.set(i, anterior);
                pacientesCadastrados.set(i - 1, atual);
                i--;
            } else {
                break;
            }
        }
    }

    public static List<Paciente> listarPacientesFila() {
        return pacientesFila;
    }

    public static List<Paciente> listarPacientesCadastrados() {
        return pacientesCadastrados;
    }
    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}