
package org.cloudbus.cloudsim.examples.mytest4_1_1;


import java.util.Random;

public class Measurement {
		public final static int TREND_UP   = 0;
		public final static int TREND_DOWN = 1;
		
		private final int iBegin = 2;
		private final int iEnd   = 75; //18
		private int trend;
		private int t; //stores the time. Restarts after every x has been reached
		private int x; //stores for many times the trend is valid.
		private double currentValue;
		private Random r; //random number generator.
		//Mean and std for the gaussian distribution for the initial value.
		private final double mean;
		private final double std; 
		
		public Measurement(double mean, double std) {
			t=-1; //-1 means that measure() has not been executed before. 
			r=new Random();
			this.mean = mean;
			this.std  = std; 
		}
		
		public double measure() {
			
			if (t==-1) {
				//Draw the first measurement (at least 0.1)	
				currentValue = Math.max(0.1, r.nextGaussian() *std + mean);
				t=0;
			} 
			else if(t==0||t>x) {
				//For the second measurement, or if trend is over, decide whether trend up or down (trend) & decide for how long this trend will last (x)
				if(r.nextBoolean()) {
					trend = TREND_UP;
				} else {
					trend = TREND_DOWN;
				}
				x = r.nextInt(5) + 2;
				t=1;
			}
			if(t<=x) {
				//If trend still lasts, change current value to value +/- defined interval.
				if(trend==TREND_UP) {
					currentValue = currentValue * (1 + (r.nextDouble()*(iEnd - iBegin) + iBegin)/100.0);
				} else {
					currentValue = currentValue * (1 - (r.nextDouble()*(iEnd - iBegin) + iBegin)/100.0);
					//Demand cannot be lower than 0. If yes, make sure, trend is up and set value to positive one.
					if(currentValue<=0.1) {
						currentValue = Math.min(2.0, mean-std);
						trend=TREND_UP;
					}
				}
				
				t++;
			}
			return currentValue;
		}
                
                public void getparam(){
                    
                }
	}