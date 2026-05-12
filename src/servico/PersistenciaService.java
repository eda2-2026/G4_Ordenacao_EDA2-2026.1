package servico;

import entidade.HistoricoClinico;
import entidade.Paciente;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaService {

    private static final String ARQUIVO = "pacientes.csv";
    private static final String SEPARADOR = ",";
    private static final String SEPARADOR_LISTA = ";";

    private PersistenciaService() {}

    protected static void salvar(List<Paciente> pacientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            // cabeçalho
            writer.write("cpf,nome,sexo,dataNascimento,fatoresRisco,historicoClinico");
            writer.newLine();

            for (Paciente p : pacientes) {
                String linha = p.getCpf() + SEPARADOR +
                        p.getNome() + SEPARADOR +
                        p.getSexo() + SEPARADOR +
                        p.getDataNascimento() + SEPARADOR +
                        listaParaString(p.getHistoricoClinico());

                writer.write(linha);
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    protected static List<Paciente> carregar() {
        List<Paciente> pacientes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO))) {
            reader.readLine(); // pula cabeçalho

            String linha;
            while ((linha = reader.readLine()) != null) {
                Paciente paciente = stringParaPaciente(linha);
                if (paciente != null)
                    pacientes.add(paciente);
            }

        } catch (FileNotFoundException e) {
            // primeira execução
            System.out.println("Arquivo de dados não encontrado. Uma nova base será criada.");
        } catch (IOException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }

        return pacientes;
    }

    private static String listaParaString(List<? extends Enum<?>> lista) {
        if (lista == null || lista.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Enum<?> e : lista) {
            sb.append(e.name()).append(SEPARADOR_LISTA);
        }
        return sb.substring(0, sb.length() - 1); // remove último ';'
    }

    private static Paciente stringParaPaciente(String linha) {
        try {
            String[] campos          = linha.split(SEPARADOR);
            if (campos.length < 4) return null;

            String cpf               = campos[0];
            String nome              = campos[1];
            char sexo                = campos[2].charAt(0);
            LocalDate dataNasc       = LocalDate.parse(campos[3]);

            List<HistoricoClinico> historico = stringParaHistorico(campos.length > 5 ? campos[4] : "");

            return new Paciente(cpf, nome, sexo, dataNasc, historico);

        } catch (Exception e) {
            System.err.println("Linha inválida ignorada: " + linha);
            return null;
        }
    }

    private static List<HistoricoClinico> stringParaHistorico(String texto) {
        List<HistoricoClinico> lista = new ArrayList<>();
        if (texto.isEmpty()) return lista;

        for (String nome : texto.split(SEPARADOR_LISTA)) {
            try {
                lista.add(HistoricoClinico.valueOf(nome));
            } catch (IllegalArgumentException e) {
                System.err.println("Condição inválida ignorada: " + nome);
            }
        }
        return lista;
    }
}