/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.write.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.WriteAtEndOfPagePhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.Statement;
import io.proleap.cobol.asg.metamodel.procedure.write.AtEndOfPagePhrase;

public class AtEndOfPagePhraseImpl extends CobolDivisionElementImpl implements AtEndOfPagePhrase {

	protected WriteAtEndOfPagePhraseContext ctx;

	protected List<Statement> statements = new ArrayList<Statement>();

	public AtEndOfPagePhraseImpl(final ProgramUnit programUnit, final WriteAtEndOfPagePhraseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public void addStatement(final Statement statement) {
		statements.add(statement);
	}

	@Override
	public List<Statement> getStatements() {
		return statements;
	}

}
