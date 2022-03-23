package org.eda2.practica1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CentroEstadistico {

	public static ArrayList<Player> jugadores = null;

	/*
	 * public CentroEstadistico(String nombreArchivo) { jugadores = new
	 * ArrayList<Player>(); File f = new File(nombreArchivo); try {
	 * leerEstadisticas(f); } catch (Exception e) { } }
	 */

	public static ArrayList<Player> leerEstadisticas(File archivo) throws Exception {
		ArrayList<Player> jug = new ArrayList<Player>();
		ArrayList<Player> listaJugador = new ArrayList<Player>();
		int edad = 0;
		double porcentajeAciertos = 0;
		int scr = 0;
		int auxScr = 0;
		ArrayList<String> pos = new ArrayList<String>();
		ArrayList<String> aux = new ArrayList<String>();
		ArrayList<String> tm = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(archivo));
		// cabecera
		String linea = br.readLine();
		linea = br.readLine();
		// información que queremos
		while (linea != null) {
			Scanner sc = new Scanner(linea);
			sc.useDelimiter(";");
			int seasonStart = sc.nextInt();
			String nombreJugador = sc.next();
			String salario = sc.next();
			String posicion = sc.next();
			if (!sc.hasNextInt()) {
				edad = -1;
				sc.next();
			} else {
				edad = sc.nextInt();
			}
			String equipo = sc.next();
			if (!sc.hasNextDouble()) {
				porcentajeAciertos = -1;
				sc.next();
			} else {
				porcentajeAciertos = sc.nextDouble();
			}
			int puntosTemp = sc.nextInt();
			int score = (int) (puntosTemp * (porcentajeAciertos / 100));
			Player p = new Player(nombreJugador, equipo, posicion, score);
			jug.add(p);
			linea = br.readLine();
		}
		br.close();

		for (Player j : jug) {
			if (!listaJugador.contains(j)) {
				listaJugador.add(j);
			} else {
				pos = j.getPositions();
				aux = listaJugador.get(listaJugador.lastIndexOf(j)).getPositions();
				if (!(aux.contains(pos.get(0)))) {
					listaJugador.remove(j);
					aux.add(pos.get(0));
					j.setPositions(aux);
					listaJugador.add(j);
				}
				tm = j.getTeams();
				aux = listaJugador.get(listaJugador.lastIndexOf(j)).getTeams();
				if (!(aux.contains(tm.get(0)))) {
					listaJugador.remove(j);
					aux.add(tm.get(0));
					j.setTeams(aux);
					listaJugador.add(j);
				}
				scr = j.getScore();
				auxScr = listaJugador.get(listaJugador.lastIndexOf(j)).getScore();
				auxScr = (scr + auxScr) / 2;
				listaJugador.remove(j);
				j.setScore(auxScr);
				listaJugador.add(j);
			}
		}
		return listaJugador;
	}

//////////////////////////////////////////
	public static ArrayList<Player> diezMejoresQuickSort(ArrayList<Player> listaJugadores){
		quickSort(listaJugadores);
		ArrayList<Player> solAux = new ArrayList<Player>();
		for(int i=listaJugadores.size(); i>listaJugadores.size()-10; i--) {
			solAux.add(listaJugadores.get(i-1));
		}
		return solAux;
	}
	
	private static ArrayList<Player> quickSort(ArrayList<Player> listaJugadores) {

		/* Caso base */
		if (listaJugadores.size() <= 1)
			return listaJugadores;

		Player pivote = listaJugadores.get(0); // Tomamos primer elemento como pivote

		ArrayList<Player> left = new ArrayList<Player>(); /* Lista izquierda */
		ArrayList<Player> right = new ArrayList<Player>(); /* Lista derecha */
		ArrayList<Player> sol = new ArrayList<Player>(); /* Array solucion */

		for (int i = 1; i < listaJugadores.size(); i++) { /* Bucle que debe recorrer todo el ArrayList */

			// System.out.println("Valor de i " + i); /* Usado para entender cuanto iteraba
			// el metodo*/

			if (listaJugadores.get(i).getScore() <= pivote.getScore()) { // Si el Player es menor o igual que el pivote
																			// lo meto a la izquierda
				left.add(listaJugadores.get(i));
			}

			else { // Si el Player es menor que el pivote lo meto a la derecha y viceversa
				right.add(listaJugadores.get(i));

			}
		}

		/*
		 * En el ArrayList sol (solucion) añadire, la parte izquierda (left), el pivote
		 * en medio, y la parte derecha(right)
		 * 
		 * Añadiremos volviendo a llamar al método quicksort tanto en left como en right
		 * hasta llegar al caso base En el caso de pivote se queda donde está, su
		 * posición correcta ya que está ordenado con respecto a left y right Una vez se
		 * llega al caso base en left y right, habremos conseguido el ArrayList ordenado
		 * 
		 */
		sol.addAll(quickSort(left));
		sol.add(pivote);
		sol.addAll(quickSort(right));

		return sol;

	}

//////////////////////////////////////////
	public static ArrayList<Player> diezMejoresMerge(ArrayList<Player> listaJugadores) {
		mergeSort(listaJugadores);
		ArrayList<Player> resul = new ArrayList<Player>();
		for (int i = 1; i <= 10; i++) {
			resul.add(listaJugadores.get(listaJugadores.size() - i));
		}
		return resul;
	}

	public static void mergeSort(ArrayList<Player> listaJugadores) {
		Player[] aux = new Player[listaJugadores.size()];

		mergeSort(listaJugadores, aux, 0, listaJugadores.size() - 1);
	}

	private static void mergeSort(ArrayList<Player> array, Player[] aux, int ini, int fin) {
		if (ini >= fin)
			return;
		int med = (ini + fin) / 2;
		mergeSort(array, aux, ini, med);
		mergeSort(array, aux, med + 1, fin);
		mezclar(array, aux, ini, med, fin);
	}

	private static void mezclar(ArrayList<Player> array, Player[] aux, int ini, int med, int fin) {
		int i = ini, j = med + 1, k = ini;
		while (i <= med && j <= fin) {
			if (array.get(i).compareTo(array.get(j)) < 0) {
				aux[k] = array.get(i);
				i++;
				k++;
			} else {
				aux[k] = array.get(j);
				j++;
				k++;
			}
		}
		while (i <= med) {
			aux[k] = array.get(i);
			i++;
			k++;
		}
		while (j <= fin) {
			aux[k] = array.get(j);
			j++;
			k++;
		}
		for (k = ini; k <= fin; k++) {
			array.set(k, aux[k]);
		}
	}

///////////////////////////////////////////
	public static ArrayList<Player> diezMejores(ArrayList<Player> listaJugadores) {
		ArrayList<Player> resul = diezMejores(listaJugadores, 0, listaJugadores.size() - 1);
		Collections.reverse(resul);
		return resul;
	}

	private static ArrayList<Player> diezMejores(ArrayList<Player> l, int ini, int fin) {
		if (fin - ini <= 10) {
			ArrayList<Player> resul = new ArrayList<Player>();
			for (int i = ini; i <= fin; i++) {
				resul.add(l.get(i));
			}
			Collections.sort(resul);
			return resul;
		}
		int med = (ini + fin) / 2;
		ArrayList<Player> resul1 = diezMejores(l, ini, med);
		ArrayList<Player> resul2 = diezMejores(l, med + 1, fin);
		resul1.addAll(resul2);
		Collections.sort(resul1);
		while (resul1.size() > 10) {
			resul1.remove(0);
		}
		return resul1;
	}
///////////////////////////////////////////

	public static void main(String[] args) throws Exception {

		String directorio = System.getProperty("user.dir") + File.separator + "src" + File.separator + "org"
				+ File.separator + "eda2" + File.separator + "practica1" + File.separator;

		File f = new File(directorio + "NbaStats.csv");

		ArrayList<Player> l = leerEstadisticas(f);

		long tiempoMMini = System.currentTimeMillis();
		ArrayList<Player> resul1 = diezMejoresMerge(l);
		long tiempoMMfin = System.currentTimeMillis();
		long tiempoMM = tiempoMMfin - tiempoMMini;
		System.out.println("El top jugadores es:\n" + resul1);
		System.out.println("\nEl método diezMejoresMerge ha tardado: " + tiempoMM + " ms\n");

		long tiempoMini = System.currentTimeMillis();
		ArrayList<Player> resul2 = diezMejores(l);
		long tiempoMfin = System.currentTimeMillis();
		long tiempoM = tiempoMfin - tiempoMini;
		System.out.println("\nEl top jugadores es:\n" + resul2);
		System.out.println("\nEl método diezMejores ha tardado: " + tiempoM + " ms");
		
		long tiempoQSini = System.currentTimeMillis();
		ArrayList<Player> resul3 = diezMejoresQuickSort(l);
		long tiempoQSfin = System.currentTimeMillis();
		long tiempoQS = tiempoQSfin - tiempoQSini;
		System.out.println("\nEl top jugadores es:\n" + resul3);
		System.out.println("\nEl método diezMejoresQuickSort ha tardado: " + tiempoQS + " ms");
		
	}
}
