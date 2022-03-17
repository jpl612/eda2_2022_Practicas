package org.eda2.practica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CentroEstadistico {
	
	public static ArrayList<Player> jugadores=null;

	public CentroEstadistico(String nombreArchivo) {
		jugadores= new ArrayList<Player>();
		File f = new File(nombreArchivo);
		try {
			leerEstadisticas(f);
		}catch(Exception e){
		}
	}
	
	public static ArrayList<Player> leerEstadisticas (File archivo) throws Exception {
		ArrayList<Player> jug = new ArrayList<Player>();
		ArrayList<Player> listaJugador = new ArrayList<Player>();
		double porcentajeAciertos;
		String nombreJugador = null;
		String equipo = null;
		String posicion = null;
		int score = 0;
		int edad=0;
		Player p = new Player(nombreJugador, equipo, posicion, score);
		BufferedReader br = new BufferedReader(new FileReader(archivo));
		//cabecera
		String linea = br.readLine();
		linea = br.readLine();
		//información que queremos
		while(linea != null){
			Scanner sc = new Scanner(linea);
			sc.useDelimiter(";");
			int seasonStart = sc.nextInt();
			nombreJugador = sc.next();
			String salario = sc.next();
			posicion = sc.next();
			if(!sc.hasNextInt()) {edad = -1; sc.next();}
			else{edad = sc.nextInt();}
			equipo = sc.next();
			if(!sc.hasNextDouble()) { porcentajeAciertos = -1; sc.next();}
			else {porcentajeAciertos = sc.nextDouble();}
			int puntosTemp = sc.nextInt();
			score = (int) (puntosTemp*(porcentajeAciertos/100));
			p = new Player(nombreJugador, equipo, posicion, score);
			//System.out.println(p);
			jug.add(p);
			linea = br.readLine();
		}
		br.close();
		for(Player j : jug) {
			if(!listaJugador.contains(j)) {
				listaJugador.add(j);
			}
			//añadir el equipo, posicion y score nuevo. al jugador que ya he registrado previamente.
		}
		return listaJugador;
	}
	
	
	/**
	 * Usando divide y venceras:
	 * 
	 * dividir el arraylist que se pasa por parametro en 2
	 * ordenar los array
	 * sacar los jugadores con los 3 scores mas altos de cada array
	 * conseguir un array de 6 jugadores
	 * ordenarlo
	 * sacar los 3 jugadores con mejor score
	 * solucion
	 * 
	 * @param listaJugadores
	 * @return mejores
	 */
	public static ArrayList<Player> topJugadores(ArrayList<Player> listaJugadores) {
		ArrayList<Player> mejores = new ArrayList<Player>();
		//mitad del array
		int mitad = listaJugadores.size()/2;
		//for que ordene
		//mirar como usar el divide y venceras
		//Puede que se necesite otro metodo
		for(int i=0; i<listaJugadores.size(); i++) {
			
			//System.out.println(listaJugadores.get(i).getPlayerName()+" : "+listaJugadores.get(i).getScore());
		}
		
		return mejores;
		
	}
	
public static void main(String[] args) throws Exception {
	String directorio = System.getProperty("user.dir")+File.separator+"src"+File.separator+"org"+File.separator+
			"eda2"+File.separator+"practica1"+File.separator;
		File f = new File(directorio+"NbaStats.csv");
		leerEstadisticas(f);
		System.out.println(leerEstadisticas(f));
		topJugadores(leerEstadisticas(f));
	}
}
