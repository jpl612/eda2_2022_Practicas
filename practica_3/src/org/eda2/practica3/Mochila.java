package org.eda2.practica3;

public class Mochila {
	
	public static int knapsackCaso1(int[] val, int[] weight, int w) {
		
		/**
		 * tabla inicial con la longitud del array de los valores + 1
		 * y la anchura dada por el peso maximo + 1
		 */
		int[][] k = new int[val.length + 1][w + 1];
		
		
		for (int i = 0; i <= val.length; i++) {
			for (int j = 0; j <= w; j++) {
				/**
				 * Se rellenan la primera fila y columna con ceros
				 */
				if (i == 0 || j == 0) {
					k[i][j] = 0;
					continue;
				}
				/**
				 * Mirar la linea en la que estoy, restar al peso maximo el peso de la linea anterior
				 */
				if (j - weight[i - 1] >= 0) {
					k[i][j] = Math.max(k[i - 1][j], k[i - 1][j - weight[i - 1]] + val[i - 1]);
				}
				/**
				 * Si el peso maximo actual no se puede rellenar con ningun valor entonces cogemos el valor
				 * anterior de la misma posicion en la fila anterior.
				 */
				else {
					k[i][j] = k[i - 1][j];
				}
			}
		}
		/**
		 * Escribe la matriz y devuelve el valor más óptimo que puede coger
		 */
		for(int i = 0; i < w+1; i++) { //pesos disponibles para cada columna
			System.out.print(i+"\t");
		}
		System.out.println();
		for(int i = 0; i < w+1; i++) {
			System.out.print("--------");
		}
		System.out.println();
		
		
		for(int i = 0; i < k.length; i++) {
			for(int j = 0; j < k[0].length; j++) {
				System.out.print(k[i][j]+"\t");
			}
			System.out.println("");
			
		}
		System.out.println();
		return k[val.length][w];
	}
	
public static double knapsackCaso2(int[] val, double[] weight, int w) {
		
		/**
		 * tabla inicial con la longitud del array de los valores + 1
		 * y la anchura dada por el peso maximo + 1
		 */
		double[][] k = new double[val.length + 1][w + 1];
		
		/**
		 * ordenar el array de pesos y valores, de menos a mas peso
		 */
		boolean sorted = false;
	    double temp;
	    int temp2;
	    while(!sorted) {
	        sorted = true;
			for(int i=0; i<weight.length-1; i++) {
				 if (weight[i] > weight[i+1]) {
		                temp = weight[i];
		                weight[i] = weight[i+1];
		                weight[i+1] = temp;
		                
		                temp2 = val[i];
		                val[i] = val[i+1];
		                val[i+1] = temp2;
		                
		                sorted = false;       
				 }
			}
	    }
		
		for (int i = 0; i <= val.length; i++) {
			for (int j = 0; j <= w; j++) {
				/**
				 * Se rellenan la primera fila y columna con ceros
				 */
				if (i == 0 || j == 0) {
					k[i][j] = 0;
					continue;
				}
				/**
				 * Mirar la linea en la que estoy, restar al peso maximo el peso anterior,
				 * el peso anterior siempre ser� menor al estar ordenado el array de pesos.
				 */
				if (j - weight[i - 1] >= 0) {
					k[i][j] = Math.max(k[i - 1][j], k[i - 1][j-1] + val[i - 1]);
				}
				/**
				 * Si el peso maximo actual no se puede rellenar con ningun valor entonces cogemos el valor
				 * anterior de la misma posicion en la fila anterior.
				 */
				else {
					k[i][j] = k[i - 1][j];
				}
			}
		}
		/**
		 * Escribe la matriz y devuelve el valor más óptimo que puede coger
		 */
		for(int i = 0; i < w+1; i++) { //pesos disponibles para cada columna
			System.out.print(i+"\t");
		}
		System.out.println();
		for(int i = 0; i < w+1; i++) {
			System.out.print("--------");
		}
		System.out.println();
		
		
		for(int i = 0; i < k.length; i++) {
			for(int j = 0; j < k[0].length; j++) {
				System.out.print(k[i][j]+"\t");
			}
			System.out.println("");
			
		}
		System.out.println();
		return k[val.length][w];
	}

	public static void main(String args[]) {
		int val[] = { 4, 3, 8, 1 };
		int weight[] = { 2, 3, 4, 5 };
		int w = 8;
		
		int val2[] = { 4, 16, 8, 1 };
		double weight2[] = { 3.5, 4.4, 0.1, 10.2 };
		int w2 = 8;
		System.out.println(knapsackCaso1(val, weight, w));
		System.out.println(knapsackCaso2(val2, weight2, w2));
		
	}
}
