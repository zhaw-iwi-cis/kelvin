package ch.zhaw.iwi.cis.kelvin.service;



public interface GlobalService extends Service
{
	public String shutdown();
	public String ping();
	public String showPrincipal();
}
