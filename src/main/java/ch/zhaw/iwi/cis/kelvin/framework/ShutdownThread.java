package ch.zhaw.iwi.cis.kelvin.framework;

public class ShutdownThread extends Thread
{
	@Override
	public void run()
	{
		KelvinEngine.getEngine().stop();
	}
}
