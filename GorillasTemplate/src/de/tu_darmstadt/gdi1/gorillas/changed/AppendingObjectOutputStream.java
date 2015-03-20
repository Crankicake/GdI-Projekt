package de.tu_darmstadt.gdi1.gorillas.changed;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import de.tu_darmstadt.gdi1.gorillas.main.InputOutput;

/**
 * Diese Klasse erbt und ueberschreibt ObjectOutputstream dahingehend, dass der
 * Header des serialisierten Objekts nicht geschrieben wird. Dadurch wird das
 * appenden eines weiteren Objekts ermoeglicht {@link InputOutput}.
 * 
 * Credits to www.stackoverflow.com
 * 
 * @author Simon Foitzik, Salim Karacaoglan, Christoph Gombert, Fabian Czappa
 */

public class AppendingObjectOutputStream extends ObjectOutputStream {
	public AppendingObjectOutputStream(OutputStream out) throws IOException {
		super(out);
	}

	@Override
	protected void writeStreamHeader() throws IOException {
		// do not write a header, but reset:
		// this line added after another question
		// showed a problem with the original
		reset();
	}

}