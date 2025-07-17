/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.examples.dynamicFuzzy3_1;

/**
 *
 * @author Sunmedia
 */
public class NumOfViolation {
    
     public static boolean violation(long used, long provided) {
        
        if(provided < used)
              return true;
        else
            return false;
    
}
}
