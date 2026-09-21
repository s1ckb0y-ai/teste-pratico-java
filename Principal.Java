import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    public static void main(String[] args) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat currencyFormat = new DecimalFormat("#,##0.00", symbols);

        // 3.1 – Inserir todos os funcionários
        List funcionarios = new ArrayList<>(Arrays.asList(
            new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"),
            new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"),
            new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"),
            new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"),
            new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"),
            new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"),
            new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"),
            new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"),
            new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"),
            new Funcionario("Helena", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente")
        ));

        // 3.2 – Remover o funcionário “João”
        funcionarios.removeIf(f -> f.getNome().equalsIgnoreCase("João"));

        // 3.3 – Imprimir todos os funcionários
        System.out.println("=== 3.3 LISTA DE FUNCIONÁRIOS ===");
        imprimirFuncionarios(funcionarios, dateFormatter, currencyFormat);

        // 3.4 – Aumento de 10% no salário
        for (Funcionario f : funcionarios) {
            BigDecimal novoSalario = f.getSalario().multiply(new BigDecimal("1.10"));
            f.setSalario(novoSalario);
        }

        // 3.5 – Agrupar os funcionários por função em um MAP
        Map> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 – Imprimir os funcionários agrupados por função
        System.out.println("\n=== 3.6 FUNCIONÁRIOS AGRUPADOS POR FUNÇÃO ===");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("\nFunção: " + funcao);
            imprimirFuncionarios(lista, dateFormatter, currencyFormat);
        });

        // 3.8 – Imprimir os aniversariantes do mês 10 e 12
        System.out.println("\n=== 3.8 ANIVERSARIANTES DOS MESES 10 E 12 ===");
        List aniversariantes = funcionarios.stream()
                .filter(f -> f.getDataNascimento().getMonthValue() == 10 || f.getDataNascimento().getMonthValue() == 12)
                .collect(Collectors.toList());
        imprimirFuncionarios(aniversariantes, dateFormatter, currencyFormat);

        // 3.9 – Imprimir o funcionário com a maior idade
        System.out.println("\n=== 3.9 FUNCIONÁRIO COM MAIOR IDADE ===");
        Funcionario maisVelho = funcionarios.stream()
                .min(Comparator.comparing(Funcionario::getDataNascimento))
                .orElse(null);

        if (maisVelho != null) {
            int idade = Period.between(maisVelho.getDataNascimento(), LocalDate.now()).getYears();
            System.out.println("Nome: " + maisVelho.getNome() + " | Idade: " + idade + " anos");
        }

        // 3.10 – Imprimir a lista de funcionários por ordem alfabética
        System.out.println("\n=== 3.10 FUNCIONÁRIOS EM ORDEM ALFABÉTICA ===");
        List funcionariosOrdenados = funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .collect(Collectors.toList());
        imprimirFuncionarios(funcionariosOrdenados, dateFormatter, currencyFormat);

        // 3.11 – Imprimir o total dos salários
        System.out.println("\n=== 3.11 TOTAL DOS SALÁRIOS ===");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários: R$ " + currencyFormat.format(totalSalarios));

        // 3.12 – Imprimir quantos salários mínimos ganha cada funcionário
        System.out.println("\n=== 3.12 QUANTIDADE DE SALÁRIOS MÍNIMOS POR FUNCIONÁRIO ===");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        for (Funcionario f : funcionarios) {
            BigDecimal qtdSalariosMinimos = f.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println(f.getNome() + " ganha " + qtdSalariosMinimos + " salário(s) mínimo(s).");
        }
    }

    private static void imprimirFuncionarios(List lista, DateTimeFormatter dateFormatter, DecimalFormat currencyFormat) {
        for (Funcionario f : lista) {
            System.out.println("Nome: " + f.getNome() +
                    " | Data Nasc: " + f.getDataNascimento().format(dateFormatter) +
                    " | Salário: R$ " + currencyFormat.format(f.getSalario()) +
                    " | Função: " + f.getFuncao());
        }
    }
}