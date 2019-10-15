/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.environment.inputoutput.iocontrol.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.CommitmentControlClauseContext;
import io.proleap.cobol.Cobol85Parser.FileNameContext;
import io.proleap.cobol.Cobol85Parser.IoControlParagraphContext;
import io.proleap.cobol.Cobol85Parser.MultipleFileClauseContext;
import io.proleap.cobol.Cobol85Parser.MultipleFilePositionContext;
import io.proleap.cobol.Cobol85Parser.RerunClauseContext;
import io.proleap.cobol.Cobol85Parser.SameClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.iocontrol.CommitmentControlClause;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.iocontrol.IoControlParagraph;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.iocontrol.MultipleFileClause;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.iocontrol.RerunClause;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.iocontrol.SameClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class IoControlParagraphImpl extends CobolDivisionElementImpl implements IoControlParagraph {

	protected CommitmentControlClause commitmentControlClause;

	protected final IoControlParagraphContext ctx;

	protected Call fileCall;

	protected MultipleFileClause multipleFileClause;

	protected RerunClause rerunClause;

	protected List<SameClause> sameClauses = new ArrayList<SameClause>();
	
	private final Producer producer;

	public IoControlParagraphImpl(final ProgramUnit programUnit, final IoControlParagraphContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public CommitmentControlClause addCommitmentControlClause(final CommitmentControlClauseContext ctx) {
		CommitmentControlClause result = (CommitmentControlClause) getASGElement(ctx);

		if (result == null) {
			result = new CommitmentControlClauseImpl(programUnit, ctx, producer);

			if (ctx.fileName() != null) {
				final Call fileCall = createCall(ctx.fileName());
				result.setFileCall(fileCall);
			}

			commitmentControlClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public MultipleFileClause addMultipleFileClause(final MultipleFileClauseContext ctx) {
		MultipleFileClause result = (MultipleFileClause) getASGElement(ctx);

		if (result == null) {
			result = new MultipleFileClauseImpl(programUnit, ctx, producer);

			for (final MultipleFilePositionContext multipleFilePositionContext : ctx.multipleFilePosition()) {
				result.addMultipleFilePosition(multipleFilePositionContext);
			}

			multipleFileClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public RerunClause addRerunClause(final RerunClauseContext ctx) {
		RerunClause result = (RerunClause) getASGElement(ctx);

		if (result == null) {
			result = new RerunClauseImpl(programUnit, ctx, producer);

			/*
			 * on value
			 */
			final ValueStmt onValueStmt = createValueStmt(ctx.assignmentName(), ctx.fileName());
			result.setOnValueStmt(onValueStmt);

			if (ctx.rerunEveryRecords() != null) {
				result.addRerunEveryRecords(ctx.rerunEveryRecords());
			}

			if (ctx.rerunEveryOf() != null) {
				result.addRerunEveryOf(ctx.rerunEveryOf());
			}

			if (ctx.rerunEveryClock() != null) {
				result.addRerunEveryClock(ctx.rerunEveryClock());
			}

			rerunClause = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SameClause addSameClause(final SameClauseContext ctx) {
		SameClause result = (SameClause) getASGElement(ctx);

		if (result == null) {
			result = new SameClauseImpl(programUnit, ctx, producer);

			/*
			 * form
			 */
			final SameClause.Form form;

			if (ctx.RECORD() != null) {
				form = SameClause.Form.RECORD;
			} else if (ctx.SORT() != null) {
				form = SameClause.Form.SORT;
			} else if (ctx.SORT_MERGE() != null) {
				form = SameClause.Form.SORT_MERGE;
			} else {
				form = null;
			}

			result.setForm(form);

			/*
			 * file names
			 */
			for (final FileNameContext fileNameContext : ctx.fileName()) {
				final Call fileCall = createCall(fileNameContext);
				result.addFileCall(fileCall);
			}

			sameClauses.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public CommitmentControlClause getCommitmentControlClause() {
		return commitmentControlClause;
	}

	@Override
	public Call getFileCall() {
		return fileCall;
	}

	@Override
	public MultipleFileClause getMultipleFileClause() {
		return multipleFileClause;
	}

	@Override
	public RerunClause getRerunClause() {
		return rerunClause;
	}

	@Override
	public List<SameClause> getSameClauses() {
		return sameClauses;
	}

	@Override
	public void setFileCall(final Call fileCall) {
		this.fileCall = fileCall;
	}
}
