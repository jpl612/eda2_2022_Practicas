package org.eda2.practica4;

import java.util.Map.Entry;
import java.util.TreeMap;

public class EDALandBacktracking<Vertex extends Comparable<Vertex>> extends GrafoReader<Vertex> {

	public static final double INFINITO = 10000;
	private Vertex sol[] ;
	private int x[]; //vertices ya asignados
	private double[][] g; //matriz de adyacencias
	private TreeMap<Vertex, Integer> vertices;
	
	public EDALandBacktracking (String nombre) {
		super (nombre);
		crearMatriz ();
		x = new int [g.length];
	}
	
	/**
	 * Metodo que crea la matriz
	 * 
	 * Crea un mapa que asocia a cada vertice una posicion
	 */
	private void crearMatriz () {
		vertices = new TreeMap<Vertex, Integer>();
		int n=0;
		for (Entry<Vertex, TreeMap<Vertex, Double>> e1 : adjacencyMap.entrySet()) {
			vertices.put(e1.getKey(), n);
			n++;
		}
		crearMatrizAdyacencias(n);
		modificacionAristas();
			
	}
	
	/**
	 * Metodo que crea la matriz de adyacencias y la inicializa
	 * @param n : posicion limite de la matriz
	 */
	private void crearMatrizAdyacencias(int n) {
		g = new double [n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) {
				g[i][j] = INFINITO;
			}
		}
	}
	
	/**
	 * Metodo que lee la lista y modifica las posiciones que tienen arista
	 */
	private void modificacionAristas() {
		for (Entry<Vertex, TreeMap<Vertex, Double>> e1 : adjacencyMap.entrySet()) {
			int n1 = vertices.get(e1.getKey());
			for (Entry<Vertex, Double> e2 : e1.getValue().entrySet()) {
				int n2 = vertices.get(e2.getKey());
				g[n1][n2] = e2.getValue();
			}
		}
	}
	
	public int siguienteValor(int k) {
		x[k] = 0;
		do {
			x[k] ++;
			if (g[x[k-1]][x[k]] != INFINITO && (x[k]<1 || x[k]>k-1) && (k<g.length || (k==g.length && g[x[k]][x[1]] != INFINITO)))
				return x[k];
		}while (x[k] < g.length);
		x[k] = 0;
		return 0;
	}
	
	/**
	 * Metodo que dada una posicion te da el vertice asociado
	 * @param n : la posicion en la matriz
	 * @return : el vertice asociado a la posicion
	 */
	private Vertex verticeAsociado (int n) {
		for (Entry<Vertex, Integer> e : vertices.entrySet()) {
			if (e.getValue() == n)
				return e.getKey();
		}
		return null;
	}
	
	public void hamiltoniano(int k) {
		if (k == g.length)
			almacenar();
		else {
			do {
				x[k] = siguienteValor(k);
				if (x[k] != 0)
					k++;
					hamiltoniano (k);
			}while (x[k] != 0);
		}
	}
	
	/**
	 * Metodo que almacenara la solucion actual
	 */
	public void almacenar() {
		sol = (Vertex[]) new Comparable[g.length];
		for (int i=0; i<x.length; i++) {
			sol[i] = verticeAsociado(x[i]);
		}
	}
}
