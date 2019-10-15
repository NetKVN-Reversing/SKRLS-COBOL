/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.visitor.impl;

import java.util.List;

import org.antlr.v4.runtime.CommonTokenStream;
import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.impl.CompilationUnitImpl;

/**
 * Visitor for collecting units in the AST.
 */
public class CobolCompilationUnitVisitorImpl extends AbstractCobolParserVisitorImpl {

	protected final String compilationUnitName;

	protected final List<String> lines;

	protected final CommonTokenStream tokens;
	
	private final Producer producer;

	public CobolCompilationUnitVisitorImpl(final String compilationUnitName, final List<String> lines,
			final CommonTokenStream tokens, final Program program, final Producer producer) {
		super(program);
		
		this.producer = producer;
		this.compilationUnitName = compilationUnitName;
		this.lines = lines;
		this.tokens = tokens;
	}

	@Override
	public Boolean visitCompilationUnit(final Cobol85Parser.CompilationUnitContext ctx) {
		final CompilationUnit compilationUnit = new CompilationUnitImpl(compilationUnitName, program, tokens, ctx, producer);
		compilationUnit.setLines(lines);

		return visitChildren(ctx);
	}
}
