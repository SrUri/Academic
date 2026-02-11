import java.util.*;

public class BestFirst extends Search {
    public BestFirst(float[][] costMap, Heuristic heuristic) {
        super(costMap, heuristic);
        this.heuristic = heuristic;
        this.costMap = costMap;
    }

    @Override
    public float DoSearch(State initialState, State targetState) {
        PriorityQueue<State> pendents = new PriorityQueue<>(Comparator.comparingDouble(State::getHeuristica));
        Set<State> tractats = new HashSet<>();
        List<State> solucio = new ArrayList<>();
        float costTotal = 0;
        boolean trobat = false;
        pendents.add(initialState);
        while (!pendents.isEmpty() && !trobat) {
            State actual = pendents.poll();
            tractats.add(actual);
            if (actual.equals(targetState)) {
                trobat = true;
                solucio = actual.getCami();
                costTotal = obtenirCostTotal(solucio);
            } else {
                List<State> seguentEstat = EvaluateOperators(actual, targetState);
                for (State estat : seguentEstat) {
                    if (!tractats.contains(estat) && !pendents.contains(estat)) {
                        pendents.add(estat);
                    }
                }
            }
        }
        return costTotal;
    }
}
