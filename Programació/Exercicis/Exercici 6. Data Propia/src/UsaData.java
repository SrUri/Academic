
public class UsaData {
	private static void mostraData(Data data){
		System.out.println(data.getDia() + "/" + data.getMes() + "/" + data.getAny());
	}
	public static void main(String[] args) {
		Data data, data2;
		
		
		System.out.println("Constructor sense parametres");
		data = new Data();
		mostraData(data);
		
		System.out.println("Constructor amb parametres, data correcta");
		data = new Data(27, 7, 2011);
		mostraData(data);
		
		System.out.println("Constructor amb parametres, data incorrecta");
		data = new Data(29, 2, 2011);
		mostraData(data);
		
		System.out.println("Constructor sense parametres i usem el setData, data correcta");
		data = new Data();
		data.setData(27, 7, 2011);
		mostraData(data);
		
		System.out.println("Constructor sense parametres i usem el setData, data incorrecta");
		data = new Data();
		data.setData(29, 2, 2011);
		mostraData(data);
		
		System.out.println("Dia seguent");
		data = new Data(27, 7, 2011);
		data.diaSeguent();
		mostraData(data);
		
		System.out.println("Dia seguent en un febrer no any de traspas");
		data = new Data(28, 2, 2011);
		data.diaSeguent();
		mostraData(data);
		
		System.out.println("Dia seguent en un febrer any de traspas");
		data = new Data(28, 2, 2012);
		data.diaSeguent();
		mostraData(data);
		
		System.out.println("Dia anterior en un febrer no any de traspas");
		data = new Data(1, 3, 2011);
		data.diaAnterior();
		mostraData(data);
		
		System.out.println("Dia anterior en un febrer any de traspas");
		data = new Data(1, 3, 2012);
		data.diaAnterior();
		mostraData(data);
		
		System.out.println("comprova si dos dates son iguals, dates iguals");
		data = new Data(27, 7, 2011);
		data2 = new Data(27, 7, 2011);
		System.out.println(data.esIgual(data2));
		
		System.out.println("comprova si dos dates son iguals, dates diferents");
		data = new Data(27, 7, 2011);
		data2 = new Data(28, 7, 2011);
		System.out.println(data.esIgual(data2));
		
		System.out.println("donada una data comprova si es any de traspas, any de traspas ");
		data = new Data(27, 7, 2012);
		System.out.println(data.esDataAnyTraspas());
		
		System.out.println("donada una data comprova si es any de traspas, no any de traspas ");
		data = new Data(27, 7, 2011);
		System.out.println(data.esDataAnyTraspas());
		
		System.out.println("nombre de dies entre dos dates, LA DATA per paràmetre SEMPRE ES MAJOR que l'actual");
		data = new Data(27, 7, 2011);
		data2 = new Data(27, 8, 2011);
		System.out.println(data.numDiesAData(data2));
		
		System.out.println("nombre de dies entre dos dates, dates iguals");
		data2 = new Data(27, 7, 2011);
		System.out.println(data.numDiesAData(data2));
		

		System.out.println("nombre de dies entre dos dates, un any de diferencia any de traspas");
		data = new Data(27, 2, 2012);
		data2 = new Data(27, 2, 2013);
		System.out.println(data.numDiesAData(data2));
		System.out.println("nombre de dies entre dos dates, un any de diferencia no any de traspas");
		data = new Data(27, 2, 2010);
		data2 = new Data(27, 2, 2011);
		System.out.println(data.numDiesAData(data2));
		System.out.println("nombre de dies des de l'inici de la nostra era");
		data2 = new Data(27, 7, 2011);
		data = new Data(1, 1, 1);
		System.out.println(data.numDiesAData(data2));
		
		System.out.println("nombre de dies quan la data sobre la que es crida no és inferior a la que es passa per paràmetre");
		data = new Data(27, 2, 2014);
		data2 = new Data(27, 2, 2013);
		System.out.println(data.numDiesAData(data2));
		
		System.out.println(" i per comprovar si una data és inferior o igual a una altra que es passa per paràmetre");
		data = new Data(27, 7, 2010);
		data2 = new Data(27, 7, 2010);
		if (data.esDataInferiorOigual(data2))
			System.out.println(data+" es inferior o igual a "+data2);
		else
			System.out.println(data2+" es inferior o igual a "+data);
	
		data = new Data(27, 7, 2014);
		data2 = new Data(27, 7, 2011);
		if (data.esDataInferiorOigual(data2))
			System.out.println(data+" es inferior o igual a "+data2);
		else
			System.out.println(data2+" es inferior o igual a "+data);
		
		data = new Data(29, 7, 2010);
		data2 = new Data(27, 7, 2010);
		if (data.esDataInferiorOigual(data2))
			System.out.println(data+" es inferior o igual a "+data2);
		else
			System.out.println(data2+" es inferior o igual a "+data);
		
	}
}
