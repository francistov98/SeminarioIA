package codigoChallenge1;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
/**
 *
 * @author Francisco Tovar
 */
public class Challenge1 extends Agent {

  protected void setup() {
    System.out.println("Agente "+getLocalName()+" ha iniciado.");
    addBehaviour(new CalculoGradiente());
  } 

  private class CalculoGradiente extends Behaviour {

    int cont=0;
    int iteraciones=200;
    double factorAprendizaje=0.5;
    int minimo=-1;
    int maximo=1;
    

    public void action() {
        System.out.println("El agente se esta ejecutando");
        gradienteDescendente(minimo, maximo, iteraciones, factorAprendizaje);
        cont+=1;
    } 
    
    public boolean done() {
        return this.cont == 1;
    }
   
    public int onEnd() {
      myAgent.doDelete();
      return super.onEnd();
    }
    public double objetivo(double x){
        return Math.pow(x, 2);
    }
    public double derivada(double x){
        return x*2;
    }
    public void gradienteDescendente(int minimo, int maximo,  int n_iteraciones, double fact_ap){
        
        double posibleSolucion=0;
        double gradiente=0;
        double solucionEvaluada=0;
        posibleSolucion=Math.random()*(this.maximo-this.minimo)+this.minimo;
        double normalizado= (posibleSolucion-this.minimo) /(this.maximo-this.minimo);
        System.out.println("El valor inicial para X sera: " +normalizado);
        for(int i=1; i<n_iteraciones; i++){
            gradiente=derivada(normalizado);
            normalizado-= (fact_ap * gradiente);
            solucionEvaluada=objetivo(normalizado);
            System.out.println("-" + i +" F(" + normalizado +") = " + solucionEvaluada);
            normalizado=(normalizado-this.minimo) /(this.maximo-this.minimo);
        }
    }
  }
} 