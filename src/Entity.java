import java.util.Set;
import java.util.HashSet;

public abstract class Entity {

	private final long ID;
	private Set<ComponentKey> componentKeys;

	public Entity(Long id) {
		ID = id;
		componentKeys = new HashSet<ComponentKey>();
	}
}