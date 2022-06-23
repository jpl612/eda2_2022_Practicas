package org.eda2.practica2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.Map.Entry;

public class Grafo {

	public HashMap<String, HashMap<String, Double>> map = new HashMap<String, HashMap<String, Double>>();

	private boolean dirigido = false;
	private String origen = "";
	private int nVertices, nAristas, size;
	
	/**
	 * Genera un grafo tomando un archivo de texto de entrada
	 * @param filename
	 */
	public Grafo(String filename) {
		cargarGrafo(filename);
		this.size = this.map.size();
	}
	/**
	 * Genera un grafo con el numero especificado de vertices y aristas
	 * @param nVertices
	 * @param nAristas
	 */
	public Grafo(int nVertices, int nAristas) {
		generarGrafo(nVertices, nAristas);
		this.size = this.map.size();
	}
	/**
	 * Genera el grafo.
	 * @param nVertices
	 * @param nAristas
	 */
	public void generarGrafo(int nVertices, int nAristas) {
		map.clear();
		HashMap<Integer, Integer> nodes = new HashMap<Integer, Integer>();
		HashSet<Integer> remain = new HashSet<Integer>();
		for (int i = 0; i < nVertices; i++) {
			nodes.put(i, 0);
			remain.add(i);
		}
		Random r = new Random();

		this.nVertices = nVertices;
		this.nAristas = nAristas * nVertices;
		int index = 0;
		while (!remain.isEmpty()) {
			int n = 1;
			if (!map.containsKey("" + index))
				map.put("" + index, new HashMap<String, Double>());

			int numEnlaces = nodes.get(index);
			while (numEnlaces < nAristas) {
				int indexVecino = (index + n) % nVertices;
				int nAux = nodes.get(indexVecino);
				if (nAux >= nAristas) {
					remain.remove(nAux);
					continue;
				}

				double weight = r.nextDouble() * 1000;
				BigDecimal bd = BigDecimal.valueOf(weight);
				weight = bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
				map.get("" + index).put("" + indexVecino, weight);
				if (!dirigido) {
					if (!map.containsKey("" + indexVecino))
						map.put("" + indexVecino, new HashMap<String, Double>());
					map.get("" + indexVecino).put("" + index, weight);
				}
				nodes.put(indexVecino, nAux++);
				nodes.put(index, numEnlaces++);
				n++;
			}
			remain.remove(index);
			index++;
		}

		origen = "" + 0;
	}
	/**
	 * Lee el archivo de texto con la información del grafo
	 * @param filename
	 */
	public void cargarGrafo(String filename) {
		map.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = "";
			line = br.readLine().trim();

			if (Integer.parseInt(line) != 0)
				dirigido = true;

			while (line != null) {
				line = br.readLine();
				nVertices = Integer.parseInt(line);
				for (int i = 0; i < nVertices; i++) {
					line = br.readLine();
					if (i == 0)
						origen = line;
					map.put(line, new HashMap<String, Double>());
				}
				line = br.readLine();
				nAristas = Integer.parseInt(line);
				for (int i = 0; i < nAristas; i++) {
					line = br.readLine();
					String[] atributo = line.split(" ");

					map.get(atributo[0]).put(atributo[1], tryParseDouble(atributo[2]));
					if (!dirigido)
						map.get(atributo[1]).put(atributo[0], tryParseDouble(atributo[2]));
				}
				line = br.readLine();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void print() {
		for (Entry<String, HashMap<String, Double>> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":");

			int c = 1;
			for (Entry<String, Double> valueEntry : entry.getValue().entrySet()) {
				System.out.println("\t" + c++ + ":");
				System.out.println("\t\tDestino: " + valueEntry.getKey() + "\n\t\tDistancia: " + valueEntry.getValue());
			}
			if (c == 1)
				System.out.println("\t" + "No entry");
		}
	}

	static Double tryParseDouble(String text) {
		try {
			return Double.parseDouble(text);
		} catch (NullPointerException e) {
			return null;
		}
	}
	/**
	 * Devuelve el peso entre dos vértices
	 * @param v1
	 * @param v2
	 * @return
	 */
	public Double getWeight(String v1, String v2) {
		return map.containsKey(v1) && map.get(v1).containsKey(v2) ? map.get(v1).get(v2) : null;
	}
	/**
	 * Devuelve el tamaño del grafo
	 * @return
	 */
	public int getSize() {
		return size;
	}
	/**
	 * Devuelve el nodo origen del grafo
	 * @return
	 */
	public String getOrigen() {
		return origen;
	}
	/**
	 * Devuelve si el grafo es dirigido o no
	 * @return
	 */
	public boolean isDirigido() {
		return dirigido;
	}
	/**
	 * Devuelve el número de aristas
	 * @return
	 */
	public int getNumA() {
		return nAristas;
	}
	/**
	 * Devuelve el numero de vertices
	 * @return
	 */
	public int getNumV() {
		return nVertices;
	}

}
