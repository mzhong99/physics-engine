import javafx.scene.shape.Rectangle;

public class PointEntity extends Entity {
	
	public PointEntity() {
		this(0, 0);
	}

	public PointEntity(double p_x, double p_y) {
		this(p_x, p_y, 0, 0);
	}

	public PointEntity(double p_x, double p_y, double v_x, double v_y) {
		super();

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
		
		Vector2D delta  = new Vector2D();
		Vector2D buffer = new Vector2D(rectangle.getWidth(), rectangle.getHeight());
		buffer = Vector2D.scalarMultiply(0.5, buffer);

		rectangle.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				Vector2D oldCenter = new Vector2D(
					rectangle.getX() + 0.5 * rectangle.getWidth(),
					rectangle.getY() + 0.5 * rectangle.getHeight()
				);

				Vector2D newCenter = new Vector2D(event.getX(), event.getY());

				delta = Vector2D.subtract(newCenter, oldCenter);
				rectangle.getScene().setCursor(Cursor.MOVE);
			}
		});

		rectangle.setOnMouseReleased(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				rectangle.getScene().setCursor(Cursor.HAND);
			}
		});

		rectangle.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				rectangle.setX(event.getX() + delta.x - buffer.x);
				rectangle.setY(event.getY() + delta.y - buffer.y);
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