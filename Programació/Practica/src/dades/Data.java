package dades;

public class Data {
    private int dia, mes, any;
    
    public Data(int dia, int mes, int any){
        this.dia = dia;
        this.mes = mes;
        this.any = any;
    }

    public Data(){
        this.dia = 1;
        this.mes = 1;
        this.any = 2023;
    }

    public String toString() {
		return("dia: "+dia+" mes: "+mes+" any: "+any);
	}

	public Data copia() {
		return new Data(dia, mes, any);
	}
}
