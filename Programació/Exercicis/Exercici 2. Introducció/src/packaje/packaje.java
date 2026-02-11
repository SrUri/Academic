package packaje;

import java.util.Scanner;


public class packaje {
	static Scanner teclat=new Scanner(System.in);
	public static void main(String[] args) {
		int[] t1= {1,4,3,5,8,19,71,71,7,7,8,4,3,3};
		int[] t3;
		int nElem;
		
		System.out.println("Indica el numero d'elements de la taula");
		nElem=teclat.nextInt();
		t3=new int[nElem];
		llegirValorsTaula(t3);
		
		int max=maximValorTaula(t1);
		System.out.println("El maxim de la taula es "+max);
		max=maximValorTaula(t3);
		System.out.println("El maxim de la taula 3 es "+max);
		

	}
	private static void llegirValorsTaula(int [] t) {
		
		int i;
		int cmpic=0;
		int cmvall=0;
		
		for(i=0; i<t.length; i++)
		{
			t[i]=teclat.nextInt();
		}
		
		for(i=1; i<t.length-1; i++) 
		{
			if((t[i]>t[i-1])&&(t[i]>t[i+1]))
			{
				cmpic++;
			}
			if((t[i]<t[i-1])&&(t[i]<t[i+1]))
			{
				cmvall++;
			}
		}
			
		
		System.out.println("Hi ha "+cmpic+" pics a la taula");
		System.out.println("Hi ha "+cmvall+" valls a la taula");
	}
	
	private static int maximValorTaula(int[] t) {
		int maxim=t[0];
		for(int i=1; i<t.length; i++)
			if(t[i]>maxim)
				maxim=t[i];
		
		return maxim;
	}
	

}
