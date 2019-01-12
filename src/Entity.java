import java.util.Map;
import java.util.HashMap;

import java.util.Set;
import java.util.HashSet;

public abstract class Entity {

	public static long GLOBAL_ID = 0;
	
	public final long ID;
	public final Set<EntityType> entityTypes;
	public final Map<ComponentType, Object> components;
	public final PhysicsEngine engine;

	public Entity(PhysicsEngine engine) {
		this.ID = GLOBAL_ID++;
		this.entityTypes = new HashSet<EntityType>();
		this.components = new HashMap<ComponentType, Object>();
		this.engine = engine;
	}

	public int hashCode() {
		return Long.hashCode(ID);
	}
}