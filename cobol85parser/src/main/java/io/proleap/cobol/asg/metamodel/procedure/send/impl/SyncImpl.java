/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.send.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SendAdvancingPhraseContext;
import io.proleap.cobol.Cobol85Parser.SendFromPhraseContext;
import io.proleap.cobol.Cobol85Parser.SendStatementSyncContext;
import io.proleap.cobol.Cobol85Parser.SendWithPhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.send.Advancing;
import io.proleap.cobol.asg.metamodel.procedure.send.From;
import io.proleap.cobol.asg.metamodel.procedure.send.Sync;
import io.proleap.cobol.asg.metamodel.procedure.send.With;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class SyncImpl extends CobolDivisionElementImpl implements Sync {

	protected Advancing advancing;

	protected SendStatementSyncContext ctx;

	protected From from;

	protected ValueStmt receivingProgramValueStmt;

	protected boolean replacing;

	protected With with;
	
	private final Producer producer;

	public SyncImpl(final ProgramUnit programUnit, final SendStatementSyncContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public Advancing addAdvancing(final SendAdvancingPhraseContext ctx) {
		Advancing result = (Advancing) getASGElement(ctx);

		if (result == null) {
			result = new AdvancingImpl(programUnit, ctx, producer);

			// type
			final Advancing.AdvancingType type;

			if (ctx.sendAdvancingPage() != null) {
				type = Advancing.AdvancingType.PAGE;
			} else if (ctx.sendAdvancingLines() != null) {
				result.addAdvancingLines(ctx.sendAdvancingLines());
				type = Advancing.AdvancingType.LINES;
			} else if (ctx.sendAdvancingMnemonic() != null) {
				result.addAdvancingMnemonic(ctx.sendAdvancingMnemonic());
				type = Advancing.AdvancingType.MNEMONIC;
			} else {
				type = null;
			}

			result.setAdvancingType(type);

			// position type
			final Advancing.PositionType positionType;

			if (ctx.AFTER() != null) {
				positionType = Advancing.PositionType.AFTER;
			} else if (ctx.BEFORE() != null) {
				positionType = Advancing.PositionType.BEFORE;
			} else {
				positionType = null;
			}

			result.setPositionType(positionType);

			advancing = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public From addFrom(final SendFromPhraseContext ctx) {
		From result = (From) getASGElement(ctx);

		if (result == null) {
			result = new FromImpl(programUnit, ctx, producer);

			// from
			if (ctx.identifier() != null) {
				final Call fromCall = createCall(ctx.identifier());
				result.setFromCall(fromCall);
			}

			from = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public With addWith(final SendWithPhraseContext ctx) {
		With result = (With) getASGElement(ctx);

		if (result == null) {
			result = new WithImpl(programUnit, ctx, producer);

			// type
			final With.WithType type;

			if (ctx.identifier() != null) {
				final Call withCall = createCall(ctx.identifier());
				result.setWithCall(withCall);
				type = With.WithType.CALL;
			} else if (ctx.EGI() != null) {
				type = With.WithType.EGI;
			} else if (ctx.EMI() != null) {
				type = With.WithType.EMI;
			} else if (ctx.ESI() != null) {
				type = With.WithType.ESI;
			} else {
				type = null;
			}

			result.setWithType(type);

			with = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Advancing getAdvancing() {
		return advancing;
	}

	@Override
	public From getFrom() {
		return from;
	}

	@Override
	public ValueStmt getReceivingProgramValueStmt() {
		return receivingProgramValueStmt;
	}

	@Override
	public With getWith() {
		return with;
	}

	@Override
	public boolean isReplacing() {
		return replacing;
	}

	@Override
	public void setReceivingProgramValueStmt(final ValueStmt receivingProgramValueStmt) {
		this.receivingProgramValueStmt = receivingProgramValueStmt;
	}

	@Override
	public void setReplacing(final boolean replacing) {
		this.replacing = replacing;
	}

}
