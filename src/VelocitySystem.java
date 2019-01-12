
public class VelocitySystem extends GameSystem {
	
	public VelocitySystem() {
		super(EntityType.MOVABLE);
	}

	private void update(Entity entity) {
		
		Vector2D oldPosition = entity.components.get(ComponentType.POSITION);
		Vector2D velocity    = entity.components.get(ComponentType.VELOCITY);
		Vector2D newPosition = Vector2D.add(oldPosition, velocity);
		entity.components.put(ComponentType.POSITION, newPosition);
	}
}