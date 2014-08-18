package ch.zhaw.iwi.cis.kelvin.framework.service;

import java.io.InputStream;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.configuration.XMLConfiguration;

import __java.io.__InputStream;
import __org.apache.commons.configuration.__XMLConfiguration;

@ServiceRegistryAgent
public class EntityManagerServiceRegistryAgent implements ServiceRegistryAgentInterface
{
	@Override
	public void registerServiceFactories( ServiceRegistry registry )
	{
		InputStream configStream = ClassLoader.getSystemResourceAsStream( "META-INF/persistence.xml" );
		XMLConfiguration xmlConfig = new XMLConfiguration();
		xmlConfig.setDelimiterParsingDisabled( true );
		__XMLConfiguration.load( xmlConfig, configStream );
		__InputStream.close( configStream );

		for ( int i = 0 ; true ; ++i )
		{
			String name = xmlConfig.getString( "persistence-unit(" + i + ")[@name]" );
			
			if ( name == null )
				break;
			
			EntityManagerFactory emFactory = Persistence.createEntityManagerFactory( name );
			EntityManagerServiceFactory serviceFactory = new EntityManagerServiceFactory( emFactory );
			registry.registerServiceFactory( name, serviceFactory );
		}
	}
}
