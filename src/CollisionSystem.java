
public class CollisionSystem extends GameSystem {
	
	private final long WORLD_WIDTH;
	private final long WORLD_HEIGHT;

	public CollisionSystem(long WORLD_WIDTH, long WORLD_HEIGHT) {
		super(EntityType.COLLIDABLE);

		this.WORLD_WIDTH  = WORLD_WIDTH;
		this.WORLD_HEIGHT = WORLD_HEIGHT;
	}

	private void update(Entity entity) {
		
		Vector2D position = entity.components.get(ComponentType.POSITION);
		
		position.x = position.x > WORLD_WIDTH 
			? WORLD_WIDTH / 2 
			: position.x;
		position.y = position.y > WORLD_WIDTH
			? WORLD_HEIGHT / 2
			: position.y;

		entity.components.put(ComponentType.POSITION, position);
	}
}