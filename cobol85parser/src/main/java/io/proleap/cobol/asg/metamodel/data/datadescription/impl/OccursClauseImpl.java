/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.datadescription.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DataOccursClauseContext;
import io.proleap.cobol.Cobol85Parser.DataOccursSortContext;
import io.proleap.cobol.Cobol85Parser.IndexNameContext;
import io.proleap.cobol.Cobol85Parser.QualifiedDataNameContext;
import io.proleap.cobol.asg.metamodel.IntegerLiteral;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.data.datadescription.Index;
import io.proleap.cobol.asg.metamodel.data.datadescription.OccursClause;
import io.proleap.cobol.asg.metamodel.data.datadescription.OccursSort;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class OccursClauseImpl extends CobolDivisionElementImpl implements OccursClause {

	protected DataOccursClauseContext ctx;

	protected Call dependingOnCall;

	protected IntegerLiteral from;

	protected List<Index> indices = new ArrayList<Index>();

	protected Map<String, Index> indicesSymbolTable = new HashMap<String, Index>();

	protected List<OccursSort> occursSorts = new ArrayList<OccursSort>();

	protected IntegerLiteral to;
	
	private final Producer producer;

	public OccursClauseImpl(final ProgramUnit programUnit, final DataOccursClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public Index addIndex(final IndexNameContext ctx) {
		final Index result = (Index) getASGElement(ctx);

		if (result == null) {
			final String name = determineName(ctx);
			final Index index = new IndexImpl(name, programUnit, ctx, producer);

			indices.add(index);
			indicesSymbolTable.put(getSymbol(name), index);

			registerASGElement(index);
		}

		return result;
	}

	@Override
	public OccursSort addOccursSort(final DataOccursSortContext ctx) {
		OccursSort result = (OccursSort) getASGElement(ctx);

		if (result == null) {
			result = new OccursSortImpl(programUnit, ctx, producer);

			/*
			 * order
			 */
			final OccursSort.Order order;

			if (ctx.ASCENDING() != null) {
				order = OccursSort.Order.ASCENDING;
			} else if (ctx.DESCENDING() != null) {
				order = OccursSort.Order.DESCENDING;
			} else {
				order = null;
			}

			result.setOrder(order);

			/*
			 * qualified data names
			 */
			for (final QualifiedDataNameContext qualifiedDataNameContext : ctx.qualifiedDataName()) {
				final Call keyCall = createCall(qualifiedDataNameContext);
				result.addKeyCall(keyCall);
			}

			occursSorts.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Call getDependingOnCall() {
		return dependingOnCall;
	}

	@Override
	public IntegerLiteral getFrom() {
		return from;
	}

	@Override
	public Index getIndex(final String name) {
		return indicesSymbolTable.get(getSymbol(name));
	}

	@Override
	public List<Index> getIndices() {
		return indices;
	}

	@Override
	public List<OccursSort> getOccursSorts() {
		return occursSorts;
	}

	@Override
	public IntegerLiteral getTo() {
		return to;
	}

	@Override
	public void setDependingOnCall(final Call dependingOnCall) {
		this.dependingOnCall = dependingOnCall;
	}

	@Override
	public void setFrom(final IntegerLiteral from) {
		this.from = from;
	}

	@Override
	public void setTo(final IntegerLiteral to) {
		this.to = to;
	}

}
