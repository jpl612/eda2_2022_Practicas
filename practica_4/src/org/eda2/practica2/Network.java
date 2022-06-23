package org.eda2.practica4;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Stack;


/**
 * Clase que gestiona un grafo y sus componentes (vertices, aristas...)
 * @author jlper
 *
 * @param <Vertex>
 */
public class Network<Vertex extends Comparable<Vertex>> implements Graph<Vertex>, Iterable<Vertex> {

	private boolean directed; 	
	
	
	protected TreeMap<Vertex, TreeMap<Vertex, Double>> adjacencyMap; 
	
	/**
	 * Constructor del grafo 
	 */
	public Network(){
		this.directed = true;
		this.adjacencyMap = new TreeMap<Vertex, TreeMap<Vertex, Double>>();
	}
	
	/**
	 * Constructor del grafo dado si es dirigido o no
	 * @param uDOrD si es true: es dirigido, si es falso: es no dirigido
	 */
   	public Network(boolean uDOrD) { 
  		this.directed = uDOrD;
		this.adjacencyMap = new TreeMap<Vertex, TreeMap<Vertex, Double>>();
	} 

  	public void setDirected(boolean uDOrD) {
  		this.directed = uDOrD;
  	}

  	public boolean getDirected() {
  		return this.directed;
  	}

  	@Override
  	public boolean isEmpty() {
    	return this.adjacencyMap.isEmpty();
  	} 

  	@Override
  	public void clear() {
		this.adjacencyMap.clear();
	}

  	@Override
  	public int numberOfVertices() {
    	return this.adjacencyMap.size();
  	} 

  	@Override
  	public int numberOfEdges() {
  		int count = 0;
  		for (TreeMap<Vertex, Double> itMap : this.adjacencyMap.values())
  			count += itMap.size();
  		return count;
  	} 

  	@Override
  	public boolean containsVertex(Vertex vertex) {
    	return this.adjacencyMap.containsKey(vertex);
  	} 
  	
  	@Override
  	public boolean containsEdge(Vertex v1, Vertex v2) {
    	return this.adjacencyMap.containsKey(v1) && this.adjacencyMap.get(v1).containsKey(v2);
  	} 

  	/**
  	 * método que devuelve el peso asociado a la arista dados los vértices v1 y v2:
  	 * - Si no el vertice no tiene vecinos devolvemos -1
  	 * @param v1 el primer vertice
  	 * @param v2 el segundo vertice
  	 */
  	@Override
  	public double getWeight (Vertex v1, Vertex v2) {
  		TreeMap<Vertex, Double> neighbors = this.adjacencyMap.get(v1);
  		return neighbors == null || (!neighbors.containsKey(v2)) ? -1 : neighbors.get(v2);
   	} 

  	
  	@Override
  	public double setWeight (Vertex v1, Vertex v2, double w) {
  		TreeMap<Vertex, Double> neighbors = this.adjacencyMap.get(v1);
  		if (neighbors == null) return -1;
  		Double oldWeight = neighbors.get(v2);
  		if (oldWeight == null) return -1;
  		neighbors.put(v2, w);
		return oldWeight;
	}

  	public boolean isAdjacent (Vertex v1, Vertex v2) {
		return (adjacencyMap.containsKey(v1) && adjacencyMap.get(v1).containsKey(v2));
 
	}

  	public boolean addVertex(Vertex vertex) {
        if (this.adjacencyMap.containsKey(vertex)) return false;
        this.adjacencyMap.put(vertex, new TreeMap<Vertex, Double>());
        return true;
  	} 

  	public boolean addEdge(Vertex v1, Vertex v2, double w) {
  		if (!containsVertex(v1) || !containsVertex(v2)) return false;
  		this.adjacencyMap.get(v1).put(v2, w);
       	if (!this.directed) {
        	this.adjacencyMap.get(v2).put(v1, w);
       	}
    	return true;
  	} 

  	/**
  	 * método que elimina un vertice y todas sus adyacencias dado el vertice
  	 * @param vertex el vertice a eliminar
  	 */
  	public boolean removeVertex(Vertex vertex) {
        if (!containsVertex(vertex)) return false;
        for(TreeMap<Vertex, Double> aux : this.adjacencyMap.values()) { 
        	if(aux.containsKey(vertex)) {
        		aux.remove(vertex);  
    	  }		
       }
       this.adjacencyMap.remove(vertex);
       return true;
   	} 

  	public boolean removeEdge (Vertex v1, Vertex v2) {
    	if (!containsEdge(v1,v2)) return false;

    	this.adjacencyMap.get(v1).remove(v2);
    	
    	if (!this.directed) {
        	this.adjacencyMap.get(v2).remove(v1);    		
    	}
    	
    	return true;
  	} 
  	
	@Override
  	public TreeSet<Vertex> vertexSet() {
    	return new TreeSet<Vertex>(this.adjacencyMap.keySet());
  	}

  	/**
  	 *  Returns a LinkedList object of the neighbors of a specified Vertex object.
  	 *
  	 *  @param v - the Vertex object whose neighbors are returned.
  	 *
  	 *  @return a LinkedList of the vertices that are neighbors of v.
   	 */

  	public TreeSet<Vertex> getNeighbors(Vertex v) {
  		TreeMap<Vertex, Double> neighbors = this.adjacencyMap.get(v);
  		LinkedList<Vertex> a = new LinkedList<Vertex>();
  		if(!this.adjacencyMap.containsKey(v) ) return null;
  		for(Vertex vecinos : neighbors.keySet())
  			if(!a.contains(vecinos))
  				a.add(vecinos);
  		return neighbors == null ? null : new TreeSet<Vertex>(a);
  				
  	}

  	@Override
  	public String toString() {
    	return this.adjacencyMap.toString();
  	} 

  
	
	private TreeMap<Vertex, Vertex> Dijkstra(Vertex source, Vertex destination) {
  		final double INFINITY = Double.MAX_VALUE;
  		
  		Double weight = .0, minWeight = INFINITY;
    	TreeMap<Vertex, Double> D = new TreeMap<Vertex, Double>();
    	TreeMap<Vertex, Vertex> S = new TreeMap<Vertex, Vertex>();
    	TreeSet<Vertex> V_minus_S = new TreeSet<Vertex>();
    	
    	Vertex from = null;

     	
    	for (Vertex e : this.adjacencyMap.keySet()) {
    		if (source.equals(e)) continue;
    		V_minus_S.add(e);
    	}
    	
    	for (Vertex v : V_minus_S){
    		if (isAdjacent(source,v)){
    			S.put(v, source);
    			D.put(v, getWeight(source,v));
    		}
    		else{
    			S.put(v, null);
    			D.put(v, INFINITY);
    		}
    	}
    	
		S.put(source, source);
		D.put(source, 0.0);
    	
		while (!V_minus_S.isEmpty()) {
		    minWeight = INFINITY;
		    from = null;
		    for (Vertex v : V_minus_S){
		    	if (D.get(v) < minWeight){
		    		minWeight = D.get(v);
		    		from = v;
		    	}
		    }
	    	if (from == null) break;
	    	
			V_minus_S.remove(from);
				
		    for (Vertex v : V_minus_S){
		    	if (isAdjacent(from,v)){
		    		weight = getWeight(from,v);
		    		if (D.get(from) + weight < D.get(v)){
		    			D.put(v, D.get(from) + weight);
		    			S.put(v, from);
		    		}
		    	}
		    }
		}
		if (S.get(destination) == null) {
			throw new RuntimeException("The vertex " + destination + " is not reachable from " + source);
		}
		return S;
	}
	
	public ArrayList<Vertex> getDijkstra(Vertex source, Vertex destination) {
		ArrayList<Vertex> path = null;
    	Stack<Vertex> pila = null;
		TreeMap<Vertex, Vertex> salidaDijkstra = null;
		
		if (source == null || destination == null) return null;
    	
    	if (source.equals(destination))	return null;
    	
    	
    	try {
    		salidaDijkstra = Dijkstra(source, destination);	
    	}catch(Exception e) {
    		System.out.println(e.getMessage());
    		return null;
    	}
    	path = new ArrayList<Vertex>();
    	pila = new Stack<Vertex>();
    	
    	/**
    	 * Queremos la pila para devolver el orden correcto que seguir para llegar de un vertice a otro
    	 */
		
    	pila.push(destination);
		while (!pila.peek().equals(source)) {
			pila.push(salidaDijkstra.get(pila.peek()));
		}
		while (!pila.isEmpty()) {
			path.add(pila.pop());
		}
		return path;
	}
	
	/**
	 * método que devuelve la distancia (la suma de los pesos) de un vertice destino al fuente
	 * @param source El vértice fuente
	 * @param destination El vértice destino
	 * @return devuelve el arraylist que contiene el camino y el peso
	 */
	public ArrayList<String> getDijkstraWithDistance(Vertex source, Vertex destination){
		ArrayList<Vertex> path = getDijkstra(source, destination);
		ArrayList<String> pathWithDistance = null;
		double suma = .0;
		if (path == null) return null;
		
		pathWithDistance = new ArrayList<String>();
		
		pathWithDistance.add(path.get(0) + "=0.0");
		//1 for()
		//...
		Vertex anterior = path.get(0);
		for (int i=1; i<path.size(); i++) {
			Vertex siguiente = path.get(i);
			double peso = getWeight(anterior, siguiente);
			pathWithDistance.add(siguiente+"="+peso);
			suma+=peso;
			anterior=siguiente;
		}
		pathWithDistance.add(0, String.valueOf(suma));
    	return pathWithDistance; 
    }
 
  	//toArray() methods
	
	//DF = depthFirst (en profundidad)
	//BF = breadthFirst (en anchura)
  
  	public ArrayList<Vertex> toArrayDFRecursive(Vertex start) {
  		if (!containsVertex(start)) return null;
  		ArrayList<Vertex> result = new ArrayList<Vertex>();
		TreeMap<Vertex,Boolean> visited = new TreeMap<Vertex, Boolean>();
		for (Vertex v : this.adjacencyMap.keySet()){
			visited.put(v,false);
		}
		
		toArrayDFRecursive(start, result, visited);
		return result;
	}
	
	private void toArrayDFRecursive(Vertex current, ArrayList<Vertex> result, TreeMap<Vertex,Boolean> visited) {
		result.add(current);
		visited.put(current, true);
		for (Vertex to : this.adjacencyMap.get(current).descendingKeySet()) {
			if (visited.get(to)) continue;
			toArrayDFRecursive(to, result, visited);
		}
	}
	
	/**
	 * método que hace una búsqueda en profundidad
	 * -si el vértice fuente no pertenece a la red devuelve null
	 * @param start El vértice fuente
	 * @return Arraylist de los vertices que explora hasta llegar al resultado
	 */
	public ArrayList<Vertex> toArrayDF(Vertex start) {
		if (!containsVertex(start)) return null;
		ArrayList<Vertex> resultado = new ArrayList<Vertex>();
		Stack<Vertex> stack = new Stack<Vertex>();
		TreeMap<Vertex, Boolean> visited = new TreeMap<Vertex, Boolean>();
		
		
		for (Vertex vertex : this.adjacencyMap.keySet()) {
			visited.put(vertex, false);
		}
		Vertex current;
		stack.push(start);
		while (!stack.isEmpty()) {
			current = stack.pop();
			if (visited.get(current)) continue;
			visited.put(current, true);
			resultado.add(current);
			for (Vertex to : this.adjacencyMap.get(current).keySet()) {
    			if (visited.get(to)) continue;
      			stack.push(to);
			}
		}
		return resultado;
	}
	
	/**
	 * Método que realiza una búsqueda en anchura
	 * @param start El vértice fuente
	 * @return Arraylist de los vertices que explora hasta llegar al resultado
	 */
	public ArrayList<Vertex> toArrayBF(Vertex start) {
		if (!containsVertex(start)) return null;
		ArrayList<Vertex> resultado = new ArrayList<Vertex>();
		Queue<Vertex> cola = new LinkedList<Vertex>();
		TreeMap<Vertex, Boolean> visited = new TreeMap<Vertex, Boolean>();
		for (Vertex vertex : this.adjacencyMap.keySet()) {
			visited.put(vertex, false);
		}
		Vertex current;
		cola.add(start);
		while(!cola.isEmpty()) {
			current=cola.poll();
			if(visited.get(current))continue;
			visited.put(current, true);
			resultado.add(current);
			for (Vertex to : this.adjacencyMap.get(current).keySet()) {
				if(visited.get(to))continue;
				cola.add(to);
			}
		}
		
		return resultado;
	}
	
	
 	
	/**
	 * Iterador sobre el conjunto de claves
	 */
	@Override
	public Iterator<Vertex> iterator() { //Iterador sobre el conjunto de claves --> orden lexicografico
        return this.adjacencyMap.keySet().iterator();
  	} 
	
	/**
	 * Iterador usando el método toArrayBF (anchura) a partir de v
	 * @param v EL vértice dado
	 * @return ArrayList con la iteración hecha en anchura
	 */
  	public ArrayList<Vertex> breadthFirstIterator (Vertex v) { //Iterador en anchura a partir de v
    	if (!adjacencyMap.containsKey(v)) return null;
    	return this.toArrayBF(v);
  	} 

  	/**
	 * Iterador usando el método toArrayDF (profundidad) a partir de v
	 * @param v EL vértice dado
	 * @return ArrayList con la iteración hecha en profundidad
	 */
  	public ArrayList<Vertex> depthFirstIterator (Vertex v) { //Iterador en profundidad a partir de v
    	if (!adjacencyMap.containsKey (v)) return null;
    	return this.toArrayDF(v);
  	} 
 } 
