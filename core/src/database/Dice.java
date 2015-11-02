package database;

import java.util.Random;

public class Dice {
	Random generator = new Random();
	//int result=0;
	int throDice(){
		//rzut dwoma koœæmi
		return generator.nextInt(11)+1;
		//result=generator.nextInt(5)+1+generator.nextInt(5)+1;
		//return result;
		//					0-11+1=1-12
		//return generator.nextInt(11)+1; alternatywa krótsza
	}
}
