/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudbus.cloudsim.examples.dynamicFuzzy3_1;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionTriangular;
import net.sourceforge.jFuzzyLogic.membership.Value;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleExpression;
import net.sourceforge.jFuzzyLogic.rule.RuleTerm;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import net.sourceforge.jFuzzyLogic.ruleConnectionMethod.RuleConnectionMethodAndMin;


/**
 *
 * @author Sunmedia
 */
public class KnowledgeManagement {

    public static long rule(long used, long provided) {
        try {
            long ut = Math.round(((double) used / (double) provided) * 100);
            if (ut < 50 || ut > 75) {
                provided = Math.round(used * 1.6);
            }
            return provided;
        } catch (ArithmeticException e) {
            return (long) .8 * used;
        }
    }

    public static long rule(long used, long provided, long previousUsed) {
        try {
            
            long ut = Math.round(((double) used / (double) provided) * 100);
           // if(ut < 50 || ut > 75){
            double sc = (double) used / previousUsed;
            double zarib;
            if(used==1)
               zarib=2;
            else
               zarib = getZarib(ut, sc);
            System.out.println("vm=" + used +"    host=" + provided + "      ut=" + ut +"     sc=" + sc + "     zarib="+zarib);
           // System.out.println(zarib);
            provided = Math.round(used * zarib);
           // }
            return provided;
            
        } catch (ArithmeticException e) {
            return (long) .8 * used;
        }
    }

    public static double getZarib(long ut, double sc) {
        String fileName = "dynamicTest2_1.fcl";
        FIS fis = FIS.load(fileName, true);
        if (fis == null) {
            System.err.println("Can't laod file:'" + fileName + "'");
            System.exit(0);
        }

        FunctionBlock functionBlock = fis.getFunctionBlock(null);
        
                if(ut>100)
                   ut=100;
		
		if ( sc>0 && sc<0.5 )
		{
                    
                    
			Variable u01 = fis.getVariable("utility").add("u01", new MembershipFunctionTriangular(new Value(-1), new Value(50), new Value(55)));
			Variable u11 = fis.getVariable("utility").add("u11", new MembershipFunctionTriangular(new Value(50), new Value(100), new Value(105)));
			
			Variable sc01 = fis.getVariable("scale").add("sc01", new MembershipFunctionTriangular(new Value(0), new Value(0.5), new Value(0.5)));
		//	Variable sc11 = fis.getVariable("scale").add("sc11", new MembershipFunctionTriangular(new Value(1.5), new Value(2), new Value(2)));
			
			Variable z01 = fis.getVariable("zarib").add("z01", new MembershipFunctionTriangular(new Value(1), new Value(1.1), new Value(1.1)));
			Variable z11 = fis.getVariable("zarib").add("z11", new MembershipFunctionTriangular(new Value(1.1), new Value(1.2), new Value(1.2)));
			
			
			RuleBlock ruleBlock = fis.getFunctionBlock(null).getFuzzyRuleBlock(null);
			
			Rule rule1 = new Rule("Rule1", ruleBlock);
			RuleTerm term11 = new RuleTerm(u01 , "u01", false);
			RuleTerm term21 = new RuleTerm(sc01 , "sc01", false);
			RuleExpression antecedentAnd1 = new RuleExpression(term11, term21, new RuleConnectionMethodAndMin());
			rule1.setAntecedents(antecedentAnd1);
			rule1.addConsequent(z01, "z01", false);
			ruleBlock.add(rule1);


            Rule rule2 = new Rule("Rule2", ruleBlock);
			RuleTerm term12 = new RuleTerm(u11 , "u11", false);
			RuleTerm term22 = new RuleTerm(sc01 , "sc01", false);
			RuleExpression antecedentAnd2 = new RuleExpression(term12, term22, new RuleConnectionMethodAndMin());
			rule2.setAntecedents(antecedentAnd2);
			rule2.addConsequent(z11, "z11", false);
			ruleBlock.add(rule2);

			
		}//end if
		
		if ( sc>=0.5 && sc<1 )
		{
			Variable u02 = fis.getVariable("utility").add("u02", new MembershipFunctionTriangular(new Value(-1), new Value(30), new Value(30)));
			Variable u12 = fis.getVariable("utility").add("u12", new MembershipFunctionTriangular(new Value(25), new Value(60), new Value(60)));
			Variable u22 = fis.getVariable("utility").add("u22", new MembershipFunctionTriangular(new Value(55), new Value(105), new Value(105)));

			Variable sc02 = fis.getVariable("scale").add("sc02", new MembershipFunctionTriangular(new Value(0.4), new Value(1), new Value(1)));
		//	Variable sc12 = fis.getVariable("scale").add("sc12", new MembershipFunctionTriangular(new Value(1), new Value(2), new Value(2)));
		//	Variable sc22 = fis.getVariable("scale").add("sc22", new MembershipFunctionTriangular(new Value(2), new Value(3), new Value(3)));

			Variable z02 = fis.getVariable("zarib").add("z02", new MembershipFunctionTriangular(new Value(1.1), new Value(1.2), new Value(1.2)));
			Variable z12 = fis.getVariable("zarib").add("z12", new MembershipFunctionTriangular(new Value(1.2), new Value(1.3), new Value(1.3)));
			Variable z22 = fis.getVariable("zarib").add("z22", new MembershipFunctionTriangular(new Value(1.3), new Value(1.4), new Value(1.4)));
			
            RuleBlock ruleBlock = fis.getFunctionBlock(null).getFuzzyRuleBlock(null);
			
			Rule rule3 = new Rule("Rule1", ruleBlock);
			RuleTerm term13 = new RuleTerm(u02 , "u02", false);
			RuleTerm term23 = new RuleTerm(sc02 , "sc02", false);
			RuleExpression antecedentAnd3 = new RuleExpression(term13, term23, new RuleConnectionMethodAndMin());
			rule3.setAntecedents(antecedentAnd3);
			rule3.addConsequent(z02, "z02", false);
			ruleBlock.add(rule3);
			
			Rule rule4 = new Rule("Rule2", ruleBlock);
			RuleTerm term14 = new RuleTerm(u12 , "u12", false);
			RuleTerm term24 = new RuleTerm(sc02 , "sc02", false);
			RuleExpression antecedentAnd4 = new RuleExpression(term14, term24, new RuleConnectionMethodAndMin());
			rule4.setAntecedents(antecedentAnd4);
			rule4.addConsequent(z12, "z12", false);
			ruleBlock.add(rule4);
			
			Rule rule5 = new Rule("Rule3", ruleBlock);
			RuleTerm term15 = new RuleTerm(u22 , "u22", false);
			RuleTerm term25 = new RuleTerm(sc02 , "sc02", false);
			RuleExpression antecedentAnd5 = new RuleExpression(term15, term25, new RuleConnectionMethodAndMin());
			rule5.setAntecedents(antecedentAnd5);
			rule5.addConsequent(z22, "z22", false);
			ruleBlock.add(rule5);
		}//end if
		
		
		if ( sc>=1 && sc<1.5 )
		{
			Variable u03 = fis.getVariable("utility").add("u03", new MembershipFunctionTriangular(new Value(-1), new Value(25), new Value(25)));
			Variable u13 = fis.getVariable("utility").add("u13", new MembershipFunctionTriangular(new Value(20), new Value(50), new Value(50)));
			Variable u23 = fis.getVariable("utility").add("u23", new MembershipFunctionTriangular(new Value(45), new Value(75), new Value(75)));
			Variable u33 = fis.getVariable("utility").add("u33", new MembershipFunctionTriangular(new Value(70), new Value(105), new Value(105)));

		    Variable sc03 = fis.getVariable("scale").add("sc03", new MembershipFunctionTriangular(new Value(0.9), new Value(1.5), new Value(1.5)));
			
		    Variable z03 = fis.getVariable("zarib").add("z03", new MembershipFunctionTriangular(new Value(1.4), new Value(1.55), new Value(1.55)));
			Variable z13 = fis.getVariable("zarib").add("z13", new MembershipFunctionTriangular(new Value(1.55), new Value(1.7), new Value(1.7)));
			Variable z23 = fis.getVariable("zarib").add("z23", new MembershipFunctionTriangular(new Value(1.7), new Value(1.85), new Value(1.85)));
			Variable z33 = fis.getVariable("zarib").add("z33", new MembershipFunctionTriangular(new Value(1.85), new Value(2), new Value(2)));

			
			 RuleBlock ruleBlock = fis.getFunctionBlock(null).getFuzzyRuleBlock(null);
				
				Rule rule6 = new Rule("Rule1", ruleBlock);
				RuleTerm term16 = new RuleTerm(u03 , "u03", false);
				RuleTerm term26 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd6 = new RuleExpression(term16, term26, new RuleConnectionMethodAndMin());
				rule6.setAntecedents(antecedentAnd6);
				rule6.addConsequent(z03, "z03", false);
				ruleBlock.add(rule6);
				
				Rule rule7 = new Rule("Rule2", ruleBlock);
				RuleTerm term17 = new RuleTerm(u13 , "u13", false);
				RuleTerm term27 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd7 = new RuleExpression(term17, term27, new RuleConnectionMethodAndMin());
				rule7.setAntecedents(antecedentAnd7);
				rule7.addConsequent(z13, "z13", false);
				ruleBlock.add(rule7);
				
				Rule rule8 = new Rule("Rule3", ruleBlock);
				RuleTerm term18 = new RuleTerm(u23 , "u23", false);
				RuleTerm term28 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd8 = new RuleExpression(term18, term28, new RuleConnectionMethodAndMin());
				rule8.setAntecedents(antecedentAnd8);
				rule8.addConsequent(z23, "z23", false);
				ruleBlock.add(rule8);
				
				Rule rule9 = new Rule("Rule4", ruleBlock);
				RuleTerm term19 = new RuleTerm(u33 , "u33", false);
				RuleTerm term29 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd9 = new RuleExpression(term19, term29, new RuleConnectionMethodAndMin());
				rule9.setAntecedents(antecedentAnd9);
				rule9.addConsequent(z33, "z33", false);
				ruleBlock.add(rule9);
		}//end if
		
                if ( sc>=1.5 && sc<2 )
		{
			Variable u03 = fis.getVariable("utility").add("u03", new MembershipFunctionTriangular(new Value(-1), new Value(20), new Value(20)));
			Variable u13 = fis.getVariable("utility").add("u13", new MembershipFunctionTriangular(new Value(15), new Value(40), new Value(40)));
			Variable u23 = fis.getVariable("utility").add("u23", new MembershipFunctionTriangular(new Value(35), new Value(60), new Value(60)));
			Variable u33 = fis.getVariable("utility").add("u33", new MembershipFunctionTriangular(new Value(55), new Value(80), new Value(80)));
			Variable u43 = fis.getVariable("utility").add("u43", new MembershipFunctionTriangular(new Value(75), new Value(105), new Value(105)));

		        Variable sc03 = fis.getVariable("scale").add("sc03", new MembershipFunctionTriangular(new Value(1.4), new Value(2), new Value(2)));
			
		        Variable z03 = fis.getVariable("zarib").add("z03", new MembershipFunctionTriangular(new Value(1.5), new Value(1.7), new Value(1.7)));
			Variable z13 = fis.getVariable("zarib").add("z13", new MembershipFunctionTriangular(new Value(1.6), new Value(1.8), new Value(1.8)));
			Variable z23 = fis.getVariable("zarib").add("z23", new MembershipFunctionTriangular(new Value(1.7), new Value(1.9), new Value(1.9)));
			Variable z33 = fis.getVariable("zarib").add("z33", new MembershipFunctionTriangular(new Value(1.8), new Value(2), new Value(2)));
			Variable z43 = fis.getVariable("zarib").add("z43", new MembershipFunctionTriangular(new Value(1.9), new Value(2.1), new Value(2.1)));

			
			 RuleBlock ruleBlock = fis.getFunctionBlock(null).getFuzzyRuleBlock(null);
				
				Rule rule6 = new Rule("Rule1", ruleBlock);
				RuleTerm term16 = new RuleTerm(u03 , "u03", false);
				RuleTerm term26 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd6 = new RuleExpression(term16, term26, new RuleConnectionMethodAndMin());
				rule6.setAntecedents(antecedentAnd6);
				rule6.addConsequent(z03, "z03", false);
				ruleBlock.add(rule6);
				
				Rule rule7 = new Rule("Rule2", ruleBlock);
				RuleTerm term17 = new RuleTerm(u13 , "u13", false);
				RuleTerm term27 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd7 = new RuleExpression(term17, term27, new RuleConnectionMethodAndMin());
				rule7.setAntecedents(antecedentAnd7);
				rule7.addConsequent(z13, "z13", false);
				ruleBlock.add(rule7);
				
				Rule rule8 = new Rule("Rule3", ruleBlock);
				RuleTerm term18 = new RuleTerm(u23 , "u23", false);
				RuleTerm term28 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd8 = new RuleExpression(term18, term28, new RuleConnectionMethodAndMin());
				rule8.setAntecedents(antecedentAnd8);
				rule8.addConsequent(z23, "z23", false);
				ruleBlock.add(rule8);
				
				Rule rule9 = new Rule("Rule4", ruleBlock);
				RuleTerm term19 = new RuleTerm(u33 , "u33", false);
				RuleTerm term29 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd9 = new RuleExpression(term19, term29, new RuleConnectionMethodAndMin());
				rule9.setAntecedents(antecedentAnd9);
				rule9.addConsequent(z33, "z33", false);
				ruleBlock.add(rule9);
                                
                                Rule rule99 = new Rule("Rule5", ruleBlock);
				RuleTerm term199 = new RuleTerm(u43 , "u43", false);
				RuleTerm term299 = new RuleTerm(sc03 , "sc03", false);
				RuleExpression antecedentAnd99 = new RuleExpression(term199, term299, new RuleConnectionMethodAndMin());
				rule99.setAntecedents(antecedentAnd99);
				rule99.addConsequent(z43, "z43", false);
				ruleBlock.add(rule99);
		}//end if
		
		if ( sc>=2 )
		{
                        if ( sc>3 )
                            return 1.5;
			if ( sc>2.5 )
                             sc=2.5;
                        
						
			Variable u04 = fis.getVariable("utility").add("u04", new MembershipFunctionTriangular(new Value(-1), new Value(17), new Value(17)));
			Variable u14 = fis.getVariable("utility").add("u14", new MembershipFunctionTriangular(new Value(15), new Value(34), new Value(34)));
			Variable u24 = fis.getVariable("utility").add("u24", new MembershipFunctionTriangular(new Value(30), new Value(51), new Value(51)));
			Variable u34 = fis.getVariable("utility").add("u34", new MembershipFunctionTriangular(new Value(48), new Value(68), new Value(68)));
			Variable u44 = fis.getVariable("utility").add("u44", new MembershipFunctionTriangular(new Value(60), new Value(85), new Value(85)));
			Variable u54 = fis.getVariable("utility").add("u54", new MembershipFunctionTriangular(new Value(80), new Value(105), new Value(105)));

           	Variable sc04 = fis.getVariable("scale").add("sc04", new MembershipFunctionTriangular(new Value(1.9), new Value(2.5), new Value(2.5)));
        // 	Variable sc14 = fis.getVariable("scale").add("sc14", new MembershipFunctionTriangular(new Value(2), new Value(3), new Value(3)));

  		    Variable z04 = fis.getVariable("zarib").add("z04", new MembershipFunctionTriangular(new Value(1.6), new Value(1.7), new Value(1.7)));
  		    Variable z14 = fis.getVariable("zarib").add("z14", new MembershipFunctionTriangular(new Value(1.7), new Value(1.8), new Value(1.8)));
  		    Variable z24 = fis.getVariable("zarib").add("z24", new MembershipFunctionTriangular(new Value(1.8), new Value(1.9), new Value(1.9)));
  		    Variable z34 = fis.getVariable("zarib").add("z34", new MembershipFunctionTriangular(new Value(1.9), new Value(2), new Value(2)));
  		    Variable z44 = fis.getVariable("zarib").add("z44", new MembershipFunctionTriangular(new Value(2), new Value(2.1), new Value(2.1)));
  		    Variable z54 = fis.getVariable("zarib").add("z54", new MembershipFunctionTriangular(new Value(2.1), new Value(2.2), new Value(2.2)));

            RuleBlock ruleBlock = fis.getFunctionBlock(null).getFuzzyRuleBlock(null);
	
	        Rule rule10 = new Rule("Rule1", ruleBlock);
	        RuleTerm term110 = new RuleTerm(u04 , "u04", false);
	        RuleTerm term210 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd10 = new RuleExpression(term110, term210, new RuleConnectionMethodAndMin());
	        rule10.setAntecedents(antecedentAnd10);
	        rule10.addConsequent(z04, "z04", false);
	        ruleBlock.add(rule10);
	
	        Rule rule11 = new Rule("Rule2", ruleBlock);
	        RuleTerm term111 = new RuleTerm(u14 , "u14", false);
	        RuleTerm term211 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd11 = new RuleExpression(term111, term211, new RuleConnectionMethodAndMin());
	        rule11.setAntecedents(antecedentAnd11);
	        rule11.addConsequent(z14, "z14", false);
	        ruleBlock.add(rule11);
	        
	        Rule rule12 = new Rule("Rule3", ruleBlock);
	        RuleTerm term112 = new RuleTerm(u24 , "u24", false);
	        RuleTerm term212 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd12 = new RuleExpression(term112, term212, new RuleConnectionMethodAndMin());
	        rule12.setAntecedents(antecedentAnd12);
	        rule12.addConsequent(z24, "z24", false);
	        ruleBlock.add(rule12);
	        
	        Rule rule13 = new Rule("Rule4", ruleBlock);
	        RuleTerm term113 = new RuleTerm(u34 , "u34", false);
	        RuleTerm term213 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd13 = new RuleExpression(term113, term213, new RuleConnectionMethodAndMin());
	        rule13.setAntecedents(antecedentAnd13);
	        rule13.addConsequent(z34, "z34", false);
	        ruleBlock.add(rule13);
	        
	        Rule rule14 = new Rule("Rule5", ruleBlock);
	        RuleTerm term114 = new RuleTerm(u44 , "u44", false);
	        RuleTerm term214 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd14 = new RuleExpression(term114, term214, new RuleConnectionMethodAndMin());
	        rule14.setAntecedents(antecedentAnd14);
	        rule14.addConsequent(z44, "z44", false);
	        ruleBlock.add(rule14);

		
                Rule rule15 = new Rule("Rule6", ruleBlock);
	        RuleTerm term115 = new RuleTerm(u54 , "u54", false);
	        RuleTerm term215 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd15 = new RuleExpression(term115, term215, new RuleConnectionMethodAndMin());
	        rule15.setAntecedents(antecedentAnd15);
	        rule15.addConsequent(z54, "z54", false);
	        ruleBlock.add(rule15);
		}//end if
		
              /*  if ( sc>=2.5 && sc<3 )
		{
			
			if ( sc>2.5 )
                             sc=2;
			
			Variable u04 = fis.getVariable("utility").add("u04", new MembershipFunctionTriangular(new Value(-1), new Value(17), new Value(17)));
			Variable u14 = fis.getVariable("utility").add("u14", new MembershipFunctionTriangular(new Value(15), new Value(34), new Value(34)));
			Variable u24 = fis.getVariable("utility").add("u24", new MembershipFunctionTriangular(new Value(30), new Value(51), new Value(51)));
			Variable u34 = fis.getVariable("utility").add("u34", new MembershipFunctionTriangular(new Value(48), new Value(68), new Value(68)));
			Variable u44 = fis.getVariable("utility").add("u44", new MembershipFunctionTriangular(new Value(60), new Value(85), new Value(85)));
			Variable u54 = fis.getVariable("utility").add("u54", new MembershipFunctionTriangular(new Value(80), new Value(105), new Value(105)));

           	Variable sc04 = fis.getVariable("scale").add("sc04", new MembershipFunctionTriangular(new Value(1.9), new Value(2.5), new Value(2.5)));
        // 	Variable sc14 = fis.getVariable("scale").add("sc14", new MembershipFunctionTriangular(new Value(2), new Value(3), new Value(3)));

  		    Variable z04 = fis.getVariable("zarib").add("z04", new MembershipFunctionTriangular(new Value(1.5), new Value(1.6), new Value(1.6)));
  		    Variable z14 = fis.getVariable("zarib").add("z14", new MembershipFunctionTriangular(new Value(1.6), new Value(1.7), new Value(1.7)));
  		    Variable z24 = fis.getVariable("zarib").add("z24", new MembershipFunctionTriangular(new Value(1.7), new Value(1.8), new Value(1.8)));
  		    Variable z34 = fis.getVariable("zarib").add("z34", new MembershipFunctionTriangular(new Value(1.8), new Value(1.9), new Value(1.9)));
  		    Variable z44 = fis.getVariable("zarib").add("z44", new MembershipFunctionTriangular(new Value(1.9), new Value(2), new Value(2)));
  		    Variable z54 = fis.getVariable("zarib").add("z54", new MembershipFunctionTriangular(new Value(2), new Value(2.1), new Value(2.1)));

            RuleBlock ruleBlock = fis.getFunctionBlock(null).getFuzzyRuleBlock(null);
	
	        Rule rule10 = new Rule("Rule1", ruleBlock);
	        RuleTerm term110 = new RuleTerm(u04 , "u04", false);
	        RuleTerm term210 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd10 = new RuleExpression(term110, term210, new RuleConnectionMethodAndMin());
	        rule10.setAntecedents(antecedentAnd10);
	        rule10.addConsequent(z04, "z04", false);
	        ruleBlock.add(rule10);
	
	        Rule rule11 = new Rule("Rule2", ruleBlock);
	        RuleTerm term111 = new RuleTerm(u14 , "u14", false);
	        RuleTerm term211 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd11 = new RuleExpression(term111, term211, new RuleConnectionMethodAndMin());
	        rule11.setAntecedents(antecedentAnd11);
	        rule11.addConsequent(z14, "z14", false);
	        ruleBlock.add(rule11);
	        
	        Rule rule12 = new Rule("Rule3", ruleBlock);
	        RuleTerm term112 = new RuleTerm(u24 , "u24", false);
	        RuleTerm term212 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd12 = new RuleExpression(term112, term212, new RuleConnectionMethodAndMin());
	        rule12.setAntecedents(antecedentAnd12);
	        rule12.addConsequent(z24, "z24", false);
	        ruleBlock.add(rule12);
	        
	        Rule rule13 = new Rule("Rule4", ruleBlock);
	        RuleTerm term113 = new RuleTerm(u34 , "u34", false);
	        RuleTerm term213 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd13 = new RuleExpression(term113, term213, new RuleConnectionMethodAndMin());
	        rule13.setAntecedents(antecedentAnd13);
	        rule13.addConsequent(z34, "z34", false);
	        ruleBlock.add(rule13);
	        
	        Rule rule14 = new Rule("Rule5", ruleBlock);
	        RuleTerm term114 = new RuleTerm(u44 , "u44", false);
	        RuleTerm term214 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd14 = new RuleExpression(term114, term214, new RuleConnectionMethodAndMin());
	        rule14.setAntecedents(antecedentAnd14);
	        rule14.addConsequent(z44, "z44", false);
	        ruleBlock.add(rule14);

		
                Rule rule15 = new Rule("Rule6", ruleBlock);
	        RuleTerm term115 = new RuleTerm(u54 , "u54", false);
	        RuleTerm term215 = new RuleTerm(sc04 , "sc04", false);
	        RuleExpression antecedentAnd15 = new RuleExpression(term115, term215, new RuleConnectionMethodAndMin());
	        rule15.setAntecedents(antecedentAnd15);
	        rule15.addConsequent(z54, "z54", false);
	        ruleBlock.add(rule15);
		}//end if
	*/	
		
//functionBlock.chart();
        functionBlock.setVariable("utility", ut);
        functionBlock.setVariable("scale", sc);

        functionBlock.evaluate();
//System.out.println(functionBlock);
        return functionBlock.getVariable("zarib").getValue();

    }
}
