/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.runner.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.jemiahlabs.skrls.core.Producer;

import com.google.common.collect.Lists;

import io.proleap.cobol.Cobol85Lexer;
import io.proleap.cobol.Cobol85Parser;
import io.proleap.cobol.Cobol85Parser.StartRuleContext;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.Program;
import io.proleap.cobol.asg.metamodel.impl.ProgramImpl;
import io.proleap.cobol.asg.params.CobolParserParams;
import io.proleap.cobol.asg.params.impl.CobolParserParamsImpl;
import io.proleap.cobol.asg.runner.CobolParserRunner;
import io.proleap.cobol.asg.runner.ThrowingErrorListener;
import io.proleap.cobol.asg.visitor.ParserVisitor;
import io.proleap.cobol.asg.visitor.impl.CobolCompilationUnitVisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolDataDivisionStep1VisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolDataDivisionStep2VisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolFileControlClauseVisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolFileDescriptionEntryClauseVisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolProcedureDivisionVisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolProcedureStatementVisitorImpl;
import io.proleap.cobol.asg.visitor.impl.CobolProgramUnitVisitorImpl;
import io.proleap.cobol.preprocessor.CobolPreprocessor.CobolSourceFormatEnum;
import io.proleap.cobol.preprocessor.impl.CobolPreprocessorImpl;

public class CobolParserRunnerImpl implements CobolParserRunner {

	private final Producer producer;
	
	public CobolParserRunnerImpl(Producer producer) {
		this.producer = producer;
	}

	protected void analyze(final Program program) {
		analyzeProgramUnits(program);

		analyzeDataDivisionsStep1(program);
		analyzeDataDivisionsStep2(program);

		analyzeFileControlClauses(program);
		analyzeFileDescriptionEntriesClauses(program);

		analyzeProcedureDivisions(program);
		analyzeProcedureStatements(program);
	}

	protected void analyzeDataDivisionsStep1(final Program program) {
		for (final CompilationUnit compilationUnit : program.getCompilationUnits()) {
			final ParserVisitor visitor = new CobolDataDivisionStep1VisitorImpl(program);
			
			producer.emitInfoMessage("Analyzing data divisions of compilation unit " + compilationUnit.getName() + " in step 1.");
			visitor.visit(compilationUnit.getCtx());
		}
	}

	protected void analyzeDataDivisionsStep2(final Program program) {
		for (final CompilationUnit compilationUnit : program.getCompilationUnits()) {
			final ParserVisitor visitor = new CobolDataDivisionStep2VisitorImpl(program);

			producer.emitInfoMessage("Analyzing data divisions of compilation unit " + compilationUnit.getName() + " in step 2.");
			visitor.visit(compilationUnit.getCtx());
		}
	}

	@Override
	public Program analyzeFile(final File cobolFile, final CobolSourceFormatEnum format) throws IOException {
		final CobolParserParams params = createDefaultParams(cobolFile);
		return analyzeFile(cobolFile, format, params);
	}

	@Override
	public Program analyzeFile(final File inputFile, final CobolSourceFormatEnum format, final CobolParserParams params)
			throws IOException {
		final Program program = new ProgramImpl();

		parseFile(inputFile, program, format, params);
		analyze(program);

		return program;
	}

	protected void analyzeFileControlClauses(final Program program) {
		for (final CompilationUnit compilationUnit : program.getCompilationUnits()) {
			final ParserVisitor visitor = new CobolFileControlClauseVisitorImpl(program);
			
			producer.emitInfoMessage("Analyzing file control clauses of compilation unit "+ compilationUnit.getName() +".");
			visitor.visit(compilationUnit.getCtx());
		}
	}

	protected void analyzeFileDescriptionEntriesClauses(final Program program) {
		for (final CompilationUnit compilationUnit : program.getCompilationUnits()) {
			final ParserVisitor visitor = new CobolFileDescriptionEntryClauseVisitorImpl(program);

			producer.emitInfoMessage("Analyzing file description entries of compilation unit " + compilationUnit.getName() + ".");
			visitor.visit(compilationUnit.getCtx());
		}
	}

	protected void analyzeProcedureDivisions(final Program program) {
		for (final CompilationUnit compilationUnit : program.getCompilationUnits()) {
			final ParserVisitor visitor = new CobolProcedureDivisionVisitorImpl(program);

			producer.emitInfoMessage("Analyzing procedure divisions of compilation unit " + compilationUnit.getName() + ".");
			visitor.visit(compilationUnit.getCtx());
		}
	}

	protected void analyzeProcedureStatements(final Program program) {
		for (final CompilationUnit compilationUnit : program.getCompilationUnits()) {
			final ParserVisitor visitor = new CobolProcedureStatementVisitorImpl(program);

			producer.emitInfoMessage("Analyzing statements of compilation unit " + compilationUnit.getName() + ".");
			visitor.visit(compilationUnit.getCtx());
		}
	}

	protected void analyzeProgramUnits(final Program program) {
		for (final CompilationUnit compilationUnit : program.getCompilationUnits()) {
			final ParserVisitor visitor = new CobolProgramUnitVisitorImpl(compilationUnit);
			
			producer.emitInfoMessage("Analyzing program units of compilation unit " + compilationUnit.getName() + ".");
			visitor.visit(compilationUnit.getCtx());
		}
	}

	protected CobolParserParams createDefaultParams(final File cobolFile) {
		final CobolParserParams result = new CobolParserParamsImpl();

		final File copyBooksDirectory = cobolFile.getParentFile();
		result.setCopyBookDirectories(Lists.newArrayList(copyBooksDirectory));

		return result;
	}

	protected String getCompilationUnitName(final File cobolFile) {
		return StringUtils.capitalize(FilenameUtils.removeExtension(cobolFile.getName()));
	}

	protected void parseFile(final File cobolFile, final Program program, final CobolSourceFormatEnum format,
			final CobolParserParams params) throws IOException {
		if (!cobolFile.isFile()) {
			producer.emitWarningMessage("Could not find file: " + cobolFile.getAbsolutePath());
		} else {
			// preprocess input stream
			final String preProcessedInput = new CobolPreprocessorImpl(producer).process(cobolFile, format, params);

			producer.emitInfoMessage("Parsing file " + cobolFile.getName() + ".");

			// run the lexer
			final Cobol85Lexer lexer = new Cobol85Lexer(CharStreams.fromString(preProcessedInput));

			if (!params.getIgnoreSyntaxErrors()) {
				// register an error listener, so that preprocessing stops on errors
				lexer.removeErrorListeners();
				lexer.addErrorListener(new ThrowingErrorListener());
			}

			// get a list of matched tokens
			final CommonTokenStream tokens = new CommonTokenStream(lexer);

			// pass the tokens to the parser
			final Cobol85Parser parser = new Cobol85Parser(tokens);

			if (!params.getIgnoreSyntaxErrors()) {
				// register an error listener, so that preprocessing stops on errors
				parser.removeErrorListeners();
				parser.addErrorListener(new ThrowingErrorListener());
			}

			// specify our entry point
			final StartRuleContext ctx = parser.startRule();

			// determine the copy book name
			final String compilationUnitName = getCompilationUnitName(cobolFile);

			// analyze contained compilation units
			final List<String> lines = splitLines(preProcessedInput);
			final ParserVisitor visitor = new CobolCompilationUnitVisitorImpl(compilationUnitName, lines, tokens,
					program, producer);
			
			producer.emitInfoMessage("Collecting units in file " + cobolFile.getName() + ".");
			visitor.visit(ctx);
		}
	}

	protected List<String> splitLines(final String preProcessedInput) {
		final Scanner scanner = new Scanner(preProcessedInput);
		final List<String> result = new ArrayList<String>();

		while (scanner.hasNextLine()) {
			result.add(scanner.nextLine());
		}

		scanner.close();
		return result;
	}
}
