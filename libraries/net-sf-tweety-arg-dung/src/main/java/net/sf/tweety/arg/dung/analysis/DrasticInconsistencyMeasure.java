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
package net.sf.tweety.arg.dung.analysis;

import net.sf.tweety.arg.dung.syntax.DungTheory;

/**
 * @author Timothy Gillespie
 * @param <T> the type of Dung theories used
 *
 */
public class DrasticInconsistencyMeasure<T extends DungTheory> implements InconsistencyMeasure<T> {
	/* (non-Javadoc)
	 * @see net.sf.tweety.arg.dung.analysis.InconsistencyMeasure#inconsistencyMeasure
	 */
	@Override
	public Double inconsistencyMeasure(T argumentationFramework) {
		if(argumentationFramework.getAttacks().size() == 0) return 0d;
		return 1d;
	}
}
