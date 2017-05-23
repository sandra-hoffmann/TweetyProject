package net.sf.tweety.arg.aspic;

import net.sf.tweety.arg.aspic.syntax.AspicArgument;
import net.sf.tweety.arg.aspic.syntax.InferenceRule;
import net.sf.tweety.arg.aspic.syntax.StrictInferenceRule;
import net.sf.tweety.arg.dung.AbstractExtensionReasoner;
import net.sf.tweety.arg.dung.DungTheory;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.commons.Answer;
import net.sf.tweety.commons.BeliefBase;
import net.sf.tweety.commons.Formula;
import net.sf.tweety.commons.Reasoner;
import net.sf.tweety.logics.commons.syntax.interfaces.Invertable;
import net.sf.tweety.logics.pl.syntax.PropositionalFormula;

/**
 * @author Nils Geilen
 *	This class models a reasoner ober Aspic formulae
 */
public class AspicReasoner extends Reasoner  {

	int semantics, inferencetype;

	/**
	 * Creates a new instance
	 * @param beliefBase	an AspicArgumentationTheory
	 * @param semantics	an indicator for the used semantics (c.f. net.sf.tweety.arg.dung.semantics.Semantics)
	 * @param inferencetype	an indicator for the used inference (c.f. net.sf.tweety.arg.dung.semantics.Semantics)
	 */
	public AspicReasoner(BeliefBase beliefBase, int semantics, int inferencetype) {
		super(beliefBase);
		this.semantics = semantics;
		this.inferencetype = inferencetype;
		if (! (beliefBase instanceof AspicArgumentationTheory))
			throw new IllegalArgumentException("Knowledge base of type AspicArgumentationTheory<?> expected");
	}

	/* (non-Javadoc)
	 * @see net.sf.tweety.commons.Reasoner#query(net.sf.tweety.commons.Formula)
	 */
	@Override
	public Answer query(Formula query) {
		AspicArgumentationTheory<?> aat = (AspicArgumentationTheory<?>)getKnowledgeBase();
		DungTheory dt = aat.asDungTheory();
		AbstractExtensionReasoner aer = AbstractExtensionReasoner.getReasonerForSemantics(dt, semantics, inferencetype);
		
		if (query instanceof Argument) {
			return aer.query(query);
		}
		
		for (AspicArgument<?> arg : aat.getArguments()) {
			if (arg.getConclusion().equals(query)) {
				Answer answer = aer.query(arg);
				if (answer.getAnswerBoolean())
					return answer;
			}
		}
		
		Answer answer = new Answer(aat, query);
		answer.setAnswer(false);
		return answer;
		
		
		
	}
	
	

}
