/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.subtract.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SubtractCorrespondingStatementContext;
import io.proleap.cobol.Cobol85Parser.SubtractFromGivingStatementContext;
import io.proleap.cobol.Cobol85Parser.SubtractFromStatementContext;
import io.proleap.cobol.Cobol85Parser.SubtractGivingContext;
import io.proleap.cobol.Cobol85Parser.SubtractMinuendContext;
import io.proleap.cobol.Cobol85Parser.SubtractStatementContext;
import io.proleap.cobol.Cobol85Parser.SubtractSubtrahendContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.Scope;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.procedure.NotOnSizeErrorPhrase;
import io.proleap.cobol.asg.metamodel.procedure.OnSizeErrorPhrase;
import io.proleap.cobol.asg.metamodel.procedure.StatementType;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.impl.StatementImpl;
import io.proleap.cobol.asg.metamodel.procedure.subtract.SubtractCorrespondingStatement;
import io.proleap.cobol.asg.metamodel.procedure.subtract.SubtractFromGivingStatement;
import io.proleap.cobol.asg.metamodel.procedure.subtract.SubtractFromStatement;
import io.proleap.cobol.asg.metamodel.procedure.subtract.SubtractStatement;

public class SubtractStatementImpl extends StatementImpl implements SubtractStatement {

	protected final SubtractStatementContext ctx;

	protected NotOnSizeErrorPhrase notOnSizeErrorPhrase;

	protected OnSizeErrorPhrase onSizeErrorPhrase;

	protected final StatementType statementType = StatementTypeEnum.SUBTRACT;

	protected SubtractCorrespondingStatement subtractCorrespondingStatement;

	protected SubtractFromGivingStatement subtractFromGivingStatement;

	protected SubtractFromStatement subtractFromStatement;

	protected SubtractType subtractType;
	
	private final Producer producer;

	public SubtractStatementImpl(final ProgramUnit programUnit, final Scope scope, final SubtractStatementContext ctx, final Producer producer) {
		super(programUnit, scope, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public SubtractCorrespondingStatement addSubtractCorrespondingStatement(
			final SubtractCorrespondingStatementContext ctx) {
		SubtractCorrespondingStatement result = (SubtractCorrespondingStatement) getASGElement(ctx);

		if (result == null) {
			result = new SubtractCorrespondingStatementImpl(programUnit, ctx, producer);

			// subtrahend call
			final Call subtrahendCall = createCall(ctx.qualifiedDataName());
			result.setSubtrahendCall(subtrahendCall);

			// minuend
			result.addMinuend(ctx.subtractMinuendCorresponding());

			subtractCorrespondingStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SubtractFromGivingStatement addSubtractFromGivingStatement(final SubtractFromGivingStatementContext ctx) {
		SubtractFromGivingStatement result = (SubtractFromGivingStatement) getASGElement(ctx);

		if (result == null) {
			result = new SubtractFromGivingStatementImpl(programUnit, ctx, producer);

			// subtrahends
			for (final SubtractSubtrahendContext subtractSubtrahendContext : ctx.subtractSubtrahend()) {
				result.addSubtrahend(subtractSubtrahendContext);
			}

			// minuend
			result.addMinuend(ctx.subtractMinuendGiving());

			// giving
			for (final SubtractGivingContext subtractGivingContext : ctx.subtractGiving()) {
				result.addGiving(subtractGivingContext);
			}

			subtractFromGivingStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SubtractFromStatement addSubtractFromStatement(final SubtractFromStatementContext ctx) {
		SubtractFromStatement result = (SubtractFromStatement) getASGElement(ctx);

		if (result == null) {
			result = new SubtractFromStatementImpl(programUnit, ctx, producer);

			// subtrahends
			for (final SubtractSubtrahendContext subtractSubtrahendContext : ctx.subtractSubtrahend()) {
				result.addSubtrahend(subtractSubtrahendContext);
			}

			// minuends
			for (final SubtractMinuendContext subtractMinuendContext : ctx.subtractMinuend()) {
				result.addMinuend(subtractMinuendContext);
			}

			subtractFromStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public NotOnSizeErrorPhrase getNotOnSizeErrorPhrase() {
		return notOnSizeErrorPhrase;
	}

	@Override
	public OnSizeErrorPhrase getOnSizeErrorPhrase() {
		return onSizeErrorPhrase;
	}

	@Override
	public StatementType getStatementType() {
		return statementType;
	}

	@Override
	public SubtractCorrespondingStatement getSubtractCorrespondingStatement() {
		return subtractCorrespondingStatement;
	}

	@Override
	public SubtractFromGivingStatement getSubtractFromGivingStatement() {
		return subtractFromGivingStatement;
	}

	@Override
	public SubtractFromStatement getSubtractFromStatement() {
		return subtractFromStatement;
	}

	@Override
	public SubtractType getSubtractType() {
		return subtractType;
	}

	@Override
	public void setNotOnSizeErrorPhrase(final NotOnSizeErrorPhrase notOnSizeErrorPhrase) {
		this.notOnSizeErrorPhrase = notOnSizeErrorPhrase;
	}

	@Override
	public void setOnSizeErrorPhrase(final OnSizeErrorPhrase onSizeErrorPhrase) {
		this.onSizeErrorPhrase = onSizeErrorPhrase;
	}

	@Override
	public void setSubtractType(final SubtractType subtractType) {
		this.subtractType = subtractType;
	}

}
