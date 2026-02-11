import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class State{
    private int x;
    private int y;
    private List<State> cami;
    private float heuristica;

    public State(int x, int y, List<State> cami, float heuristica){
        this.x = x;
        this.y = y;
        this.cami = new ArrayList<>(cami);
        this.heuristica = heuristica;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        State state = (State) other;
        return x == state.x && y == state.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public float getHeuristica(){
        return heuristica;
    }

    public List<State> getCami(){
        return cami;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void setHeuristica(float heuristica){
        this.heuristica = heuristica;
    }
}
