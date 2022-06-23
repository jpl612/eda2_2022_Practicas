package org.eda2.practica2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class Principal {

	private static Grafo grafo;

	private static final String ruta = System.getProperty("user.dir") + "\\" + "dataset\\";
	private static String archivo;

	public static void main(String[] args) {
		System.out.println("Selecciona con qué fichero quieres ejecutar el programa:\n" + "0 - graphPrimKruskal.txt\n" + "1 - graphEDAland.txt\n"
				+ "2 - graphEDAlandLarge.txt\n" + "3 - Grafo aleatorio.\n" + "4 - Realizar experimento.");
		System.out.println(ruta);
		Scanner sc = new Scanner(System.in);
		int input = sc.nextInt();

		switch (input) {
			case 0:
				archivo = "graphPrimKruskal.txt";
				break;
	
			case 1:
				archivo = "graphEDAland.txt";
				break;
	
			case 2:
				archivo = "graphEDAlandLarge.txt";
				break;
	
			case 3:
				archivo = "";
				break;
	
			case 4:
				System.out.println("Nº de iteraciones a realizar: ");
				int iterations = sc.nextInt();
				System.out.println("Nº de aristas por vertice: ");
				int density = sc.nextInt();
				getExperimentalResults(iterations, density);
				break;

			default:
				System.out.println("No has elegido ninguna opcion valida.");
				sc.close();
				System.exit(0);
				break;
		}

		if (archivo.isEmpty()) {
			System.out.println("Selecciona el tamaño del grafo:");
			input = sc.nextInt();
			System.out.println("Nº de aristas por vertice: ");
			int densidad = sc.nextInt();
			grafo = new Grafo(input, densidad);
		} 
		else {
			System.out.println(ruta);
			grafo = new Grafo(ruta + archivo);
		}

		grafo.print();
		System.out.println("Selecciona algoritmo:\n" + "0: Prim\n" + "1: Prim con PQ\n" + "2: Kruskal");
		input = sc.nextInt();

		long start = System.currentTimeMillis();
		HashMap<String, ArrayList<String>> resultado = null;
		switch (input) {
		case 0:
			resultado = Algoritmos.prim(grafo);
			break;

		case 1:
			resultado = Algoritmos.primPQ(grafo);
			break;

		case 2:
			resultado = Algoritmos.kruskal(grafo);
			break;

		default:
			System.out.println("No has elegido ninguna opcion valida");
			sc.close();
			System.exit(0);
			break;
		}
		long end = System.currentTimeMillis();

		if (resultado == null) {
			System.out.println("No hay resultados.");
		} else {
			print(resultado);
			System.out.println("Tiempo de ejecución para algoritmo: " +
					(end - start) + " ms.");
			long cost = coste(resultado, grafo);
			System.out.println("\n\tNumero Vertices: " + grafo.getNumV() + " | Numero Aristas: " + grafo.getNumA());
			System.out.println("\tEl coste es: " + cost);
		}

		sc.close();
	}

	private static long coste(HashMap<String, ArrayList<String>> resultado, Grafo grafo) {
		long sum = 0;
		for (Entry<String, ArrayList<String>> entry : resultado.entrySet()) {
			String key = entry.getKey();
			ArrayList<String> values = entry.getValue();

			for (String value : values) {
				HashMap<String, Double> map = grafo.map.get(value);
				if (map != null) {
					for (Entry<String, Double> adjMapEntry : map.entrySet()) {
						if (adjMapEntry.getKey() != key) {
							sum += adjMapEntry.getValue();
						}
					}
				}
			}
		}
		return sum;
	}

	private static void print(HashMap<String, ArrayList<String>> resultado) {
		System.out.println("\nMST is: ");
		for (Entry<String, ArrayList<String>> entry : resultado.entrySet()) {
			for (String value : entry.getValue()) {
				System.out.println("\t" + entry.getKey() + " -- " + value);
			}
		}
	}

	private static void getExperimentalResults(int iteraciones, int densidad) {

		String primResults, primPQResults, kruskalResults;
		primResults = primPQResults = kruskalResults = "";
		int pow = 2;
		int numVertices;
		long start;
		long end;
		for (int i = 0; i < iteraciones; i++) {
			numVertices = (int) Math.pow(2, pow);
			Grafo grafo = new Grafo(numVertices, densidad);
			System.out.println(" Iteracion N� " + (i + 1) + "\n N� de vertices: " + grafo.getNumV()
					+ " | N� de aristas: " + grafo.getNumA());
			System.out.println("----------------------------------");
			// Algoritmo de Prim:
			start = System.currentTimeMillis();
			Algoritmos.prim(grafo);
			end = System.currentTimeMillis();

			primResults += "\n" + numVertices + " = " + ((end - start) <= 0 ? 1 : (end - start)) + " .ms";

			// Prim con cola de prioridad:
			start = System.currentTimeMillis();
			Algoritmos.primPQ(grafo);
			end = System.currentTimeMillis();
			primPQResults += "\n" + numVertices + " = " + ((end - start) <= 0 ? 1 + "< ms." : (end - start));

			// Algoritmo de Kruskal:
			start = System.currentTimeMillis();
			Algoritmos.kruskal(grafo);
			end = System.currentTimeMillis();
			kruskalResults += "\n" + numVertices + " = " + ((end - start) <= 0 ? 1 : (end - start)) + " ms.";

			pow++;
		}

		System.out.println("\n" + "Resultados Prim:");
		System.out.println(primResults);
		System.out.println("\n" + "Resultados Prim Priority Queue:");
		System.out.println(primPQResults);
		System.out.println("\n" + "Resultados Kruskal:");
		System.out.println(kruskalResults);
		System.exit(0);
	}

}
