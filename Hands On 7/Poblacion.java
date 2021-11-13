/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handsOn7;

import jade.core.Agent;
import jade.core.behaviours.*;
/**
 *
 * @author Francisco Tovar
 */
public class EcuationSolverAgent extends Agent {

    protected void setup() {
        System.out.println("Agent " + getLocalName() + "started.");
        addBehaviour(new SolveEquationnBehaviour());
    }

    private class SolveEquationnBehaviour extends OneShotBehaviour {

        public void action() {
            int tamPblacion = 10;
            double velocidadMutacion = 0.001;
            double velocidadRecombinacion = 0.95;
            int elitismo = 0;
            int longitudCromosoma = 6;
            int objetivo = 30;
            AlgoritmoGenetico algoritmo = new AlgoritmoGenetico(tamPblacion, velocidadMutacion, velocidadRecombinacion, elitismo,
                    objetivo);
            Poblacion poblacion = algoritmo.iniciarPoblacion(longitudCromosoma);
            int generation = 1;

            algoritmo.evaluarPoblacion(poblacion);

            while (!algoritmo.condicionTerminacion(poblacion)) {
                imprimirDatosGeneracion(generation, poblacion);

                poblacion = algoritmo.recombinarPoblacion(poblacion);

                poblacion = algoritmo.mutarPoblacion(poblacion);

                algoritmo.evaluarPoblacion(poblacion);

                generation++;
            }

            imprimirDatosGeneracion(generation, poblacion);

            System.out.println("Solucion encontrada en " + generation + " generationes");
            System.out.println("cromosoma de la mejor solucion: " + poblacion.getMejor(0).toString());
            System.out.println("ecuacion de la mejor solucion: " + poblacion.getMejor(0).equationToString());
        }

        public void imprimirDatosGeneracion(int generacion, Poblacion poblacion) {
            double adaptPoblacion = poblacion.getAdaptPoblacion();
            System.out.println("Generacion:" + generacion);
            System.out.println("Adaptabilidad de poblacion: " + adaptPoblacion);

            System.out.println("Cromosomas:");
            for (Individuo individuo : poblacion.getIndividuos()) {
                double AdapIndividuo = individuo.getAdaptabilidad();
                double adaptProporcionada = (AdapIndividuo * 100) / adaptPoblacion;
                System.out.println(individuo.toString() + " | Adaptabilidad: " + individuo.getAdaptabilidad()
                        + " | Valor adaptabilidad proporcionado: " + adaptProporcionada + "%");
            }

            System.out.println();
        }

        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}