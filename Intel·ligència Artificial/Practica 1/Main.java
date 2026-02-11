import java.util.ArrayList;

public class Main {
    public static char[][] OriginalCharMap = {
      {'P','N','N','N','P','P','P','P','P','P'},
      {'P','N','N','N','M','M','P','P','N','P'},
      {'P','N','N','N','M','M','N','N','N','P'},
      {'P','A','A','A','A','A','A','N','N','N'},
      {'P','N','A','C','A','A','A','A','A','N'},
      {'P','A','A','C','M','C','C','A','A','A'},
      {'P','A','M','A','M','M','C','A','A','A'},
      {'A','A','M','A','M','C','C','P','M','P'},
      {'A','A','M','C','M','C','P','P','P','P'},
      {'A','A','C','C','M','C','C','C','C','C'},
    };
    public static Map OriginalMap = new Map(OriginalCharMap);

    public static char[][] CustomCharMap = {
      {'N','A','C','N','A'},
      {'M','M','P','P','N'},
      {'A','A','C','P','M'},
      {'N','P','N','N','M'},
      {'P','M','P','A','C'},
    };
    public static Map CustomMap = new Map(CustomCharMap);

    public static void main(String args[]){

      Map map = OriginalMap;

      State estatInicial = new State(0,0, new ArrayList<>(), 0);
      State estatObjectiu = new State(9,9, new ArrayList<>(), 0);

      Heuristic[] heuristics = new Heuristic[3];
      heuristics[0] = Heuristics::Heuristic1;
      heuristics[1] = Heuristics::Heuristic2;
      heuristics[2] = Heuristics::Heuristic3;
      
      Search[] searches = new Search[6];
      searches[0] = new BestFirst(map.getCostMap(), heuristics[0]);
      searches[1] = new A(map.getCostMap(), heuristics[0]);
      searches[2] = new BestFirst(map.getCostMap(), heuristics[1]);
      searches[3] = new A(map.getCostMap(), heuristics[1]);
      searches[4] = new BestFirst(map.getCostMap(), heuristics[2]);
      searches[5] = new A(map.getCostMap(), heuristics[2]);
      float costTotal = 0;
      for (int i = 0; i < searches.length; i++) {
        costTotal = searches[i].DoSearch(estatInicial, estatObjectiu);
        System.out.println("Search " + (i+1) + " solucio: " + costTotal);
      }

      map = CustomMap;

      estatInicial = new State(0,0, new ArrayList<>(), 0);
      estatObjectiu = new State(4,4, new ArrayList<>(), 0);

      searches[0] = new BestFirst(map.getCostMap(), heuristics[0]);
      searches[1] = new A(map.getCostMap(), heuristics[0]);
      searches[2] = new BestFirst(map.getCostMap(), heuristics[1]);
      searches[3] = new A(map.getCostMap(), heuristics[1]);
      searches[4] = new BestFirst(map.getCostMap(), heuristics[2]);
      searches[5] = new A(map.getCostMap(), heuristics[2]);

      costTotal = 0;
      for (int i = 0; i < searches.length; i++) {
        costTotal = searches[i].DoSearch(estatInicial, estatObjectiu);
        System.out.println("Search " + (i+1) + " solucio: " + costTotal);
      }
    }
}


