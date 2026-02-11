public class Cost implements Heuristic{
    @Override
    public float Evaluate(State currentState, State targetState, float[][] map) {
        return Heuristics.Heuristic2(currentState, targetState, map);
    }
}
