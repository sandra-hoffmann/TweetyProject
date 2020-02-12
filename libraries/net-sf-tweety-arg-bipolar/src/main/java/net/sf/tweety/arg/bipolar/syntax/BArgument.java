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

package net.sf.tweety.arg.bipolar.syntax;

import net.sf.tweety.arg.dung.syntax.Argument;

/**
 * This class models an argument used by bipolar abstract argumentation theories.
 * @author Lars Bengel
 *
 */
public class BArgument extends net.sf.tweety.arg.dung.syntax.Argument implements BipolarEntity {

    public BArgument(String name) {
        super(name);
    }

    public BArgument(Argument arg) {
        super(arg.getName());
    }

    public boolean contains(Object arg0) {
        return this.equals(arg0);
    }
}