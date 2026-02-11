/** 
 * Sessió de laboratori 3.
 * Treballant amb els punts.
 * 
 * @author Professores programació
 * @version 1.0
 *
 */

public class UsaPunt {

  public static void main(String[] args) {
    Punt p1 = new Punt();
    Punt p2 = new Punt(8, 9);

    Punt p3;
    p3 = p2.copia();

    Punt p4;
    p4 = p1;
   
    if (p3.iguals(p2))
        System.out.println("Punts iguals ");

    p3.setX(4);
    p3.setY(7);

    if (p3.iguals(p2))
        System.out.println("Punts iguals ");
    else
        System.out.println("Punts no iguals ");

    if (p4.iguals(p1))
        System.out.println("Punts iguals ");

    p4.setX(1);
    p4.setY(5);
    if (p4.iguals(p1))
        System.out.println("Punts iguals ");
    else
        System.out.println("Punts no iguals ");



    /* a descomentar per provar l'exercici de modificadors public/private de la sessió


    Punt p = new Punt(15,30);
   
    p.setX(-30);
    p.setY(90);
    System.out.println(p.getX() + " " + p.getY());
   
    p.x=-40;
    p.y=120;

    System.out.println(p.getX() + " " + p.getY());

	*/
	
  }
}
