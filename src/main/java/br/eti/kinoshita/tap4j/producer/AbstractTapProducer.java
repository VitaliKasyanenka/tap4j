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
package br.eti.kinoshita.tap4j.producer;

import java.util.ArrayList;
import java.util.List;

import br.eti.kinoshita.tap4j.model.BailOut;
import br.eti.kinoshita.tap4j.model.Comment;
import br.eti.kinoshita.tap4j.model.Footer;
import br.eti.kinoshita.tap4j.model.Header;
import br.eti.kinoshita.tap4j.model.Plan;
import br.eti.kinoshita.tap4j.model.TapResult;
import br.eti.kinoshita.tap4j.model.TestResult;

/**
 * Abstract TAP Producer. Implements few basic methods.
 * 
 * @author Bruno P. Kinoshita - http://www.kinoshita.eti.br
 * @since 1.0
 */
public abstract class AbstractTapProducer 
implements TapProducer
{

	/**
	 * Header.
	 */
	protected Header header;
	
	/**
	 * Plan.
	 */
	protected Plan plan;
	
	/**
	 * List of Tap Lines.
	 */
	protected List<TapResult> tapLines = new ArrayList<TapResult>();
	
	/**
	 * Number of Test Results.
	 */
	protected Integer numberOfTestResults = 0;
	
	/**
	 * Number of Bail Outs.
	 */
	protected Integer numberOfBailOuts = 0;
	
	/**
	 * Number of Comments.
	 */
	protected Integer numberOfComments = 0;
	
	/**
	 * Footer.
	 */
	protected Footer footer;
	
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.TapProducer#setHeader(br.eti.kinoshita.tap4j.Header)
	 */
	public void setHeader(Header header) 
	{
		this.header = header;
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.TapProducer#setPlan(br.eti.kinoshita.tap4j.Plan)
	 */
	public void setPlan(Plan plan) 
	{
		this.plan = plan;
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.TapProducer#addTestResult(br.eti.kinoshita.tap4j.TestResult)
	 */
	public boolean addTestResult(TestResult testResult) 
	{
		this.numberOfTestResults += 1;
		return this.tapLines.add( testResult );
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.producer.TapProducer#getNumberOfTestResults()
	 */
	public Integer getNumberOfTestResults()
	{
		return this.numberOfTestResults;
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.TapProducer#addBailOut(br.eti.kinoshita.tap4j.BailOut)
	 */
	public boolean addBailOut(BailOut bailOut) 
	{
		this.numberOfBailOuts += 1;
		return this.tapLines.add( bailOut );
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.producer.TapProducer#getNumberOfBailOuts()
	 */
	public Integer getNumberOfBailOuts()
	{
		return this.numberOfBailOuts;
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.TapProducer#getTapLines()
	 */
	public List<TapResult> getTapLines() 
	{
		return this.tapLines;
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.TapProducer#addComment(br.eti.kinoshita.tap4j.Comment)
	 */
	public boolean addComment(Comment comment) 
	{
		this.numberOfComments += 1;
		return this.tapLines.add( comment );
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.producer.TapProducer#getNumberOfComments()
	 */
	public Integer getNumberOfComments()
	{
		return this.numberOfComments;
	}
	
	/* (non-Javadoc)
	 * @see br.eti.kinoshita.tap4j.TapProducer#addFooter(br.eti.kinoshita.tap4j.Footer)
	 */
	public void setFooter(Footer footer) 
	{
		this.footer = footer;
	}
	
}