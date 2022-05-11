package org.eda2.practica3;

public class KnapSackUnbound {
	
    private static int unboundKnapsack(int pesoMochila, int n, int[] val, int[] peso){
         
        int dp[] = new int[pesoMochila + 1];
         
        for(int i = 0; i <= pesoMochila; i++){
            for(int j = 0; j < n; j++){
                if(peso[j] <= i){
                    dp[i] = Math.max(dp[i], dp[i - peso[j]] +
                                val[j]);
                }
            }
        }
        return dp[pesoMochila];
    }
 
    public static void main(String[] args){
        int pesoMochila = 100;
        int val[] = {10, 30, 20};
        int peso[] = {1, 10, 15};
        int n = val.length;
        System.out.println(unboundKnapsack(pesoMochila, n, val, peso));
    }
}
