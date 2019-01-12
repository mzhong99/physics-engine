import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import javafx.event.EventHandler;

public class PointEntity extends Entity {
	
	public PointEntity(PhysicsEngine engine) {
		this(engine, 0, 0);
	}

	public PointEntity(PhysicsEngine engine, double p_x, double p_y) {
		this(engine, p_x, p_y, 0, 0);
	}

	public PointEntity(PhysicsEngine engine, double p_x, double p_y, double v_x, double v_y) {
		super(engine);

		entityTypes.add(EntityType.MOVABLE);
		entityTypes.add(EntityType.COLLIDABLE);
		entityTypes.add(EntityType.VIEWABLE);
		entityTypes.add(EntityType.WEIGHTABLE);

		Rectangle rectangle = new Rectangle(20, 20);

		initRectangleDraggedBehavior(rectangle);

		components.put(ComponentType.POSITION, new Vector2D(p_x, p_y));
		components.put(ComponentType.VELOCITY, new Vector2D(v_x, v_y));
		components.put(ComponentType.VIEW, rectangle);
	}

	private void initRectangleDraggedBehavior(final Rectangle rectangle) {
		
		final Vector2D delta  = new Vector2D();
		final Vector2D buffer = Vector2D.scalarMultiply(
			0.5, 
			new Vector2D(
				rectangle.getWidth(), 
				rectangle.getHeight()
			)
		);

		rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				Vector2D oldCenter = new Vector2D(
					rectangle.getX() + 0.5 * rectangle.getWidth(),
					rectangle.getY() + 0.5 * rectangle.getHeight()
				);

				Vector2D newCenter = new Vector2D(event.getX(), event.getY());
				Vector2D deltaTemp = Vector2D.subtract(newCenter, oldCenter);
				
				delta.x = deltaTemp.x;
				delta.y = deltaTemp.y;
				
				components.put(ComponentType.VELOCITY, new Vector2D());

				rectangle.getScene().setCursor(Cursor.MOVE);
			}
		});

		rectangle.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				rectangle.getScene().setCursor(Cursor.HAND);

				if (engine.worldIsRunning()) {
					
					double safeDeltaTime = engine.deltaTime();
					if (safeDeltaTime < 1.0 / 60.0) {
						safeDeltaTime = 1.0 / 60.0;
					}

					Vector2D newVelocity = new Vector2D(delta.x / safeDeltaTime, delta.y / safeDeltaTime);
					components.put(ComponentType.VELOCITY, newVelocity);
				}
			}
		});

		rectangle.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				((Vector2D)components.get(ComponentType.POSITION)).x = event.getX() + delta.x - buffer.x;
				((Vector2D)components.get(ComponentType.POSITION)).y = event.getY() + delta.y - buffer.y;
			}
		});

		rectangle.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (!event.isPrimaryButtonDown()) {
					rectangle.getScene().setCursor(Cursor.HAND);
				}
			}
		});

		rectangle.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if (!event.isPrimaryButtonDown()) {
					rectangle.getScene().setCursor(Cursor.DEFAULT);
				}
			}
		});
	}
}