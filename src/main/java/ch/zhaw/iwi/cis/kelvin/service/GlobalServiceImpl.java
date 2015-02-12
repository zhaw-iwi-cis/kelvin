package ch.zhaw.iwi.cis.kelvin.service;

import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinEngine;
import ch.zhaw.iwi.cis.kelvin.framework.KelvinServlet;
import ch.zhaw.iwi.cis.kelvin.framework.service.Service;

@Transactional
@Service( name="GlobalService" )
public class GlobalServiceImpl implements GlobalService
{
	@Override
	public String shutdown()
	{
		KelvinEngine.getEngine().stop();
		
		return "shutdown successful";
	}

	@Override
	public String ping()
	{
		return "ping!";
	}

	@Override
	public String showPrincipal()
	{
		return "principal=" + KelvinServlet.getServletRequest().getUserPrincipal();
	}
}
