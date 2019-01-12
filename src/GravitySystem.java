
public class GravitySystem extends GameSystem {
	
	private Vector2D acceleration;

	public GravitySystem(long grav) {
		super(EntityType.WEIGHTABLE);
		this.acceleration = new Vector2D(0, grav);
	}

	protected void update(Entity entity, double deltaTime) {
		
		Vector2D oldVelocity   = (Vector2D) entity.components.get(ComponentType.VELOCITY);
		Vector2D deltaVelocity = Vector2D.scalarMultiply(deltaTime, acceleration);
		Vector2D newVelocity   = Vector2D.add(oldVelocity, deltaVelocity);
		entity.components.put(ComponentType.VELOCITY, newVelocity);
	}
}