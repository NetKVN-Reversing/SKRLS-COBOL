/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.subtract.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SubtractFromGivingStatementContext;
import io.proleap.cobol.Cobol85Parser.SubtractGivingContext;
import io.proleap.cobol.Cobol85Parser.SubtractMinuendGivingContext;
import io.proleap.cobol.Cobol85Parser.SubtractSubtrahendContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.subtract.Giving;
import io.proleap.cobol.asg.metamodel.procedure.subtract.MinuendGiving;
import io.proleap.cobol.asg.metamodel.procedure.subtract.SubtractFromGivingStatement;
import io.proleap.cobol.asg.metamodel.procedure.subtract.Subtrahend;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class SubtractFromGivingStatementImpl extends CobolDivisionElementImpl implements SubtractFromGivingStatement {

	protected final SubtractFromGivingStatementContext ctx;

	protected List<Giving> givings = new ArrayList<Giving>();

	protected MinuendGiving minuend;

	protected List<Subtrahend> subtrahends = new ArrayList<Subtrahend>();
	
	private final Producer producer;

	public SubtractFromGivingStatementImpl(final ProgramUnit programUnit,
			final SubtractFromGivingStatementContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public Giving addGiving(final SubtractGivingContext ctx) {
		Giving result = (Giving) getASGElement(ctx);

		if (result == null) {
			result = new GivingImpl(programUnit, ctx, producer);

			// giving call
			if (ctx.identifier() != null) {
				final Call givingCall = createCall(ctx.identifier());
				result.setGivingCall(givingCall);
			}

			// rounded
			if (ctx.ROUNDED() != null) {
				result.setRounded(true);
			}

			givings.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public MinuendGiving addMinuend(final SubtractMinuendGivingContext ctx) {
		MinuendGiving result = (MinuendGiving) getASGElement(ctx);

		if (result == null) {
			result = new MinuendGivingImpl(programUnit, ctx, producer);

			// minuend
			final ValueStmt minuendValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setMinuendValueStmt(minuendValueStmt);

			minuend = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Subtrahend addSubtrahend(final SubtractSubtrahendContext ctx) {
		Subtrahend result = (Subtrahend) getASGElement(ctx);

		if (result == null) {
			result = new SubtrahendImpl(programUnit, ctx, producer);

			// subtrahend
			final ValueStmt subtrahendValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setSubtrahendValueStmt(subtrahendValueStmt);

			subtrahends.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<Giving> getGivings() {
		return givings;
	}

	@Override
	public MinuendGiving getMinuend() {
		return minuend;
	}

	@Override
	public List<Subtrahend> getSubtrahends() {
		return subtrahends;
	}
}
