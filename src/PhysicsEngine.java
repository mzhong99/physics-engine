import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.Button;

import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;

public class PhysicsEngine {

	private class CenterBase {
		
		private StackPane root;
		
		public CenterBase() {
			root = new StackPane();
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

	private Map<ComponentKey, List<Entity>> entities;

	public PhysicsEngine(Stage primaryStage) {
		
		mainStage = primaryStage;
		init();
		mainStage.show();
	}

	private void init() {
		
		entities = new HashMap<Long, Entity>();

		initBases();
		initStage();
	}

	private void initStage() {
		
		BorderPane mainPane = new BorderPane();

		mainPane.setCenter(centerBase.root);
		mainPane.setRight(rightBase.root);

		mainStage.setScene(new Scene(mainPane));
		mainStage.setTitle("Physics Engine");
	}

	private void initBases() {

		centerBase = new CenterBase();
		rightBase  = new RightBase();
	}
}