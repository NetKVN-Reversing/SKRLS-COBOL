/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.arithmetic.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.BasisContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;
import io.proleap.cobol.asg.metamodel.valuestmt.arithmetic.Basis;
import io.proleap.cobol.asg.metamodel.valuestmt.impl.ValueStmtImpl;

public class BasisImpl extends ValueStmtImpl implements Basis {

	protected ValueStmt basisValueStmt;

	protected BasisContext ctx;

	public BasisImpl(final ProgramUnit programUnit, final BasisContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public ValueStmt getBasisValueStmt() {
		return basisValueStmt;
	}

	@Override
	public void setBasisValueStmt(final ValueStmt basisValueStmt) {
		this.basisValueStmt = basisValueStmt;
	}
}
