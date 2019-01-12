
public class CollisionSystem extends GameSystem {
	
	private final long WORLD_WIDTH;
	private final long WORLD_HEIGHT;

	public CollisionSystem(long WORLD_WIDTH, long WORLD_HEIGHT) {
		super(EntityType.COLLIDABLE);

		this.WORLD_WIDTH  = WORLD_WIDTH;
		this.WORLD_HEIGHT = WORLD_HEIGHT;
	}

	protected void update(Entity entity, double deltaTime) {
		
		Vector2D position = (Vector2D) entity.components.get(ComponentType.POSITION);
		Vector2D velocity = (Vector2D) entity.components.get(ComponentType.VELOCITY);

		if (position.x > WORLD_WIDTH) {
			velocity.x *= -1;
			position.x = WORLD_WIDTH;
		}	

		if (position.x < 0) {
			velocity.x *= -1;
			position.x = 0;
		}

		if (position.y > WORLD_HEIGHT) {
			velocity.y *= -1;
			position.y = WORLD_HEIGHT;
		}

		if (position.y < 0) {
			velocity.y *= -1;
			position.y = 0;
		}

		entity.components.put(ComponentType.POSITION, position);
		entity.components.put(ComponentType.VELOCITY, velocity);
	}
}