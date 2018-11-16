package cmc;
import mapa.MapaInfo;
import tda.CmcSC;

public class CmcImple extends CmcSC 
{
	public void run(MapaInfo mapa) 
	{
		this.mapa = mapa;
		new CmcResolucion(mapa, this);
	}
}
