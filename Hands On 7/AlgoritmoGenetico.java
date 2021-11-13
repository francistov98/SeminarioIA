/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package handsOn7;

import java.util.Random;

/**
 *
 * @author Francisco Tovar
 */
public class AlgoritmoGenetico {
    private int tamPoblacion;
    private double velocidadMutacion;
    private double velocidadRecombinacion;
    private int elitismo;
    private int objetivo;

    public AlgoritmoGenetico(int tamPoblacion, double velMutacion, double velRecombinacion, int elitismo, int objetivo) {
        this.tamPoblacion = tamPoblacion;
        this.velocidadMutacion = velMutacion;
        this.velocidadRecombinacion = velRecombinacion;
        this.elitismo = elitismo;
        this.objetivo = objetivo;
    }

    public Poblacion iniciarPoblacion(int longitudCromosoma) {
        Poblacion poblacion = new Poblacion(this.tamPoblacion, longitudCromosoma);
        return poblacion;
    }

    public double calcularAdaptabilidad(Individuo individuo) {
        int resultadoEcuacion = individuo.evaluate();
        double adaptabilidad = 100 - Math.abs(resultadoEcuacion - this.objetivo);
        individuo.setAdaptabilidad(adaptabilidad);
        return adaptabilidad;
    }

    public void evaluarPoblacion(Poblacion poblacion) {
        double adaptPoblacion = 0;

        for(Individuo individual : poblacion.getIndividuos()) {
           adaptPoblacion += calcularAdaptabilidad(individual);
        }

        poblacion.setAdaptPoblacion(adaptPoblacion);
    }

    public boolean condicionTerminacion(Poblacion poblacion) {
        for(Individuo individuo : poblacion.getIndividuos()) {
            if (individuo.getAdaptabilidad() == 100) {
                return true;
            }
        }
        return false;
    }

    public Individuo seleccionarPadre(Poblacion poblacion) {
        Individuo individuos[] = poblacion.getIndividuos();

        double adaptPoblacion = poblacion.getAdaptPoblacion();
        double posicionRuleta = Math.random() * adaptPoblacion;

        double giroRuleta = 0;

        for(Individuo individual : individuos) {
            giroRuleta += individual.getAdaptabilidad();
            if (giroRuleta >= posicionRuleta) {
                return individual;
            }
        }

        return individuos[poblacion.longitud() - 1];
    }

    public Poblacion recombinarPoblacion(Poblacion poblacion) {
        Poblacion nuevaPoblacion = new Poblacion(poblacion.longitud());

        for(int indicePoblacion = 0; indicePoblacion < poblacion.longitud(); indicePoblacion++) {
            Individuo parent1 = poblacion.getMejor(indicePoblacion);

            if (this.velocidadRecombinacion > Math.random() && indicePoblacion >= this.elitismo) {
                Individuo offsping = new Individuo(parent1.getLongitudCromosoma());

                Individuo parent2 = seleccionarPadre(poblacion);

                for (int indiceGen = 0; indiceGen < parent1.getLongitudCromosoma(); indiceGen++) {
                    if (0.5 > Math.random()) {
                        offsping.setGen(indiceGen, parent1.getGen(indiceGen));
                    } else {
                        offsping.setGen(indiceGen, parent2.getGen(indiceGen));
                    }
                }

                nuevaPoblacion.setIndividuo(indicePoblacion, offsping);
            } else {
                nuevaPoblacion.setIndividuo(indicePoblacion, parent1);
            }
        }
        return nuevaPoblacion;
    }

    public Poblacion mutarPoblacion(Poblacion poblacion) {
        Poblacion nuevaPoblacion = new Poblacion(this.tamPoblacion);
        Random rand = new Random();

        for (int indicePoblacion = 0; indicePoblacion < poblacion.longitud(); indicePoblacion ++) {
            Individuo individuo = poblacion.getMejor(indicePoblacion);

            for (int indexGen = 0; indexGen < individuo.getLongitudCromosoma(); indexGen++) {            
                if (indicePoblacion > this.elitismo) {
                    if (this.velocidadMutacion > Math.random()) {                        
                        int nuevoGen =  rand.nextInt((9 - 1) + 1) + 1;
                        individuo.setGen(indexGen, nuevoGen);
                    }
                }
            }
            nuevaPoblacion.setIndividuo(indicePoblacion, individuo);
        }

        return nuevaPoblacion;
    }

    public int getTamPoblacion() {
        return tamPoblacion;
    }

    public void setTamPoblacion(int tamPoblacion) {
        this.tamPoblacion = tamPoblacion;
    }

    public double getVelocidadMutacion() {
        return velocidadMutacion;
    }

    public void setVelocidadMutacion(double velocidadMutacion) {
        this.velocidadMutacion = velocidadMutacion;
    }

    public double getVelocidadRecombinacion() {
        return velocidadRecombinacion;
    }

    public void setVelocidadRecombinacion(double velocidadRecombinacion) {
        this.velocidadRecombinacion = velocidadRecombinacion;
    }

    public int getElitismo() {
        return elitismo;
    }

    public void setElitismo(int elitismo) {
        this.elitismo = elitismo;
    }    
}