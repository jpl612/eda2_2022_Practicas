package org.eda2.practica4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.TreeMap;
import java.util.Map.Entry;

public class GrafoReader<Vertex extends Comparable<Vertex>> extends Network<Vertex> {
	
	public GrafoReader (String nombre) {
		super ();
		leerGrafo (nombre);
	}

	public void leerGrafo (String nombre) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(nombre)));
			int numVertex, numEdges;
			boolean directed=false; 	
			
			String linea=br.readLine();
			//Si el grafo esta dirigido o no
			if(Integer.valueOf(linea)==0) {
				directed=false;
			}else {
				directed=true;
			}
			linea = br.readLine();
			//numero de vertices
			numVertex=Integer.valueOf(linea);
			//crear grafo
			for(int i=0; i<numVertex; i++) {
				linea = br.readLine();
				addVertex((Vertex)linea);
			}
			linea=br.readLine();
			//numero de aristas
			numEdges = Integer.valueOf(linea);
			//crear grafo
			for(int i=0; i<numEdges; i++) {
				linea=br.readLine();
				String[] items=linea.split(" ");
				addEdge((Vertex)items[0], (Vertex)items[1], Double.valueOf(items[2]));
				
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	
	
}
