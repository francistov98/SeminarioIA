package codigoHO4;
import jade.core.Agent;
import jade.core.behaviours.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static java.util.Arrays.asList;
import java.util.Scanner;

/**
 *
 * @author Francisco Tovar
 */
public class RLSAgent extends Agent {

    private static final List<Integer> x = asList(23, 26, 30, 34, 43, 48, 52, 57, 58); // Advertising
    private static final List<Integer> y = asList(651, 762, 856, 1063, 1190, 1298, 1421, 1440, 1518); // Ventas

    protected void setup() {
        System.out.println("Agente " + getLocalName() + " ha iniciado.");
        addBehaviour(new calcularRegresion());

    }

    private void predecirValor(int valor) {
        if (x.size() != y.size()) {
            throw new IllegalStateException("Los datos deben ser pares ordenados");
        }

        Integer cantidadDatos = x.size();

        List<Double> xCuadrada = x
                .stream()
                .map(position -> Math.pow(position, 2))
                .collect(Collectors.toList());

        List<Integer> productoXY = IntStream.range(0, cantidadDatos)
                .map(i -> x.get(i) * y.get(i))
                .boxed()
                .collect(Collectors.toList());

        Integer sumX = x
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer sumY = y
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Double sumXCuadrada = xCuadrada
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        Integer sumXY = productoXY
                .stream()
                .reduce((prev, next) -> prev + next)
                .get();

        int pendienteNom = cantidadDatos * sumXY - sumY * sumX;
        double pendienteDen = cantidadDatos * sumXCuadrada - Math.pow(sumX, 2);
        Double pendiente = pendienteNom / pendienteDen;

        double intercepNom = sumY - pendiente * sumX;
        double intercepDen = cantidadDatos;
        Double intercepto = intercepNom / intercepDen;
        
        double y=(pendiente * valor) + intercepto;

        System.out.println("El valor de Y estimado con X= " + valor + " es: " + y );
        System.out.println("Pendiente (b1): " + pendiente);
        System.out.println("Intercepto (b0): " + intercepto);
        System.out.println("Ecuacion de regrresion: (" + pendiente + ")( " + valor + " ) + " + intercepto + " = " + y);
    }

    private class calcularRegresion extends OneShotBehaviour {

        public void action() {
            Scanner entrada = new Scanner(System.in);
            System.out.println("Ingresa un valor para X: ");
            int valorIngresado = entrada.nextInt();
            predecirValor(valorIngresado);
        }
        public int onEnd() {
            myAgent.doDelete();   
            return super.onEnd();
        }
    } 
}