/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.perform.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.PerformInlineStatementContext;
import io.proleap.cobol.Cobol85Parser.PerformProcedureStatementContext;
import io.proleap.cobol.Cobol85Parser.PerformStatementContext;
import io.proleap.cobol.Cobol85Parser.ProcedureNameContext;
import io.proleap.cobol.Cobol85Parser.StatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.Scope;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.call.ProcedureCall;
import io.proleap.cobol.asg.metamodel.call.impl.ProcedureCallImpl;
import io.proleap.cobol.asg.metamodel.procedure.Paragraph;
import io.proleap.cobol.asg.metamodel.procedure.StatementType;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.impl.StatementImpl;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformInlineStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformProcedureStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformStatement;

public class PerformStatementImpl extends StatementImpl implements PerformStatement {

	protected final PerformStatementContext ctx;

	protected PerformInlineStatement performInlineStatement;

	protected PerformProcedureStatement performProcedureStatement;

	protected PerformStatementType performStatementType;

	protected final StatementType statementType = StatementTypeEnum.PERFORM;
	
	private final Producer producer;

	public PerformStatementImpl(final ProgramUnit programUnit, final Scope scope, final PerformStatementContext ctx, final Producer producer) {
		super(programUnit, scope, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	protected List<Call> addCallsThrough(final Call firstCall, final Call lastCall,
			final PerformProcedureStatementContext ctx) {
		final List<Call> result = new ArrayList<Call>();

		final String firstCallName = firstCall.getName();
		final String lastCallName = lastCall.getName();

		boolean inThrough = false;

		final List<Paragraph> paragraphs = programUnit.getProcedureDivision().getParagraphs();

		for (final Paragraph paragraph : paragraphs) {
			final String paragraphName = paragraph.getName();

			if (paragraphName.equals(lastCallName)) {
				break;
			} else if (paragraphName.equals(firstCallName)) {
				inThrough = true;
			} else if (inThrough) {
				final ProcedureCall call = new ProcedureCallImpl(paragraphName, paragraph, programUnit, ctx, producer);
				result.add(call);

				linkProcedureCallWithParagraph(call, paragraph);
			}
		}

		return result;
	}

	@Override
	public PerformInlineStatement addPerformInlineStatement(final PerformInlineStatementContext ctx) {
		PerformInlineStatement result = (PerformInlineStatement) getASGElement(ctx);

		if (result == null) {
			result = new PerformInlineStatementImpl(programUnit, ctx, producer);

			// type
			if (ctx.performType() != null) {
				result.addPerformType(ctx.performType());
			}

			// statements
			for (final StatementContext statementContext : ctx.statement()) {
				result.addStatement(statementContext);
			}

			performInlineStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public PerformProcedureStatement addPerformProcedureStatement(final PerformProcedureStatementContext ctx) {
		PerformProcedureStatement result = (PerformProcedureStatement) getASGElement(ctx);

		if (result == null) {
			result = new PerformProcedureStatementImpl(programUnit, ctx, producer);

			/*
			 * procedures
			 */
			final List<ProcedureNameContext> procedureNames = ctx.procedureName();

			if (procedureNames.isEmpty()) {
				producer.emitWarningMessage("no calls in " + ctx);
			} else {
				final Call firstCall = createCall(procedureNames.get(0));
				result.addCall(firstCall);

				if (procedureNames.size() > 1) {
					final Call lastCall = createCall(procedureNames.get(1));
					final List<Call> callsThrough = addCallsThrough(firstCall, lastCall, ctx);

					result.addCalls(callsThrough);
					result.addCall(lastCall);
				}
			}

			/*
			 * type
			 */
			if (ctx.performType() != null) {
				result.addPerformType(ctx.performType());
			}

			performProcedureStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public PerformInlineStatement getPerformInlineStatement() {
		return performInlineStatement;
	}

	@Override
	public PerformProcedureStatement getPerformProcedureStatement() {
		return performProcedureStatement;
	}

	@Override
	public PerformStatementType getPerformStatementType() {
		return performStatementType;
	}

	@Override
	public StatementType getStatementType() {
		return statementType;
	}

	@Override
	public void setPerformStatementType(final PerformStatementType performStatementType) {
		this.performStatementType = performStatementType;
	}

}
