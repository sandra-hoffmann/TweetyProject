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
package net.sf.tweety.arg.adf.reasoner;

import java.util.Iterator;

import net.sf.tweety.arg.adf.reasoner.strategy.ModelIterator;
import net.sf.tweety.arg.adf.reasoner.strategy.admissible.SatAdmissibleReasonerStrategy;
import net.sf.tweety.arg.adf.reasoner.strategy.conflictfree.SatConflictFreeReasonerStrategy;
import net.sf.tweety.arg.adf.reasoner.strategy.preferred.PreferredReasonerStrategy;
import net.sf.tweety.arg.adf.reasoner.strategy.preferred.DefaultPreferredReasonerStrategy;
import net.sf.tweety.arg.adf.sat.IncrementalSatSolver;
import net.sf.tweety.arg.adf.semantics.Interpretation;
import net.sf.tweety.arg.adf.syntax.AbstractDialecticalFramework;

/**
 * @author Mathias Hofer
 *
 */
public class PreferredReasoner extends AbstractDialecticalFrameworkReasoner {

	private PreferredReasonerStrategy strategy;

	/**
	 * @param solver the underlying sat solver
	 */
	public PreferredReasoner(IncrementalSatSolver solver) {
		super();
		this.strategy = new DefaultPreferredReasonerStrategy(
				new SatAdmissibleReasonerStrategy(new SatConflictFreeReasonerStrategy(solver, true), solver));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.sf.tweety.arg.adf.reasoner.AbstractDialecticalFrameworkReasoner#
	 * modelIterator(net.sf.tweety.arg.adf.syntax.AbstractDialecticalFramework)
	 */
	@Override
	public Iterator<Interpretation> modelIterator(AbstractDialecticalFramework adf) {
		return new ModelIterator(strategy, adf);
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// net.sf.tweety.arg.adf.reasoner.AbstractDialecticalFrameworkReasoner#
	// * getModels(net.sf.tweety.arg.adf.syntax.AbstractDialecticalFramework)
	// */
	// @Override
	// public Collection<Interpretation> getModels(AbstractDialecticalFramework
	// adf) {
	// SatEncoding2 enc = new SatEncoding2(adf);
	// Collection<Interpretation> models = new LinkedList<Interpretation>();
	// Interpretation interpretation = new Interpretation(adf);
	// try (SatSolverState state = solver.createState()) {
	// state.add(enc.conflictFreeInterpretation());
	// state.add(enc.bipolar());
	// Collection<Disjunction> kBipolar = null;
	// if (!adf.bipolar()) {
	// kBipolar = enc.kBipolar(interpretation);
	// state.add(kBipolar);
	// }
	// while ((interpretation = existsLargerAdm(adf, new Interpretation(adf),
	// state, enc)) != null) {
	// // found admissible interpretation, now maximize it
	// try (SatSolverState newState = solver.createState()) {
	// newState.add(enc.conflictFreeInterpretation());
	// newState.add(enc.bipolar());
	// if (!adf.bipolar()) {
	// newState.add(kBipolar);
	// }
	// Interpretation prfd = interpretation;
	// while ((interpretation = existsLargerAdm(adf, interpretation, newState,
	// enc)) != null) {
	// prfd = interpretation;
	// }
	// if (prfd != null) {
	// state.add(enc.refineLarger(prfd));
	// models.add(prfd);
	// }
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return models;
	// }
	//
	// /*
	// * (non-Javadoc)
	// *
	// * @see
	// net.sf.tweety.arg.adf.reasoner.AbstractDialecticalFrameworkReasoner#
	// * getModel(net.sf.tweety.arg.adf.syntax.AbstractDialecticalFramework)
	// */
	// @Override
	// public Interpretation getModel(AbstractDialecticalFramework adf) {
	// SatEncoding2 enc = new SatEncoding2(adf);
	// Interpretation interpretation = new Interpretation(adf);
	// try (SatSolverState state = solver.createState()) {
	// state.add(enc.conflictFreeInterpretation());
	// state.add(enc.bipolar());
	// if (!adf.bipolar()) {
	// state.add(enc.kBipolar(interpretation));
	// }
	// while ((interpretation = existsLargerAdm(adf, new Interpretation(adf),
	// state, enc)) != null) {
	// // found admissible interpretation, now maximize it
	// try (SatSolverState newState = solver.createState()) {
	// newState.add(enc.conflictFreeInterpretation());
	// newState.add(enc.bipolar());
	// if (!adf.bipolar()) {
	// newState.add(enc.kBipolar(interpretation));
	// }
	// Interpretation prfd = interpretation;
	// while ((interpretation = existsLargerAdm(adf, interpretation, newState,
	// enc)) != null) {
	// prfd = interpretation;
	// }
	// return prfd;
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return null;
	// }
	//
	// private Interpretation existsLargerAdm(AbstractDialecticalFramework adf,
	// Interpretation interpretation,
	// SatSolverState state, SatEncoding2 enc) {
	// state.add(enc.largerInterpretation(interpretation));
	// net.sf.tweety.commons.Interpretation<PlBeliefSet, PlFormula> witness =
	// state.witness();
	// Interpretation result = null;
	// while (witness != null) {
	// result = enc.interpretationFromWitness(witness);
	// try (SatSolverState newState = solver.createState()) {
	// Collection<Disjunction> verifyAdmissible = enc.verifyAdmissible(result);
	// newState.add(verifyAdmissible);
	// boolean sat = newState.satisfiable();
	// if (sat) {
	// Disjunction refineUnequal = enc.refineUnequal(result);
	// state.add(refineUnequal);
	// } else {
	// return result;
	// }
	// witness = state.witness();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// return null;
	// }

}