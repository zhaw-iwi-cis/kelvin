package ch.zhaw.iwi.cis.kelvin;

public class KelvinConfig
{
	public static final String KELVIN_HOME = KelvinConfig.class.getPackage().getName() + ".kelvinHome";
	public static final String BIN = "/bin";
	public static final String CONF = "/conf";
	public static final String DB = "/db";
	public static final String LIB = "/lib";
	public static final String LOG = "/log";
	public static final String PLUGIN = "/plugin";
	public static final String WEB = "/web";
	
	public static String getCleantechHome()
	{
		return System.getProperty( KELVIN_HOME );
	}
	
	public static String getBinDir()
	{
		return getCleantechHome() + BIN;
	}
	
	public static String getConfDir()
	{
		return getCleantechHome() + CONF;
	}
	
	public static String getDbDir()
	{
		return getCleantechHome() + DB;
	}
	
	public static String getLibDir()
	{
		return getCleantechHome() + LIB;
	}
	
	public static String getLogDir()
	{
		return getCleantechHome() + LOG;
	}
	
	public static String getPluginDir()
	{
		return getCleantechHome() + PLUGIN;
	}
	
	public static String getWebDir()
	{
		// TODO Make webBase configuration dependent: alternative is ""
		String webBase = "/../../../src";
		return getCleantechHome() + webBase + WEB;
	}
}
