/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.call.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.CallByContentContext;
import io.proleap.cobol.Cobol85Parser.CallByContentPhraseContext;
import io.proleap.cobol.Cobol85Parser.CallByReferenceContext;
import io.proleap.cobol.Cobol85Parser.CallByReferencePhraseContext;
import io.proleap.cobol.Cobol85Parser.CallByValueContext;
import io.proleap.cobol.Cobol85Parser.CallByValuePhraseContext;
import io.proleap.cobol.Cobol85Parser.CallUsingParameterContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.call.ByContentPhrase;
import io.proleap.cobol.asg.metamodel.procedure.call.ByReferencePhrase;
import io.proleap.cobol.asg.metamodel.procedure.call.ByValuePhrase;
import io.proleap.cobol.asg.metamodel.procedure.call.UsingParameter;

public class UsingParameterImpl extends CobolDivisionElementImpl implements UsingParameter {

	protected ByContentPhrase byContentPhrase;

	protected ByReferencePhrase byReferencePhrase;

	protected ByValuePhrase byValuePhrase;

	protected final CallUsingParameterContext ctx;

	protected ParameterType parameterType;
	
	private final Producer producer;

	public UsingParameterImpl(final ProgramUnit programUnit, final CallUsingParameterContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public ByContentPhrase addByContentPhrase(final CallByContentPhraseContext ctx) {
		ByContentPhrase result = (ByContentPhrase) getASGElement(ctx);

		if (result == null) {
			result = new ByContentPhraseImpl(programUnit, ctx, producer);

			for (final CallByContentContext callByContentContext : ctx.callByContent()) {
				result.addByContent(callByContentContext);
			}

			byContentPhrase = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ByReferencePhrase addByReferencePhrase(final CallByReferencePhraseContext ctx) {
		ByReferencePhrase result = (ByReferencePhrase) getASGElement(ctx);

		if (result == null) {
			result = new ByReferencePhraseImpl(programUnit, ctx, producer);

			for (final CallByReferenceContext callByReferenceContext : ctx.callByReference()) {
				result.addByReference(callByReferenceContext);
			}

			byReferencePhrase = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ByValuePhrase addByValuePhrase(final CallByValuePhraseContext ctx) {
		ByValuePhrase result = (ByValuePhrase) getASGElement(ctx);

		if (result == null) {
			result = new ByValuePhraseImpl(programUnit, ctx, producer);

			for (final CallByValueContext callByValueContext : ctx.callByValue()) {
				result.addByValue(callByValueContext);
			}

			byValuePhrase = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ByContentPhrase getByContentPhrase() {
		return byContentPhrase;
	}

	@Override
	public ByReferencePhrase getByReferencePhrase() {
		return byReferencePhrase;
	}

	@Override
	public ByValuePhrase getByValuePhrase() {
		return byValuePhrase;
	}

	@Override
	public ParameterType getParameterType() {
		return parameterType;
	}

	@Override
	public void setParameterType(final ParameterType parameterType) {
		this.parameterType = parameterType;
	}
}
