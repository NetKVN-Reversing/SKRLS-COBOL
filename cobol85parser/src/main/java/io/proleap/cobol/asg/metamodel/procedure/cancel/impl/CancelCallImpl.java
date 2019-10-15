/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.cancel.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.CancelCallContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.cancel.CancelCall;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class CancelCallImpl extends CobolDivisionElementImpl implements CancelCall {

	protected CancelType cancelType;

	protected CancelCallContext ctx;

	protected ValueStmt valueStmt;

	public CancelCallImpl(final ProgramUnit programUnit, final CancelCallContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public CancelType getCancelType() {
		return cancelType;
	}

	@Override
	public ValueStmt getValueStmt() {
		return valueStmt;
	}

	@Override
	public void setCancelType(final CancelType cancelType) {
		this.cancelType = cancelType;
	}

	@Override
	public void setValueStmt(final ValueStmt valueStmt) {
		this.valueStmt = valueStmt;
	}

}
