package cmc;

import graficos.Punto;
import mapa.MapaInfo;

public class Nodo implements Comparable<Nodo>
{
	public Nodo anterior;
	public Punto ubicacion;
	public int pesoAcumulado;
	public int distanciaDestino;
	
	public Nodo(Nodo anterior,Punto actual,MapaInfo mapa,Punto destino)
	{
		this.anterior = anterior;
		this.ubicacion = actual;
		this.pesoAcumulado = calcularPeso(mapa,anterior,actual);
		this.distanciaDestino = (int)actual.distance(destino);
	}

	private int calcularPeso(MapaInfo mapa, Nodo anterior, Punto actual) 
	{
		if (anterior != null)
		{
			int peso;
			if(anterior.ubicacion.x != actual.x && anterior.ubicacion.y != actual.y)
			{
				// diagonal, raiz (10^2+10^2)
				peso = 14; 
			}
			else
				peso = 10; // perpendicular
			return anterior.pesoAcumulado + (mapa.getDensidad(actual)+1) * peso;
		}
		else return 0;
	}
	
	public int compareTo(Nodo nodo)
	{	
		return Integer.compare((pesoAcumulado + distanciaDestino), (nodo.pesoAcumulado + nodo.distanciaDestino));
	}
}
