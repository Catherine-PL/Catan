package database;
//chyba ¿e tu singleton walniemy
/*
 * do przemyœlenia logika tej klasy
 */
public class SpecialCard extends Element {
	private String type;
	private static int count;
	
	SpecialCard(int i){
		String army="Largest army",road="Longest road";
		if(count<2){
			
		if(i==1) setType(army);
		if(i==2) setType(road);
		
		count++;
		}
	}
	private void setType(String type){
		this.type=type;
	}
	public String getType(){
		return this.type;
	}
}
