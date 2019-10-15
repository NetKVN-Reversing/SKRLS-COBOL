/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.accept.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.AcceptFromDateStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.accept.AcceptFromDateStatement;

public class AcceptFromDateImpl extends CobolDivisionElementImpl implements AcceptFromDateStatement {

	protected final AcceptFromDateStatementContext ctx;

	protected DateType dateType;

	public AcceptFromDateImpl(final ProgramUnit programUnit, final AcceptFromDateStatementContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public DateType getDateType() {
		return dateType;
	}

	@Override
	public void setDateType(final DateType dateType) {
		this.dateType = dateType;
	}

}
