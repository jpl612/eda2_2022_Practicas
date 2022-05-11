package org.eda2.practica3;

import java.util.Arrays;
import java.util.Comparator;

public class KnapSackContinuous {
    
    private static double max(int[] peso, int[] valor, int cap){
        ValorObjeto[] iVal = new ValorObjeto[peso.length];
 
        for (int i = 0; i < peso.length; i++) {
            iVal[i] = new ValorObjeto(peso[i], valor[i], i);
        }
 
        Arrays.sort(iVal, new Comparator<ValorObjeto>() {
            @Override
            public int compare(ValorObjeto o1, ValorObjeto o2)
            {
                return o2.coste.compareTo(o1.coste);
            }
        });
 
        double valorTotal = 0;
 
        for (ValorObjeto i : iVal) {
 
            int pesoAct = (int)i.wt;
            int valorAct = (int)i.val;
 
            if (cap - pesoAct >= 0) {
                cap = cap - pesoAct;
                valorTotal += valorAct;
            }
            else {
                double fraction = ((double)cap / (double)pesoAct);
                valorTotal += (valorAct * fraction);
                cap = (int)(cap - (pesoAct * fraction));
                break;
            }
        }
 
        return valorTotal;
    }
 
    static class ValorObjeto {
        Double coste;
        double wt, val, ind;
 
        public ValorObjeto(int wt, int val, int ind)
        {
            this.wt = wt;
            this.val = val;
            this.ind = ind;
            coste = new Double((double)val / (double)wt);
        }
    }
 
    public static void main(String[] args)
    {
        int[] peso = { 10, 40, 20, 30 };
        int[] valor = { 60, 40, 100, 120 };
        int pesoMochila = 50;
 
        double valorMax = max(peso, valor, pesoMochila);
 
        System.out.println(valorMax);
    }
}
