import javafx.scene.shape.Rectangle;

public class RenderSystem extends GameSystem {
	
	public RenderSystem() {
		super(EntityType.VIEWABLE);
	}

	protected void update(Entity entity, double deltaTime) {

		Vector2D  position = (Vector2D)  entity.components.get(ComponentType.POSITION);
		Rectangle view     = (Rectangle) entity.components.get(ComponentType.VIEW);
		
		view.setX(position.x);
		view.setY(position.y);
	}
}