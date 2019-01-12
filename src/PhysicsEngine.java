import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.Scene;

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

		public RightBase() {
			
			initSpawnButton();
			combineAndBuild();
		}

		private void combineAndBuild() {

			root = new VBox(6);
			root.setPadding(new Insets(6));
			root.getChildren().add(spawnButton);
		}

		private void initSpawnButton() {
			
			spawnButton = new Button("Spawn an Entity");
			spawnButton.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					System.out.println("clicked");
				}
			});
		}
	}

	private Stage mainStage;

	private CenterBase centerBase;
	private RightBase  rightBase;

	private Map<EntityType, List<Entity>> entities;
	private List<GameSystem> systems;

	private final long WORLD_WIDTH = 1280;
	private final long WORLD_HEIGHT = 720;

	public PhysicsEngine(Stage primaryStage) {
		
		mainStage = primaryStage;
		init();
		mainStage.show();
	}

	private void init() {
		
		entities = new HashMap<EntityType, List<Entity>>();

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

		systems = new ArrayList<GameSystem>();

		systems.add(new CollisionSystem(WORLD_WIDTH, WORLD_HEIGHT));
		systems.add(new VelocitySystem());
		systems.add(new GravitySystem(1));
	}
}