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

    public static Paciente criarPacienteTemporario(String nome, int idade, char sexo) {
        int id = ++contadorId;
        return new Paciente(id, nome, idade, sexo);
    }

    public static Paciente cadastrarPaciente(String cpf, String nome, char sexo, LocalDate dataNascimento, List<Condicao> historicoCondicoes) {
        int idade = calcularIdade(dataNascimento);
        Paciente paciente = new Paciente(cpf, nome, idade, sexo, dataNascimento, historicoCondicoes);
        inserirOrdenado(paciente);
        return paciente;
    }

    public static void adicionarPacienteFila(Paciente paciente, List<Condicao> condicoesAtuais, int scorePrioridade) {
        if (paciente.getId() == 0)
            paciente.setId(++contadorId);
        paciente.setChegada(LocalDateTime.now());
        paciente.setCondicoesAtuais(condicoesAtuais);
        paciente.setScorePrioridade(scorePrioridade);
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
        return new ArrayList<>(pacientesFila);
    }

    public static List<Paciente> listarPacientesCadastrados() {
        return new ArrayList<>(pacientesCadastrados);
    }
    private static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}