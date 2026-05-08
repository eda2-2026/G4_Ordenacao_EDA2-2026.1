package util;

import java.time.LocalDate;
import java.time.Period;

public class DataUtil {
    private DataUtil() {}

    public static int calcularIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }
}