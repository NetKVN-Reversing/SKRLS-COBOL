/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.evaluate.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.EvaluateSelectContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.evaluate.Select;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class SelectImpl extends CobolDivisionElementImpl implements Select {

	protected final EvaluateSelectContext ctx;

	protected ValueStmt selectValueStmt;

	public SelectImpl(final ProgramUnit programUnit, final EvaluateSelectContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public ValueStmt getSelectValueStmt() {
		return selectValueStmt;
	}

	@Override
	public void setSelectValueStmt(final ValueStmt selectValueStmt) {
		this.selectValueStmt = selectValueStmt;
	}

}
