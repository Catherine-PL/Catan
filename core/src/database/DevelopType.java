package database;

import java.util.Random;

public enum DevelopType {
	
		YEAR,POINT,SOLDIER,ROAD,MONOPOL;
	
		
		static DevelopType randomDevelopType() {
			Random generator=new Random();
			int index=generator.nextInt(DevelopType.values().length);		   
		    return DevelopType.values()[index];
		}
}
