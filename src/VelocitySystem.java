
public class VelocitySystem extends GameSystem {
	
	public VelocitySystem() {
		super(EntityType.MOVABLE);
	}

	protected void update(Entity entity, double deltaTime) {
		
		Vector2D oldPosition   = (Vector2D) entity.components.get(ComponentType.POSITION);
		Vector2D velocity      = (Vector2D) entity.components.get(ComponentType.VELOCITY);
		
		Vector2D deltaPosition = Vector2D.scalarMultiply(deltaTime, velocity);
		Vector2D newPosition   = Vector2D.add(oldPosition, deltaPosition);

		entity.components.put(ComponentType.POSITION, newPosition);
	}
}