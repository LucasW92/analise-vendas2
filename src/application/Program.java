package application;

import entities.Sale;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;


public class Program {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        System.out.print("Digite o caminho do arquivo: ");
        String path = sc.nextLine();

        sc.close();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {

            List<Sale> sales = br.lines().map(line -> {
                String[] splitLine = line.split(",");
                return new Sale(
                        Integer.parseInt(splitLine[0]),
                        Integer.parseInt(splitLine[1]),
                        splitLine[2],
                        Integer.parseInt(splitLine[3]),
                        Double.parseDouble(splitLine[4])
                );
            }).toList();

            Set<String> uniqueSellers = sales.stream()
                    .map(Sale::getSeller)
                    .collect(Collectors.toSet());


            System.out.println("\nTotal de vendas por vendedor: ");

            for (String seller : uniqueSellers) {
                double totalSoldBySeller = sales.stream()
                        .filter(sale -> sale.getSeller().equals(seller))
                        .mapToDouble(Sale::getTotal)
                        .sum();
                System.out.printf("%s - R$ %s\n", seller, totalSoldBySeller);
            }


        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado no local: " + path);
        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage());
        }

    }

}
