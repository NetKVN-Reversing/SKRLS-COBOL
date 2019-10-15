/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.report.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.asg.metamodel.data.report.ReportGroupDescriptionEntriesSymbolTableEntry;
import io.proleap.cobol.asg.metamodel.data.report.ReportGroupDescriptionEntry;

public class ReportGroupDescriptionEntriesSymbolTableEntryImpl
		implements ReportGroupDescriptionEntriesSymbolTableEntry {

	protected List<ReportGroupDescriptionEntry> reportGroupDescriptionEntries = new ArrayList<ReportGroupDescriptionEntry>();

	private final Producer producer;
	
	public ReportGroupDescriptionEntriesSymbolTableEntryImpl(Producer producer) {
		this.producer = producer;
	}
	
	@Override
	public void addReportGroupDescriptionEntry(final ReportGroupDescriptionEntry reportGroupDescriptionEntry) {
		if (!reportGroupDescriptionEntries.isEmpty()) {
			producer.emitWarningMessage("multiple declarations of report group description entry " + reportGroupDescriptionEntry);
		}

		reportGroupDescriptionEntries.add(reportGroupDescriptionEntry);
	}

	@Override
	public List<ReportGroupDescriptionEntry> getReportGroupDescriptionEntries() {
		return reportGroupDescriptionEntries;
	}

	@Override
	public ReportGroupDescriptionEntry getReportGroupDescriptionEntry() {
		return reportGroupDescriptionEntries.isEmpty() ? null : reportGroupDescriptionEntries.get(0);
	}
}
