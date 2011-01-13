/*
 * The MIT License
 *
 * Copyright (c) <2010> <Bruno P. Kinoshita>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.eti.kinoshita.tap4j.ext;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import br.eti.kinoshita.tap4j.model.Plan;
import br.eti.kinoshita.tap4j.model.TestResult;
import br.eti.kinoshita.tap4j.model.TestSet;
import br.eti.kinoshita.tap4j.producer.TapProducer;
import br.eti.kinoshita.tap4j.producer.TapProducerFactory;

/**
 * This class implements a TAP reporter for individual tests.
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br, January 3, 2010
 * @since January 3, 2010
 */
public class TestTAPReporter 
extends TestListenerAdapter
{
	protected final Map<Class<?>, List<ITestResult>> testResultsPerClass = new LinkedHashMap<Class<?>, List<ITestResult>>();
	
	protected final Map<ITestNGMethod, List<ITestResult>> testResultsPerMethod = new LinkedHashMap<ITestNGMethod, List<ITestResult>>();
	
	
	/**
	 * TAP Producer.
	 */
	protected TapProducer tapProducer = TapProducerFactory.makeTap13YamlProducer();
	
	/**
	 * TAP Test Set
	 */
	TestSet testSet;
	
	
	/* (non-Javadoc)
	 * @see org.testng.TestListenerAdapter#onFinish(org.testng.ITestContext)
	 */
	@Override
	public void onFinish( ITestContext testContext )
	{
		this.generateTAPPerClass(testContext);

		this.generateTAPPerMethod(testContext);
	}
	
	
	/**
	 * Generate TAP file for tests
	 * 
	 * @param testContext
	 */
	protected void generateTAPPerClass(ITestContext testContext)
	{
		List<ITestResult> testNGTestResults = TAPUtils.getTestNGResults(testContext);
		
		for ( ITestResult testResult : testNGTestResults )
		{
			Class<?> clazz = testResult.getMethod().getRealClass();
			List<ITestResult> testResultsForThisClass = testResultsPerClass.get( clazz );
			if ( testResultsForThisClass == null )
			{
				testResultsForThisClass = new ArrayList<ITestResult>();
				testResultsPerClass.put(clazz, testResultsForThisClass);
			}
			testResultsForThisClass.add( testResult );
		}
		
		Set<Class<?>> keySet = testResultsPerClass.keySet();
		
		for( Class<?> clazz : keySet )
		{
			testSet = new TestSet();
			
			List<ITestResult> testResults = testResultsPerClass.get( clazz );
			
			testSet.setPlan( new Plan(testResults.size()) );
			
			for ( ITestResult testResult : testResults )
			{
				TestResult tapTestResult = TAPUtils.generateTAPTestResult( testResult, testSet.getNumberOfTestResults()+1 );
				testSet.addTestResult( tapTestResult );
			}
			 
			File output = new File(testContext.getOutputDirectory(), clazz.getName()+".tap");
			tapProducer.dump(testSet, output);
		}
	}
	
	
	/**
	 * Generate a TAP file for every method tested
	 * 
	 * @param testContext
	 */
	protected void generateTAPPerMethod(ITestContext testContext)
	{
		List<ITestResult> testNGTestResults = TAPUtils.getTestNGResults(testContext);
		
		for ( ITestResult testResult : testNGTestResults )
		{
			ITestNGMethod method = testResult.getMethod();
			
			List<ITestResult> testResultsForThisMethod = testResultsPerMethod.get( method );
			
			if ( testResultsForThisMethod == null )
			{
				testResultsForThisMethod = new ArrayList<ITestResult>();
				testResultsPerMethod.put(method, testResultsForThisMethod);
			}
			testResultsForThisMethod.add( testResult );
		}
		
		
		Set<ITestNGMethod> keySet = testResultsPerMethod.keySet();
		
		for( ITestNGMethod method : keySet )
		{
			testSet = new TestSet();
			
			List<ITestResult> testResults = testResultsPerMethod.get( method );
			testSet.setPlan( new Plan(testResults.size()) );
			
			for ( ITestResult testResult : testResults )
			{
				TestResult tapTestResult = TAPUtils.generateTAPTestResult( testResult, testSet.getNumberOfTestResults()+1 );
				testSet.addTestResult( tapTestResult );
			}

			File output = new File(testContext.getOutputDirectory(), method.getTestClass().getName()+"#"+method.getMethodName()+".tap");
			tapProducer.dump(testSet, output);
		}	
	}
}
