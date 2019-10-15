/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.environment.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ConfigurationSectionContext;
import io.proleap.cobol.Cobol85Parser.ConfigurationSectionParagraphContext;
import io.proleap.cobol.Cobol85Parser.EnvironmentDivisionContext;
import io.proleap.cobol.Cobol85Parser.InputOutputSectionContext;
import io.proleap.cobol.Cobol85Parser.InputOutputSectionParagraphContext;
import io.proleap.cobol.Cobol85Parser.SpecialNameClauseContext;
import io.proleap.cobol.Cobol85Parser.SpecialNamesParagraphContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.environment.EnvironmentDivision;
import io.proleap.cobol.asg.metamodel.environment.configuration.ConfigurationSection;
import io.proleap.cobol.asg.metamodel.environment.configuration.impl.ConfigurationSectionImpl;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.InputOutputSection;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.impl.InputOutputSectionImpl;
import io.proleap.cobol.asg.metamodel.environment.specialnames.SpecialNamesParagraph;
import io.proleap.cobol.asg.metamodel.environment.specialnames.impl.SpecialNamesParagraphImpl;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionImpl;

public class EnvironmentDivisionImpl extends CobolDivisionImpl implements EnvironmentDivision {

	protected ConfigurationSection configurationSection;

	protected final EnvironmentDivisionContext ctx;

	protected InputOutputSection inputOutputSection;

	protected SpecialNamesParagraph specialNamesParagraph;
	
	private final Producer producer;

	public EnvironmentDivisionImpl(final ProgramUnit programUnit, final EnvironmentDivisionContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public ConfigurationSection addConfigurationSection(final ConfigurationSectionContext ctx) {
		ConfigurationSection result = (ConfigurationSection) getASGElement(ctx);

		if (result == null) {
			result = new ConfigurationSectionImpl(programUnit, ctx, producer);
			configurationSection = result;

			for (final ConfigurationSectionParagraphContext configurationSectionParagraphContext : ctx
					.configurationSectionParagraph()) {
				if (configurationSectionParagraphContext.sourceComputerParagraph() != null) {
					result.addSourceComputerParagraph(configurationSectionParagraphContext.sourceComputerParagraph());
				} else if (configurationSectionParagraphContext.objectComputerParagraph() != null) {
					result.addObjectComputerParagraph(configurationSectionParagraphContext.objectComputerParagraph());
				} else if (configurationSectionParagraphContext.specialNamesParagraph() != null) {
					addSpecialNamesParagraphNonStandardsCompliant(
							configurationSectionParagraphContext.specialNamesParagraph());
				} else {
					producer.emitWarningMessage("unknown configuration section paragraph " + configurationSectionParagraphContext);
				}
			}

			registerASGElement(result);
		}

		return result;
	}

	@Override
	public InputOutputSection addInputOutputSection(final InputOutputSectionContext ctx) {
		InputOutputSection result = (InputOutputSection) getASGElement(ctx);

		if (result == null) {
			result = new InputOutputSectionImpl(programUnit, ctx, producer);
			inputOutputSection = result;

			for (final InputOutputSectionParagraphContext inputOutputSectionParagraphContext : ctx
					.inputOutputSectionParagraph()) {
				if (inputOutputSectionParagraphContext.fileControlParagraph() != null) {
					result.addFileControlParagraph(inputOutputSectionParagraphContext.fileControlParagraph());
				} else if (inputOutputSectionParagraphContext.ioControlParagraph() != null) {
					result.addIoControlParagraph(inputOutputSectionParagraphContext.ioControlParagraph());
				} else {
					producer.emitWarningMessage("unknown input output section paragraph " + inputOutputSectionParagraphContext);
				}
			}

			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SpecialNamesParagraph addSpecialNamesParagraph(final SpecialNamesParagraphContext ctx) {
		SpecialNamesParagraph result = (SpecialNamesParagraph) getASGElement(ctx);

		if (result == null) {
			result = new SpecialNamesParagraphImpl(programUnit, ctx, producer);
			specialNamesParagraph = result;

			for (final SpecialNameClauseContext specialNameClauseContext : ctx.specialNameClause()) {
				if (specialNameClauseContext.classClause() != null) {
					result.addClassClause(specialNameClauseContext.classClause());
				} else if (specialNameClauseContext.channelClause() != null) {
					result.addChannelClause(specialNameClauseContext.channelClause());
				} else if (specialNameClauseContext.currencySignClause() != null) {
					result.addCurrencySignClause(specialNameClauseContext.currencySignClause());
				} else if (specialNameClauseContext.decimalPointClause() != null) {
					result.addDecimalPointClause(specialNameClauseContext.decimalPointClause());
				} else if (specialNameClauseContext.defaultDisplaySignClause() != null) {
					result.addDefaultDisplaySignClause(specialNameClauseContext.defaultDisplaySignClause());
				} else if (specialNameClauseContext.odtClause() != null) {
					result.addOdtClause(specialNameClauseContext.odtClause());
				} else if (specialNameClauseContext.reserveNetworkClause() != null) {
					result.addReserveNetworkClause(specialNameClauseContext.reserveNetworkClause());
				} else if (specialNameClauseContext.symbolicCharactersClause() != null) {
					result.addSymbolicCharactersClause(specialNameClauseContext.symbolicCharactersClause());
				} else if (specialNameClauseContext.alphabetClause() != null) {
					result.createAlphabetClause(specialNameClauseContext.alphabetClause());
				} else {
					producer.emitWarningMessage("unknown special name clause " + specialNameClauseContext);
				}
			}

			registerASGElement(result);
		}

		return result;
	}

	/**
	 * per specification, a special names paragraph has to be contained in the
	 * environment division; however, IBM COBOL allows a special names paragraph to
	 * be contained in the configuration section; this method bridges the gap
	 * between the denormalized AST and the standards-compliant ASG.
	 */
	protected SpecialNamesParagraph addSpecialNamesParagraphNonStandardsCompliant(
			final SpecialNamesParagraphContext ctx) {
		return addSpecialNamesParagraph(ctx);
	}

	@Override
	public ConfigurationSection getConfigurationSection() {
		return configurationSection;
	}

	@Override
	public InputOutputSection getInputOutputSection() {
		return inputOutputSection;
	}

	@Override
	public SpecialNamesParagraph getSpecialNamesParagraph() {
		return specialNamesParagraph;
	}

}
