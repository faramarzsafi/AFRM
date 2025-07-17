/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.examples.mytest4_1_1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;
import java.util.LinkedList;
import java.util.List;
import org.cloudbus.cloudsim.*;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 *
 * @author Zahra Bagheri
 */
public class TestClass {
	/** The broker. */
	protected static DatacenterBroker broker;

	/** The cloudlet list. */
	protected static List<Cloudlet> cloudletList;

	/** The vm list. */
	protected static List<Vm> vmList;

	/** The host list. */
	protected static List<Host> hostList; 
        
        /** number of iteration*/
        protected static final int NUM = 1000;
        
        /** number of SLA_violation*/
        public static int SLA_violation= 0;
        
        public static double SUM_Unused= 0;
        public static double SUM_Unused_id= 0;

        public static int  Actions= 0;
        
        public static long SUMofUt = 0;
        public static int NUM_ut = NUM;
        public static long avg_st = 0;
        
        protected static Formatter formatter;
        
        protected static long[] ut;
        
                
    	/**
	 * Creates main() to run this test
	 */
	public static void main(String[] args) {
		Log.printLine("Starting My Test...");

		try {
			formatter = new Formatter("test.txt");
			int num_user = 1;   
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; 
			CloudSim.init(num_user, calendar, trace_flag);
                        
                        broker = createBroker();
			int brokerId = broker.getId();

			cloudletList = createCloudletList(brokerId, NUM);
			vmList = createVmList(brokerId, NUM);
			hostList = createHostList(NUM);
                        
			@SuppressWarnings("unused")
			Datacenter datacenter = createDatacenter("Datacenter");

			broker.submitVmList(vmList);
			broker.submitCloudletList(cloudletList);
                        
                        for(Cloudlet cloudlet : cloudletList){
                                broker.bindCloudletToVm(cloudlet.getCloudletId(),cloudlet.getCloudletId());
                        }
                        
                        CloudSim.startSimulation();
                        
                        List<Cloudlet> newList = broker.getCloudletReceivedList();
			Log.printLine("Received " + newList.size() + " cloudlets");
                        Log.printLine("Number of failed cloudlets: " + (NUM - newList.size()));
                        
                         double sc = 0;
                          double zz = 0;
           
                        ut = new long[NUM];
                        for(int i=0; i<NUM; i++){
                            long used = vmList.get(i).getSize();
                            long provided = hostList.get(i).getStorage();
                            avg_st+=provided;
                            ut[i] = Math.round(((double)used / (double)provided)*100);
                            if(ut[i]<200){
                             SUMofUt+=ut[i];
                          }
                            else{
                            NUM_ut--;
                            }
                             if(i>=1){
                   sc = (double) used / vmList.get(i-1).getSize();
            
                }
                
                Log.printLine("vm size: " + vmList.get(i).getSize() + "      host sorage: " + hostList.get(i).getStorage() + "    ut= " + ut[i] + "     scale = "+ sc );
            }
                        Log.printLine("UT: "+Math.round((double)SUMofUt/(double)NUM_ut));
                       // Log.printLine("avg_st: "+(avg_st/NUM));
                        Log.printLine("number of Violation: "+SLA_violation);
                        Log.printLine("RAE : "+(((double)SUMofUt/(double)(SLA_violation+1))));
                       // Log.printLine("cost : "+ (((((double)SLA_violation/(double)NUM) * 100))+((((double)SUM_Unused /(double)NUM )*5)) + Actions));
                      //  Log.printLine("cost1 : "+ ((((double)SLA_violation/(double)NUM) * 100*100)));
                      //  Log.printLine("cost2 : "+ (((double)SUM_Unused/(double)SUM_Unused_id )*5));
                      //  Log.printLine("cost3 : "+ Actions);
                      //  Log.printLine("cost4 : "+ (double)Actions/(double)899);
                        Log.printLine("COST : "+ (((((double)SLA_violation/(double)NUM) * 100*100))+((((double)SUM_Unused /(double)SUM_Unused_id )*5)) + ((double)Actions/(double)999)));

                        
                        
                        CloudSim.stopSimulation();
                        
                        //printResults(newList);
                     
                        Log.printLine("My Test Finished");
                        formatter.close();
                }
                catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
                
        }
        
	/**
	 * Creates the broker.
	 * 
	 * @return the datacenter broker
	 */
	public static DatacenterBroker createBroker() {
		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return broker;
	}      
        
	/**
	 * Creates the cloudlet list.
	 * 
	 * @param brokerId the broker id
	 * @param cloudletsNumber the cloudlets number
	 * 
	 * @return the list< cloudlet>
	 */
	public static List<Cloudlet> createCloudletList(int brokerId, int cloudletsNumber) {
		// Creates a container to store Cloudlets
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

		//cloudlet parameters
		long length = 1000000;
		long fileSize = 300;
		long outputSize = 300;
		int pesNumber = 1;
		UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet = new Cloudlet[cloudletsNumber];

		for(int i=0;i<cloudletsNumber;i++){
			cloudlet[i] = new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel);
			// setting the owner of these Cloudlets
			cloudlet[i].setUserId(brokerId);
			list.add(cloudlet[i]);
		}

		return list;
	}    
        
	/**
	 * Creates the vm list.
	 * 
	 * @param brokerId the broker id
	 * @param vmsNumber the vms number
	 * 
	 * @return the list< vm>
	 */
	public static List<Vm> createVmList(int brokerId, int vmsNumber) {
            
		//Creates a container to store VMs. This list is passed to the broker later
		LinkedList<Vm> list = new LinkedList<Vm>();

		//VM Parameters
		long size; //image size (MB)
		int ram = 512; //vm memory (MB)
		int mips = 250;
		long bw = 1000;
		int pesNumber = 1; //number of cpus
		String vmm = "Xen"; //VMM name

		//create VMs
		Measurement measurement = new Measurement(400, 100);
                Vm[] vm = new Vm[vmsNumber];

		for(int i=0;i<vmsNumber;i++){
                    size = (long) measurement.measure();
                    formatter.format("%d\n", size);
                    vm[i] = new Vm(i,
                                       brokerId,
                                       mips, 
                                       pesNumber, 
                                       ram, 
                                       bw, 
                                       size,
                                       "Xen", 
                                       new CloudletSchedulerSpaceShared());

			list.add(vm[i]);
		}
                
                

		return list;
                
	}      
        
	public static List<Host> createHostList(int hostsNumber) {
		List<Host> hostList = new ArrayList<Host>();
                
                int mips = 1000;
		int ram = 10000; //host memory (MB)
		long storage; //host storage
		int bw = 1000000;
                int pesNumber = 1; //number of cpus
                
                List<Pe> peList = new ArrayList<Pe>();
                        for (int j = 0; j < pesNumber; j++) {
				peList.add(new Pe(j, new PeProvisionerSimple(mips)));
			}
                
                hostList.add(new Host(0, new RamProvisionerSimple(ram),	new BwProvisionerSimple(bw), 800, peList, new VmSchedulerTimeShared(peList)));        
                        
		for (int i = 1; i < hostsNumber; i++) {
                    storage = KnowledgeManagement.rule(vmList.get(i-1).getSize(), hostList.get(i-1).getStorage());
                    if(NumOfViolation.violation(vmList.get(i-1).getSize(), hostList.get(i-1).getStorage())){
                        SLA_violation++;
                    }
                    else{
                    SUM_Unused += (int)(((hostList.get(i-1).getStorage()-vmList.get(i-1).getSize())/(double)hostList.get(i-1).getStorage())*100);
                    SUM_Unused_id++;
                    long uti = Math.round(((double)vmList.get(i-1).getSize() / (double) hostList.get(i-1).getStorage()) * 100);
                    if (uti < 50 || uti > 75) {
                       Actions++;
                      }
                    }
                      
                    
                    hostList.add(new Host(
					i,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList,
					new VmSchedulerTimeShared(peList)));
		}
		return hostList;
	}  
        
	private static Datacenter createDatacenter(String name){

		//    Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.001;	// the cost of using storage in this resource
		double costPerBw = 0.0;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

	       DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
	                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		//  Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new MyVmAllocationPolicy(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}        
        
	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printResults(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				indent + "VM ID" + indent + indent + "Time" + indent + "Start Time" + indent + "Finish Time" );

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine(  indent + indent + indent + cloudlet.getVmId() +
						indent + indent + dft.format(cloudlet.getActualCPUTime()) +
						indent + indent + dft.format(cloudlet.getExecStartTime())+ indent + indent + indent + dft.format(cloudlet.getFinishTime())
                                              );
			} 
		}

	}        

}
