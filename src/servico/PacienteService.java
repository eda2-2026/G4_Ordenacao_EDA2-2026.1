package servico;

import entidade.HistoricoClinico;
import entidade.Paciente;
import entidade.CondicaoAtual;
import estrutura.MaxHeap;
import estrutura.BuscaBinaria;
import estrutura.InsertionSort;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PacienteService {
    private static final List<Paciente> pacientesFila = new ArrayList<>();
    private static final List<Paciente> pacientesAtendidos = new ArrayList<>();
    private static List<Paciente> pacientesCadastrados = new ArrayList<>();
    private static int contadorId = 0;

    private PacienteService() {}

    public static Paciente criarPacienteTemporario(String nome, int idade, char sexo) {
        int id = ++contadorId;
        return new Paciente(id, nome, idade, sexo);
    }

    public static void cadastrarPaciente(String cpf, String nome, char sexo, LocalDate dataNascimento,
                                         List<HistoricoClinico> historicoClinico) {
        Paciente paciente = new Paciente(cpf, nome, sexo, dataNascimento, historicoClinico);
        InsertionSort.inserirOrdenado(pacientesCadastrados, paciente);
        PersistenciaService.salvar(pacientesCadastrados);
    }

    public static void cadastrarPaciente(Paciente paciente) {
        paciente.setTemCadastro(true);
        InsertionSort.inserirOrdenado(pacientesCadastrados, paciente);
        PersistenciaService.salvar(pacientesCadastrados);
    }

    public static void adicionarPacienteFila(Paciente paciente, List<CondicaoAtual> condicoesAtuais) {
        if (paciente.getId() == 0)
            paciente.setId(++contadorId);

        paciente.setChegada(LocalDateTime.now());
        paciente.setCondicoesAtuais(condicoesAtuais);

        // MATEMÁTICA DE TRIAGEM (Soma dos pesos dos 3 enums)
        int scoreCalculado = 0;

        for (CondicaoAtual s : condicoesAtuais) {
            scoreCalculado += s.getPeso();
        }

        for (HistoricoClinico h : paciente.getHistoricoClinico()) {
            scoreCalculado += h.getPeso();
        }

        paciente.setScorePrioridade(scoreCalculado);
        MaxHeap.inserirPacienteFilaPrioridade(paciente, pacientesFila);
    }

    public static Paciente atenderProximoFila() {

        Paciente paciente = MaxHeap.removerPacienteFilaPrioridade(pacientesFila);
        if (paciente == null) return null;
        pacientesAtendidos.add(paciente);
        return paciente;
    }

    public static Paciente lerProximoFila() {
        if (pacientesFila.isEmpty()) return null;
        return pacientesFila.getFirst();
    }

    public static void atualizarPaciente(Paciente paciente, String cpf, String nome, char sexo, LocalDate dataNascimento,
                                         List<HistoricoClinico> historicoClinico) {
        boolean cpfMudou = !paciente.getCpf().equals(cpf);

        paciente.setCpf(cpf);
        paciente.setNome(nome);
        paciente.setSexo(sexo);
        paciente.setDataNascimento(dataNascimento);
        paciente.setHistoricoClinico(historicoClinico);

        if (paciente.temCadastro()) {
            if (cpfMudou)
                InsertionSort.ordenar(pacientesCadastrados);
            PersistenciaService.salvar(pacientesCadastrados);
            return;
        }

        paciente.setIdade(0);
        cadastrarPaciente(paciente);
    }

    public static void recuperarPacientesCadastrados() {
        pacientesCadastrados = PersistenciaService.carregar();
        InsertionSort.ordenar(pacientesCadastrados);
    }

    public static Paciente buscarCliente(String cpf) {
        return BuscaBinaria.porCpf(pacientesCadastrados, cpf);
    }

    public static List<Paciente> listarPacientesAtendidos() {
        return new ArrayList<>(pacientesAtendidos);
    }

    public static List<Paciente> listarPacientesFila() {
        return MaxHeap.ListarPacientes(pacientesFila);
    }

    public static List<Paciente> listarPacientesCadastrados() {
        return new ArrayList<>(pacientesCadastrados);
    }

}