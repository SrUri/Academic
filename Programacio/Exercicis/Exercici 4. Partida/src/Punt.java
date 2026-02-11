/** 
 * Sessió de laboratori 3.
 * Classe Punt: permet definir i operar amb punts de dos dimensions en l'espai euclideo
 * 
 * @author Professores programació
 * @version 1.0
 * 
 */
public class Punt {
  private int x, y;
  
  private static int xMin=10, xMax=40;
  private static int yMin=20, yMax=70;
  /** Constructor per defecte
   * 
   */
  public Punt() {
      x=xMin; y=yMin;
  }

  /** Constructor que rep el valor de les coordenades x e y del punt
   * 
   * @param x és el valor de la component x del punt
   * @param y és el valor de la component y del punt
   */
  public Punt(int x, int y) 
  	if(xEnRangX(x) && yEnRangY(y))
  	{
  			this.x=x;
  			this.y=y; 
  	} else
  		this.x=xMin;
  		this.y=yMin;
  this.y=y;
  }

	private boolean yEnRangY (int y) {
		return (y >= yMin && y <= yMax);
	}
	private boolean xEnRangX (int x) {
		return (x >= xMin && x <= xMax);
	}
  /** setter
   * 
   * @param x és el valor de la component x del punt
   */
  public void setX(int x) {
	this.x=x;
  }

  /** setter
   * 
   * @param y és el valor de la component x del punt
   */
  public void setY(int y) {
	this.y=y;
  }

  /** 
   * getter
   * @return retorna la component x del punt
   */
  public int getX() {
      return(x);
  }
  
  /** 
   * getter
   * @return retorna la component y del punt
   */
  public int getY() {
      return(y);
  }

  /** 
   * mètode que crea una copia de l'objecte actual
   * @return retorna una nova instància de la classe Punt amb el mateix contingut que l'objecte actual
   */
  public Punt copia() {
	Punt aux=new Punt(x,y);
	return aux;
  }
  
  /** 
   * mètode que comprova si l'objecte actual té el mateix contingut al que rep per paràmetre
   * @param p és la referencia de l'objecte amb el que es vol comparar el contingut de l'objecte actual
   * @return retorna cert o fals segons si els continguts coincideixen
   */
  public boolean iguals(Punt p) {
      return ((p.x==x) && (p.y==y));
  }
  
  /** 
   * mètode que calcula la distància entre l'objecte actual i el que rep per paràmetre
   * @param p és la referencia de l'objecte amb el que es vol calcular la distància respecte a l'objecte actual
   * @return retorna la distancia entera entre els dos punts
   */
  public int distancia(Punt p) {
      double d;
      d = Math.sqrt((p.x-x)*(p.x-x)+(p.y-y)*(p.y-y));
      return((int)d);
  }

  public String toString() {
	return "Punt => ("+x+", "+y+")";
  }
  
  public static void ampliaFinestra (int xMin, int xMax, int yMin, int yMax) {
	  Punt.xMin=xMin;
	  Punt.xMax=xMax;
	  Punt.yMin=yMin;
	  Punt.yMax=yMax;
  }
}