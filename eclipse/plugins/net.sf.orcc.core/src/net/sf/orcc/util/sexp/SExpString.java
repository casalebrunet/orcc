/*
 * Copyright (c) 2011, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.util.sexp;

/**
 * This class defines an s-expression that is a quoted string.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SExpString extends SExpAtom {

	private String contents;

	/**
	 * Creates a new atomic string s-expression with the given contents.
	 * 
	 * @param contents
	 *            contents of this atomic string s-expression
	 */
	public SExpString(String contents) {
		this.contents = contents;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SExpString) {
			SExpString other = (SExpString) obj;
			return getContents().equals(other.getContents());
		}
		return false;
	}

	/**
	 * Returns the contents of this atomic string s-expression.
	 * 
	 * @return the contents of this atomic string s-expression
	 */
	public String getContents() {
		return contents;
	}

	@Override
	public boolean isString() {
		return true;
	}

	/**
	 * Sets the contents of this atomic string s-expression.
	 * 
	 * @param contents
	 *            contents
	 */
	public void setContents(String contents) {
		this.contents = contents;
	}

	@Override
	public String toString() {
		return '"' + contents + '"';
	}

}
