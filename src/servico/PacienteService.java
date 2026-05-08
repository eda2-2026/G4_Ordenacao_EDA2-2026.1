package servico;

import entidade.Condicao;
import entidade.Paciente;
import estrutura.MaxHeap;

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

    public static void cadastrarPaciente(String cpf, String nome, char sexo, LocalDate dataNascimento, List<Condicao> historicoCondicoes) {
        Paciente paciente = new Paciente(cpf, nome, sexo, dataNascimento, historicoCondicoes);
        inserirOrdenado(paciente);
        PersistenciaService.salvar(pacientesCadastrados);
    }

    public static void cadastrarPaciente(Paciente paciente) {
        paciente.setTemCadastro(true);
        inserirOrdenado(paciente);
        PersistenciaService.salvar(pacientesCadastrados);
    }

    public static void adicionarPacienteFila(Paciente paciente, List<Condicao> condicoesAtuais, int scorePrioridade) {
        if (paciente.getId() == 0)
            paciente.setId(++contadorId);
        paciente.setChegada(LocalDateTime.now());
        paciente.setCondicoesAtuais(condicoesAtuais);
        paciente.setScorePrioridade(scorePrioridade);
        MaxHeap.inserirPacienteFilaPrioridade(paciente, pacientesFila);
    }

    public static Paciente atenderProximoFila() {
        Paciente paciente = MaxHeap.removerPacienteFilaPrioridade(pacientesFila);
        if (paciente != null)
            pacientesAtendidos.add(paciente);
        return paciente;
    }

    public static Paciente lerProximoFila() {
        return pacientesFila.getFirst();
    }

    public static void atualizarPaciente(Paciente paciente, String cpf, String nome, char sexo, LocalDate dataNascimento, List<Condicao> historicoCondicoes) {
        boolean cpfMudou = !paciente.getCpf().equals(cpf);

        paciente.setCpf(cpf);
        paciente.setNome(nome);
        paciente.setSexo(sexo);
        paciente.setDataNascimento(dataNascimento);
        paciente.setHistoricoCondicoes(historicoCondicoes);

        if (paciente.temCadastro()) {
            if (cpfMudou)
                ordenar();
            PersistenciaService.salvar(pacientesCadastrados);
            return;
        }

        paciente.setIdade(0);
        cadastrarPaciente(paciente);
    }

    public static void recuperarPacientesCadastrados() {
        pacientesCadastrados = PersistenciaService.carregar();
        ordenar();
    }

    private static void inserirOrdenado(Paciente paciente) {
        // Insertion sort incremental
        pacientesCadastrados.add(paciente);
        int i = pacientesCadastrados.size() - 1;

        while (i > 0) {
            Paciente atual = pacientesCadastrados.get(i);
            Paciente anterior = pacientesCadastrados.get(i - 1);

            if (atual.getCpf().compareTo(anterior.getCpf()) < 0) {
                pacientesCadastrados.set(i, anterior);
                pacientesCadastrados.set(i - 1, atual);
                i--;

            } else break;
        }
    }

    private static void ordenar() {
        // Insertion sort
        int n = pacientesCadastrados.size();
        for (int i = 1; i < n; i++) {
            Paciente atual = pacientesCadastrados.get(i);
            int j = i - 1;
            while (j >= 0 && pacientesCadastrados.get(j).getCpf().compareTo(atual.getCpf()) > 0) {
                pacientesCadastrados.set(j + 1, pacientesCadastrados.get(j));
                j--;
            }
            pacientesCadastrados.set(j + 1, atual);
        }
    }

    public static Paciente buscarCliente(String cpf) {
        // Busca binária
        int inicio = 0;
        int fim = pacientesCadastrados.size() - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            String cpfMeio = pacientesCadastrados.get(meio).getCpf();
            int comparacao = cpfMeio.compareTo(cpf);

            if (comparacao == 0)
                return pacientesCadastrados.get(meio);
            else if (comparacao < 0)
                inicio = meio + 1;
            else
                fim = meio - 1;
        }

        return null;
    }

    public static List<Paciente> listarPacientesAtendidos() {
        return new ArrayList<>(pacientesAtendidos);
    }

    public static List<Paciente> listarPacientesFila() {
        // Não está ordenada. Apenas o primeiro elemento está na posição correta.
        return new ArrayList<>(pacientesFila);
    }

    public static List<Paciente> listarPacientesCadastrados() {
        return new ArrayList<>(pacientesCadastrados);
    }

}