/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.example.cycledetect;

import com.espertech.esper.plugin.PlugInAggregationMultiFunctionStateContext;
import com.espertech.esper.plugin.PlugInAggregationMultiFunctionStateFactory;
import com.espertech.esper.epl.agg.access.AggregationState;
import com.espertech.esper.epl.expression.ExprEvaluator;

public class CycleDetectorAggregationStateFactory implements PlugInAggregationMultiFunctionStateFactory {

    private final ExprEvaluator fromEvaluator;
    private final ExprEvaluator toEvaluator;

    public CycleDetectorAggregationStateFactory(ExprEvaluator fromEvaluator, ExprEvaluator toEvaluator) {
        this.fromEvaluator = fromEvaluator;
        this.toEvaluator = toEvaluator;
    }

    public AggregationState makeAggregationState(PlugInAggregationMultiFunctionStateContext stateContext) {
        return new CycleDetectorAggregationState(this);
    }

    public ExprEvaluator getFromEvaluator() {
        return fromEvaluator;
    }

    public ExprEvaluator getToEvaluator() {
        return toEvaluator;
    }
}
