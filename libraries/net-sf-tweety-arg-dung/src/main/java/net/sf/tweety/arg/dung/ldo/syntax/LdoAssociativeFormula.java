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
package net.sf.tweety.arg.dung.ldo.syntax;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import net.sf.tweety.arg.dung.syntax.DungSignature;
import net.sf.tweety.commons.Signature;
import net.sf.tweety.logics.commons.syntax.AssociativeFormulaSupport;
import net.sf.tweety.logics.commons.syntax.AssociativeFormulaSupport.AssociativeSupportBridge;
import net.sf.tweety.logics.commons.syntax.interfaces.AssociativeFormula;
import net.sf.tweety.logics.commons.syntax.interfaces.SimpleLogicalFormula;
import net.sf.tweety.logics.pl.syntax.PropositionalPredicate;
import net.sf.tweety.logics.pl.syntax.PropositionalSignature;

/**
 * This class captures the common functionalities of formulas with an associative
 * operation like conjunction, disjunction, etc.
 *
 * @author Matthias Thimm
 * @author Tim Janus
 */
public abstract class LdoAssociativeFormula extends LdoFormula implements AssociativeFormula<LdoFormula>, AssociativeSupportBridge, Collection<LdoFormula> {

	/**
	 * The inner formulas of this formula 
	 */
	protected AssociativeFormulaSupport<LdoFormula> support;
	
	/**
	 * Creates a new (empty) associative formula.
	 */
	public LdoAssociativeFormula(){
		support = new AssociativeFormulaSupport<LdoFormula>(this);
	}
	
	/**
	 * Creates a new associative formula with the given inner formulas. 
	 * @param formulas a collection of formulas.
	 */
	public LdoAssociativeFormula(Collection<? extends LdoFormula> formulas){
		this();
		this.support.addAll(formulas);
	}
	
	/**
	 * Creates a new associative formula with the two given formulae
	 * @param first a propositional formula.
	 * @param second a propositional formula.
	 */
	public LdoAssociativeFormula(LdoFormula first, LdoFormula second){
		this();
		this.add(first);
		this.add(second);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Set<PropositionalPredicate> getPredicates() {
		return (Set<PropositionalPredicate>) support.getPredicates();
	}
	
	@Override
	public List<LdoFormula> getFormulas() {
		return support.getFormulas();
	}

	@Override
	public <C extends SimpleLogicalFormula> Set<C> getFormulas(Class<C> cls) {
		return support.getFormulas(cls);
	
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Set<LdoArgument> getAtoms() {
		return (Set<LdoArgument>) support.getAtoms();
	}
	
		
	/* (non-Javadoc)
	 * @see net.sf.tweety.logics.pl.syntax.PropositionalFormula#getLiterals()
	 */
	@Override
	public Set<LdoFormula> getLiterals(){
		Set<LdoFormula> result = new HashSet<LdoFormula>();
		for(LdoFormula f: this.support){
			result.addAll(f.getLiterals());
		}
		return result;
	}
	
	@Override
	public String toString() {
		return support.toString();
	}
	
	@Override
	public DungSignature getSignature() {
		return (DungSignature)support.getSignature();
	}
	
	@Override
	public Signature createEmptySignature() {
		return new PropositionalSignature();
	}
	
	//-------------------------------------------------------------------------
	//	UTILITY METHODS
	//-------------------------------------------------------------------------
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((support == null) ? 0 : support.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LdoAssociativeFormula other = (LdoAssociativeFormula) obj;
		if (support == null) {
			if (other.support != null)
				return false;
		} else if (!support.equals(other.support))
			return false;
		return true;
	}
	
	//-------------------------------------------------------------------------
	//	COLLECTION INTERFACE
	//-------------------------------------------------------------------------
	
	@Override
	public boolean add(LdoFormula f){
		return this.support.add(f);
	}
	
	@Override
	public boolean addAll(Collection<? extends LdoFormula> c){
		return this.support.addAll(c);
	}
	
	@Override
	public void clear(){
		this.support.clear();
	}
	
	@Override
	public boolean contains(Object o){
		return this.support.contains(o);
	}
	
	@Override
	public boolean containsAll(Collection<?> c){
		return this.support.containsAll(c);
	}
	
	@Override
	public boolean isEmpty(){
		return this.support.isEmpty();
	}
	
	@Override
	public Iterator<LdoFormula> iterator(){
		return this.support.iterator();
	}

	@Override
	public boolean remove(Object o){
		return this.support.remove(o);
	}
	
	@Override
	public boolean removeAll(Collection<?> c){
		return this.support.removeAll(c);
	}
	
	@Override
	public boolean retainAll(Collection<?> c){
		return this.support.retainAll(c);
	}
	
	@Override
	public int size(){
		return this.support.size();
	}
	
	@Override
	public Object[] toArray(){
		return this.support.toArray();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object[] toArray(Object[] a){
		return this.support.toArray(a);
	}
	
	@Override
	public void add(int index, LdoFormula element) {
		this.support.add(index, element);
	}

	@Override
	public boolean addAll(int index, Collection<? extends LdoFormula> c) {
		return this.support.addAll(index, c);
	}

	@Override
	public LdoFormula get(int index) {
		return this.support.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.support.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.support.lastIndexOf(o);
	}

	@Override
	public ListIterator<LdoFormula> listIterator() {
		return this.support.listIterator();
	}

	@Override
	public ListIterator<LdoFormula> listIterator(int index) {
		return this.support.listIterator(index);
	}

	@Override
	public LdoFormula remove(int index) {
		return this.support.remove(index);
	}

	@Override
	public LdoFormula set(int index, LdoFormula element) {
		return this.support.set(index, element);
	}

	@Override
	public List<LdoFormula> subList(int fromIndex, int toIndex) {
		return this.support.subList(fromIndex, toIndex);
	}
	
}