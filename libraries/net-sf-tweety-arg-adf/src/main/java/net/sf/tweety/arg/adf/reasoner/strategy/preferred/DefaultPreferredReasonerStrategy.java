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
 *  Copyright 2019 The TweetyProject Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.arg.adf.reasoner.strategy.preferred;

import net.sf.tweety.arg.adf.reasoner.strategy.SearchSpace;
import net.sf.tweety.arg.adf.reasoner.strategy.admissible.AdmissibleReasonerStrategy;
import net.sf.tweety.arg.adf.semantics.Interpretation;
import net.sf.tweety.arg.adf.syntax.AbstractDialecticalFramework;

/**
 * @author Mathias Hofer
 *
 */
public class DefaultPreferredReasonerStrategy implements PreferredReasonerStrategy {

	private AdmissibleReasonerStrategy admissibleStrategy;

	/**
	 * @param satSolver
	 */
	public DefaultPreferredReasonerStrategy(AdmissibleReasonerStrategy admissibleStrategy) {
		this.admissibleStrategy = admissibleStrategy;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.sf.tweety.arg.adf.reasoner.ReasonerStrategy#createSearchSpace(net.sf.
	 * tweety.arg.adf.syntax.AbstractDialecticalFramework)
	 */
	@Override
	public SearchSpace createSearchSpace(AbstractDialecticalFramework adf) {
		return admissibleStrategy.createSearchSpace(adf);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.tweety.arg.adf.reasoner.strategy.preferred.
	 * PreferredReasonerStrategy#nextPreferred(net.sf.tweety.arg.adf.reasoner.
	 * strategy.SearchSpace)
	 */
	@Override
	public Interpretation nextPreferred(SearchSpace searchSpace) {
		Interpretation result = admissibleStrategy.next(searchSpace);
		if (result != null) {
			AbstractDialecticalFramework adf = searchSpace.getAbstractDialecticalFramework();
			SearchSpace maximizeSearchSpace = admissibleStrategy.createSearchSpace(adf);
			maximizeSearchSpace.updateSpecificLarger(result);
			Interpretation max = null;
			while ((max = admissibleStrategy.next(maximizeSearchSpace)) != null) {
				maximizeSearchSpace.updateSpecificLarger(max);
				result = max;
			}
			searchSpace.updateLarger(result);
		}
		return result;
	}

}