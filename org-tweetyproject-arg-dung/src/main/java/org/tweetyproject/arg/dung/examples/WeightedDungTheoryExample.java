package org.tweetyproject.arg.dung.examples;

import java.io.File;
import java.util.Random;

import org.tweetyproject.arg.dung.syntax.Argument;
import org.tweetyproject.arg.dung.syntax.Attack;
import org.tweetyproject.arg.dung.syntax.DungTheory;
import org.tweetyproject.arg.dung.util.DefaultDungTheoryGenerator;
import org.tweetyproject.arg.dung.util.DungTheoryGenerationParameters;
import org.tweetyproject.arg.dung.util.DungTheoryGenerator;
import org.tweetyproject.arg.dung.weightedArgumentation.WeightedArgumentationFramework;

public class WeightedDungTheoryExample {

	public static void main(String[] args) {
		// generate classic Dung theory
		
		
		
		Random random = new Random();
		WeightedArgumentationFramework<Double> waf = new WeightedArgumentationFramework<>();


		for(int i = 0; i < 25; i++) {
			Argument arg = new Argument("a"+i);
			waf.add(arg);
		}
		for(Argument a: waf)
			for(Argument b: waf){
				if(a == b)
					continue;
				if(random.nextDouble() <= 0.2)
					waf.add(new Attack(a,b));
			}
		for(Attack att: waf.getAttacks()) {
			waf.setWeight(att, random.nextDouble());
		}
		
		for(Attack attA: waf.getAttacks()) {
			for(Attack attB: waf.getAttacks()) {
				System.out.println(waf.strongerAttack(attA, attB));
			}
		}
		

	

	}

}
