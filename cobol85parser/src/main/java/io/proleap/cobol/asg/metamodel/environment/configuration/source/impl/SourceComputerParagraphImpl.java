/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.environment.configuration.source.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SourceComputerParagraphContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.environment.configuration.source.SourceComputerParagraph;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class SourceComputerParagraphImpl extends CobolDivisionElementImpl implements SourceComputerParagraph {

	protected final SourceComputerParagraphContext ctx;

	protected boolean debuggingMode;

	protected String name;

	public SourceComputerParagraphImpl(final String name, final ProgramUnit programUnit,
			final SourceComputerParagraphContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.name = name;
		this.ctx = ctx;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isDebuggingMode() {
		return debuggingMode;
	}

	@Override
	public void setDebuggingMode(final boolean debuggingMode) {
		this.debuggingMode = debuggingMode;
	}

}
