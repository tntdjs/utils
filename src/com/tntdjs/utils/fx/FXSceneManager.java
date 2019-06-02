package com.tntdjs.utils.fx;

import java.util.HashMap;
import java.util.ResourceBundle;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * 
 * Copyright (c) 2017, Todd M. Senauskas and/or its affiliates. All rights reserved. 
 * @author tsenausk
 *
 */
public class FXSceneManager extends StackPane {
	/** Logging reference */
	private static final Logger LOGGER = LogManager.getLogger(FXSceneManager.class.getName());
	/** scenes */
	private HashMap<String, Node> scenes = new HashMap<>();
	/** fxml loader */
	private FXMLLoader fxmlLoader;
	/** the current scene */
	private String currentScene;
	
	/**
	 * Add a scene to the manage list
	 * @param name
	 * @param scene
	 */
	public void addScene(String name, Node scene) {
		scenes.put(name, scene);
	}

	/**
	 * Load a scene into the scene manager
	 * @param classLoader
	 * @param name
	 * @param resource
	 * @param controllerBundle
	 * @return
	 * @throws FXMLLoaderException
	 */
	public boolean loadScene(ClassLoader classLoader, String name, String resource, ResourceBundle controllerBundle)
			throws FXMLLoaderException {
		try {
			LOGGER.info("Classloader: " + classLoader + " - FXName: " + name + " - FXResource: " + resource + " - ResourceBundle: " + controllerBundle);
//			System.out.println("Classloader: " + classLoader + " - FXName: " + name + " - FXResource: " + resource + " - ResourceBundle: " + controllerBundle);
			fxmlLoader = new FXMLLoader(classLoader.getResource(resource), controllerBundle);
			Parent loadScene = (Parent) fxmlLoader.load();
			@SuppressWarnings("unused")
			IFXSceneController myScreenControler = ((IFXSceneController) fxmlLoader.getController());
//			myScreenControler.setScreenParent(this);
			addScene(name, loadScene);
		
		} catch (Exception exception) {
			throw new FXMLLoaderException("FXML Class Loader Exception for :: " + name + " :: " + resource, exception);
		}
		return true;
	}

	/**
	 * Set Scene
	 * @param name
	 * @return boolean is scene set
	 */
	public boolean setScene(final String name) {
		currentScene = name;
		
		if (scenes.get(name) != null) { // scene loaded
			final DoubleProperty opacity = opacityProperty();

			// Is there is more than one scene
			if (!getChildren().isEmpty()) {
				Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
						new KeyFrame(new Duration(1000),
							arg0 -> {	//Lambda / functional interface
								// remove displayed scene
								getChildren().remove(0);
								// add new scene
								getChildren().add(0, scenes.get(name));
								Timeline fadeIn = new Timeline(
										new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
										new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}, new KeyValue(opacity, 0.0)));

				fade.play();
			} else {
				// no one else been displayed, then just show
				setOpacity(0.0);
				getChildren().add(scenes.get(name));
				Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
						new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}

			return true;
		} else {
			LOGGER.error("scene hasn't been loaded!\n");			
			return false;
		}
	}

	/**
	 * Unload the requested Scene
	 * @param name
	 * @return
	 */
	public boolean unloadScene(String name) {
		if (scenes.remove(name) == null) {
			LOGGER.error("scene didn't exist");
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Get the FXML Loader
	 * @return
	 */
	public FXMLLoader getFmxlLoader() {
		return fxmlLoader;
	}
	
	/**
	 * Get the current FX Scene
	 * @return
	 */
	public String getCurrentScene() {
		return currentScene;
	}

}
