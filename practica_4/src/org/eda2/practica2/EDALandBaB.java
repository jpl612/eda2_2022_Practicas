package org.eda2.practica4;

import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @author GT3-08
 *
 * @param <Vertex>
 */
public class EDALandBaB<Vertex extends Comparable<Vertex>> extends Network<Vertex>{

	

	private double minEdgeValue;
	private ArrayList<Vertex> shortestCircuit;

	/**
	 * Metodo que devuelve el valor menor de todas las aristas
	 * 
	 * @return la arista con menor peso
	 */
	public double minimumEdgeValue() {
		double minimum = Double.MAX_VALUE;
		// Devuelve el menor valor de arista del grafo
		// Dos bucles 'for' anidados
		
		for(Entry<Vertex, TreeMap<Vertex, Double>> map : adjacencyMap.entrySet()) {
			for(Entry<Vertex,Double> edg : map.getValue().entrySet()) {
				if(edg.getValue()<minimum) {
					minimum=edg.getValue();
				}
			}
		}
		return minimum;
	}

	/**
	 * Metodo que hace el TSP usando el algoritmo Branch and Bound
	 * @param source : el vertice fuente
	 * @return el circuito mas corto
	 */
	public ArrayList<Vertex> TSPBaB(Vertex source) {
		TreeMap<Vertex, Double> neighborMap = adjacencyMap.get(source);
		if (neighborMap == null)
			return null;
		minEdgeValue = minimumEdgeValue();
	// Constructor de clase PathNode
		PathNode firstNode = new PathNode(source);
		PriorityQueue<PathNode> priorityQueue = new PriorityQueue<>();
		priorityQueue.add(firstNode);
		shortestCircuit = null;
		double bestCost = Double.MAX_VALUE;
		while(priorityQueue.size() > 0) {
	// Y (PathNode) = menorElemento de la cola de prioridad en funcion de 'estimatedCost'
	
		PathNode Y = priorityQueue.poll();
		if (Y.getEstimatedCost() >= bestCost)
			break;
		else {
			Vertex from = Y.lastVertexRes();
	
	// Si el numero de vertices visitados es n
	// y existe una arista que conecte 'from' con source
	
			if ((Y.getVisitedVertices() == numberOfVertices()) && (containsEdge(from, source))) {
				
	// Actualizar 'res' en Y añadiendo el vertice 'source'
	// Actualizar 'totalCost' en Y con Y.totalCost + weight(from, source)
				Y.res.add(source);
				Y.totalCost += getWeight(from, source);
		
				if (Y.getTotalCost() < bestCost) {
					
	// Actualizar 'bestCost', 'shortestDistanceCircuit' y 'shortestCircuit'
					bestCost = Y.getTotalCost();
					//actualizar con que shortestDistanceCircuit y ShortestCircuit
				}
			}
			else {
	// Iterar para todos los vertices adyacentes a from,
	// a cada vertice lo denominamos 'to'
				for(Vertex to : getNeighbors(from))
					if (Y.isVertexVisited(to)) { // hacer uso de la funcion 'isVertexVisited(vertex)' de PathNode
						PathNode X = new PathNode(Y); // Uso de constructor copia
						X.res.add(to);
						X.visitedVertices++;
						X.totalCost = Y.totalCost+getWeight(from, to);
						X.estimatedCost = X.totalCost+((numberOfVertices()-X.getVisitedVertices()+1)*minEdgeValue);
	// Anadir 'to' a 'res' en X
	// Incrementar en 1 los vertices visitados en X
	// Actualizar 'totalCost' en X con Y.totalCost +weight(from, to)
	// Actualizar 'estimatedCost' en X con X.totalCost +((nVertices - X.getVisitedVertices() + 1) * minEdgeValue)
	
						if (X.getEstimatedCost() < bestCost) {
							priorityQueue.add(X);
						}
					}
				}
			}
		}
	
	return shortestCircuit;
}
/**
 * 
 * @author GT3-08
 *
 */
protected class PathNode implements Comparable<PathNode> {
	private ArrayList<Vertex> res; // result
	private int visitedVertices; // The number of the visited vertices
	private double totalCost; // The total cost of the path taken so far
	private double estimatedCost; // Representing the lower bound of this state.

	/**
	 * Constructor de la clase PathNode
	 * @param vertexToVisit : el vertice a visitar
	 */
	PathNode(Vertex vertexToVisit) {
		res = new ArrayList<Vertex>();
		res.add(vertexToVisit);
		visitedVertices = 1;
		totalCost = 0.0;
		estimatedCost = numberOfVertices() * minEdgeValue;
	}

	/**
	 * Constructor copia del PathNode que se le pasa
	 * @param parentPathNode : el PathNode a copiar
	 */
	PathNode(PathNode parentPathNode) {
		// Constructor copia
		res = parentPathNode.res;
		visitedVertices = parentPathNode.visitedVertices;
		totalCost = parentPathNode.totalCost;
		estimatedCost = parentPathNode.estimatedCost;
	}

	/**
	 * CompareTo cuyo criterio de comparacion es el coste estimado
	 */
	@Override
	public int compareTo(PathNode p) {
		// El criterio de comparacion es 'estimatedCost' que se correponde con la
		// prioridad
		if(this.estimatedCost < p.estimatedCost) {
			return -1;
		}
		if(this.estimatedCost > p.estimatedCost) {
			return 1;
		}
		return 0;
	}

	public ArrayList<Vertex> getRes() {
		return res;
	}

	public void addVertexRes(Vertex v) {
		this.res.add(v);
	}

	/**
	 * Metodo que devuelve el ultimo vertice anadido al camino
	 * @return el ultimo elemento del camino (res)
	 */
	public Vertex lastVertexRes() {
	// Devuelve el ultimo vertice que se ha anadido al camino (ultimo elemento de 'res')
		return res.get(res.size());
		
	}

	/**
	 * Metodo para saber si un vertice ya ha sido visitado
	 * @param v el vertice a comprobar
	 * @return true si ya esta visitado, falso si no
	 */
	public boolean isVertexVisited(Vertex v) {
		// Se ha visitado el vertice v si esta actualmente en 'res'. Una sola linea
		return res.contains(v);
	}

	public int getVisitedVertices() {
	return visitedVertices;
	
	}

	public void setVisitedVertices(int visitedVertices) {
		this.visitedVertices = visitedVertices;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public double getEstimatedCost() {
		return estimatedCost;
	}

	public void setEstimatedCost(double estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
}
}
