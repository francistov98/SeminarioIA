package handsOn6;
import jade.core.Agent;
import jade.core.behaviours.*;
/**
 *
 * @author Francisco Tovar
 */
public class MaxOnesAgent extends Agent {
    
    public double probMutacion = 0.25;
    public double probRecom = 0.8;
    public final Integer tamPoblacion = 10;
    public final Integer genes = 10;
    public Integer[][] individuos = new Integer[tamPoblacion][genes];
    public Integer genesMutados = 1; 
    public Integer genMax = 1000;
        
    protected void setup() {
        System.out.println("Agente " + getLocalName() + " ha iniciado.");
        addBehaviour(new iniciarAlgoritmo());
    }

    private class iniciarAlgoritmo extends OneShotBehaviour {
        public void action() {
            iniciar();
        }
        public int onEnd() {
            myAgent.doDelete();   
            return super.onEnd();
        }
    }
    
    public void iniciar(){
        iniciarPoblacion();
        
		for(Integer gen = 0; gen < genMax; gen++) {
			Integer id[] = padres();
			for(Integer i = 0; i < tamPoblacion; i++) {
				if(i == id[0] || i == id[1]) continue; 
				if(Math.random() < probRecom) { 
					Integer[] hijo = recombinar(individuos[id[0]], individuos[id[1]]); 
					if(Math.random() < probMutacion) mutar(hijo);					
					if(adaptabilidad(individuos[i]) < adaptabilidad(hijo)) individuos[i] = hijo; 
				}
			}
                        
			Integer mejor = mejorIndividuo();
			Integer mejorAdap = adaptabilidad(individuos[mejor]);
			System.out.println("> Generacion: " + gen);
			System.out.print("> Mejor adaptabilidad: " + mejorAdap + "\n\n");
                        
                        if(mejorAdap.equals(genes)) { 
				System.out.print("");
				for(Integer i = 0; i < genes; i++) System.out.print(individuos[mejor][i] + " ");
				break;
                        }
                }
    }

	public void iniciarPoblacion() {
		for(Integer i = 0; i < tamPoblacion; i++) {
			for(Integer j = 0; j < genes; j++) {
				individuos[i][j] = (Math.random() < 0.5 ? 1 : 0);
			}
		}
	}
	
	public void mutar(Integer gen[]) {
		for(Integer i = 0; i < genesMutados; i++) {
			Integer g = valorRandom(0,genes-1);
			gen[g] = (gen[g] == 0 ? 1 : 0);
		}
	}
	
	public Integer adaptabilidad(Integer gen[]) {
		Integer adap = 0;
		for(Integer i = 0; i < genes; i++) {
			adap += gen[i];
		}
		return adap;
	}
	
	public Integer[] recombinar(Integer[] padre1, Integer[] padre2) {
		Integer[] hijo = new Integer[genes];
		Integer puntoRecom = valorRandom(1,genes);
		for(Integer i = 0; i < puntoRecom; i++) hijo[i] = padre1[i];
		for(Integer i = puntoRecom; i < genes; i++) hijo[i] = padre2[i];
		return hijo;
	}
	
	public Integer mejorIndividuo() {
		Integer id = 0;
		for(Integer i = 0; i < tamPoblacion; i++) id = (adaptabilidad(individuos[i]) > adaptabilidad(individuos[id]) ? i : id);
		return id;
	}
    
	public Integer[] padres() {
		Integer id[] = new Integer[2];
		id[0] = 0;
		id[1] = 0;
		for(Integer i = 0; i < tamPoblacion; i++) {
			if(adaptabilidad(individuos[i]) > adaptabilidad(individuos[id[0]])) id[0] = i;
		}
		for(Integer i = 0; i < tamPoblacion; i++) {
			if(i == id[0]) continue;
			if(adaptabilidad(individuos[i]) > adaptabilidad(individuos[id[1]])) id[1] = i;
		}
		return id;
	}
	
	public Integer valorRandom(Integer min, Integer max) {
		return (min + (int) (Math.random() * ((max - min) + 1)));
    }
    
}