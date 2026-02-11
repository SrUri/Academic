public class Manhattan implements Heuristic {
    @Override
    public float Evaluate(State currentState, State targetState, float[][] map) {
        return Heuristics.Heuristic1(currentState, targetState, map);
    }
}
