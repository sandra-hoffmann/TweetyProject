package org.tweetyproject.arg.dung.weightedArgumentation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;

import org.tweetyproject.arg.dung.syntax.Argument;
import org.tweetyproject.arg.dung.syntax.Attack;
import org.tweetyproject.arg.dung.syntax.DungTheory;
import org.tweetyproject.commons.Signature;

public class WeightedArgumentationFramework<T> extends DungTheory{

	
	//default constructor returns classic Dung style AF
    private Map<String, T> weightMap;
    private Semiring<T> semiring;
    
    public WeightedArgumentationFramework() {
    	super();
    	this.weightMap  = new HashMap<>();
        this.semiring = (Semiring<T>) new FuzzySemiring();
    }
    
    //constructor for WAF with specific Semiring
    
    
    //constructor for WAF from graph with specific Semiring

    public WeightedArgumentationFramework(
            Map<String, T> weightMap,
            Semiring<T> semiring
    ) {
    	super();
        this.weightMap = weightMap;
        this.semiring = semiring;
    }
    
    
	/**
	 * Adds the given attack to this weighted Dung theory.
	 * @param attack an attack
	 * @param weight
	 * @return "true" if the set of attacks has been modified.
	 */
	public boolean add(Attack attack, T weight){
		return this.addAttack(attack.getAttacker(), attack.getAttacked(), weight); 
	}
	
	/**
	 * Adds an attack from the first argument to the second to this weighted Dung theory.
	 * @param attacker some argument
	 * @param attacked some argument
	 * @param weight
	 * @return "true" if the set of attacks has been modified.
	 */
	public boolean addAttack(Argument attacker, Argument attacked, T weight){
		boolean result = super.addAttack(attacker, attacked);
		this.setWeight(attacker, attacked, weight);
		return result; 
	}
	
	//returns the strongerAttack
	public Attack strongerAttack (Attack attackA, Attack attackB) {
		T weigthAttackA = this.getWeight(attackA);
		T weigthAttackB = this.getWeight(attackB);
		if (semiring.largerOrSame(weigthAttackA, weigthAttackB) ) {
			return attackA;
		} else {
			return attackB;
		}
	}
	
	//returns the weight of the stronger Attack
	public T compareWeights (Attack attackA, Attack attackB) {
		T weigthAttackA = this.getWeight(attackA);
		T weigthAttackB = this.getWeight(attackB);
		return (semiring.add(weigthAttackA,weigthAttackB));
	}
	
	
    
    //sets the weight of a given attack
    public void setWeight(Attack attack, T weight) {
    	weightMap.put(attack.toString(), semiring.validateAndReturn(weight));
    }
    
    public void setWeight(Argument attacker, Argument attacked, T weight) {
    	String attack = "(" + attacker.getName() +"," + attacked.getName()+ ")";
    	weightMap.put(attack, semiring.validateAndReturn(weight));
    }
    
    public T getWeight(Attack attack) {
    	return weightMap.get(attack.toString());
    }
    
    
    
	public String toString(){		
		return "(" + super.toString() + "," + this.weightMap + ")";
	}

 

    // Inner class for creating c-semiring instances and handling semiring functions
    // T corresponds to the Set 
    static class Semiring<T> {
        private BinaryOperator<T> addition;
        private BinaryOperator<T> multiplication;
        private T zeroElement;
        private T oneElement;

        public Semiring(BinaryOperator<T> addition, BinaryOperator<T> multiplication, T zeroElement, T oneElement) {
            this.addition = addition;
            this.multiplication = multiplication;
            this.zeroElement = zeroElement;
            this.oneElement = oneElement;
        }

        public BinaryOperator<T> getAddition() {
            return addition;
        }

        public BinaryOperator<T> getMultiplication() {
            return multiplication;
        }

        public T getZeroElement() {
            return zeroElement;
        }
        
        public T getOneElement() {
            return oneElement;
        }

        // Multiplication function
        public T multiply(T a, T b) {
            return multiplication.apply(a, b);
        }
        
        // Multiplication function
        public T add(T a, T b) {
            return addition.apply(a, b);
        }
        
        //returns true if value a is larger than or equal to b
        public Boolean largerOrSame(T a, T b) {
        	T larger = add(a,b);
        	return a == larger;
        }
        
        public T validateAndReturn(T value) {
            return value;
        }
        
        
        
        
    }

    // Inner class for a Boolean c-semiring
    static class BooleanSemiring extends Semiring<Boolean> {
        public BooleanSemiring() {
            super((a, b) -> a || b, (a, b) -> a && b, false,true);
        }
    }
    
    
    // Inner class for a Fuzzy c-semiring
    static class FuzzySemiring extends Semiring<Double> {
        public FuzzySemiring() {
            super((a, b) -> Math.max(a, b), (a, b) -> Math.min(a, b), 0.0, 1.0);
        }

            public Double validateAndReturn(Double value) {
                if (value < 0.0 || value > 1.0) {
                    throw new IllegalArgumentException("Value must be between 0 and 1");
                }
                return value;
            }
       }
        
}
