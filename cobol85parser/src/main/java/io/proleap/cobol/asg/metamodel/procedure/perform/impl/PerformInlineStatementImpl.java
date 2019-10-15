/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.perform.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.PerformInlineStatementContext;
import io.proleap.cobol.Cobol85Parser.PerformTypeContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.ScopeImpl;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformInlineStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformType;

public class PerformInlineStatementImpl extends ScopeImpl implements PerformInlineStatement {

	protected final PerformInlineStatementContext ctx;

	protected PerformType performType;
	
	private final Producer producer;

	public PerformInlineStatementImpl(final ProgramUnit programUnit, final PerformInlineStatementContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public PerformType addPerformType(final PerformTypeContext ctx) {
		PerformType result = (PerformType) getASGElement(ctx);

		if (result == null) {
			result = new PerformTypeImpl(programUnit, ctx, producer);

			final PerformType.PerformTypeType type;

			if (ctx.performTimes() != null) {
				type = PerformType.PerformTypeType.TIMES;

				result.addTimes(ctx.performTimes());
			} else if (ctx.performUntil() != null) {
				type = PerformType.PerformTypeType.UNTIL;

				result.addUntil(ctx.performUntil());
			} else if (ctx.performVarying() != null) {
				type = PerformType.PerformTypeType.VARYING;

				result.addVarying(ctx.performVarying());
			} else {
				type = null;
			}

			result.setPerformTypeType(type);

			performType = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public PerformType getPerformType() {
		return performType;
	}
}
