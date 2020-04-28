/*
 *  This file is part of "TweetyProject", a collection of Java libraries for
 *  logical aspects of artificial intelligence and knowledge representation.
 *
 *  TweetyProject is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License version 3 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *  Copyright 2016 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.arg.rankings.reasoner;

import java.util.Collection;
import java.util.HashSet;

import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.arg.dung.syntax.DungTheory;
import net.sf.tweety.arg.rankings.semantics.NumericalArgumentRanking;
import net.sf.tweety.math.matrix.Matrix;
import net.sf.tweety.math.term.FloatConstant;

/**
 * This class implements the argument ranking approach of [Pu, Zhang, G.Luo,
 * J.Luo. Attacker and Defender Counting Approach for Abstract Argumentation.
 * CoRR 2015].
 * 
 * This approach ranks arguments by counting the number of their attackers and
 * defenders in form of a dialogue game where proponents are defenders and
 * opponents are attackers.
 * 
 * @author Anna Gessler
 */
public class CountingRankingReasoner extends AbstractRankingReasoner<NumericalArgumentRanking> {

	/**
	 * This parameter influences whether shorter/longer attackers/defender lines are
	 * preferred. As shown in [Pu, G.Luo, J.Luo. Some Supplementaries to The Counting Semantics 
	 * for Abstract Argumentation. CoRR 2015], for most applications it is best to choose a 
	 * value in [0.9, 0.98]
	 */
	double damping_factor;

	/**
	 * The algorithm terminates when the change between two iterations is below this
	 * tolerance parameter.
	 */
	double epsilon;

	/**
	 * Create a new CountingRankingReasoner with default parameters.
	 */
	public CountingRankingReasoner() {
		this.damping_factor = 0.9;
		this.epsilon = 0.001;
	}

	/**
	 * Create a new CountingRankingReasoner with the given parameters.
	 * 
	 * @param damping_factor must be in (0,1)
	 */
	public CountingRankingReasoner(double damping_factor, double epsilon) {
		this.damping_factor = damping_factor;
		this.epsilon = epsilon;
	}
	
	/**
	 * Create a new CountingRankingReasoner with the given parameters.
	 * 
	 * @param damping_factor must be in (0,1)
	 */
	public CountingRankingReasoner(double damping_factor) {
		this.damping_factor = damping_factor;
		this.epsilon = 0.001;
	}


	@Override
	public Collection<NumericalArgumentRanking> getModels(DungTheory bbase) {
		Collection<NumericalArgumentRanking> ranks = new HashSet<NumericalArgumentRanking>();
		ranks.add(this.getModel(bbase));
		return ranks;
	}

	@Override
	public NumericalArgumentRanking getModel(DungTheory kb) {
		Matrix adjacency_matrix = kb.getAdjacencyMatrix();
		
		// Apply matrix norm to guarantee that the argument strength scale is bounded
		adjacency_matrix = adjacency_matrix.mult((1.0 / getInfiniteNormalizationFactor(adjacency_matrix)));

		// Apply damping factor
		adjacency_matrix = adjacency_matrix.mult(this.damping_factor).simplify();
		
		int n = kb.getNumberOfNodes();
		Matrix valuations = new Matrix(1, n); // Stores values of the current iteration
		Matrix valuations_old = new Matrix(1, n); // Stores values of the last iteration
		
		Matrix e = new Matrix(1, n); // column vector of all ones
		for (int i = 0; i < n; i++) {
			e.setEntry(0, i, new FloatConstant(1.0)); 
		}
		// the ranking for step 0 is 1.0 for all arguments
		valuations = e; 
		
		do {
			valuations_old = valuations;
			valuations = e.minus(adjacency_matrix.mult(valuations_old)).simplify();
		} while (getDistance(valuations_old, valuations) > epsilon);
		
		NumericalArgumentRanking ranking = new NumericalArgumentRanking();
		ranking.setSortingType(NumericalArgumentRanking.SortingType.DESCENDING);
		int i = 0;
		for (Argument a : kb) 
			ranking.put(a, valuations.getEntry(0, i++).doubleValue());

		return ranking;
	}

	/**
	 * Calculates the infinite matrix norm of the given matrix (i.e. the maximum
	 * absolute row sum).
	 * 
	 * @param matrix
	 * @return infinite matrix norm of the matrix
	 */
	private double getInfiniteNormalizationFactor(Matrix matrix) {
		double max_sum = 0.0;
		for (int y = 0; y < matrix.getXDimension(); y++) {
			double sum = 0.0;
			for (int x = 0; x < matrix.getYDimension(); x++)
				sum += matrix.getEntry(x, y).doubleValue();
			if (sum > max_sum)
				max_sum = sum;
		}
		return max_sum;
	}

	/**
	 * Computes the Euclidean distance between to the given column vectors.
	 * 
	 * @param v_old first column vector
	 * @param v     second column vector
	 * @return distance between v and v_old
	 */
	private double getDistance(Matrix v_old, Matrix v) {
		double sum = 0.0;
		for (int i = 0; i < v.getYDimension(); i++) {
			sum += Math.pow(v.getEntry(0, i).doubleValue() - v_old.getEntry(0, i).doubleValue(), 2.0);
		}
		return Math.sqrt(sum);
	}

}