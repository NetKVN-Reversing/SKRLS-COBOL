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
import io.proleap.cobol.Cobol85Parser.AcceptFromEscapeKeyStatementContext;
import io.proleap.cobol.Cobol85Parser.AcceptFromMnemonicStatementContext;
import io.proleap.cobol.Cobol85Parser.AcceptMessageCountStatementContext;
import io.proleap.cobol.Cobol85Parser.AcceptStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.Scope;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.procedure.NotOnExceptionClause;
import io.proleap.cobol.asg.metamodel.procedure.OnExceptionClause;
import io.proleap.cobol.asg.metamodel.procedure.StatementType;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.accept.AcceptFromDateStatement;
import io.proleap.cobol.asg.metamodel.procedure.accept.AcceptFromEscapeKeyStatement;
import io.proleap.cobol.asg.metamodel.procedure.accept.AcceptFromMnemonicStatement;
import io.proleap.cobol.asg.metamodel.procedure.accept.AcceptMessageCountStatement;
import io.proleap.cobol.asg.metamodel.procedure.accept.AcceptStatement;
import io.proleap.cobol.asg.metamodel.procedure.impl.StatementImpl;

public class AcceptStatementImpl extends StatementImpl implements AcceptStatement {

	protected Call acceptCall;

	protected AcceptFromDateStatement acceptFromDateStatement;

	protected AcceptFromEscapeKeyStatement acceptFromEscapeKeyStatement;

	protected AcceptFromMnemonicStatement acceptFromMnemonicStatement;

	protected AcceptMessageCountStatement acceptMessageCountStatement;

	protected AcceptType acceptType;

	protected final AcceptStatementContext ctx;

	protected NotOnExceptionClause notOnExceptionClause;

	protected OnExceptionClause onExceptionClause;

	protected final StatementType statementType = StatementTypeEnum.ACCEPT;
	
	private final Producer producer;

	public AcceptStatementImpl(final ProgramUnit programUnit, final Scope scope, final AcceptStatementContext ctx, final Producer producer) {
		super(programUnit, scope, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public AcceptFromDateStatement addAcceptFromDateStatement(final AcceptFromDateStatementContext ctx) {
		AcceptFromDateStatement result = (AcceptFromDateStatement) getASGElement(ctx);

		if (result == null) {
			result = new AcceptFromDateImpl(programUnit, ctx, producer);

			/*
			 * date type
			 */
			final AcceptFromDateStatement.DateType dateType;

			if (ctx.DATE() != null && ctx.YYYYMMDD() == null) {
				dateType = AcceptFromDateStatement.DateType.DATE;
			} else if (ctx.DATE() != null && ctx.YYYYMMDD() != null) {
				dateType = AcceptFromDateStatement.DateType.DATE_YYYYMMDD;
			} else if (ctx.DAY() != null && ctx.YYYYMMDD() == null) {
				dateType = AcceptFromDateStatement.DateType.DAY;
			} else if (ctx.DAY() != null && ctx.YYYYMMDD() != null) {
				dateType = AcceptFromDateStatement.DateType.DAY_YYYYMMDD;
			} else if (ctx.TIME() != null) {
				dateType = AcceptFromDateStatement.DateType.TIME;
			} else if (ctx.TIMER() != null) {
				dateType = AcceptFromDateStatement.DateType.TIMER;
			} else if (ctx.TODAYS_DATE() != null && ctx.YYYYMMDD() == null) {
				dateType = AcceptFromDateStatement.DateType.TODAYS_DATE;
			} else if (ctx.TODAYS_DATE() != null && ctx.YYYYMMDD() != null) {
				dateType = AcceptFromDateStatement.DateType.TODAYS_DATE_MMDDYYYY;
			} else if (ctx.TODAYS_NAME() != null) {
				dateType = AcceptFromDateStatement.DateType.TODAYS_NAME;
			} else if (ctx.YEAR() != null) {
				dateType = AcceptFromDateStatement.DateType.YEAR;
			} else if (ctx.YYYYDDD() != null) {
				dateType = AcceptFromDateStatement.DateType.YYYYDDD;
			} else if (ctx.MMDDYYYY() != null) {
				dateType = AcceptFromDateStatement.DateType.YYYYMMDD;
			} else {
				dateType = null;
			}

			acceptFromDateStatement = result;
			result.setDateType(dateType);

			registerASGElement(result);
		}

		return result;
	}

	@Override
	public AcceptFromEscapeKeyStatement addAcceptFromEscapeKeyStatement(final AcceptFromEscapeKeyStatementContext ctx) {
		AcceptFromEscapeKeyStatement result = (AcceptFromEscapeKeyStatement) getASGElement(ctx);

		if (result == null) {
			result = new AcceptFromEscapeKeyImpl(programUnit, ctx, producer);

			acceptFromEscapeKeyStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public AcceptFromMnemonicStatement addAcceptFromMnemonicStatement(final AcceptFromMnemonicStatementContext ctx) {
		AcceptFromMnemonicStatement result = (AcceptFromMnemonicStatement) getASGElement(ctx);

		if (result == null) {
			result = new AcceptFromMnemonicImpl(programUnit, ctx, producer);

			final Call mnemonicCall = createCall(ctx.mnemonicName());
			result.setMnemonicCall(mnemonicCall);

			acceptFromMnemonicStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public AcceptMessageCountStatement addAcceptMessageCountStatement(final AcceptMessageCountStatementContext ctx) {
		AcceptMessageCountStatement result = (AcceptMessageCountStatement) getASGElement(ctx);

		if (result == null) {
			result = new AcceptMessageCountImpl(programUnit, ctx, producer);

			acceptMessageCountStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Call getAcceptCall() {
		return acceptCall;
	}

	@Override
	public AcceptFromDateStatement getAcceptFromDateStatement() {
		return acceptFromDateStatement;
	}

	@Override
	public AcceptFromEscapeKeyStatement getAcceptFromEscapeKeyStatement() {
		return acceptFromEscapeKeyStatement;
	}

	@Override
	public AcceptFromMnemonicStatement getAcceptFromMnemonicStatement() {
		return acceptFromMnemonicStatement;
	}

	@Override
	public AcceptMessageCountStatement getAcceptMessageCountStatement() {
		return acceptMessageCountStatement;
	}

	@Override
	public AcceptType getAcceptType() {
		return acceptType;
	}

	@Override
	public NotOnExceptionClause getNotOnExceptionClause() {
		return notOnExceptionClause;
	}

	@Override
	public OnExceptionClause getOnExceptionClause() {
		return onExceptionClause;
	}

	@Override
	public StatementType getStatementType() {
		return statementType;
	}

	@Override
	public void setAcceptCall(final Call acceptCall) {
		this.acceptCall = acceptCall;
	}

	@Override
	public void setAcceptType(final AcceptType acceptType) {
		this.acceptType = acceptType;
	}

	@Override
	public void setNotOnExceptionClause(final NotOnExceptionClause notOnExceptionClause) {
		this.notOnExceptionClause = notOnExceptionClause;
	}

	@Override
	public void setOnExceptionClause(final OnExceptionClause onExceptionClause) {
		this.onExceptionClause = onExceptionClause;
	}
}
