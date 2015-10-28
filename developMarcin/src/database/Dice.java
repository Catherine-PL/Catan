package database;

import java.util.Random;

public class Dice {
	Random generator = new Random();
	int wynik=0;
	int throwDice(){
		//rzut dwoma koœæmi
		
		wynik=generator.nextInt(5)+1+generator.nextInt(5)+1;
		return wynik;
		//					0-11+1=1-12
		//return generator.nextInt(11)+1; alternatywa krótsza
	}
}
