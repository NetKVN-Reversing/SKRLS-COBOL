/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.close.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.CloseFileContext;
import io.proleap.cobol.Cobol85Parser.CloseStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.Scope;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.procedure.StatementType;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.close.CloseFile;
import io.proleap.cobol.asg.metamodel.procedure.close.CloseStatement;
import io.proleap.cobol.asg.metamodel.procedure.impl.StatementImpl;

public class CloseStatementImpl extends StatementImpl implements CloseStatement {

	protected List<CloseFile> closeFiles = new ArrayList<CloseFile>();

	protected final CloseStatementContext ctx;

	protected final StatementType statementType = StatementTypeEnum.CLOSE;
	
	private final Producer producer;

	public CloseStatementImpl(final ProgramUnit programUnit, final Scope scope, final CloseStatementContext ctx, final Producer producer) {
		super(programUnit, scope, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public CloseFile addCloseFile(final CloseFileContext ctx) {
		CloseFile result = (CloseFile) getASGElement(ctx);

		if (result == null) {
			result = new CloseFileImpl(programUnit, ctx, producer);

			// file
			final Call fileCall = createCall(ctx.fileName());
			result.setFileCall(fileCall);

			// type
			final CloseFile.CloseFileType type;

			// close reel unit
			if (ctx.closeReelUnitStatement() != null) {
				result.addCloseReelUnitStatement(ctx.closeReelUnitStatement());
				type = CloseFile.CloseFileType.ReelUnit;
			}
			// close relative
			else if (ctx.closeRelativeStatement() != null) {
				result.addCloseRelativeStatement(ctx.closeRelativeStatement());
				type = CloseFile.CloseFileType.Relative;
			}
			// close port io
			else if (ctx.closePortFileIOStatement() != null) {
				result.addClosePortFileIOStatement(ctx.closePortFileIOStatement());
				type = CloseFile.CloseFileType.PortFileIO;
			} else {
				type = null;
			}

			result.setCloseFileType(type);

			closeFiles.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<CloseFile> getCloseFiles() {
		return closeFiles;
	}

	@Override
	public StatementType getStatementType() {
		return statementType;
	}

}
