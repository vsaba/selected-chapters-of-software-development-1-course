package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

public class Glavni2 {

	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));

	}

	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
		"ORIGIN                 0.05 0.05",
		"anGLE                 0",
		"unITLength             0.9",
		"unITLenGthDegreeScaler 1.0 / 2.0",
		"",
		"commaNd F draw 1",
		"coMmaNd + rotate 90",
		"comMand - rotate -90",
		"",
		"aXIom L",
		"",
		"production L +RF-LFL-FR+",
		"produCTiOn R -LF+RFR+FL-"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}

}
