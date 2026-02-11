public class Combinada implements Heuristic{
    @Override
    public float Evaluate(State currentState, State targetState, float[][] map) {
        return Heuristics.Heuristic3(currentState, targetState, map);
    }
}
