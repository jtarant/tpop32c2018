package cmc;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import javax.swing.JOptionPane;
import graficos.Punto;
import mapa.MapaInfo;

public class CmcResolucion
{
	private MapaInfo mapa;
	private CmcImple cmc;
	private Map<Punto,Nodo> nodosExpandidos = new HashMap<>();
	private PriorityQueue<Nodo> nodosRestantes = new PriorityQueue<>(); // siempre sale el de menor peso primero
	private Punto origen = null;
	private Punto destino = null;
		
	public CmcResolucion(MapaInfo mapa, CmcImple cmc) 
	{
		this.mapa = mapa;																	// 1
		this.cmc = cmc;																		// 1
		
		if (mapa.getPuntos().size() <= 2)
		{
			origen = mapa.getPuntos().get(0);												// 1
			destino = mapa.getPuntos().get(1);												// 1
			
			Nodo nodoOrigen = new Nodo(null, origen, mapa, destino);						// 1
			nodosRestantes.add(nodoOrigen);													// 1
			nodosExpandidos.put(origen, nodoOrigen);										// 1
			
			Nodo nodoDestino = obtenerNodoDestino();										// 1
			if (nodoDestino != null)															// 1
				generarCamino(nodoDestino);													// 1
			else																			// 1
				JOptionPane.showMessageDialog(null, "No se puede llegar al punto de destino.");	// 1
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Debe seleccionar un punto de origen y otro de destino."); // 1
		}
	}
		
	private Nodo obtenerNodoDestino()
	{
		// busco llegar al nodo que tiene el punto destino
		while (!nodosExpandidos.containsKey(destino))					// n
		{
			// expando y quito el nodo que expandi
			expandir(nodosRestantes.poll());							// n
			
			// si me quede sin nodos, no se puede llegar al destino
			if (nodosRestantes.isEmpty()) return null;					// 1
		}
		return nodosExpandidos.get(destino);							// 1
	}
	
	private void expandir(Nodo actual)
	{
		for (int x = actual.ubicacion.x - 1; x <= actual.ubicacion.x + 1; x++)					// 2n+1
			for (int y = actual.ubicacion.y - 1; y <= actual.ubicacion.y + 1; y++)				// 2n+1
				if (puedoExpandir(actual,x,y))													// 1
				{
					// creo el nuevo nodo, seteando como anterior el actual
					Nodo nodo = new Nodo(actual,new Punto(x,y),mapa,destino);					// n^2 (por los fors)
					
					// lo agrego a la cola de prioridades de los que falan procesar
					nodosRestantes.add(nodo);													// n^2 (por los fors)
					nodosExpandidos.put(new Punto(x, y), nodo);									// n^2 (por los fors)
				}
	}
	
	private boolean puedoExpandir(Nodo actual,int x,int y)
	{
		// Verifico que no lo haya expandido ya, y que este dentro de los limites del mapa
		if (!nodosExpandidos.containsKey(new Punto(x, y)) && (y >= 0 && y < MapaInfo.ALTO) && (x >= 0 && x < MapaInfo.LARGO))		// 1
		{
			// Verifico que no sea infranqueable
			if (mapa.getDensidad(x,y) != 4 )																						// 1												
			{
				// Si voy a ir en diagonal
				if (actual.ubicacion.x != x && actual.ubicacion.y != y)																// 1
				{
					// verifico que pueda hacerlo (no vaya a infranqueable)
					if (mapa.getDensidad(actual.ubicacion.x, y) != 4 && mapa.getDensidad(x, actual.ubicacion.y) != 4)				// 1
						return true;																								// 1
				}
				else return true;														// 1
			}
		}
		return false;																	// 1
	}
	
	private void generarCamino(Nodo fin)
	{
		// Lo hago saliendo desde el destino y usando el valor de anterior para ir al siguiente.
		
		List<Punto> camino = new LinkedList<>();										// 1
		Nodo nodoActual = fin;															// 1
		while (nodoActual.anterior != null)												// n
		{
			camino.add(nodoActual.ubicacion);											// n
			nodoActual = nodoActual.anterior;											// 1
		}
		cmc.dibujarCamino(camino,Color.red);											// 1
		mapa.enviarMensaje("Camino minimo: " + camino.size() + " puntos");				// 1
	}
}
