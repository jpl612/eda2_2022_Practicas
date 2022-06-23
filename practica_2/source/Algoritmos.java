package org.eda2.practica2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class Algoritmos {
	private static class Edge implements Comparable<Edge> {

		public Edge(String origen, String destino, double peso) {
			super();
			this.origen = origen;
			this.destino = destino;
			this.peso = peso;
		}

		@Override
		public int compareTo(Edge o) {
			return Double.compare(this.peso, o.peso);
		}

		public String origen, destino;
		public double peso;
	}

	private static HashMap<String, String> heap = new HashMap<String, String>();
	private static HashMap<String, Double> weight = new HashMap<String, Double>();
	
	/**
	 * Realiza el algoritmo de Prim sobre el grafo que le pasamos como entrada
	 * @param grafo
	 * @return
	 */
	public static HashMap<String, ArrayList<String>> prim(Grafo grafo) {
		//array con los nodos restantes
		HashSet<String> remain = new HashSet<String>();
		
		for (String v : grafo.map.keySet())
			remain.add(v);
		remain.remove(grafo.getOrigen());

		heap.clear();
		weight.clear();
		for (String v : remain) {
			
			Double w = grafo.getWeight(grafo.getOrigen(), v);
			if (w != null) {
				heap.put(v, grafo.getOrigen());
				weight.put(v, w);
				
			} 
			
			else {
				heap.put(v, null);
				weight.put(v, Double.MAX_VALUE);
			}
		}

		heap.put(grafo.getOrigen(), grafo.getOrigen());
		
		weight.put(grafo.getOrigen(), 0.0);

		//mientras que queden nodos sin escoger, el algoritmo se repetirá
		HashMap<String, ArrayList<String>> resultado = new HashMap<String, ArrayList<String>>();
		while (!remain.isEmpty()) {
			double minValue = Double.MAX_VALUE;
			String minKey = "";
			for (String v : remain) {
				double w = weight.get(v);
				//coge el menor valor
				if (w < minValue) {
					minValue = w;
					minKey = v;
				}
			}

			if (minKey.isEmpty())
				break;

			remain.remove(minKey);

			String origen = heap.get(minKey);
			if (!resultado.containsKey(origen)) {
				resultado.put(origen, new ArrayList<String>());
			}
			resultado.get(origen).add(minKey);

			for (String v : remain) {
				Double w = grafo.getWeight(minKey, v);
				if (w != null && w < weight.get(v)) {
					weight.put(v, w);
					heap.put(v, minKey);
				}
			}
		}
		return resultado;
	}
	/**
	 * Realiza el algoritmo de Prim con cola de prioridad al algoritmo que le pasamos como entrada
	 * @param grafo
	 * @return
	 */
	public static HashMap<String, ArrayList<String>> primPQ(Grafo grafo) {

		HashSet<String> remain = new HashSet<String>();
		for (String v : grafo.map.keySet())
			remain.add(v);
		remain.remove(grafo.getOrigen());

		PriorityQueue<Edge> queue = new PriorityQueue<Edge>();

		HashMap<String, ArrayList<String>> resultado = new HashMap<String, ArrayList<String>>();
		String origen = grafo.getOrigen();
		String destino = "";
		while (!remain.isEmpty()) {
			for (Entry<String, Double> entry : grafo.map.get(origen).entrySet()) {
				destino = entry.getKey();
				if (remain.contains(destino)) {
					queue.add(new Edge(origen, destino, entry.getValue()));
				}
			}
			Edge edge = null;
			do {
				edge = queue.poll();
				destino = edge.destino;
			} while (!remain.contains(destino));
			origen = edge.origen;

			remain.remove(destino);

			if (!resultado.containsKey(origen)) {
				resultado.put(origen, new ArrayList<String>());
			}
			resultado.get(origen).add(destino);

			origen = destino;
		}

		return resultado;
	}
	/**
	 * Realiza el algoritmo de Kruskal al algoritmo que le pasamos como entrada
	 * @param grafo
	 * @return
	 */
	public static HashMap<String, ArrayList<String>> kruskal(Grafo grafo) {

		HashMap<String, Double> remain = new HashMap<String, Double>();
		for (String v : grafo.map.keySet())
			remain.put(v, Double.MAX_VALUE);
		remain.remove(grafo.getOrigen());

		heap.clear();
		String minKey = grafo.getOrigen();
		String to, from;
		boolean firstLoop = true;
		HashMap<String, ArrayList<String>> resultado = new HashMap<String, ArrayList<String>>();
		while (!remain.isEmpty()) {
			// Obtenemos el valor minimo
			double minValue = Double.MAX_VALUE;
			if (firstLoop)
				firstLoop = false;
			else
				minKey = remain.keySet().stream().findFirst().toString();

			for (Entry<String, Double> entry : remain.entrySet()) {
				if (entry.getValue() < minValue) {
					minValue = entry.getValue();
					minKey = entry.getKey();
				}
			}

			remain.remove(minKey);

			for (Entry<String, Double> entry : grafo.map.get(minKey).entrySet()) {
				to = entry.getKey();
				Double weight = grafo.getWeight(heap.get(to), to);

				weight = (weight == null) ? Double.MAX_VALUE : weight;

				if (remain.containsKey(to) && entry.getValue() < Double.MAX_VALUE && entry.getValue() < weight) {
					remain.put(to, entry.getValue());
					heap.put(to, minKey);
				}
			}
		}
		// A�adir resultado
		for (Entry<String, String> entry : heap.entrySet()) {
			to = entry.getKey();
			from = entry.getValue();
			if (!resultado.containsKey(from))
				resultado.put(from, new ArrayList<String>());
			resultado.get(from).add(to);
		}
		return resultado;
	}
}
