/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.alter.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.AlterProceedToContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.alter.ProceedTo;

public class ProceedToImpl extends CobolDivisionElementImpl implements ProceedTo {

	protected final AlterProceedToContext ctx;

	protected Call sourceCall;

	protected Call targetCall;

	public ProceedToImpl(final ProgramUnit programUnit, final AlterProceedToContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getSourceCall() {
		return sourceCall;
	}

	@Override
	public Call getTargetCall() {
		return targetCall;
	}

	@Override
	public void setSourceCall(final Call sourceCall) {
		this.sourceCall = sourceCall;
	}

	@Override
	public void setTargetCall(final Call targetCall) {
		this.targetCall = targetCall;
	}

}
