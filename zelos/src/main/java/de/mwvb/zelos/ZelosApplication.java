package de.mwvb.zelos;

import java.io.File;

import de.mwvb.zelos.gui.main.MainWindow;
import de.mwvb.zelos.model.entity.Modell;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Startklasse für Zelos Codegenerator
 * 
 * @author Marcus Warm
 */
public class ZelosApplication extends Application {
	public static final String APP_NAME = "Zelos Code Generator";
	public static final String APP_VERSION = "0.1.0";
	public static ZelosConfig config;
	/** aktives Modell */
	public static Modell modell;
	
	public static void main(String[] args) {
		if (args.length > 0) {
			File f = new File(args[0]);
			if (f.exists()) {
				config = new ZelosConfig(f);
			} else {
				System.out.println("Datei nicht vorhanden: " + f.getAbsolutePath());
				System.exit(-1);
				return;
			}
		} else {
			config = new ZelosConfig();
		}
		ZelosApplication.modell = config.getModelle().get(0);

		launch(ZelosApplication.class, new String[] {});
	}

	@Override
	public void start(Stage stage) {
		new MainWindow().show(stage, false);
	}
}
