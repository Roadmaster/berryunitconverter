/*
 * BerryUnitConverter.java
 *
 * Copyright Daniel Manrique (roadmr@tomechangosubanana.com),2007 This program
 * converts from/to measurement units. Its official name is BerryUnitConverter
 but a full name should be "blackberry unit converter". A webpage is at
http://tomechangosubanana.com/berryunitconverter
 

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
                private Hashtable               groups;
                private Hashtable               temp_groups;
                private String[]        magnitudes;

                int pow( int x, int y)  /*we define the power method with
                                                                  base x and power y (i.e., x^y)*/
                {
                        int z = x; 

                        for( int i = 1; i < y; i++ )z *= x;

                        return z;
                }
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

                        main_unit_list.addElement( new  Unit("Kilogram","Weight",1000.0,0.0));
                        main_unit_list.addElement( new  Unit("Gram","Weight",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Carat","Weight",0.2,0.0));
                        main_unit_list.addElement( new  Unit("Miligram","Weight",0.001,0.0));
                        main_unit_list.addElement( new  Unit("Pound","Weight",453.5924,0.0));
                        main_unit_list.addElement( new  Unit("Stone","Weight",6350.29318,0.0));
                        main_unit_list.addElement( new  Unit("Ounce","Weight",28.34952,0.0));
                        main_unit_list.addElement( new  Unit("Arroba","Weight",11500.23,0.0));
                        main_unit_list.addElement( new  Unit("Quintal","Weight",4.0*11500.23,0.0));
                        main_unit_list.addElement( new  Unit("Pennyweight","Weight",1.55517384,0.0));

                        main_unit_list.addElement( new  Unit("Celsius",         "Temperature",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Fahrenheit",      "Temperature",5.0/9.0,-(160.0/9.0)));
                        main_unit_list.addElement( new  Unit("Kelvin",          "Temperature",1.0,-273.0));

                        main_unit_list.addElement( new  Unit("Kilometers per Hour","Speed",1000.0/3600,0.0));
                        main_unit_list.addElement( new  Unit("Miles per Hour","Speed",(1609.344/3600.0) ,0.0));
                        main_unit_list.addElement( new  Unit("Meters per Second","Speed",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Meters per Minute","Speed",1.0/60.0,0.0));
                        main_unit_list.addElement( new  Unit("Meters per Hour","Speed",1.0/3600.0,0.0));
                        main_unit_list.addElement( new  Unit("Miles per Second","Speed",1609.344,0.0));
                        main_unit_list.addElement( new  Unit("Kilometers per Second","Speed",1000.0,0.0));
                        main_unit_list.addElement( new  Unit("Feet per Hour","Speed",0.3048/3600.0,0.0));
                        main_unit_list.addElement( new  Unit("Feet per Minute","Speed",0.3048/60.0,0.0));
                        main_unit_list.addElement( new  Unit("Feet per Second","Speed",0.3048,0.0));
                        main_unit_list.addElement( new  Unit("Light Speed","Speed",299792500.0,0.0));
                        main_unit_list.addElement( new  Unit("Mach","Speed",331.46,0.0));
                        main_unit_list.addElement( new  Unit("Knots","Speed",0.5144444,0.0));

                        main_unit_list.addElement( new  Unit("Newton","Force",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Pounds Force","Force",4.44822162,0.0));
                        main_unit_list.addElement( new  Unit("Kilograms Force","Force",9.80665,0.0));
                        main_unit_list.addElement( new  Unit("Dynes","Force",0.00001,0.0));
                        main_unit_list.addElement( new  Unit("Kilo-Newton","Force",1000.0,0.0));
                        main_unit_list.addElement( new  Unit("Ounce Force","Force",0.2780139,0.0));

                        main_unit_list.addElement( new  Unit("Newton-meters","Torque",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Foot-pounds","Torque",1.355818,0.0));
                        main_unit_list.addElement( new  Unit("Kilogram-meters","Torque",9.80665,0.0));
                        main_unit_list.addElement( new  Unit("Inch-pounds","Torque",1.355818/12.0,0.0));

                        main_unit_list.addElement( new  Unit("Kilometer","Length",1000,0.0));
                        main_unit_list.addElement( new  Unit("Meter","Length",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Mile","Length",1609.344,0));
                        main_unit_list.addElement( new  Unit("Inch","Length",(2.54/100.0),0));
                        main_unit_list.addElement( new  Unit("Centimeter","Length",0.01,0));
                        main_unit_list.addElement( new  Unit("Millimeter","Length",0.001,0));
                        main_unit_list.addElement( new  Unit("Micrometer","Length",0.000001,0));
                        main_unit_list.addElement( new  Unit("Nanometer","Length",0.000000001,0));

                        main_unit_list.addElement( new  Unit("league","Length",4828,0));
                        main_unit_list.addElement( new  Unit("Rod","Length",5.02921,0));
                        main_unit_list.addElement( new  Unit("Yard","Length",0.9144,0));
                        main_unit_list.addElement( new  Unit("Foot","Length",0.3048,0));
                        main_unit_list.addElement( new  Unit("Hand","Length",0.1016,0));
                        main_unit_list.addElement( new  Unit("Nautical Mile","Length",1852.0,0));

                        main_unit_list.addElement( new  Unit("Liter","Volume",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Cubic meter","Volume",1000,0.0));
                        main_unit_list.addElement( new  Unit("mililiter","Volume",.001,0.0));
                        main_unit_list.addElement( new  Unit("Gallon (US)","Volume",3.785412,0.0));
                        main_unit_list.addElement( new  Unit("Quart","Volume",0.9463529,0.0));
                        main_unit_list.addElement( new  Unit("Pint (US)","Volume",0.4731765,0.0));
                        main_unit_list.addElement( new  Unit("Pint (UK)","Volume",0.568261485,0.0));
                        main_unit_list.addElement( new  Unit("Fl. Ounce","Volume",0.02957353,0.0));
                        main_unit_list.addElement( new  Unit("Bushel","Volume",35.24,0.0));
                        main_unit_list.addElement( new  Unit("Arroba","Volume",16.133,0.0));
                        main_unit_list.addElement( new  Unit("Cubic Feet","Volume",28.31685,0.0));
                        main_unit_list.addElement( new  Unit("Cubic Inches","Volume",0.01638706,0.0));
                        main_unit_list.addElement( new  Unit("Cubic Yards","Volume",764.5549,0.0));
                        main_unit_list.addElement( new  Unit("Cups","Volume",0.2365882,0.0));
                        main_unit_list.addElement( new  Unit("Gallon (UK)","Volume",4.546092,0.0));
                        main_unit_list.addElement( new  Unit("Ounces (UK)","Volume",0.02841307,0.0));
                        main_unit_list.addElement( new  Unit("Tablespoon","Volume",0.01478676,0.0));
                        main_unit_list.addElement( new  Unit("Teaspoon","Volume",0.004928922,0.0));


                        main_unit_list.addElement( new  Unit("Square meter","Area",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Square kilometer","Area",1000000.0,0.0));
                        main_unit_list.addElement( new  Unit("Hectare","Area",10000.0,0.0));
                        main_unit_list.addElement( new  Unit("Square centimeter","Area",0.0001,0.0));
                        main_unit_list.addElement( new  Unit("Square millimeter","Area",0.000001,0.0));
                        main_unit_list.addElement( new  Unit("Square mile","Area",2589988,0.0));
                        main_unit_list.addElement( new  Unit("Acre","Area",4046.85642,0.0));
                        main_unit_list.addElement( new  Unit("Square foot","Area",0.09290304,0.0));
                        main_unit_list.addElement( new  Unit("Square inch","Area",0.00064516,0.0));
                        main_unit_list.addElement( new  Unit("Square Yard","Area",0.8361274,0.0));

                        main_unit_list.addElement( new  Unit("Pascal","Pressure",1.0,0.0)); 
                        main_unit_list.addElement( new  Unit("Inches water column","Pressure",249.089,0.0));
                        main_unit_list.addElement( new  Unit("Kilopascal","Pressure",1000,0.0));
                        main_unit_list.addElement( new  Unit("Bar","Pressure",100000,0.0));
                        main_unit_list.addElement( new  Unit("Psi","Pressure",6894.757,0.0));
                        main_unit_list.addElement( new  Unit("Kg/cm2","Pressure",98066.14,0.0));
                        main_unit_list.addElement( new  Unit("Torr","Pressure",133.3224,0.0));
                        main_unit_list.addElement( new  Unit("Atmospheres","Pressure",101325.0,0.0));
                        main_unit_list.addElement( new  Unit("Cm Mercury","Pressure",1333.224,0.0));
                        main_unit_list.addElement( new  Unit("Inches Mercury","Pressure",3386.388,0.0));

                        main_unit_list.addElement( new  Unit("Watt","Power",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Kilowatt","Power",1000,0.0));
                        main_unit_list.addElement( new  Unit("Horsepower","Power",745.7,0.0));
                        main_unit_list.addElement( new  Unit("BTU per Second","Power",1054.118,0.0));
                        main_unit_list.addElement( new  Unit("BTU per Minute","Power",1054.118/60.0,0.0));
                        main_unit_list.addElement( new  Unit("BTU per Hour","Power",1054.118/3600.0,0.0));
                        main_unit_list.addElement( new  Unit("Calories per Second","Power",4.183076,0.0));
                        main_unit_list.addElement( new  Unit("Foot-pound per Second","Power",1.355818,0.0));

                        main_unit_list.addElement( new  Unit("Joule","Energy",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Calorie","Energy",4.1868,0.0));
                        main_unit_list.addElement( new  Unit("Kilowatt hour","Energy",3600000.0,0.0));
                        main_unit_list.addElement( new  Unit("BTU","Energy",1055.056,0.0));
                        main_unit_list.addElement( new  Unit("Kiloton","Energy",4184000000000.0,0.0));

                        main_unit_list.addElement( new  Unit("Second","Film",1.0,0.0));
                        main_unit_list.addElement( new  Unit("Foot","Film",0.66666666666666666666,0.0));
                        main_unit_list.addElement( new  Unit("Film frame","Film",1.0/24.0,0.0));
                        main_unit_list.addElement( new  Unit("Meter","Film",2.1872265966,0.0));

                        main_unit_list.addElement(new Unit("Bit","Data",1.0,0.0));
                        main_unit_list.addElement(new Unit("Kilobit","Data",1.0*pow(10,3),0.0));
                        main_unit_list.addElement(new Unit("Megabit","Data",1.0*pow(10,6),0.0));
                        main_unit_list.addElement(new Unit("Gigabit","Data",1.0*pow(10,9),0.0));
                        main_unit_list.addElement(new Unit("Terabit","Data",1.0*pow(10,12),0.0));

                        main_unit_list.addElement(new Unit("Byte","Data",8.0,0.0));
                        main_unit_list.addElement(new Unit("Kilobyte","Data",8.0*1000.0,0.0));
                        main_unit_list.addElement(new Unit("Megabyte","Data",8.0* 1000000.0,0.0));
                        main_unit_list.addElement(new Unit("Gigabyte","Data",8.0* 1000000000.0,0.0));
                        main_unit_list.addElement(new Unit("Terabyte","Data",8.0* 1000000000000.0,0.0));
                        main_unit_list.addElement(new Unit("Exabyte","Data", 8.0*1000000000000000.0,0.0));
                        main_unit_list.addElement(new Unit("Petabyte","Data",8.0* 1000000000000000000.0,0.0));

                        main_unit_list.addElement(new Unit("Kibibyte","Data",8.0*pow(2,10),0.0));
                        main_unit_list.addElement(new Unit("Mebibyte","Data",8.0*pow(2,20),0.0));
                        main_unit_list.addElement(new Unit("Gibibyte","Data",8.0*pow(2,30),0.0));
                        main_unit_list.addElement(new Unit("Tebibyte","Data",8.0*pow(2,40),0.0));
                        main_unit_list.addElement(new Unit("Exbibyte","Data", 8.0*pow(2,50),0.0));
                        main_unit_list.addElement(new Unit("Pebibyte","Data",8.0*pow(2,60),0.0));
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

                //override the onClose() method, even though we
                //don't do anything special here.
                public boolean onClose()
                {
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

                // Swaps the units; from becomes to, to becomes from.
                private void swapUnits(){
                        int fromIndex = fromUnitField.getSelectedIndex();
                        int toIndex   = toUnitField.getSelectedIndex();

                        fromUnitField.setSelectedIndex(toIndex);
                        toUnitField.setSelectedIndex(fromIndex);
                        updateConversion();
                }


                private void updateConversion(){
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

                private MenuItem _swapItem = new MenuItem("Swap",150,10) {
                        public void run() {
                                swapUnits();
                        }
                };

                //override makeMenu to add the new menu items
                protected void makeMenu( Menu menu, int instance )
                {
                        menu.add(_closeItem);
                        menu.add(_aboutItem);
                        menu.add(_swapItem);

                }

}
// THis is an inner class
final class AboutScreen extends MainScreen
{
        public AboutScreen() {
                super();
                add(new RichTextField("BerryUnitConverter 1.7 (c) 2007-2009 by Daniel Manrique (roadmr@tomechangosubanana.com).\n"));
                add(new RichTextField("http://tomechangosubanana.com/berryunitconverter.\n"));
                add(new RichTextField("This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA."));
        }
}
