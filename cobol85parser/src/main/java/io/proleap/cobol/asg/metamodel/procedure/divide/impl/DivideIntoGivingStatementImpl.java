/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.divide.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DivideGivingContext;
import io.proleap.cobol.Cobol85Parser.DivideGivingPhraseContext;
import io.proleap.cobol.Cobol85Parser.DivideIntoGivingStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.divide.DivideIntoGivingStatement;
import io.proleap.cobol.asg.metamodel.procedure.divide.GivingPhrase;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class DivideIntoGivingStatementImpl extends CobolDivisionElementImpl implements DivideIntoGivingStatement {

	protected DivideIntoGivingStatementContext ctx;

	protected GivingPhrase givingPhrase;

	protected ValueStmt intoValueStmt;
	
	private final Producer producer;

	public DivideIntoGivingStatementImpl(final ProgramUnit programUnit, final DivideIntoGivingStatementContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public GivingPhrase addGivingPhrase(final DivideGivingPhraseContext ctx) {
		GivingPhrase result = (GivingPhrase) getASGElement(ctx);

		if (result == null) {
			result = new GivingPhraseImpl(programUnit, ctx, producer);

			// givings
			for (final DivideGivingContext divideGivingContext : ctx.divideGiving()) {
				result.addGiving(divideGivingContext);
			}

			givingPhrase = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public GivingPhrase getGivingPhrase() {
		return givingPhrase;
	}

	@Override
	public ValueStmt getIntoValueStmt() {
		return intoValueStmt;
	}

	@Override
	public void setIntoValueStmt(final ValueStmt intoValueStmt) {
		this.intoValueStmt = intoValueStmt;
	}
}
