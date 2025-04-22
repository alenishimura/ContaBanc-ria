import java.util.ArrayList;
import java.util.List;

interface Sacavel {
    void sacar(double valor);
}

interface Depositavel {
    void depositar(double valor);
}

interface Transferivel {
    void transferir(ContaBancaria paraConta, double valor);
}

interface Extrato {
    void imprimirExtrato();
}

class ContaBancaria implements Sacavel, Depositavel, Transferivel, Extrato {
    private String numeroConta;
    private String titular;
    private double saldo;
    private List<String> historicoTransacoes;

    public ContaBancaria(String numeroConta, String titular) {
        this.numeroConta = numeroConta;
        this.titular = titular;
        this.saldo = 0.0;
        this.historicoTransacoes = new ArrayList<>();
        historicoTransacoes.add("Conta criada com saldo inicial: R$ 0.00");
    }

    @Override
    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
            historicoTransacoes.add(String.format("Saque: -R$ %.2f | Saldo atual: R$ %.2f", valor, saldo));
            System.out.printf("Saque de R$ %.2f realizado com sucesso!%n", valor);
        } else {
            System.out.println("Saldo insuficiente ou valor inválido!");
        }
    }

    @Override
    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
            historicoTransacoes.add(String.format("Depósito: +R$ %.2f | Saldo atual: R$ %.2f", valor, saldo));
            System.out.printf("Depósito de R$ %.2f realizado com sucesso!%n", valor);
        } else {
            System.out.println("Valor inválido para depósito!");
        }
    }

    @Override
    public void transferir(ContaBancaria paraConta, double valor) {
        if (valor > 0 && valor <= saldo) {
            this.sacar(valor);
            paraConta.depositar(valor);
            historicoTransacoes.add(String.format("Transferência para conta %s: -R$ %.2f | Saldo atual: R$ %.2f", paraConta.numeroConta, valor, saldo));
            System.out.printf("Transferência de R$ %.2f para a conta %s realizada com sucesso!%n", valor, paraConta.numeroConta);
        } else {
            System.out.println("Saldo insuficiente ou valor inválido para transferência!");
        }
    }

    @Override
    public void imprimirExtrato() {
        System.out.println("\n--- Extrato da Conta ---");
        System.out.println("Conta: " + numeroConta);
        System.out.println("Titular: " + titular);
        historicoTransacoes.forEach(System.out::println);
        System.out.printf("Saldo atual: R$ %.2f%n", saldo);
    }
}

public class App {
    public static void main(String[] args) {
        ContaBancaria conta1 = new ContaBancaria("12345", "Maria");
        ContaBancaria conta2 = new ContaBancaria("67890", "João");

        conta1.depositar(500);
        conta1.sacar(200);
        conta1.transferir(conta2, 100);
        conta1.imprimirExtrato();

        conta2.imprimirExtrato();
    }
}
