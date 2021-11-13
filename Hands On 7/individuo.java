package handsOn7;
import java.util.Random;
/**
 *
 * @author Francisco Tovar
 */
public class Individuo {
    private int[] cromosoma;
    private double adaptabilidad = -1;

    public Individuo(int[] cromosoma) {
        this.cromosoma = cromosoma;
    }

    public Individuo(int longitud) {
        this.cromosoma = new int[longitud];

        for (int i = 0; i < cromosoma.length; i++) {
            Random rand = new Random();
            this.cromosoma[i] = rand.nextInt((9 - 1) + 1) + 1;
        }
    }

    public int evaluate() {
        int a = getGen(0);
        int b = getGen(1);
        int c = getGen(2);
        int d = getGen(3);
        int e = getGen(4);
        int f = getGen(5);
        return a + (2*b) - (3*c) + d + (4*e) + f;
    }

    public int[] getCromosoma() {
        return this.cromosoma;
    }

    public int getLongitudCromosoma() {
        return this.cromosoma.length;
    }
    
    public void setGen(int offset, int gene) {
        this.cromosoma[offset] = gene;
    }

    public int getGen(int offset) {
        return this.cromosoma[offset];
    }

    public void setAdaptabilidad(double adaptabilidad) {
        this.adaptabilidad = adaptabilidad;
    }

    public double getAdaptabilidad() {
        return this.adaptabilidad;
    }

    public String toString() {
        String output = "";

        for(int gene = 0; gene < this.getLongitudCromosoma(); gene++) {
            output += this.getGen(gene);
        }

        return output;
    }

    public String equationToString() {
        return getGen(0) + " + 2(" + getGen(1) + ") - 3(" + getGen(2) + ") + " + getGen(3) + " + 4(" + getGen(4) + ") + " + getGen(5) + " = " + this.evaluate();
    }
}