import java.util.Map;
import java.util.HashMap;

import java.util.Set;
import java.util.HashSet;

public abstract class Entity {

	public static long GLOBAL_ID = 0;
	
	public final long ID;
	public final Set<EntityType> entityTypes;
	public final Map<ComponentType, Object> components;

	public Entity() {
		ID = GLOBAL_ID++;
		entityTypes = new HashSet<EntityType>();
		components = new HashMap<ComponentType, Object>();
	}

	public int hashCode() {
		return Long.hashCode(ID);
	}
}