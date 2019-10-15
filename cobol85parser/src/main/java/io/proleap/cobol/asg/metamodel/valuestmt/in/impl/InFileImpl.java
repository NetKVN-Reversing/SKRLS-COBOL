/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.in.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.InFileContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.in.InFile;

public class InFileImpl extends CobolDivisionElementImpl implements InFile {

	protected InFileContext ctx;

	protected Call fileCall;

	public InFileImpl(final ProgramUnit programUnit, final InFileContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getFileCall() {
		return fileCall;
	}

	@Override
	public void setFileCall(final Call fileCall) {
		this.fileCall = fileCall;
	}

}
