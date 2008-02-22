/*
 * BerryUnitConverter.java
 *
 * Copyright Daniel Manrique (roadmr@tomechangosubanana.com),2007
 * This program converts from/to measurement units. Its official name is BerryUnitConverter
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

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.container.*;
import net.rim.device.api.system.*;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
/*
 * BlackBerry applications that provide a user interface
 * must extend UiApplication.
 */
public class UnitConverter extends UiApplication
{
        public static void main(String[] args)
        {
                //create a new instance of the application
                //and start the application on the event thread
                UnitConverter theApp = new UnitConverter();
                theApp.enterEventDispatcher();
        }

        public UnitConverter()
        {
                //display a new screen
                pushScreen(new UnitConverterMainScreen());
        }
}

//create a new screen that extends MainScreen, which provides
//default standard behavior for BlackBerry applications
final class UnitConverterMainScreen extends MainScreen
        implements FieldChangeListener
{
        private  AboutScreen  _aboutScreen;
        private ObjectChoiceField quantityField;
        private ObjectChoiceField fromUnitField;
        private ObjectChoiceField toUnitField;
        private EditField         amountField;
        private LabelField        resultField;
		private Hashtable		groups;
		private Hashtable		temp_groups;
		private String[]	magnitudes;
		
        public UnitConverterMainScreen()
        {
                //invoke the MainScreen constructor
                super();

                //add a title to the screen
                LabelField title = new LabelField("Berry Unit Converter",
                                LabelField.ELLIPSIS | LabelField.USE_ALL_WIDTH);
                setTitle(title);

				// since I'm lazy I'm adding all units to a global list,
				// then implementing logic to classify them by magnitude.
				Vector main_unit_list= new Vector();

				main_unit_list.addElement( new  Unit("Kilogram","Weight",1.0,0.0));
				main_unit_list.addElement( new  Unit("Gram","Weight",0.001,0.0));
				main_unit_list.addElement( new  Unit("Carat","Weight",0.0002,0.0));
				main_unit_list.addElement( new  Unit("Miligram","Weight",0.000001,0.0));
				main_unit_list.addElement( new  Unit("Pound","Weight",0.45,0.0));
				main_unit_list.addElement( new  Unit("Stone","Weight",0.45,0.0));
				main_unit_list.addElement( new  Unit("Ounce","Weight",0.45,0.0));
				main_unit_list.addElement( new  Unit("Arroba","Weight",11.5023,0.0));
				main_unit_list.addElement( new  Unit("Quintal","Weight",4.0*11.5023,0.0));
				
				main_unit_list.addElement( new  Unit("Celsius",		"Temperature",1.0,0.0));
				main_unit_list.addElement( new  Unit("Fahrenheit",	"Temperature",5.0/9.0,-(160.0/9.0)));
				main_unit_list.addElement( new  Unit("Kelvin",		"Temperature",1.0,-273.0));
			
				main_unit_list.addElement( new  Unit("Kilometer","Length",1000,0.0));
				main_unit_list.addElement( new  Unit("Meter","Length",1.0,0.0));
				main_unit_list.addElement( new  Unit("Mile","Length",1608.0,0));
				main_unit_list.addElement( new  Unit("Inch","Length",(100.0/2.54),0));
				main_unit_list.addElement( new  Unit("Centimeter","Length",0.01,0));
				main_unit_list.addElement( new  Unit("league","Length",4828,0));
				main_unit_list.addElement( new  Unit("Rod","Length",5.029,0));
				main_unit_list.addElement( new  Unit("Yard","Length",0.9144,0));
				main_unit_list.addElement( new  Unit("Foot","Length",0.3048,0));
				main_unit_list.addElement( new  Unit("Hand","Length",0.1016,0));


				main_unit_list.addElement( new  Unit("Liter","Volume",1.0,0.0));
				main_unit_list.addElement( new  Unit("Cubic meter","Volume",1000,0.0));
				main_unit_list.addElement( new  Unit("mililiter","Volume",.001,0.0));
				main_unit_list.addElement( new  Unit("Gallon","Volume",3.785,0.0));
				main_unit_list.addElement( new  Unit("Quart","Volume",0.9464,0.0));
				main_unit_list.addElement( new  Unit("Pint","Volume",0.4732,0.0));
				main_unit_list.addElement( new  Unit("Fl. Ounce","Volume",0.02957,0.0));
				main_unit_list.addElement( new  Unit("Bushel","Volume",35.24,0.0));
				main_unit_list.addElement( new  Unit("Arroba","Volume",16.133,0.0));
				
				main_unit_list.addElement( new  Unit("Square meter","Area",1.0,0.0));
				main_unit_list.addElement( new  Unit("Square kilometer","Area",1000000.0,0.0));
				main_unit_list.addElement( new  Unit("Hectare","Area",10000.0,0.0));
				main_unit_list.addElement( new  Unit("Square centimeter","Area",0.0001,0.0));
				main_unit_list.addElement( new  Unit("Square millimeter","Area",0.000001,0.0));
				main_unit_list.addElement( new  Unit("Square mile","Area",2590000,0.0));
				main_unit_list.addElement( new  Unit("Acre","Area",4047.0,0.0));
				main_unit_list.addElement( new  Unit("Square foot","Area",.0929,0.0));
				main_unit_list.addElement( new  Unit("Square inch","Area",0.0006452,0.0));
				
				main_unit_list.addElement( new  Unit("Pascal","Pressure",1.0,0.0));
				main_unit_list.addElement( new  Unit("kilopascal","Pressure",1000,0.0));
				main_unit_list.addElement( new  Unit("Bar","Pressure",100000,0.0));
				main_unit_list.addElement( new  Unit("psi","Pressure",6895.0,0.0));
				main_unit_list.addElement( new  Unit("kg/cm2","Pressure",0.00001019716,0.0));
				main_unit_list.addElement( new  Unit("torr","Pressure",0.007500638,0.0));
				
				main_unit_list.addElement( new  Unit("Watt","Power",1.0,0.0));
				main_unit_list.addElement( new  Unit("Kilowatt","Power",1000,0.0));
				main_unit_list.addElement( new  Unit("Horsepower","Power",745.7,0.0));
				
				main_unit_list.addElement( new  Unit("Joule","Power",1.0,0.0));
				main_unit_list.addElement( new  Unit("Calorie","Power",4.187,0.0));
				main_unit_list.addElement( new  Unit("Kilowatt hour","Power",3600000.0,0.0));
				main_unit_list.addElement( new  Unit("BTU","Power",1055.0,0.0));
				main_unit_list.addElement( new  Unit("Kiloton","Power",4184000000000.0,0.0));

				main_unit_list.addElement( new  Unit("Second","Film",1.0,0.0));
				main_unit_list.addElement( new  Unit("Foot","Film",1.5,0.0));
				main_unit_list.addElement( new  Unit("Film frame","Film",24,0.0));
				main_unit_list.addElement( new  Unit("Meter","Film",0.4572,0.0));
				

				groups = new Hashtable();
				
				//Ugly temporary container variables

				Hashtable temp_groups = new Hashtable();
				Vector temp_magnitudes= new Vector();

				// This sucker is the classification loop
				for (Enumeration e=   main_unit_list.elements(); e.hasMoreElements();){
					Unit thisone =  (Unit) e.nextElement();
					String magnitude = thisone.getMagnitude();

					//Add the string to the magnitudes temporary vector 
					if (!temp_magnitudes.contains(magnitude)) temp_magnitudes.addElement(magnitude);	
					//Add the unit (thisone) to the vector corresponding to this magnitude.
					//A hashmap would be ideal here.
					//However a RIM hashtable will have to do.
					Vector group = (Vector) temp_groups.get(magnitude);
					// Maybe the value I got was null; in this case I have to first
					// create the vector.
					if (group == null){
						group = new Vector(); 
						temp_groups.put(magnitude,group);
					}
					// Now that group contains a valid vector, add the unit to it
					group.addElement(thisone);

				}
				// Now I perform some horrendous hacks to end up with arrays
				// instead of vectors. temp_magnitudes will disappear.  Also,
				// vector elements of groups hashtable will be replaced by
				// arrays.  here we should have the lists

				magnitudes = new String[temp_magnitudes.size()];
				temp_magnitudes.copyInto(magnitudes);
				// yucky iterator; at least I got to implement this using For, instead
				// of Java's uber lame built in iterators
				for (int i=0;i<magnitudes.length;i++){
					String key = magnitudes[i];
					Vector tempvec = (Vector) temp_groups.get(key);
					Unit[] grouparray = new Unit[tempvec.size()];
					tempvec.copyInto(grouparray);
					groups.put(key,grouparray);
				}
				// Here we have an array of keys, and a hash which contains, for each key,
				// an array of units.
				// Keys are in "magnitudes"; groups are in "groups"; each element of "groups"
				// is an array of Units.

				amountField = new EditField("Convert: ","1",10,EditField.FILTER_REAL_NUMERIC);
				add(amountField);
				amountField.setChangeListener(this);


				quantityField = new ObjectChoiceField("Group:",magnitudes,0 );
				quantityField.setChangeListener(this);
				add(quantityField);


				fromUnitField = new ObjectChoiceField("Convert from:", null);
				
				add(fromUnitField);

				toUnitField = new ObjectChoiceField("Convert to:",null);
				
				add(toUnitField);

				resultField = new LabelField("Result: ");
				add(resultField);
				this.fieldChanged(quantityField,FieldChangeListener.PROGRAMMATIC);

				toUnitField.setChangeListener(this);
				fromUnitField.setChangeListener(this);
		}

		//override the onClose() method to display a dialog box to the user
		//with "Goodbye!" when the application is closed
		public boolean onClose()
		{
			//Dialog.alert("Goodbye!");
			System.exit(0);
			return true;
		}

		public void fieldChanged(Field field, int context) { 

			if (field==quantityField ){
				String selectedMagnitude= magnitudes[quantityField.getSelectedIndex()];
				Unit[] group =  (Unit[]) this.groups.get(selectedMagnitude);
				fromUnitField.setChoices(group);
				fromUnitField.setSelectedIndex(0);
				toUnitField.setChoices(group);
				toUnitField.setSelectedIndex(1);

			}
			// update the conversion, regardless of which fields changed.
			updateConversion();
		}


		public void updateConversion(){
			try{
				float to_convert = Float.parseFloat(amountField.getText());	
				float converted = Converter.convert( 
						(Unit) fromUnitField.getChoice(fromUnitField.getSelectedIndex()),
						(Unit) toUnitField.getChoice(toUnitField.getSelectedIndex()),
						to_convert);

				resultField.setText("Result: "+new Float(converted).toString());
			}
			catch (NumberFormatException n){
				resultField.setText("(Invalid value)");
			}
		}

		//create a menu item for users to close the application
		private MenuItem _closeItem = new MenuItem("Close", 200000, 10) {
			public void run()
			{
				onClose();
			}
		};
		private MenuItem _aboutItem = new MenuItem("About", 110, 10) {
			public void run() {
				_aboutScreen = new AboutScreen();
				UiApplication.getUiApplication().pushScreen(_aboutScreen);
			}
		};

		//override makeMenu to add the new menu items
		protected void makeMenu( Menu menu, int instance )
		{
			menu.add(_closeItem);
			menu.add(_aboutItem);
		}

}
// THis is an inner class
final class AboutScreen extends MainScreen
{
	public AboutScreen() {
		super();
		add(new RichTextField("BerryUnitConverter (c) 2007 by Daniel Manrique (roadmr@tomechangosubanana.com).\n"));
		add(new RichTextField("http://tomechangosubanana.com/berryunitconverter.\n"));
		add(new RichTextField("This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA."));
	}
}
