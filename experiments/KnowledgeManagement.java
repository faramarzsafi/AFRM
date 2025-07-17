/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.examples.mytest4_1_1;

/**
 *
 * @author Sunmedia
 */
public class KnowledgeManagement {

    public static long rule(long used, long provided) {
        

        try {
            long ut = Math.round(((double)used / (double) provided) * 100);
            if (ut < 50 || ut > 75) {
                
                provided = Math.round(used * 1.6);
            }
            return provided;
        } catch (ArithmeticException e) {
            return (long) .8 * used;
        }
    }
}
