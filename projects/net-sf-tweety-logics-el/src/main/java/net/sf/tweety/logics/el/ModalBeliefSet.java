/*
 *  This file is part of "Tweety", a collection of Java libraries for
 *  logical aspects of artificial intelligence and knowledge representation.
 *
 *  Tweety is free software: you can redistribute it and/or modify
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
 *  Copyright 2016 The Tweety Project Team <http://tweetyproject.org/contact/>
 */
package net.sf.tweety.logics.el;

import java.util.Set;

import net.sf.tweety.commons.BeliefSet;
import net.sf.tweety.commons.Formula;
import net.sf.tweety.commons.Signature;
import net.sf.tweety.logics.el.syntax.ModalFormula;
import net.sf.tweety.logics.fol.syntax.FolSignature;
import net.sf.tweety.logics.fol.syntax.RelationalFormula;

/**
 * This class models a modal knowledge base, i.e. a set of formulas
 * in modal logic.
 * 
 * @author Anna Gessler
 */
public class ModalBeliefSet extends BeliefSet<RelationalFormula> {
	
	/**
	 * Creates a new empty modal knowledge base.
	 */
	public ModalBeliefSet(){
		super();
	}
	
	/**
	 * Creates a new modal knowledge base with the given set of formulas.
	 * @param formulas
	 */
	public ModalBeliefSet(Set<RelationalFormula> formulas){
		super(formulas);
	}

	@Override
	public Signature getSignature() {
		FolSignature sig = new FolSignature();
		for(Formula m: this) {
			while (m instanceof ModalFormula) {
				m = ((ModalFormula)m).getFormula();
			}
			sig.add(m);	
			}	
		return sig;
	}

}
