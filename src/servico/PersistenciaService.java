package servico;

import entidade.Condicao;
import entidade.Paciente;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaService {

    private static final String ARQUIVO = "pacientes.csv";
    private static final String SEPARADOR = ",";
    private static final String SEPARADOR_CONDICOES = ";";

    private PersistenciaService() {}

    protected static void salvar(List<Paciente> pacientes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO))) {
            // cabeçalho
            writer.write("cpf,nome,sexo,dataNascimento,historicoCondicoes");
            writer.newLine();

            for (Paciente p : pacientes) {
                String linha = p.getCpf() + SEPARADOR +
                        p.getNome() + SEPARADOR +
                        p.getSexo() + SEPARADOR +
                        p.getDataNascimento() + SEPARADOR +
                        condicoesParaString(p.getHistoricoCondicoes());

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
        } catch (IOException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }

        return pacientes;
    }

    private static String condicoesParaString(List<Condicao> condicoes) {
        if (condicoes == null || condicoes.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Condicao c : condicoes) {
            sb.append(c.name()).append(SEPARADOR_CONDICOES);
        }
        return sb.substring(0, sb.length() - 1); // remove último ';'
    }

    private static Paciente stringParaPaciente(String linha) {
        try {
            String[] campos          = linha.split(SEPARADOR);
            String cpf               = campos[0];
            String nome              = campos[1];
            char sexo                = campos[2].charAt(0);
            LocalDate dataNasc       = LocalDate.parse(campos[3]);
            List<Condicao> condicoes = stringParaCondicoes(campos.length > 5 ? campos[4] : "");

            int idade = dataNasc.getYear() - dataNasc.getMonthValue();
            return new Paciente(cpf, nome, sexo, dataNasc, condicoes);

        } catch (Exception e) {
            System.err.println("Linha inválida ignorada: " + linha);
            return null;
        }
    }

    private static List<Condicao> stringParaCondicoes(String texto) {
        List<Condicao> condicoes = new ArrayList<>();
        if (texto.isEmpty()) return condicoes;

        for (String nome : texto.split(SEPARADOR_CONDICOES)) {
            try {
                condicoes.add(Condicao.valueOf(nome));
            } catch (IllegalArgumentException e) {
                System.err.println("Condição inválida ignorada: " + nome);
            }
        }
        return condicoes;
    }
}