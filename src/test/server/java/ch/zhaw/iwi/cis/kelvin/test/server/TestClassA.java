package ch.zhaw.iwi.cis.kelvin.test.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.zhaw.iwi.cis.kelvin.model.IdentifiableObject;


public class TestClassA extends IdentifiableObject
{
	private static final long serialVersionUID = 1L;
	
	private String valueOfA;
	private List< TestClassB > testClassBs = new ArrayList< TestClassB >();
	
	public TestClassA()
	{}
	
	public TestClassA( String valueOfA, TestClassB ... testClassBs )
	{
		super();
		this.valueOfA = valueOfA;
		this.testClassBs = Arrays.asList( testClassBs );
	}

	public String getValueOfA()
	{
		return valueOfA;
	}

	public void setValueOfA( String valueOfA )
	{
		this.valueOfA = valueOfA;
	}

	public List< TestClassB > getTestClassBs()
	{
		return testClassBs;
	}

	public void setTestClassBs( List< TestClassB > testClassBs )
	{
		this.testClassBs = testClassBs;
	}
}
