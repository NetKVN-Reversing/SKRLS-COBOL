/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.screen.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ScreenDescriptionValueClauseContext;
import io.proleap.cobol.asg.metamodel.Literal;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.screen.ValueClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class ValueClauseImpl extends CobolDivisionElementImpl implements ValueClause {

	protected ScreenDescriptionValueClauseContext ctx;

	protected Literal literal;

	public ValueClauseImpl(final ProgramUnit programUnit, final ScreenDescriptionValueClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Literal getLiteral() {
		return literal;
	}

	@Override
	public void setLiteral(final Literal literal) {
		this.literal = literal;
	}

}
