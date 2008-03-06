

//package com.roadmaster;

import java.util.*;
class TestConversion{


	public static void main(String args[])
		{
			System.out.println("caquita podrida");

			Vector unidades = new Vector();

			unidades.add( new  Unit("kilogram","weight",1.0,0.0));
			unidades.add( new  Unit("pound","weight",0.45,0.0));
			unidades.add( new  Unit("Celsius","temperature",1.0,0.0));
			unidades.add( new  Unit("Kelvin","temperature",1.0,-273.0));
			unidades.add( new  Unit("Fahrenheit","temperature",5.0/9.0,-(160.0/9.0)));
			unidades.add( new  Unit("Kilometer","length",1000,0.0));
			unidades.add( new  Unit("Meter","length",1.0,0.0));
			unidades.add( new  Unit("Mile","length",1608.0,0));
			unidades.add( new  Unit("Inch","length",(100.0/2.54),0));
			unidades.add( new  Unit("Centimeter","length",0.01,0));
			
			HashMap grupos = new HashMap();

		
			for (Iterator it = unidades.iterator();it.hasNext();){
				Unit thisone = (Unit) it.next();
				if (grupos.containsKey( thisone.getMagnitude())){
					Vector v = (Vector)grupos.get(thisone.getMagnitude());
					v.add(thisone);
				}
				else{
					Vector v = new Vector();
					v.add(thisone);
					grupos.put(thisone.getMagnitude(),v);
					
				}
			}

			// iterar llaves
			for (Iterator it = grupos.keySet().iterator(); it.hasNext();){
				Object nombregrupo= it.next();
				System.out.println(nombregrupo);
				Vector grupo = (Vector) grupos.get(nombregrupo);

				for (ListIterator i2 = grupo.listIterator();i2.hasNext();){
					Object unit = i2.next();
					System.out.println("  "+unit);
				}
			}
		}
