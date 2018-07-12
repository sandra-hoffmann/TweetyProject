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
package net.sf.tweety.arg.dung.util;

import java.io.File;
import java.io.FileReader;
import java.util.NoSuchElementException;

import net.sf.tweety.arg.dung.DungTheory;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.commons.Parser;

/**
 * This generator receives a list of files containing Dung theories
 * and returns those step by step.
 * 
 * @author Matthias Thimm
 */
public class FileDungTheoryGenerator implements DungTheoryGenerator {

	/** The files containing Dung theories. */
	private File[] files;
	/** A parser for parsing the files. */
	private Parser<DungTheory> parser;
	/** The index of the next theory to be returned. */
	private int idx;
	/** Whether to loop the files indefinitely*/
	private boolean loop;
	
	/**
	 * Creates a new theory generator for the given files, which
	 * can be parsed by the given parser.
	 * @param files an array of files.
	 * @param parser a parser for the files.
	 * @param loop whether to loop the files indefinitely.
	 */
	public FileDungTheoryGenerator(File[] files, Parser<DungTheory> parser, boolean loop){
		this.files = files;
		this.parser = parser;
		this.idx = 0;
		this.loop = loop;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.tweety.commons.BeliefSetIterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return this.loop || this.idx < this.files.length;
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.commons.BeliefSetIterator#next()
	 */
	@Override
	public DungTheory next() {
		if(this.idx >= this.files.length)
			if(!this.loop)
				throw new NoSuchElementException();
			this.idx = 0;
		try {
			return this.parser.parseBeliefBase(new FileReader(this.files[idx++]));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.arg.dung.util.DungTheoryGenerator#next(net.sf.tweety.arg.dung.syntax.Argument)
	 */
	@Override
	public DungTheory next(Argument arg) {
		throw new UnsupportedOperationException();
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.arg.dung.util.DungTheoryGenerator#setSeed(long)
	 */
	@Override
	public void setSeed(long seed) {
		// nothing to do	
	}
}
