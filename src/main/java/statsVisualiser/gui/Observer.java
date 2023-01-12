package statsVisualiser.gui;

import javax.swing.JPanel;

public class Observer {
 //When analysis complete, start render viewers
	double[] array1;
	double[] array2;
	int[] popArray;
	JPanel pan;
	
	public Observer() {

	}
	
	public void notifyObserver(Boolean done){
		if (done == true) {
			callRenderViewers(pan);
		}
	}
	
	public void supplyArrays(double[] array1, double[] array2) {
		this.array1 = array1;
		this.array2 = array2;
	}

	
	
	public void setPanel(JPanel pan) {
		this.pan = pan;
	}
	
	public void callRenderViewers(JPanel pan) {
		//if no 2nd array then
		/*
		RenderViewers test;
		test = new RenderViewers(pan, array1, array2);
		test = new RenderViewers(pan, array1, array2);
		test.createCharts(pan);
		*/

	}
}
