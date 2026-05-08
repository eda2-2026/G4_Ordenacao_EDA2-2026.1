package servico;

import entidade.Condicao;
import entidade.Paciente;

import java.io.*;
import java.util.List;

public class PersistenciaService {

    private static final String ARQUIVO = "pacientes.csv";
    private static final String SEPARADOR = ",";
    private static final String SEPARADOR_CONDICOES = ";";

    private PersistenciaService() {}

    public static void salvar(List<Paciente> pacientes) {
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

    private static String condicoesParaString(List<Condicao> condicoes) {
        if (condicoes == null || condicoes.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (Condicao c : condicoes) {
            sb.append(c.name()).append(SEPARADOR_CONDICOES);
        }
        return sb.substring(0, sb.length() - 1); // remove último ';'
    }
}