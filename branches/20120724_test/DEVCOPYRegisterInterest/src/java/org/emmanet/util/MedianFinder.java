

package org.emmanet.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 *
 * @author phil
 * *This class will find the median of a list of Doubles.
 */
public class MedianFinder {
    
    public static Double find(ArrayList<Double> data) {
Double result;
System.out.println("DATA SIZE IS:::" + data.size());
//if(data.get(0) == null || data.get(1) == null || data.get(2) == null || data.get(3) == null){
    //System.out.println("data.get(0)=" + data.get(0));

//}
Collections.sort(data);
System.out.println("Begin output of values to be calculated::");
for (Iterator it = data.listIterator(); it.hasNext();) {
    Object o = it.next();
    System.out.println(o.toString());
}
System.out.println("Finish output of values to be calculated::");
if (data.size() % 2 == 1) {
// If the number of entries in the list is not even.

// Get the middle value.
// You must floor the result of the division to drop the
// remainder.
result = data.get( (int) Math.floor(data.size()/2) );

} else  if(( data.size()/2  != 0)) {
// If the number of entries in the list are even.

// Get the middle two values and average them.
 
Double lowerMiddle = data.get( data.size()/2 );
Double upperMiddle = data.get( data.size()/2 - 1 );
result = (lowerMiddle + upperMiddle) / 2;
}
else {
    result=0.0;
}
System.out.println("MEDIAN RESULT RETURNED");
System.out.println(result);
System.out.println("END OF MEDIAN RESULT RETURNED");
return result;
    }

}
