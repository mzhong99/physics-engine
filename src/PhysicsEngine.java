import javafx.application.Platform;

import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;

import java.util.List;
import java.util.ArrayList;

import java.util.Map;
import java.util.HashMap;

public class PhysicsEngine {

	private class CenterBase {
		
		private Pane root;
		
		public CenterBase() {

			root = new Pane();
			
			Rectangle cornerStamp = new Rectangle(20, 20);
			
			cornerStamp.setX(WORLD_WIDTH);
			cornerStamp.setY(WORLD_HEIGHT);

			cornerStamp.setFill(Color.RED);
			cornerStamp.setStroke(Color.RED);
			
			root.getChildren().add(cornerStamp);
		}
	}

	private class TopBase {}
	private class BottomBase {}
	private class LeftBase {}

	private class RightBase {

		private VBox root;

		private Button spawnButton;
		private Button startButton;
		private Button stopButton;

		public RightBase() {
			
			initSpawnButton();
			initStartButton();
			initStopButton();

			combineAndBuild();
		}

		private void combineAndBuild() {

			root = new VBox(6);
			root.setPadding(new Insets(6));

			root.getChildren().add(spawnButton);
			root.getChildren().add(startButton);
			root.getChildren().add(stopButton);
		}

		private void initSpawnButton() {
			
			spawnButton = new Button("Spawn an Entity");
			spawnButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					Entity entity = new PointEntity(
						engineReference,
						(long)(Math.random() * WORLD_WIDTH), 
						(long)(Math.random() * WORLD_HEIGHT)
					);
					
					centerBase.root
						.getChildren()
						.addAll((Node)entity.components.get(ComponentType.VIEW));

					for (EntityType entityType : entity.entityTypes) {
						List<Entity> temp = entities.get(entityType);
						temp.add(entity);
					}
				}
			});
		}

		private void initStartButton() {

			startButton = new Button("Start Engine");
			startButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					startButton.setDisable(true);
					stopButton.setDisable(false);

					WORLD_IS_RUNNING = true;
				}
			});
		}

		private void initStopButton() {

			stopButton = new Button("Stop Engine");
			stopButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					
					startButton.setDisable(false);
					stopButton.setDisable(true);

					WORLD_IS_RUNNING = false;
				}
			});

			stopButton.setDisable(true);
		}
	}

	private class UpdateWorldRunnable implements Runnable {
		
		@Override
		public void run() {

			long timeNanoSeconds = System.nanoTime();
			long deltaTimeNanoSeconds = timeNanoSeconds - lastTimeNanoSeconds;
			lastTimeNanoSeconds = timeNanoSeconds;
			
			updateWorld(deltaTimeNanoSeconds);

			Platform.runLater(new UpdateWorldRunnable());
		}
	}

	private final PhysicsEngine engineReference;

	private final Map <EntityType, List<Entity>> entities;
	private final List<GameSystem> systems;
	private final RenderSystem renderSystem;
	
	private Stage mainStage;

	private CenterBase centerBase;
	private RightBase  rightBase;

	private double  deltaTimeSeconds;
	private long    lastTimeNanoSeconds;
	private boolean WORLD_IS_RUNNING = false;

	private final long WORLD_WIDTH  = 1280;
	private final long WORLD_HEIGHT = 720;

	public PhysicsEngine(Stage primaryStage) {
		
		mainStage = primaryStage;
		entities  = new HashMap<EntityType, List<Entity>>();
		systems   = new ArrayList<GameSystem>();
		
		renderSystem    = new RenderSystem();
		engineReference = this;
		
		lastTimeNanoSeconds = System.nanoTime();
		deltaTimeSeconds    = 1; // used to prevent divides by zero

		init();
		mainStage.show();

		Platform.runLater(new UpdateWorldRunnable());
	}

	private void init() {
		
		for (EntityType entityType : EntityType.values()) {
			entities.put(entityType, new ArrayList<Entity>());
		}

		initBases();
		initStage();
		initSystems();
	}

	private void initStage() {
		
		BorderPane mainPane = new BorderPane();

		mainPane.setCenter(centerBase.root);
		mainPane.setRight(rightBase.root);

		mainStage.setScene(new Scene(mainPane));
		mainStage.setTitle("Physics Engine");

		mainStage.setMinWidth(1280);
		mainStage.setMinHeight(720);
	}

	private void initBases() {

		centerBase = new CenterBase();
		rightBase  = new RightBase();
	}

	private void initSystems() {

		systems.add(new CollisionSystem(WORLD_WIDTH, WORLD_HEIGHT));
		systems.add(new VelocitySystem());
		systems.add(new GravitySystem(150)); // 10 pixels per second
	}

	private void updateWorld(long deltaTimeRaw) {
		
		deltaTimeSeconds = (double) (deltaTimeRaw / ((double) 1e9));
		
		if (WORLD_IS_RUNNING) {
			for (GameSystem system : systems) {
				system.update(entities, deltaTimeSeconds);
			}
		}

		renderSystem.update(entities, deltaTimeSeconds);
	}

	public double deltaTime() {
		return deltaTimeSeconds;
	}

	public boolean worldIsRunning() {
		return WORLD_IS_RUNNING;
	}
}