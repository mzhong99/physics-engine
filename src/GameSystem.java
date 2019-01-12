import java.util.List;
import java.util.Map;

public abstract class GameSystem {
	
	private EntityType typeToTarget;

	public GameSystem(EntityType typeToTarget) {
		this.typeToTarget = typeToTarget;
	}

	public void update(Map<EntityType, List<Entity>> entities, double deltaTime) {
		List<Entity> entitiesToUpdate = entities.get(typeToTarget);
		for (Entity entity : entitiesToUpdate) {
			update(entity, deltaTime);
		}
	}

	protected abstract void update(Entity entity, double deltaTime);
}