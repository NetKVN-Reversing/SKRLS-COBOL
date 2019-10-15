/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.write.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.WriteAdvancingLinesContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.write.AdvancingLines;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class AdvancingLinesImpl extends CobolDivisionElementImpl implements AdvancingLines {

	protected WriteAdvancingLinesContext ctx;

	protected ValueStmt linesValueStmt;

	public AdvancingLinesImpl(final ProgramUnit programUnit, final WriteAdvancingLinesContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public ValueStmt getLinesValueStmt() {
		return linesValueStmt;
	}

	@Override
	public void setLinesValueStmt(final ValueStmt linesValueStmt) {
		this.linesValueStmt = linesValueStmt;
	}

}
