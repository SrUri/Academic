import java.util.ArrayList;
import java.util.List;

public abstract class Search {
    protected float[][] costMap;
    protected Heuristic heuristic;

    public Search(float[][] costMap, Heuristic heuristic){ 
        this.costMap = costMap;
        this.heuristic = heuristic;
    }

    public float DoSearch(State initialState, State targetState){
        return 0;
    }

    protected List<State> EvaluateOperators(State currentState, State targetState){
        List<State> estatsSeguents = new ArrayList<>();
        int[][] moves = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] move : moves) {
            int newX = currentState.getX() + move[0];
            int newY = currentState.getY() + move[1];
            if (posicioValida(newX, newY)) {
                List<State> nouCami = new ArrayList<>(currentState.getCami());
                nouCami.add(currentState);
                State seguentEstat = new State(newX, newY, nouCami, 0);
                seguentEstat.setHeuristica(this.heuristic.Evaluate(seguentEstat, targetState, costMap));
                estatsSeguents.add(seguentEstat);
            }
        }
        return estatsSeguents;
    }

    public boolean posicioValida(int x, int y) {
        return x >= 0 && x < costMap.length && y >= 0 && y < costMap[0].length && costMap[x][y] != -1;
    }

    public float obtenirCostTotal(List<State> solucio) {
        float costTotal = costMap[solucio.get(0).getX()][solucio.get(0).getY()];
        for (int i = 1; i < solucio.size(); i++) {
            costTotal += costMap[solucio.get(i).getX()][solucio.get(i).getY()];
        }
        return costTotal;
    }
}
