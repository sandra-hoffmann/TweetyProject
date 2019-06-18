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
package net.sf.tweety.arg.dung.postulates;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import net.sf.tweety.arg.dung.reasoner.AbstractRankingReasoner;
import net.sf.tweety.arg.dung.semantics.ArgumentRanking;
import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.arg.dung.syntax.DungTheory;

/**
 *  The "defense precedence" postulate for ranking semantics: 
 *  For two arguments with the same number of direct attackers,
 *  a defended argument is ranked higher than a non-defended argument.
 * 
 * @author Anna Gessler
 *
 */
public class RaDefensePrecedence extends RankingPostulate {

	@Override
	public String getName() {
		return "Defense Precedence";
	}

	@Override
	public boolean isApplicable(Collection<Argument> kb) {
		if (kb.size()<2)
			return false;
		
		DungTheory dt = (DungTheory) kb;
		Iterator<Argument> it = dt.iterator();
		Argument a = it.next();
		Argument b = it.next();
		Set<Argument> attackers_a = dt.getAttackers(a);
		Set<Argument> attackers_b = dt.getAttackers(b);
		
		if (attackers_a.size() != attackers_b.size())
			return false;

		Extension kb_extension = new Extension(kb);
		return dt.isAttacked(new Extension(dt.getAttackers(a)),kb_extension) && !dt.isAttacked(new Extension(dt.getAttackers(b)),kb_extension);
	}

	@Override
	public boolean isSatisfied(Collection<Argument> kb, AbstractRankingReasoner<ArgumentRanking> ev) {
		if (!this.isApplicable(kb))
			return true;
		DungTheory dt = (DungTheory) kb;
		Iterator<Argument> it = dt.iterator();
		Argument a = it.next();
		Argument b = it.next();
		ArgumentRanking ranking = ev.getModel((DungTheory)dt);
		return ranking.isStrictlyMoreAcceptableThan(a, b); 
	}

}