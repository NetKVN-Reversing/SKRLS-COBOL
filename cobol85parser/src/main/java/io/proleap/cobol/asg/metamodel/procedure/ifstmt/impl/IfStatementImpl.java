/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.ifstmt.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.IfElseContext;
import io.proleap.cobol.Cobol85Parser.IfStatementContext;
import io.proleap.cobol.Cobol85Parser.IfThenContext;
import io.proleap.cobol.Cobol85Parser.StatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.Scope;
import io.proleap.cobol.asg.metamodel.procedure.StatementType;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.ifstmt.Else;
import io.proleap.cobol.asg.metamodel.procedure.ifstmt.IfStatement;
import io.proleap.cobol.asg.metamodel.procedure.ifstmt.Then;
import io.proleap.cobol.asg.metamodel.procedure.impl.StatementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.ConditionValueStmt;

public class IfStatementImpl extends StatementImpl implements IfStatement {

	protected ConditionValueStmt condition;

	protected final IfStatementContext ctx;

	protected Else ifElse;

	protected final StatementType statementType = StatementTypeEnum.IF;

	protected Then then;
	
	private final Producer producer;

	public IfStatementImpl(final ProgramUnit programUnit, final Scope scope, final IfStatementContext ctx, final Producer producer) {
		super(programUnit, scope, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public Else addElse(final IfElseContext ctx) {
		Else result = (Else) getASGElement(ctx);

		if (result == null) {
			result = new ElseImpl(programUnit, ctx, producer);

			// statements
			for (final StatementContext statementContext : ctx.statement()) {
				result.addStatement(statementContext);
			}

			// next sentence
			if (ctx.SENTENCE() != null) {
				result.setNextSentence(true);
			}

			ifElse = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Then addThen(final IfThenContext ctx) {
		Then result = (Then) getASGElement(ctx);

		if (result == null) {
			result = new ThenImpl(programUnit, ctx, producer);

			// statements
			for (final StatementContext statementContext : ctx.statement()) {
				result.addStatement(statementContext);
			}

			// next sentence
			if (ctx.SENTENCE() != null) {
				result.setNextSentence(true);
			}

			then = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ConditionValueStmt getCondition() {
		return condition;
	}

	@Override
	public Else getElse() {
		return ifElse;
	}

	@Override
	public StatementType getStatementType() {
		return statementType;
	}

	@Override
	public Then getThen() {
		return then;
	}

	@Override
	public void setCondition(final ConditionValueStmt condition) {
		this.condition = condition;
	}

}
