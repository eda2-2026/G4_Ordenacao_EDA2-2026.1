package estrutura;

import entidade.Paciente;
import java.util.List;

public class BuscaBinaria {

    private BuscaBinaria() {}

    public static Paciente porCpf(List<Paciente> lista, String cpf) {
        int inicio = 0;
        int fim = lista.size() - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            String cpfMeio = lista.get(meio).getCpf();
            int comparacao = cpfMeio.compareTo(cpf);

            if (comparacao == 0)
                return lista.get(meio);
            else if (comparacao < 0)
                inicio = meio + 1;
            else
                fim = meio - 1;
        }

        return null;
    }
}