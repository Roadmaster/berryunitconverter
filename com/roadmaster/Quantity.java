/*
* Copyright Daniel Manrique (roadmr@tomechangosubanana.com),2007
 * Helper class for a program that  converts from/to measurement units. Its official name is BerryUnitConverter
 but a full name should be "blackberry unit converter". A webpage is at http://tomechangosubanana.com/berryunitconverter
 

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 
*/

package com.roadmaster;

//import java.util.*;

class Converter{
		public static float convert(Unit from, Unit to, float quantity){
			float temporal = from.fromThisUnit(quantity);
			float result = to.toThisUnit(temporal);
			return result;
		}
}



class Unit{
        /*
         * A unit defines conversion
         * factors to the quantity's SI canonical unit. It defines an offset (m)
         * and a multiplication factor (b). I'm assuming all measurement units are linear
         * and thus can be abstracted as a straight line's equation y = mx+b.
         * For instance addUnit("feet",new Float(0.3048),new Float(0.0))
         * since in order to convert from feet to meters you multiply your feet by 0.3048.
         * */
        private String name;
        private float m;
        private float b;
		private String magnitude;
		Unit(String name,String magnitude,float m, float b){
			initialize(name,magnitude,m,b);    
		}
		Unit(String name,String magnitude,double m, double b){
			initialize(name,magnitude,(float)m,(float)b);    
		}

		private void initialize(String name, String magnitude, float m, float b)
		{
			this.m = m;
			this.b = b;
			this.magnitude=magnitude;
			this.name = name;

		}
		String getMagnitude(){
			return magnitude;
		}
		String getName(){
			return name;
		}

		public String toString(){
			return name;
		}

		/* Converts "convert" basic units to this unit.
		 * eg. if working with distance and THIS unit is 
		 * a foot, toThisUnit(2) would convert 2 metres to feet (2/0.3048).
		 * if we have y=mx+b then this function takes "y" and does x=(y-b)/m*/
		float toThisUnit(float y){
			return (y - b)/ m;

		}

		/* Converts "convert" units of this measuring unit to the basic unit.
		 * eg. if working with distance and THIS unit is a foot, 
		 * fromThisUnit(2) would convert 2 feet to metres (2*0.3048).
		 * this takes "x" and does y=mx+b*/
		float fromThisUnit(float x){
			return (m*x)+b;
		}


}


