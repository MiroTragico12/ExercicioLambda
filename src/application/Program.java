package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities.Products;

public class Program {

	public static void main(String[] args) {

		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		System.out.print("Enter full file path: ");
		String path = sc.nextLine();

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {

			List<Products> list = new ArrayList<>();

			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Products(fields[0],fields[1], Double.parseDouble(fields[2])));
				line = br.readLine();
			}
			System.out.println("Insira o salario: ");
			double salario = sc.nextDouble();
			
			Comparator<String> comp = (s1, s2) -> s1.toUpperCase().compareTo(s2.toUpperCase());
			
			List<String> email = list.stream()
					.filter(p -> p.getSalary() > salario)
					.map(p -> p.getEmail()).sorted(comp.reversed())
					.collect(Collectors.toList());
			
			email.forEach(System.out::println);
			
			double sum = list.stream().
					filter(x -> x.getName().charAt(0) == 'M').
					map(x->x.getSalary()).
					reduce(0.0, (x,y) -> x + y);
			
			System.out.println("Sum of salary from people whose name starts with 'M': " + String.format("%.2f", sum));
			
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		sc.close();
	}
}
