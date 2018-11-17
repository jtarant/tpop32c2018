package app;

import vista.CanvasFrame;

public class Inicio
{
	public static void main(String[] args) 
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
	          public void run() 
	          {
	            CanvasFrame frame = new CanvasFrame("TP Programacion 3 - CALISI - TARANTINO, 2do cuatrimestre 2018");
	            frame.configurar();
	            frame.presentar();
	          };
		});
	}
}
