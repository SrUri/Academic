public class Heuristics {
    public static float Heuristic1(State currentState, State targetState, float[][] map){
        int x = Math.abs(currentState.getX() - targetState.getX());
        int y = Math.abs(currentState.getY() - targetState.getY());
        return (x + y)*5;
    }

    public static float Heuristic2(State currentState, State targetState, float[][] map){
        float totalCost = 0;
        int x = currentState.getX();
        int y = currentState.getY();
        int targetX = targetState.getX();
        int targetY = targetState.getY();

        while (x != targetX || y != targetY) {
            float cost = map[x][y];
            totalCost += cost;
            if (x < targetX) {
                x++;
            } else if (x > targetX) {
                x--;
            }

            if (y < targetY) {
                y++;
            } else if (y > targetY) {
                y--;
            }
        }
        return totalCost;
    }

    public static float Heuristic3(State currentState, State targetState, float[][] map){
        float heuristica1 = Heuristic1(currentState, targetState, map);
        float heuristica2 = Heuristic2(currentState, targetState, map);
        return heuristica1 + heuristica2;
    }
}
