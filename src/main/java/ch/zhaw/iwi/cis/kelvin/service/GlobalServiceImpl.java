package ch.zhaw.iwi.cis.kelvin.service;

import javax.transaction.Transactional;

import ch.zhaw.iwi.cis.kelvin.framework.KelvinEngine;
import ch.zhaw.iwi.cis.kelvin.framework.ThreadLocalFilter;
import ch.zhaw.iwi.cis.kelvin.framework.service.Service;

@Transactional
@Service
public class GlobalServiceImpl implements GlobalService
{
	public String shutdown()
	{
		KelvinEngine.getEngine().stop();
		
		return "shutdown successful";
	}

	public String ping()
	{
		return "ping!";
	}

	public String showPrincipal()
	{
		return "principal=" + ThreadLocalFilter.getServletRequest().getUserPrincipal();
	}
}
