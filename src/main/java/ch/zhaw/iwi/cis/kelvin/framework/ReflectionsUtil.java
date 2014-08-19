package ch.zhaw.iwi.cis.kelvin.framework;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.reflections.Reflections;

public class ReflectionsUtil
{
	public static Reflections getReflections( String prefix, Scanner... scanners)
	{
		// For some reason, the Reflections API outputs a bunch of warnings about
		// system URLs it can't access (standard JRE JNI libs, etc.); at least,
		// this is what happens on Mac. For lack of a better solution, I'm just turning
		// of the logging (it works anyway, irrespective of warnings).
		Logger reflectionsLogger = Logger.getLogger( "org.reflections.Reflections" );
		reflectionsLogger.setLevel( Level.OFF );

		return new Reflections( prefix, scanners );
	}
}
