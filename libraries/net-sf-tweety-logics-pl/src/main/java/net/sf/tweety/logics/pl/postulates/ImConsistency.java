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
 *  Copyright 2018 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.logics.pl.postulates;

import java.util.Collection;

import net.sf.tweety.logics.commons.analysis.BeliefSetInconsistencyMeasure;
import net.sf.tweety.logics.pl.sat.SatSolver;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;

/**
 * The "consistency" postulate for inconsistency measures: Consistent knowledge bases 
 * receive the minimal inconsistency value (0) and all inconsistent knowledge
 * bases have strictly positive inconsistency values.
 * 
 * @author Anna Gessler
 */
public class ImConsistency extends ImPostulate {
	
	/**
	 * Protected constructor so one uses only the single instance ImPostulate.CONSISTENCY
	 */
	protected ImConsistency() {		
	}

	@Override
	public String getName() {
		return "Consistency";
	}

	@Override
	public boolean isApplicable(Collection<PropositionalFormula> kb) {
		return !kb.isEmpty();
	}

	@Override
	public boolean isSatisfied(Collection<PropositionalFormula> kb,
			BeliefSetInconsistencyMeasure<PropositionalFormula> ev) {
		if(!this.isApplicable(kb))
			return true;
		double measure = ev.inconsistencyMeasure(kb);
		SatSolver solver = SatSolver.getDefaultSolver();
		if (solver.isConsistent(kb)) 
			return (measure==0);
		return (measure>0);
	}

}