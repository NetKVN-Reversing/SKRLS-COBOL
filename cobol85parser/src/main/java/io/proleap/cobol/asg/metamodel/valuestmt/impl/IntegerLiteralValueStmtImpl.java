/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.IntegerLiteralContext;
import io.proleap.cobol.asg.metamodel.IntegerLiteral;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.valuestmt.IntegerLiteralValueStmt;

public class IntegerLiteralValueStmtImpl extends ValueStmtImpl implements IntegerLiteralValueStmt {

	protected IntegerLiteral integerLiteral;

	public IntegerLiteralValueStmtImpl(final ProgramUnit programUnit, final IntegerLiteralContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
	}

	@Override
	public IntegerLiteral getLiteral() {
		return integerLiteral;
	}

	@Override
	public void setIntegerLiteral(final IntegerLiteral integerLiteral) {
		this.integerLiteral = integerLiteral;
	}

	@Override
	public String toString() {
		return integerLiteral != null ? integerLiteral.getValue().toString() : null;
	}
}
