package P1;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;


public class TestNBA {

	ArrayList<Player> jugadores;
	
	String archivo;
	String directorioEntrada;	

	
	@Test
	public void reader() throws Exception{
		
		directorioEntrada = System.getProperty("user.dir");

		/*
		 * getProperty ruta propia 
		 * */
		String filename = "NbaStats.csv";
		
		String directorio = directorioEntrada + File.separator + "src"
				+ File.separator + "org" + File.separator + "eda2"
				+ File.separator + "practica1" + File.separator + filename;
		
		FileWriter fw = new FileWriter(directorio);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);

		archivo = directorio;
	}

	@Test
	public void test() throws Exception{
		
		directorioEntrada = System.getProperty("user.dir");
		
		String filename = "NbaStats.csv";
		
		String directorio = directorioEntrada + File.separator + "src"
				+ File.separator + "org" + File.separator + "eda2"
				+ File.separator + "practica1" + File.separator + filename;
		
		//FileWriter fw = new FileWriter(directorio);
		
		File filename2 = new File(directorio);
		
		CentroEstadistico.leerEstadisticas(filename2);
		
		/*
		 * Jugadores aparece como null, ¿está leyendo el arraylist de centro? o el del test?
		 * 
		 * */
		
		System.out.println(CentroEstadistico.jugadores.toString());
		
	}
	
}
