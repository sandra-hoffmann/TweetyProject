package net.sf.tweety.arg.dung.writer;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import net.sf.tweety.arg.dung.DungTheory;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.arg.dung.syntax.Attack;

/**
 * Writes an abstract argumentation framework into a file of the
 * APX format.
 * 
 * @author Matthias Thimm
 */
public class ApxWriter extends DungWriter{

	/* (non-Javadoc)
	 * @see argc.writer.Writer#write(net.sf.tweety.arg.dung.DungTheory, java.io.File)
	 */
	@Override
	public void write(DungTheory aaf, File f) throws IOException {
		PrintWriter writer = new PrintWriter(f, "UTF-8");
		for(Argument a: aaf)
			writer.println("arg(" + a.getName() + ").");
		for(Attack att: aaf.getAttacks())
			writer.println("att(" + att.getAttacker().getName() + "," + att.getAttacked().getName() + ").");		
		writer.close();		
	}

}