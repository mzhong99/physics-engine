
public class GravitySystem extends GameSystem {
	
	private Vector2D acceleration;

	public GravitySystem(long grav) {
		super(ComponentType.MOVABLE);
		this.acceleration = new Vector2D(0, grav);
	}

	private void update(Entity entity) {
		
		Vector2D oldVelocity = entity.components.get(ComponentType.VELOCITY);
		Vector2D newVelocity = Vector2D.add(oldVelocity, acceleration);
		entity.components.put(ComponentType.VELOCITY, newVelocity);
	}
}