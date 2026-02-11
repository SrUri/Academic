import java.util.*;

public class A extends Search {
    private float[][] costMap;

    public A(float[][] costMap, Heuristic heuristic) {
        super(costMap, heuristic);
        this.heuristic = heuristic;
        this.costMap = costMap;
    }

    @Override
    public float DoSearch(State initialState, State targetState) {
        PriorityQueue<State> pendents = new PriorityQueue<>(Comparator.comparingDouble(State::getHeuristica));
        Set<State> tractats = new HashSet<>();
        float costTotal = 0;
        boolean trobat = false;
        pendents.add(initialState);
        while (!pendents.isEmpty() && !trobat) {
            State actual = pendents.poll();
            List<State> camiActual = new ArrayList<>(actual.getCami());
            camiActual.add(actual);
            tractats.add(actual);
            if (actual.equals(targetState)) {
                trobat = true;
                costTotal = obtenirCostTotal(camiActual);
            } else {
                List<State> successors = EvaluateOperators(actual, targetState);
                for (State successor : successors) {
                    if (!tractats.contains(successor)) {
                        float costCamí = obtenirCostTotal(camiActual) + costMap[successor.getX()][successor.getY()];
                        if (!pendents.contains(successor)) {
                            pendents.add(new State(successor.getX(), successor.getY(), camiActual, costCamí));
                        } else {
                            State estatExistent = mirarEstat(successor, pendents);
                            if (costCamí < obtenirCostTotal(estatExistent.getCami())) {
                                pendents.remove(estatExistent);
                                pendents.add(new State(successor.getX(), successor.getY(), camiActual, costCamí));
                            }
                        }
                    }
                }
                
            }
        }
        return costTotal;
    }

    private State mirarEstat(State state, PriorityQueue<State> queue) {
        for (State s : queue) {
            if (s.equals(state)) {
                return s;
            }
        }
        return null;
    }
}
